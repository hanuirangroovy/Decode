import pymysql.cursors
import pymongo
import pandas as pd
import numpy as np
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import random

def crontab():
    conn = pymysql.connect(host='j6c203.p.ssafy.io', port=3306,
                       user='escape', password='escape', db='escape', charset='utf8')
    curs = conn.cursor()
    
    sql = "select genre_preference_id,user_id,gender,age from user"
    curs.execute(sql, ())
    result = curs.fetchall()

    genre_name = ["스릴러", "로맨스", "추리", "SF판타지",
                "모험액션", "코미디", "범죄", "공포", "19금", "감성드라마"]

    for x,y,k,z in result:
        if x == None: continue
        sql = "select * from genre_preference where genre_preference_id = %s"
        curs.execute(sql, (x))

        preference = curs.fetchall()
        max_preperence = max(preference[0][1:])
        genre = genre_name[preference[0][1:].index(max_preperence)]

        CB(y,genre)

        CF(y,genre)

        if k == None or z == None: continue

        CF2(y,genre,k,z)

def cf(genre, reviews, themes):
    # 유저 리뷰 정보
    reviews = pd.json_normalize(reviews)

    # 테마 정보
    theme = pd.DataFrame(list(themes))

    # 테마 유저 리뷰 정보 병합
    user_theme_rating = pd.merge(reviews, theme, on='themeId')

    # 테이블 생성 및 빈칸 0으로 채우기
    theme_user_rating = user_theme_rating.pivot_table(
        'rating', index='themeId', columns='userId')
    theme_user_rating = theme_user_rating.fillna(0)

    # 코사인 유사도 사용
    item_based_collabor = cosine_similarity(theme_user_rating)

    # 테이블로 형태 변경
    item_based_collabor2 = pd.DataFrame(
        data=item_based_collabor, index=theme_user_rating.index, columns=theme_user_rating.index)

    # 함수 생성
    def get_item_based_collabor(title):
        return item_based_collabor2[title].sort_values(ascending=False)[:20]

    #  영화 제목 찾기
    theme_list = theme[theme['theme_genre'] == genre]
    random_number = random.randint(1, len(theme_list)-1)

    # 테마 아이디 리스트로 변환
    data = get_item_based_collabor(random_number)
    new_data = []
    for i in data.index:
        new_data.append(i)
    return new_data

def cb(genre, themes):
    # 테마정보 불러오기
    theme = pd.DataFrame(list(themes))

    # 사용안하는 데이터 제거
    theme = theme[['theme_name', 'theme_genre', 'theme_is_scared', 'theme_level',
                   'theme_review_cnt', 'theme_score', 'theme_time', 'theme_type', 'themeId']]

    # 상위 비율 선택
    m = theme['theme_review_cnt'].quantile(0.01)
    theme_top = theme.loc[theme['theme_review_cnt'] >= m]

    # 평균 리뷰수
    c = theme_top['theme_review_cnt'].mean()

    # 가중치 함수
    def weighted_rating(x, m=m, c=c):
        v = x['theme_review_cnt']
        r = x['theme_score']

        return (v / (v+m) * r) + (m / (m + v) * c)

    # 가중치 적용한 값 생성
    theme_top['score'] = theme_top.apply(weighted_rating, axis=1)

    # 묵음을 (1~3)개로 선택
    count_vector = CountVectorizer(ngram_range=(1, 3))

    # 장르데이터를 백터화
    c_vector_genres = count_vector.fit_transform(theme_top['theme_genre'])

    # 코사인 유사도 사용
    genre_c_sim = cosine_similarity(
        c_vector_genres, c_vector_genres).argsort()[:, ::-1]

    # 추천 함수 생성
    def get_recommend_theme_list(df, theme_title, top=30):
        target_theme_index = df[df['theme_name'] == theme_title].index.values
        sim_index = genre_c_sim[target_theme_index, :top].reshape(-1)
        sim_index = sim_index[:30]
        # print(sim_index,target_theme_index)
        # sim_index = sim_index[sim_index != target_theme_index]
        if len(sim_index) == 1:
            sim_index = np.concatenate(sim_index).tolist()
        result = df.iloc[sim_index].sort_values('score', ascending=False)[:10]
        return result

    #  영화 제목 찾기
    theme_list = theme[theme['theme_genre'] == genre]
    random_number = random.randint(1, len(theme_list)-1)
    name = theme_list.iloc[random_number]['theme_name']

    # 리스트로 변환
    data = get_recommend_theme_list(theme_top, theme_title=name)
    new_data = []
    for r in data.index:
        new_data.append(r+1)
    return new_data

def CB(id, genre):

    conn = pymysql.connect(host='j6c203.p.ssafy.io', port=3306,
                        user='escape', password='escape', db='escape', charset='utf8')
    curs = conn.cursor()
    theme = pymongo.MongoClient("j6c203.p.ssafy.io", 27017).escape.theme

    themes = theme.find()

    genre_name = ["스릴러", "로맨스", "추리", "SF판타지",
                  "모험액션", "코미디", "범죄", "공포", "19금", "감성드라마"]

    if genre not in genre_name:
        return

    new_genre = genre

    if genre == "SF판타지":
        new_genre = "SF/판타지"
    elif genre == "모험액션":
        new_genre = "모험/액션"
    elif genre == "감성드라마":
        new_genre = "감성/드라마"

    results = cb(new_genre, themes)

    # mysql에 데이터 전달
    sql = "select user_id from recommend_genre where user_id=%s"
    curs.execute(sql, (id))

    row = curs.fetchall()

    if(not row):
        sql = "insert into recommend_genre(user_id, genre_one, genre_two, genre_three, genre_four, genre_five, genre_six) values(%s,%s,%s,%s,%s,%s,%s)"
        curs.execute(sql, (int(id), int(results[0]), int(results[1]), int(
            results[2]), int(results[3]), int(results[4]), int(results[5])))
        conn.commit()
    else:
        sql = "update recommend_genre set genre_one=%s, genre_two=%s, genre_three=%s, genre_four=%s, genre_five=%s, genre_six=%s where user_id=%s"
        curs.execute(sql, (int(results[0]), int(results[1]), int(
            results[2]), int(results[3]), int(results[4]), int(results[5]), int(id)))
        conn.commit()

    return 



def CF(id, genre):

    conn = pymysql.connect(host='j6c203.p.ssafy.io', port=3306,
                        user='escape', password='escape', db='escape', charset='utf8')
    curs = conn.cursor()
    theme = pymongo.MongoClient("j6c203.p.ssafy.io", 27017).escape.theme
    review = pymongo.MongoClient("j6c203.p.ssafy.io", 27017).escape.review

    genre_name = ["스릴러", "로맨스", "추리", "SF판타지",
                  "모험액션", "코미디", "범죄", "공포", "19금", "감성드라마"]

    if genre not in genre_name:
        return

    new_genre = genre

    if genre == "SF판타지":
        new_genre = "SF/판타지"
    elif genre == "모험액션":
        new_genre = "모험/액션"
    elif genre == "감성드라마":
        new_genre = "감성/드라마"

    themes = theme.find()
    reviews = review.find()
    results = cf(new_genre, reviews, themes)

    # mysql에 데이터 전달
    sql = "select user_id from recommend_like where user_id=%s"
    curs.execute(sql, (id))

    row = curs.fetchall()

    if(not row):
        sql = "insert into recommend_like(user_id, like_one, like_two, like_three, like_four, like_five, like_six) values(%s,%s,%s,%s,%s,%s,%s)"
        curs.execute(sql, (int(id), int(results[0]), int(results[1]), int(
            results[2]), int(results[3]), int(results[4]), int(results[5])))
        conn.commit()
    else:
        sql = "update recommend_like set like_one=%s, like_two=%s, like_three=%s, like_four=%s, like_five=%s, like_six=%s  where user_id=%s"
        curs.execute(sql, (int(results[0]), int(results[1]), int(
            results[2]), int(results[3]), int(results[4]), int(results[5]), int(id)))
        conn.commit()

    return



def CF2(id, genre, gender, age):
    conn = pymysql.connect(host='j6c203.p.ssafy.io', port=3306,
                        user='escape', password='escape', db='escape', charset='utf8')
    curs = conn.cursor()
    theme = pymongo.MongoClient("j6c203.p.ssafy.io", 27017).escape.theme
    review = pymongo.MongoClient("j6c203.p.ssafy.io", 27017).escape.review

    genre_name = ["스릴러", "로맨스", "추리", "SF판타지",
                  "모험액션", "코미디", "범죄", "공포", "19금", "감성드라마"]

    if genre not in genre_name:
        return

    if gender not in ['남', '여']:
        return

    if age not in [10, 20, 30, 40]:
        return

    new_genre = genre

    if genre == "SF판타지":
        new_genre = "SF/판타지"
    elif genre == "모험액션":
        new_genre = "모험/액션"
    elif genre == "감성드라마":
        new_genre = "감성/드라마"

    # temp_genre = '로맨스'
    themes = theme.find()
    reviews = review.find({"gender": gender, 'age': age})
    results = cf(new_genre, reviews, themes)

    # mysql에 데이터 전달
    sql = "select user_id from recommend_gender_age where user_id=%s"
    curs.execute(sql, (id))

    row = curs.fetchall()

    if(not row):
        sql = "insert into recommend_gender_age(user_id, gender_age_one, gender_age_two, gender_age_three, gender_age_four, gender_age_five, gender_age_six) values(%s,%s,%s,%s,%s,%s,%s)"
        curs.execute(sql, (int(id), int(results[0]), int(results[1]), int(
            results[2]), int(results[3]), int(results[4]), int(results[5])))
        conn.commit()
    else:
        sql = "update recommend_gender_age set gender_age_one=%s, gender_age_two=%s, gender_age_three=%s, gender_age_four=%s, gender_age_five=%s, gender_age_six=%s  where user_id=%s"
        curs.execute(sql, (int(results[0]), int(results[1]), int(
            results[2]), int(results[3]), int(results[4]), int(results[5]), int(id)))
        conn.commit()

    return 

if __name__ == '__main__':

    crontab()

import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity

# 테마정보 불러오기
theme = pd.read_csv("recommend/CF/theme.csv", encoding = 'cp949')

# 사용안하는 데이터 제거
theme = theme[['theme_name', 'theme_genre', 'theme_is_scared', 'theme_level', 'theme_review_cnt', 'theme_score', 'theme_time', 'theme_type']]
# print(theme)

# 상위 비율 선택
m = theme['theme_review_cnt'].quantile(0.01)
theme_top = theme.loc[theme['theme_review_cnt'] >= m]
# print(theme_top)

# 평균 리뷰수
c = theme_top['theme_review_cnt'].mean()
# print(c, m)

# 가중치 함수
def weighted_rating(x, m=m, c=c):
  v = x['theme_review_cnt']
  r = x['theme_score']

  return ( v / (v+m) * r ) + ( m / (m + v) * c)

# 가중치 적용한 값 생성
theme_top['score'] = theme_top.apply(weighted_rating, axis = 1)
# print(theme_top)

# 묵음을 (1~3)개로 선택
count_vector = CountVectorizer(ngram_range=(1, 3))

# 장르데이터를 백터화
c_vector_genres = count_vector.fit_transform(theme_top['theme_genre'])

# 코사인 유사도 사용
genre_c_sim = cosine_similarity(c_vector_genres, c_vector_genres).argsort()[:, ::-1]

# 추천 함수 생성
def get_recommend_theme_list(df, theme_title, top=30):
  target_theme_index = df[df['theme_name'] == theme_title].index.values
  sim_index = genre_c_sim[target_theme_index, :top].reshape(-1)
  sim_index = sim_index[sim_index != target_theme_index]
  result = df.iloc[sim_index].sort_values('score', ascending=False)[:10]

  return result

# 결과출력
print(get_recommend_theme_list(theme_top, theme_title='재개발구역: 관계자 외 출입금지'))
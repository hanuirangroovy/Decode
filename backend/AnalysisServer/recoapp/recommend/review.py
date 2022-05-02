import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
import random


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
    themeId_list = []
    for i in theme_list['themeId']:
        themeId_list.append(i)

    # 테마 아이디 리스트로 변환
    data = get_item_based_collabor(themeId_list[random_number])
    new_data = []
    for i in data.index:
        new_data.append(i)
    return new_data

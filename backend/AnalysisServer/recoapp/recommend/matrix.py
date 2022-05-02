import pandas as pd
import numpy as np
from scipy.sparse.linalg import svds


def svd(id, reviews, themes):
    # 유저 리뷰 정보
    reviews = pd.json_normalize(reviews)

    # 테마 정보
    theme = pd.DataFrame(list(themes))

    # 테마 유저 리뷰 정보 병합
    user_theme_rating = pd.merge(reviews, theme, on='themeId')

    # 테이블 생성 및 빈칸 0으로 채우기 - 유저 기준으로
    theme_user_rating = user_theme_rating.pivot_table(
        'rating', index='userId', columns='themeId')
    theme_user_rating = theme_user_rating.fillna(0)

    # 매트릭스로 변환
    matrix = theme_user_rating.to_numpy()
    user_ratings_mean = np.mean(matrix, axis=1)
    matrix_user_mean = matrix - user_ratings_mean.reshape(-1, 1)

    # svd
    u, sigma, vt = svds(matrix_user_mean, k=12)
    sigma = np.diag(sigma)
    svd_user_predicted_ratings = np.dot(
        np.dot(u, sigma), vt) + user_ratings_mean.reshape(-1, 1)
    df_svd_preds = pd.DataFrame(
        svd_user_predicted_ratings, index=theme_user_rating.index, columns=theme_user_rating.columns)

    # 추천 함수
    def recommend_theme(df_svd_preds, user_id, ori_theme_df, ori_rating_df, num_recommendation=5):
        user_row_number = user_id
        sorted_user_predictions = df_svd_preds.loc[user_row_number].sort_values(
            ascending=False)
        user_data = ori_rating_df[ori_rating_df.userId == user_id]
        user_history = user_data.merge(ori_theme_df, on='themeId').sort_values([
            'rating'], ascending=False)
        recommendations = ori_theme_df[~ori_theme_df['themeId'].isin(
            user_history['themeId'])]
        recommendations = recommendations.merge(pd.DataFrame(
            sorted_user_predictions).reset_index(), on='themeId')
        recommendations = recommendations.rename(
            columns={user_row_number: 'Predictions'}).sort_values('Predictions', ascending=False)[:num_recommendation]

        return user_history, recommendations

    # 작성된 평점, 예측 찾기
    already_rated, predictions = recommend_theme(
        df_svd_preds, id, theme, reviews, 6)

    # print(already_rated['theme_name'])
    # print(predictions)

    # 결과 출력
    new_data = []
    for i in predictions['themeId'][:6]:
        new_data.append(i)

    return new_data

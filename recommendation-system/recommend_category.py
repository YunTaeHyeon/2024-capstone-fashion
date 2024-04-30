#!/usr/bin/env python
# coding: utf-8

# In[15]:


import numpy as np
import joblib
import sys

# 추천 함수 정의
def category_recommend(user_data):
    # 저장된 모델 로드
    knn_category_model = joblib.load('knn_category_model.pkl')

    # 입력 데이터를 이용하여 가장 유사한 이웃들을 찾음
    distances, indices = knn_category_model.kneighbors([user_data])
    
    return indices
    
if __name__ == "__main__":
    # 스크립트를 실행할 때 전달된 인자 가져오기
    # python3 recommend_category.py 1.0 1.0 0.0 0.0 0.0 이런식으로   
        
    user_data = [float(pref) for pref in sys.argv[2:]]
    #['recommend_category.py', '1.0', '1.0', '0.0', '0.0', '0.0']인데 1.0은 id니까 이후 점수부터
    
    # 추천 함수 호출
    similar_people = category_recommend(user_data)
    
    print(sys.argv[1])

#이후 스프링에서 similar_people을 검색 -> 이 사람들의 행적을 분석(같은 카테고리의 취향을 가졌다면 비슷한 게시물을 볼 것이기 떄문)
# 이 추천으로도 보여주고 롱테일 문제를 해결하기 위해 일부 게시물은 랜덤으로 보여줄 수도 있음


# In[ ]:





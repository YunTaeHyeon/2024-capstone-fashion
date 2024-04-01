#!/usr/bin/env python
# coding: utf-8

# In[1]:


import numpy as np
from sklearn.neighbors import NearestNeighbors
import joblib

# 데이터 준비
# 데이터는 12개의 카테고리에 대한 사용자의 선호도를 서버에서 받아와서 준비
user_item_matrix = np.array([
    [1, 0, 1, 0, 0],
    [0, 1, 1, 0, 0],
    [1, 1, 0, 1, 0],
    [0, 0, 0, 1, 1],
    [1, 1, 0, 0, 1]
])

# 모델 학습
k = 3 #비슷한 사람을 몇명 추출할 것인가
knn_model = NearestNeighbors(n_neighbors=k, metric='cosine')
knn_model.fit(user_item_matrix)

# 모델 저장
joblib.dump(knn_model, 'knn_category_model.pkl')

print('Category recommendation model is successfully trained and saved.')


# In[ ]:





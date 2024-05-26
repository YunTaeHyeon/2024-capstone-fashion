#!/usr/bin/env python
# coding: utf-8

# In[2]:


import pandas as pd
import numpy as np
from sklearn.neighbors import NearestNeighbors
import joblib
import os
import re
from datetime import datetime

# 데이터 디렉토리
data_dir = '/'

# 파일 이름에서 날짜 추출
def extract_date(filename):
    date_pattern = r"user_category_scores_(\d{4}-\d{2}-\d{2}).csv"
    match = re.search(date_pattern, filename)
    if match:
        return datetime.strptime(match.group(1), "%Y-%m-%d")
    else:
        return None

# 가장 최신 파일 선택
def find_latest_file(directory):
    files = os.listdir(directory)
    csv_files = [file for file in files if file.endswith(".csv")]
    dated_files = [(file, extract_date(file)) for file in csv_files if extract_date(file)]
    latest_file = max(dated_files, key=lambda x: x[1])[0] if dated_files else None
    return latest_file

# 가장 최신의 파일 찾기
latest_file = find_latest_file(data_dir)

if latest_file:
    print("가장 최신의 파일:", latest_file)
else:
    print("CSV 파일이 존재하지 않습니다.")

# CSV 파일 로드하여 numpy 배열로 변환 (첫 번째 열 제외 Id니까)
user_item_matrix = pd.read_csv(os.path.join(data_dir, latest_file)).iloc[:, 1:].values

# 모델 학습
k = 1 #비슷한 사람을 몇명 추출할 것인가
knn_model = NearestNeighbors(n_neighbors=k, metric='cosine')
knn_model.fit(user_item_matrix)

# 모델 저장
joblib.dump(knn_model, 'knn_category_model.pkl')


# In[ ]:





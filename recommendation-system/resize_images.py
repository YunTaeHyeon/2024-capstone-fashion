from PIL import Image
import os

def resize_images_in_directory(directory, target_size=(640, 640)):
    # 디렉토리 내의 모든 파일에 대해 반복
    for filename in os.listdir(directory):
        if filename.endswith(".jpg") or filename.endswith(".jpeg"):
            filepath = os.path.join(directory, filename)
            try:
                # 이미지 열기
                img = Image.open(filepath)
                # 이미지 크기 조정
                img_resized = img.resize(target_size, Image.ANTIALIAS)
                # 원본 파일 덮어쓰기
                img_resized.save(filepath)
                print(f"{filename} resized successfully.")
            except Exception as e:
                print(f"Error resizing {filename}: {e}")

# 디렉토리 경로 설정
directory_path = "/Users/taehyeonyun/coding/2024-capstone-fashion/recommendation-system/init_recommend_images"
# 이미지 크기 조정
resize_images_in_directory(directory_path)

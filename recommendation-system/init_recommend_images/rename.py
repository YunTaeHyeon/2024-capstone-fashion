import os

def remove_spaces_in_filenames(directory):
    # 현재 디렉토리에서 파일 목록을 가져옴
    files = os.listdir(directory)
    print(files)
    
    # 파일 목록을 순회하면서 공백을 제거하고 파일 이름을 변경
    for filename in files:
        # 파일 이름에 공백이 있는 경우에만 처리
        if ' ' in filename:
            # 파일의 경로를 지정
            file_path = os.path.join(directory, filename)
            # 새로운 파일 이름 생성 (공백 제거)
            new_filename = filename.replace(' ', '')
            # 파일 이름 변경
            os.rename(file_path, os.path.join(directory, new_filename))
            print(f"Renamed '{filename}' to '{new_filename}'")

# 현재 디렉토리 경로 사용
current_directory = os.getcwd()

# 함수 호출하여 공백 제거 작업 수행
remove_spaces_in_filenames(current_directory)

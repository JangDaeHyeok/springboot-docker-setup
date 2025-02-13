# OpenJDK 17 기반 이미지 사용
FROM gradle:8.6-jdk17

# 작업 디렉토리 설정
WORKDIR /app

# Jar 파일 복사 (빌드 후 생긴 JAR 파일을 컨테이너 내부로 이동)
COPY . /app

RUN gradle --warning-mode all --console=plain

CMD ["sh", "start.sh"]
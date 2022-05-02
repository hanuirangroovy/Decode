FROM adoptopenjdk/openjdk11
EXPOSE 8761

# 가상의 디렉토리
VOLUME /tmp
# 앞의 파일 디렉토리를 뒤에 복사
COPY build/libs/EurekaServer-0.0.1-SNAPSHOT.jar EurekaServer.jar

# 어떠한 명령어를 가지고 도커를 실행한건지 실행 커맨드
ENTRYPOINT ["java","-jar","EurekaServer.jar"]
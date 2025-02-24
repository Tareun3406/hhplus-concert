FROM openjdk:21
ARG JAR_FILE=build/libs/hhplus-concert-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ./hhplus-concert.jar
EXPOSE 8080/tcp
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./hhplus-concert.jar"]
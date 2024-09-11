FROM amazoncorretto:26-alpine

COPY build/libs/bswe.jar /app.jar

EXPOSE 8080/tcp

CMD ["java", "-jar", "/app.jar"]

FROM eclipse-temurin:17-alpine
COPY ./build/libs/s3-service.jar app.jar
RUN apk update && apk upgrade && apk add curl && rm -rf /var/cache/apk/*
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
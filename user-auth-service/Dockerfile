FROM eclipse-temurin:17-alpine
COPY ./build/libs/user-auth-service.jar app.jar
RUN apk update && apk upgrade && apk add curl && rm -rf /var/cache/apk/*
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
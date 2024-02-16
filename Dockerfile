FROM gradle:7.0 AS build
COPY --chown=gradle:gradle . /home/gradle
RUN gradle bootJar --no-build-cache --no-daemon

FROM openjdk:8

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/build/libs/*.jar /app/cinema-gateway.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENV DEFAULT_SCHEMA=${1:+1}
ENV DATABASE_URL=${1:+1}
ENV DATABASE_USERNAME=${1:+1}
ENV DATABASE_PASSWORD=${1:+1}

CMD ["java", "-jar", "/app/cinema-gateway.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
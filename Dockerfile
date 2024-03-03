#build
FROM gradle:8.0-jdk17-alpine as builder
COPY --chown=gradle:gradle . /home/app
WORKDIR /home/app
RUN gradle build --stacktrace -x test

#start
FROM openjdk:17-alpine
COPY --from=builder /home/app/build/libs/VoluTrackBack-0.0.1-SNAPSHOT.jar /home/application.jar
EXPOSE ${CONTAINER_PORT}
ENTRYPOINT ["java", "-jar", "/home/application.jar"]

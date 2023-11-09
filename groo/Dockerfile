FROM openjdk:17
VOLUME /tmp
COPY ./build/libs/groo-0.0.1-SNAPSHOT.jar GrooService.jar
ENTRYPOINT ["java", "-jar", "GrooService.jar"]
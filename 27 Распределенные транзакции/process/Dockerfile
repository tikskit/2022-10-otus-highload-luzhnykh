FROM amazoncorretto:21-alpine3.17-jdk
COPY /target/resources-27.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dcom.sun.management.jmxremote.local.only=false", "-Dspring.jmx.enabled=true", "-Djava.rmi.server.hostname=localhost", "-Dcom.sun.management.jmxremote.rmi.port=1234", "-Dcom.sun.management.jmxremote.port=1234", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false", "-jar", "/app/app.jar"]
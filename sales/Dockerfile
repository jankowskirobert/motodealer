FROM openjdk:8-jre
ADD ./target/sales-1.0-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/sales-1.0-SNAPSHOT.jar"]
EXPOSE 7000
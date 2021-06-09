FROM openjdk:11
COPY ./target/Icom-registry-0.0.1-SNAPSHOT.jar /usr/src/icom-registry/
WORKDIR /usr/src/icom-registry
EXPOSE 8761
CMD ["java", "-jar", "Icom-registry-0.0.1-SNAPSHOT.jar"]

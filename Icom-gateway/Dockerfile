FROM openjdk:11
COPY ./target/Icom-gateway-0.0.1-SNAPSHOT.jar /usr/src/icom-gateway/
WORKDIR /usr/src/icom-gateway
EXPOSE 9000
CMD ["java", "-jar", "Icom-gateway-0.0.1-SNAPSHOT.jar"]

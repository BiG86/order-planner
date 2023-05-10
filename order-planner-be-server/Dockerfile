FROM eclipse-temurin:17-jre-alpine

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/order-manager-be-server.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/usr/order-manager-be-server.jar"]

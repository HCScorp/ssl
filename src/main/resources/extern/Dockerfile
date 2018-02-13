FROM openjdk:jre-alpine

#COPY --from=builder /root/target/generated-ssl-jar-with-dependencies.jar ./app/generated-ssl.jar
COPY ./target/generated-ssl-jar-with-dependencies.jar ./app/generated-ssl.jar

CMD ["java", "-jar", "./app/generated-ssl.jar"]
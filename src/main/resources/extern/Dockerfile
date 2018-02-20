FROM openjdk:jre-alpine

#COPY --from=builder /root/target/generated-ssl-jar-with-dependencies.jar ./app/generated-ssl.jar
COPY ./target/generated-ssl-jar-with-dependencies.jar ./app/generated-ssl.jar

ENV SSL_INFLUX_ADDRESS "http://62.210.181.35:5057"
ENV SSL_INFLUX_USER "admin"
ENV SSL_INFLUX_PWD ""

CMD ["java", "-jar", "./app/generated-ssl.jar"]
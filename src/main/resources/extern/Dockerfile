FROM openjdk:jre-alpine

#COPY --from=builder /root/target/generated-ssl-jar-with-dependencies.jar ./app/generated-ssl.jar
COPY ./target/generated-ssl-jar-with-dependencies.jar ./app/generated-ssl.jar

ENV SSL_INFLUXDB_ADDRESS "http://localhost:8086"
ENV SSL_INFLUXDB_USER "admin"
ENV SSL_INFLUXDB_PWD ""
ENV SSL_INFLUXDB_DB "ssl"

CMD ["java", "-jar", "./app/generated-ssl.jar"]
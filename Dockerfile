
FROM openjdk:8-jdk-alpine
VOLUME ["/tmp","/data/serv_contabilidad"]
ADD target/sandbox-microfocus-receiverMQ.jar app.jar
ENTRYPOINT java -jar app.jar

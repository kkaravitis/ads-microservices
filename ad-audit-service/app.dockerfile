FROM openjdk:8-jdk-slim
VOLUME /tmp
COPY target/ad-audit-service-1.0.0.jar ad-audit-service.jar
ENTRYPOINT ["java", "-XX:+PrintFlagsFinal", "-Xmx100m", "-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=n", "-jar", "ad-audit-service.jar"]


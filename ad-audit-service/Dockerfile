FROM adoptopenjdk/openjdk11-openj9:latest
VOLUME /tmp
COPY target/ad-audit-service-1.0.0.jar ad-audit-service.jar
ENTRYPOINT ["java", "-XX:+PrintFlagsFinal", "-Xmx100m", "-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n", "-jar", "ad-audit-service.jar"]


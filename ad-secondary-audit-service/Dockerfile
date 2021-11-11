FROM adoptopenjdk/openjdk11-openj9:latest
VOLUME /tmp
COPY target/ad-secondary-audit-service-1.0.0.jar ad-secondary-audit-service.jar
ENTRYPOINT ["java", "-XX:+PrintFlagsFinal", "-Xmx100m", "-jar", "ad-secondary-audit-service.jar"]


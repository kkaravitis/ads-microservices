FROM openjdk:8-jdk-slim
VOLUME /tmp
COPY target/ad-search-service-1.0.0.jar ad-search-service.jar
ENTRYPOINT ["java", "-XX:+PrintFlagsFinal", "-Xmx100m", "-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n",
"-jar", "ad-search-service.jar"]


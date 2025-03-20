FROM eclipse-temurin:21-jdk
VOLUME /tmp
COPY target/*.jar app.jar
ENV EUREKA_URL=null
ENTRYPOINT ["java","-jar","/app.jar"]
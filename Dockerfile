FROM openjdk:11
EXPOSE 8081
ADD target/spring_01-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]
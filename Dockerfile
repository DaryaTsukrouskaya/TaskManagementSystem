FROM openjdk:17
ARG JAR_FILE
COPY target/TaskManagementSystem-0.0.1-SNAPSHOT.jar /TaskManagementSystem-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","TaskManagementSystem-0.0.1-SNAPSHOT.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
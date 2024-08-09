FROM openjdk:17

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} backup-job.jar

ENTRYPOINT ["java", "-jar", "backup-job.jar"]

EXPOSE 8080

FROM openjdk:17-oraclelinux7
COPY rest/target/*-spring-boot.jar rest.jar
ENTRYPOINT ["java","-jar","rest.jar"]
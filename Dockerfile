From openjdk:11
copy ./target/dms-auth-server-0.0.1-SNAPSHOT.jar dms-auth-server-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","dms-auth-server-0.0.1-SNAPSHOT.jar"]
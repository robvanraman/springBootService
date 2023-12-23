FROM openjdk:20
WORKDIR /app
COPY target/isolate/demo-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["D:\Program Files\.jdks\openjdk-20\bin\java","-jar","target/isolate/demo-0.0.1-SNAPSHOT.jar"]
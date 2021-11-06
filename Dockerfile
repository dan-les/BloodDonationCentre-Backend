FROM maven:3.8.3-jdk-11
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/BloodDonationCentre-0.0.5-SNAPSHOT.jar"]


#  --- LOCAL ---
# 1. docker build -f Dockerfile -t spring-bdc:v5 .
# 2. docker images
# 3. docker save -o d:/spring-bdc-v5.tar spring-bdc:v5
#  --- VPN ---
# 3. docker load -i ./spring-bdc-v5.tar
# 5. docker run -itd -p 20103:8080 spring-bdc:v5
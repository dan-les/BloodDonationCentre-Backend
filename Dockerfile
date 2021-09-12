FROM openjdk:11.0-jre-slim
ADD target/BloodDonationCentre-0.0.4-SNAPSHOT.jar .
EXPOSE 20103
CMD java -jar BloodDonationCentre-0.0.4-SNAPSHOT.jar


# 1. mvn clean compile package
#     to generate .jar file
# 2. docker build -f Dockerfile -t spring-bdc:v4 .
# 3. docker images
# 4. docker run -p 20103:8080 95c
#     95c - first 3 chars of IMAGE ID
#     eq.
#     REPOSITORY   TAG         IMAGE ID        CREATED         SIZE
#     bcd-f        v1          95cfa2254944    4 hours ago     625MB

# docker run -itd -p 20103:8080 bdc:v4

# docker save -o d:/spring-bdc-v4.tar spring-bdc:v4

# docker load -i d:/image.tar
FROM openjdk:11.0-jre-slim
ADD target/BloodDonationCentre-0.0.5-SNAPSHOT.jar .
EXPOSE 20103
CMD java -jar BloodDonationCentre-0.0.5-SNAPSHOT.jar

#  --- LOCAL ---
# 1. mvn clean compile package
#    [to generate .jar file]
# 2. docker build -f Dockerfile -t spring-bdc:v5 .
# 3. docker images
# 4. docker save -o d:/spring-bdc-v5.tar spring-bdc:v5
#  --- VPN ---
# 5. docker load -i ./spring-bdc-v5.tar
# 6. docker run -itd -p 20103:8080 spring-bdc:v5


# ---------------------------------------------------------------------
#     docker run -p 20103:8080 95c
#     95c - first 3 chars of IMAGE ID
#     eq.
#     REPOSITORY   TAG         IMAGE ID        CREATED         SIZE
#     bcd-f        v1          95cfa2254944    4 hours ago     625MB
#
#     docker load -i d:/image.tar
# ---------------------------------------------------------------------
FROM openjdk:11
ADD target/BloodDonationCentre-0.0.1-SNAPSHOT.jar .
EXPOSE 20103
CMD java -jar BloodDonationCentre-0.0.1-SNAPSHOT.jar


#1. mvn clean compile package
#    to generate .jar file
#2. docker build -f Dockerfile -t bdc:v1 .
#3. docker images
#4. docker run -p 20103:8080 95c
#    95c - first 3 chars of IMAGE ID
#    eq.
#    REPOSITORY   TAG         IMAGE ID        CREATED         SIZE
#    bcd-f        v1          95cfa2254944    4 hours ago     625MB

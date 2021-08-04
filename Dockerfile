FROM openjdk:11
ADD target/BloodDonationCentre-0.0.1-SNAPSHOT.jar .
EXPOSE 20103
CMD java -jar BloodDonationCentre-0.0.1-SNAPSHOT.jar
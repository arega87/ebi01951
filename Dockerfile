FROM openjdk:11
VOLUME /tmp
ADD target/ebi01951.jar ebi01951.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","ebi01951.jar"]

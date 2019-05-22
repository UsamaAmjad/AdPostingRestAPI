FROM java:8-jdk-alpine
MAINTAINER creativeblock94@gmail.com
COPY ./target/AdListingProject-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch AdListingProject-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","AdListingProject-0.0.1-SNAPSHOT.jar"] 
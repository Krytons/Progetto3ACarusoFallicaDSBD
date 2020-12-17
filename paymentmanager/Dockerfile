FROM maven:3-openjdk-15 as builder
WORKDIR /project
COPY entities/ entities
WORKDIR /project/entities
RUN mvn install
WORKDIR /project
COPY paymentmanager .
#ADD ./target/paymentmanager-0.0.1-SNAPSHOT.jar ./paymentmanager.jar
RUN mvn package

FROM java:15-alpine
WORKDIR /app
COPY --from=builder /project/target/paymentmanager-0.0.1-SNAPSHOT.jar ./paymentmanager.jar
#ENTRYPOINT ["/bin/sh", "-c"]
CMD java -jar paymentmanager.jar
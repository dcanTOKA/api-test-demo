# Building the image
# 	docker build -t gauge-java .
# Running the image
# 	docker run  --rm -it -v ${PWD}/reports:/gauge/reports gauge-java

# This image uses the official openjdk base image.
FROM openjdk:8
ADD target/api-test-demo.jar api-test-demo.jar
ENTRYPOINT ["java", "-jar","api-test-demo.jar"]
EXPOSE 8080

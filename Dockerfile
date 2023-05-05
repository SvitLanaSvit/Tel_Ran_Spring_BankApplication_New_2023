## Use an official OpenJDK 18 runtime as a parent image
#FROM openjdk:17-jdk-alpine
#
## Set the working directory to /app
#WORKDIR /app
#
## Copy the current directory contents into the container at /app
#COPY . /app
#
## Expose port 8080 for the application
#EXPOSE 8080
#
## Set environment variables for MySQL connection
#ENV MYSQL_HOST=db \
#    MYSQL_PORT=3306 \
#    MYSQL_DATABASE=bank \
#    MYSQL_USER=bestuser \
#    MYSQL_PASSWORD=bestuser
#
## Start the MySQL container
#RUN docker run -d --name db -e MYSQL_ROOT_PASSWORD=$MYSQL_PASSWORD mysql
#
## Run the application
#CMD ["java", "-jar", "BankApplication.jar"]

FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/BankApplication-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

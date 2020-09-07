## Run the code
You need to have istalled the following tools
- mvn 3.6.0 (https://maven.apache.org/download.cgi)
- Docker (https://www.docker.com/)

Once you have the above tools installed open a terminal from inside the ad-search-statistics folder
and run the following commands:

``mvn clean install``

``docker-compose up -d``

The above docker command will download the following images and start their containers:

- Zookeeper at port 2181
- Apache Kafka at port 9092
- Mongo db at port 27017
- Mongo express at port 8081

Also docker will build the microservices images and run their containers.
- ad-search-service microservice (The search restful service) at port 8080
- ad-audit-service microservice at port 8082

Also make sure that the ports 4010 and 4011 are available on your machine.
These ports are used for java remote debugging of the ad-search-service and ad-audit-service microservices.

## Rest API     
The documentation of the REST API can be found in its swagger page
http://localhost:8080/swagger-ui.html
where you can try and play with the api.






 
 

 
 










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
The documentation of the REST API of the solution can be found in its swagger page
http://localhost:8080/swagger-ui.html
where you can try and play with the api.


## Architecture definition

### Description
<p>When a user performs a request to the search service api, the service creates and sends kafka messages asynchronous which contains the statistics information need to be stored.</p>

<p>In parallel the Audit service consumes the messages come from kafka bus, converts these messages to statistics objects and stores these objects to the corresponded mongo db collection.</p>

<p>With the above architecture our system can scale easily and can handle billions of statistics information objects.</p>
<p>For example we could run more than one instances of audit service application on multiple machines for handling faster the big number of kafka messages.</p>
<p>Also the mongo db can scale easily and it is recommended for big data storage.</p>
<p>Last but not least, the ad search service does not delay the response to the user because it creates and produces the kafka messages asynchronous 
as you can see in the source code of AuditService class </p> 


## Example Usage

Start the whole system with the docker command
``docker-compose up -d``

With the command 
``docker ps``

you can see if the containers have started up.

After the above browse to the swagger page of ad search service api http://localhost:8080/swagger-ui.html#/search-controller


And try out the GET /kkaravitis/ads
Fill the parameters as you like and click on execute, for example look at the following pics


After you executed the above open a new browser tab and browse to http://0.0.0.0:8081, the mongo express admin console.
There you will see a database named stats


Click on the stats database icon and you will redirected to the stats db collections

Here you can see the two collections filled with data when you performed the search.
The AdPageResults and the adSearchResults collections.

Finally you can click on a collection and look at its records.

 



 
 

 
 










# spring-cloud-config-server-mongodb-backend

Spring Cloud Config server and clients with a MongoDB backend.

If the server and clients are deployed to kubernetes, when the Spring Cloud Config server receives a request to update a setting, it will use Kubernetes DNS (using spring-cloud-kubernetes libraries) to notify all the instances to refresh settings by making a REST call to /refresh on all the instances. 

If the server and clients are deployed via docker compose, the Spring Cloud Config server uses an environment variable to get the URI of the module that needs to be refreshed.


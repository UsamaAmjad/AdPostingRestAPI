# Car Ad Listing Backend REST API

 **Author:** Osama Amjad 
 
 A simple Ad Listing API demostration built using `Spring Boot 2`, `Spring MVC`, `Spring Data JPA`, and `Docker`.
 
## Setup Notes

**Run Project**
```sh
mvn spring-boot:run
``` 
**Run Tests**
```sh
mvn clean test
```

 Below are the APIs that are implemented in this project. Postman API collection is included please import it for testing.
```sh
 METHOD  URI 
 ------  ------ 
 GET    /search							- Returns all listing order by posting time. Optional request params `make`, `model`, `year`, `color`
 GET	/vehicle_listings/{listing_id}	- Get AdListing by `ID`
 POST   /vehicle_listings       		- Add new listing or update exisiting if there is same `Code` from same `Dealer`
 PUT    /vehicle_listings   			- Update an exisiting AdListing
 POST 	/upload_csv/{dealer_id} 		- Upload AdListing from `CSV` file 
```
 
## Docker Setup
Docker file is also added in the project. Run the below commands in terminal to deploy project inside the docker container.
 ```sh
 mvn clean install
 docker build -t ad-lisitng .
 docker run -p 8090:8080 ad-lisitng
 ```
 
## Considerations
- I have used `H2` database to keep it simple
- Spring security is not used because it was out of scope for this project
- `Authentication` and `authorization` is not covered, hence anyone can add/update records. In Prod users should only allowed to update/delete their own records
- Basic file reading, in Production I would prefer proper csv schema genration from Headers for better validation
- I just loop through the `CSV` records to save them in DB. Batch `inserts` would be preferable in prod
- I prefer separate `DTOs` for complex (where differnet fields are required for both) json request and response but it wasnt required for current models
- I have added simple search but in prod it should be Elasticsearch/Apache Solr for fast searching

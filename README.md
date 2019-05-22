# Car Ad Listing Backend API

 **Author:** Osama Amjad 
 
 A simple Ad Listing API demostration built using `Spring Boot 2`, `Spring MVC`, `Spring Data JPA`, and `Spring Security`.
 
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
 GET    /search					- Optional request params make, model, year, color
 POST   /vehicle_listings       - Requires authentication
 PUT    /vehicle_listings   	- Requires authentication
 POST 	/upload_csv/{dealer_id} - Requires authentication
```
 #### Test User Details
 Two in-memory users created for testing along with some data. Add username and password in REST call with basic Authorization header:
 
 **User 1**
 Username: alex
 Pass: alex
 
  **User 2**
 Username: mike
 Pass: mike
 
## Docker Setup
Docker file is also added in the project. Run the below commands in terminal to deploy project inside the docker container.
 ```sh
 mvn clean install
 docker build -t ad-lisitng .
 docker run -p 8090:8080 ad-lisitng
 ```
 
## Considerations
	- I have used H2 database to keep it simple
	- Basic authentication is used to protect secured resources.
	- Basic file reading, I would prefer proper csv schema genration from Headers for better validation
	- I prefer separate DTOs for complex (where differnet fields are required for both) json request and response but it wasnt required for the sake of this project

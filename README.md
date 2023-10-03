## Design choices

### 1. Database/Model
We have these entities (model): user, vote, voter, question, poll, IoIDevice. 
We establish relations between them using JPA annotations. 

We are using JPA (Java Persistence API) with Hibernate as the provider. 
This choice abstracts the database operations, allows object-relational mapping, and simplifies database access.

Lombok (@Setter, @Getter) is being used to reduce boilerplate code. 
It automatically generates setters, getters, and other utility methods.

### 2. REST API
We create controllers that follow the RESTful principles with clear 
HTTP verb mappings (@GetMapping, @PostMapping, @PutMapping, @DeleteMapping).

The use of ResponseEntity allows for better HTTP response handling, offering flexibility in returning different HTTP status codes and headers.

The URL paths are clearly structured, making it easy to deduce the API's functionality from the endpoint routes.

We have a service layer to abstract the business logic and repository calls from the controller. 
This promotes the Single Responsibility Principle and makes the codebase more modular and maintainable.
The service layer interacts with the repository to perform CRUD operations.

### 3. Repository layer
We are utilizing Spring Data JPA repositories (UserRepository, PollRepository, etc.) which offer CRUD operations out-of-the-box, 
minimizing the need to write custom SQL queries.
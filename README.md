# Getting Started

###  Installation guide
Only a single step is required to start the application, just running the `sh start.sh` file located in the root folder, will automatically trigger the following actions: 
1- Existing containers (if existant) will be stopped, 
2- The database container will start,
3- Installation and packaging of the application .jar executable,
4- Creation of the application container image will start with the latest artifact,

It is imporant to note that, before starting to use the application, the user has to wait for about ~20 seconds, as the application container will restart until the database container is ready to serve. 

### Using the application

To access the API two options are possible, either via the default swagger interface or Postman.

#### Swagger

Go to the following URL: http://localhost:8080/swagger-ui/
- in the `mouse-gene-controller` two GET methods are available: 
- `/api/v1/search/mousegene` this method is used to get a single mouse gene by the mouse gene 'symbol', an 'identifier', or a 'synonym'.
- `/api/v1/mousetohumangenerelation` this method is used to get a list of a mouse gene mapped to multiple human genes fetched by a Mouse gene 'symbol' or 'identifier'.

#### Postman

You can directly copy this URL into a new GET method (query parameters are optional and therefore can also be omitted): 
- example1 : http://localhost:8080/api/v1/search/mousegene?symbol=test&identifier=MGI:99604&synonym=Aigf
- example2 : http://localhost:8080/api/v1/search/mousegene?synonym=Aigf

and same thing for the second GET method

- example1 : http://localhost:8080/api/v1//mousetohumangenerelation?symbol=Aoc2&identifier=MGI:99604
- example2 : http://localhost:8080/api/v1//mousetohumangenerelation?symbol=Aoc2

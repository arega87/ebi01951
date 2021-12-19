# Getting Started

###  Installation guide
Run the `sh start.sh` file located in the root folder. 
- This will automatically stop existing containers (if existant) and start the database container.
- Next starts the installation and packaging of the application .jar executable.
- Finally the application container creation will start, a new image 
will be created with the latest .jar file. (it is therefore important to leave the --build flag as is)
- Before starting to use the application, wait for ~20 seconds, as the application container will restart until the database container is ready. 

### Using the application

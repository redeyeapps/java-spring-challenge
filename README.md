### Compiling ###
Install maven and run:
`mvn clean compile`

### Execution ###
To run the project through maven:
`mvn exec:java`

### Testing ###
By default, the application will be running on port 8080.

This application does use a H2 SQL database using the persistance API. However, the application is still configured to
remake the database upon start up so user accounts and to do lists will not be persisted between runs.

The included file `Postman_test_suite.json` can be imported into the Postman extension for Chrome to be run as an integration test suite.

Note: Most of the intermittent commits will not compile due to a file rename not being committed by IntelliJ. Manually
renaming 'ToDoListService.java' to 'TodoListService.java' will fix this.
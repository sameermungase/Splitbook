# SplitBook

 SplitBook is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). It can help users to track, manage and calculate expenses among other users (called members). User need to add an event (called Trip) and keep adding their expenses (called contributions) and SplitBook will calculate how much to be owed or how much to be paid in real-time.

 ![image](https://github.com/duarauddipon/SplitBook/assets/77953953/e3384b9c-0ad4-410c-b292-a53c13291162)

 ## Run SplitBook locally

### Prerequisites

The following items should be installed in your system:

- Java 17 or newer
- Your preferred IDE
- Apache Maven (3.9.6 or later)

The repo can be cloned to your local and can be ran directly using any IDE like Eclipse, IntelliJ Idea. Tomcat server dependencies have already been added in the pom.xml so that it can be ran directly from the IDE or else the project can be built and the war file can be deployed in your standalone Tomcat server.

Building the project

```bash
mvn clean install
```

 ### Database configuration

 In its default configuration, SplitBook uses MS SQL Server as the database. The dependency is present in the pom.xml. Please create a new database in your MS SQL server and edit the configurations in application.properties. By default, the hibernate.ddl-auto property is set to 'update', so Hibernate will automatically create the required tables while running the application for the first time.

 ### Run

 After running the application, visit http://localhost:8081 in your browser.

# Budget

## Project description

**Budget** is a web application for maintaining budget statistics

## Environment requirements
* Project SDK: [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* Project Builder: [Apache Maven 3.8.6](https://maven.apache.org/download.cgi)

## How to run application (for development)

* ```mvn clean install```
* ```cd rest```
* ```mvn spring-boot:start```

## How work with database
We use Liquibase to handle all database schema changes. All changesets are stored in ```release-db``` module. 

### Database initlization

* ```mvn liquibase:update -pl release-db -Dliquibase.changeLogFile=db_changelog_master.xml -Dliquibase.url=jdbc:postgresql://localhost:5432/[schema] -Dliquibase.username=[username] -Dliquibase.password=[password]```

### Rollback changes
* ```mvn liquibase:rollback -pl release-db -Dliquibase.changeLogFile=db_changelog_master.xml -Dliquibase.rollbackCount=3 -Dliquibase.url=jdbc:postgresql://localhost:5432/[schema] -Dliquibase.username=[username] -Dliquibase.password=[password]```

### Adding new changes 
In order to add any db change a new sripts should be added with the following name ```[date]_[name].xml```, for ex. ```2022_07_14_add_user_table.xml```, changeset id should contain file name, for ex.


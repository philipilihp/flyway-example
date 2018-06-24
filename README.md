# Flyway / Hibernate Integration in Java EE
Example project to run database migrations with [Flyway](https://flywaydb.org/) in a Java EE / Hibernate application.

## Setup
Start an empty instance of [PostgreSQL](https://hub.docker.com/_/postgres/) database with docker:
```
docker run -p5432:5432 --name postgres postgres
```
Run the application with [Payara Micro](https://www.payara.fish/payara_micro):
```
java -jar payara-micro-5.181.jar --deploy flyway-example/target/flyway-example-1.0-SNAPSHOT.war
```

During the startup process the application will create a schema named <b>book_app</b>, 
run the SQL scripts from <b>src/main/resources/flyway/postgres</b> before the Hibernate validation is executed
and insert the books defined in the Libary.java class.

After the application has started, you will find the tables <b>Book</b> & <b>Schema_Version</b> in the book_app schema 
with the following entries:

|ISBN          | AUTHOR                    | TITLE     | PUBLISHER      |
|--------------|---------------------------|-----------|----------------|
|978-3442715732|Juli Zeh                   |Unterleuten|btb Verlag      |
|978-3462050660|Benjamin von Stuckrad-Barre|Panikherz  |KiWi-Taschenbuch|


|INSTALLED_RANK | VERSION | DESCRIPTION              | TYPE | SCRIPT              | SUCCESS |
|---------------|---------|--------------------------|------|---------------------|---------|
|1              |[null]   |<<Flyway Schema Creation>>|SCHEMA|"book_app"           |true     |
|2              |1        |init                      |SQL   |V1__init.sql         |true     |
|3              |2        |add publisher             |SQL   |V2__add_publisher.sql|true     |

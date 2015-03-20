# BookLibrary
### Deployment:

### Download WildFly Server
- Download Wildfly-8.2.0.Final dist from http://wildfly.org/downloads/
- Or build project using maven: run *mvn clean install -P "wildfly-managed-test"* from project root directory,
it will build project, run integration tests and download wildfly dist in target directory so you can copy it

### Deploy database driver (example use MySql DB)
**If you copy wildfly dist from target directory db driver deployed automatically so you can skip this step**

- Download db driver (example use mysql-connector-java-5.1.34)
- Run WildFly server: *WILDFLY_HOME/bin/standalone*
- Run Jboss CLI: *WILDFLY_HOME/bin/jboss.cli* and type: *connect* (if server running must connect to it)
- In Jboss CLI run the command: *deploy ~/PATH_TO_DRIVER/mysql-connector-java-5.1.34.jar*
- To check if the driver installation was successful, run the following command from Jboss CLI:
    */subsystem=datasources:read-resource(recursive=true)*

### Download DB and create schema
- Example use MySQL DB and schema called "book_library"
- In *BookLibrary-DatabaseUpdate* module you can find initial sql scripts
- You can automatically create tables running project with *value="create"* for property name="hibernate.hbm2ddl.auto"
  in persistence.xml file or *value="update"* for updating existing tables

### Create data source in wildfly:
        
- Run from Jboss CLI next command:

>data-source add --name=bookstore-ds /

>--jndi-name=java:/jdbc/bookstore-ds /

>--driver-name=mysql-connector-java-5.1.34.jar_com.mysql.jdbc.Driver_5_1 /

>--connection-url=jdbc:mysql://127.0.0.1:3306/book_library /

>--user-name=root
        
- Data source can be added/updated in *WILDFLY_HOME/standalone/configuration/standalone.xml* file
  in *urn:jboss:domain:datasources:2.0* subsystem. For example look *src/test/resources/test-ds.xml*

- You can create ds with another params and use another db but jndi-name must be the same as
    in persistence.xml file in META-INF folder

### Deploy war archive:

- Run *mvn clean install* from project root directory

- Deploy war archive from *target* directory like db driver or put war in WILDFLY_HOME/standalone/deployments directory and then restart server
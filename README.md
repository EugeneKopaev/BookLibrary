# BookLibrary
### Deployment:

1) Download Wildfly-8.2.0.Final dist from http://wildfly.org/downloads/ or
   run: mvn clean install -P "wildfly-managed-test", it will build project and
   run integration test and download wildfly dist in target directory so you can
   just copy it

2) Deploy database driver (example use MySql DB): 

- Download db driver (example use mysql-connector-java-5.1.34)
- Run WildFly server: WILDFLY_HOME/bin/standalone
- Run WILDFLY_HOME/bin/jboss.cli and type: connect (if server running must connect to it)
- in Jboss CLI run the command: deploy ~/PATH_TO_DRIVER/mysql-connector-java-5.1.34.jar
- if you copy wildfly dist from target directory db driver deployed automatically
    
To check if the driver installation was successful, run the following command from Jboss CLI:
  /subsystem=datasources:read-resource(recursive=true)

3) Download DB and create schema (example use MySQL db and schema called "book_library")

4) Create data source in wildfly:
        
- Run from Jboss CLI next command:
    data-source add --name=bookstore-ds /
    --jndi-name=java:/jdbc/bookstore-ds /
    --driver-name=mysql-connector-java-5.1.34.jar_com.mysql.jdbc.Driver_5_1 /
    --connection-url=jdbc:mysql://127.0.0.1:3306/book_library /
    --user-name=root
        
- also ds can be added/updated in WILDFLY_HOME/standalone/configuration/standalone.xml
    in urn:jboss:domain:datasources:2.0 subsystem

- you can create ds with another params and use another db but jndi-name must be the same as
    in persistence.xml file in META-INF folder

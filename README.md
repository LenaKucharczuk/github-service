# Github Service
REST service which returns details of given Github repository.

### API of the service:
`GET /repositories/{owner}/{repository-name}`
```json
{
"fullName": "...",
"description": "...",
"cloneUrl": "...",
"stars": 0,
"createdAt": "..."
}
```

### Requirements
* JDK 1.8
* Maven

### Run on local environment
* `mvn spring-boot:run`

You can add `-Dserver.port={targetPort}` if default port 8080 is taken.

### Or run from jar
* build with `mvn clean install`
* copy `target/github-service-1.0-SNAPSHOT.jar` to desired destination or leave as it is
* run `java -jar github-service-1.0-SNAPSHOT.jar` in jar directory

Specify the port with `--server.port = {targetPort}` if needed.

### Configuration
Configuration with default values is kept in src/main/resources/github-service.properties.
Logs are configured in src/main/resources/logback-spring.xml and go to standard output and file logs/github-service.log.

### Health check
`GET /actuator/health` will return application status.

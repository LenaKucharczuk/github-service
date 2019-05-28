#Github Service
REST service which returns details:
* full name of repository
* description of repository
* git clone url
* number of stargazers
* date of creation (ISO format)

 of given Github repository.

###API of the service:
GET /repositories/{owner}/{repository-name}
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

### Run on local environment
* `mvn clean install`
* `mvn spring-boot:run`

You can add `-Dserver.port={targetPort}` if default port 8080 is taken.

### Or run from jar
* `mvn clean install`
* `java -jar target/github-service-1.0-SNAPSHOT.jar`

Specify the port with `--server.port = {targetPort}` if needed.

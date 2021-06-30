# marvel character api service
Marvel character api service is used to retrieve list of character id's from an external marvel endpoint and caters in servicing character information based on character id.

# Service Endpoints
 1) GET http://localhost:8080/characters/ 
 2) GET http://localhost:8080/characters/{id}
 
 First endpoint service is a GET call, response contains an array of character id's
 Second endpoint service is also a GET call, response details of the character that is requested, contains characterId, name, description and thumbnail.
 
 #Running Locally
 If any changes have been made and for the changes to reflect run the below command
 .gradlew clean build or
  gradle clean build
  
  java -jar build/libs/Yapily_Technical_Asses-1.0-SNAPSHOT.jar
 
 #Configuration
 Application required configurations are available in application.properties file
  src
   |-main
      |-resources
         |-application.properties
         
 #Application Secure values
 Application secret values have been stored in GoogleCloudStorage
 Bucket-Name : marvel_project
 Api-Key Object: api_key.txt
 Hash Object : hash.txt  
 
 #TODO TASK
 1) Addition of more Unit Test cases covering the core logic 
 2) Java cucumber Automation tests to be added , covering the functional testing     
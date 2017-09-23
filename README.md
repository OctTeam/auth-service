# sgs-auth-service
### Description
Before install required things:
```
jdk-8
maven 3
```
After install dependencies for deploy service in the package of service call:
```
$ mvn spring-boot:run
```
When it runs it will look for the config server on http://localhost:8888 by default, so
you could run the server as well to see it all working together.

#### [API UI link](http://localhost:8888/swagger-ui.html#)
Method	| Path	| Description	| User authenticated	| Available from API UI
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
GET	| /auth/token	| Return is the token valid |  | 	×
POST	| /auth/token	| Return created token for 'userId' |  | 

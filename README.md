Complete login registration backend system using Spring Boot.
Spring Boot
Spring Security
Java Mail
Email verification with expiry
Spring Boot
Email verification link with expiry
Example requests
CURL
curl --location --request POST 'localhost:8080/api/v1/registration' \
--header 'Content-Type: application/json' \
--data-raw '{
"firstName": "Amigos",
"lastName": "Code",
"email": "hellow@amigoscode.com",
"password": "password"
}'
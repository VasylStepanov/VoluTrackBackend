# VoluTrack

Volunteer Logistics and Accounting Helper - a project to make logistic easier and cheaper, accounting more accurate and tight.

![Logo](src/main/resources/img/biglogo.png)


## Roadmap

- Initial commit. Spring boot project was created. Set up postgres and flyway.
- Registration is done commit. Added user layer(roles and service), registration service with email verification.
- Registration service is recovered commit. Fixed email verification sender. Changed Spring Boot version to newer.  Added Spring Security.
- Authentication is done commit. Access JWT token in HttpOnly Cookies, Refresh token in SQL. ToDo: Add Redis for users, to avoid extra requests to DB for every user's request.

## Authors

- [@Vasyl Stepanov](https://www.github.com/VasylStepanov)

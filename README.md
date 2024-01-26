# VoluTrack

Volunteer Logistics and Accounting Helper - a project to make logistic easier and cheaper, accounting more accurate and tight.

![Logo](src/main/resources/img/biglogo.png)


## Roadmap

- Initial commit. Spring boot project was created. Set up postgres and flyway.
- Registration is done commit. Added user layer(roles and service), registration service with email verification.
- Registration service is recovered commit. Fixed email verification sender. Changed Spring Boot version to newer.  Added Spring Security.
- Authentication is done commit. Access JWT token in HttpOnly Cookies, Refresh token in SQL.
- Big fix 1.0: Removed Redis(reason: wrong use); fixed email sending(now sending is asyncronous, better performance); improved models(removed waste stuff); Swagger UI is added; Added Docker file to containerize project.

## Usage

```shell
$ docker-compose --env-file ./src/main/resources/.env up -d
```

## Authors

- [@Vasyl Stepanov](https://www.github.com/VasylStepanov)

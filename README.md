
# [Backend] Status - A FOSS Status Page
[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
![GitHub last commit](https://img.shields.io/github/last-commit/GalaxyGamingBoy/status-backend)
![GitHub Issues or Pull Requests](https://img.shields.io/github/issues/GalaxyGamingBoy/status-backend)

Status Page Backend is a flexible and easy-to-use Java-based status page backend built with Spring, InfluxDB, and PostgreSQL. It allows you to organize status checks into different projects, store time-series data about the response time and status codes in order to optimize uptime. The project is fully open-source and HATEOAS (HAL-FORMS) compliant.
## Features
 
- Documented API with Examples
    - OPENAPI 2.0, PAW, POSTMAN
- Fully REST
    - HATEOAS & HAL-FORMS
- User based authentication
    - By default authentication on all management routes
- Request seperation into projects
- Multiple request per project
- Query a single request, all of them in a project or all
- Change the expected status code
## Roadmap
- Webhook Alerts
- Frontend Support
- Custom Header Support
- More...


## Tech Stack

- Spring & Java
- InfluxDB
- Postgres
- Gradle


## Feedback / Support

If you have any feedback or need support, make sure to open an issue on github

## Authors

- [@GalaxyGamingBoy](https://www.github.com/GalaxyGamingBoy)


## Run Locally

Clone the project

```bash
  git clone https://github.com/GalaxyGamingBoy/status-backend
```

Go to the project directory

```bash
  cd status-backend
```

Start the dev server with the development config

```bash
  ./gradlew bootRun --args='--spring.profiles.active=development'
```

Build a JAR / WAR

```bash
  ./gradlew boorJar 
  ./gradlew boorWar 
```
### Environment Variables

To run this project, you will need to add to edit the projects `application.properties` file. You can also seperate the file into a development/release file

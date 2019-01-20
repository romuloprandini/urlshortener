# URL Shortener Java

## Description
This project was created for the Vanhack Brazil challenge to demonstrate my hability with Java, Docker and React. The project deadline was small (2 days), so a simple project was created to ensure that all functionallities work.

## Structure
The project contain both Backend (Java) and Frontend (React) and a docker-compose.yml to deploy then together.
```
urlshortener
├── docker-compose.yml
├── client
│   ├── nginx
│   │   └── nginx.conf
│   └── react
│       ├── Dockerfile
│       ├── package.json
│       ├── public
│       │   ├── favicon.ico
│       │   ├── index.html
│       │   └── manifest.json
│       └── src
│           ├── App.js
│           ├── App.test.js
│           ├── Constants.js
│           ├── index.js
│           ├── Redirect.js
│           ├── serviceWorker.js
│           ├── Shortener.js
│           └── UrlService.js
├── database
│   └── dump.sql
└── server
    ├── Dockerfile
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── shortener
        │   │           ├── controller
        │   │           │   └── UrlController.java
        │   │           ├── entity
        │   │           │   ├── TopHost.java
        │   │           │   └── Url.java
        │   │           ├── exception
        │   │           │   ├── BusinessException.java
        │   │           │   └── RestResponseEntityExceptionHandler.java
        │   │           ├── repository
        │   │           │   └── UrlRepository.java
        │   │           ├── service
        │   │           │   ├── impl
        │   │           │   │   └── UrlServiceImpl.java
        │   │           │   └── UrlService.java
        │   │           ├── ShortenerApplication.java
        │   │           └── util
        │   │               └── PaginationUtil.java
        │   └── resources
        │       ├── application-prod.properties
        │       ├── application.properties
        │       ├── static
        │       └── templates
        └── test
            └── java
                └── com
                    └── shortener
                        ├── controller
                        │   └── UrlControllerTest.java
                        └── ShortenerApplicationTests.java
```

## How to use
There are three methods to deploy this application:
- Manually: where you can use each application separated and deploy then in different servers.
- Dockerfile: where you can build an image of each application and use then in a Docker environment.
- Docker-Compose: there is a `docker-compose.yml` to deploy the backend and frontend in a Nginx Server and a Mysql database for persistence.

## `Java`
Manually

Url Shortener backend is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line:
```
cd server/
./mvnw clean package
cp target/app.jar /whereyouwant/
java -jar /whereyouwant/app.jar
```
or 
```
cd server/
./mvnw spring-boot:run
```

Docker
``` 
cd server/
docker build -t urlshortener-backend . 
docker run -d -p 8080:8080 urlshortener-backend

```

The application will be avaliable at [http://localhost:8080/api](http://localhost:8080/api)

## `React`
Manually

Url Shortener frontend is a [React](https://reactjs.org/) application build using [create-react-app](https://github.com/facebook/create-react-app). You can build the static site or run it from the command line:
```
cd client/react/
yarn && yarn build
cp build /anyhttpserver/ 
```
or 
```
cd client/react/
yarn && yarn start
```
Docker
```
cd client/
docker build -t urlshortener-frontend . 
docker run -d -p 80:3000 urlshortener-frontend yarn start

```

## Note

If you decided to use manual or Dockerfile deploy the java backend use H2 memory database as default. If you want to persist the data, you will need to configure the MySQL database, and execute these steps:
- execute the script in the Mysql database `database\dump.sql`
- set the environment variable `SPRING_PROFILES_ACTIVE=prod`
- update the lines below in `server/src/main/resource/application-prod.properties`:
  ```
  spring.datasource.url=jdbc:mysql://localhost:3306/shortener
  spring.datasource.username=shortener
  spring.datasource.password=shortenerpassword
  ```
## Licence

The application is released under [MIT](https://github.com/romuloprandini/urlshortener/blob/master/LICENSE) licence

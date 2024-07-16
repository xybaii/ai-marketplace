
# Build Angular
FROM node:22 AS ngbuild


WORKDIR /front-end

# Install angular 
RUN npm i -g @angular/cli@17.3.8

COPY front-end/angular.json .
COPY front-end/package*.json .
COPY front-end/tsconfig*.json .
COPY front-end/proxy.config.json .
COPY front-end/ngsw-config.json .
COPY front-end/tailwind.config.js .
COPY front-end/src src

# Install modules
RUN npm ci
RUN ng build


# Build Spring Boot
FROM openjdk:21 AS javabuild

WORKDIR /backend

COPY backend/mvnw .
COPY backend/pom.xml .
COPY backend/.mvn .mvn
COPY backend/src src

# copy angular files to spring boot
COPY --from=ngbuild /front-end/dist/front-end/browser/ src/main/resources/static

# produce target/backend-0.0.1-SNAPSHOT.jar
RUN ./mvnw package -Dmaven.test.skip=true

# Run container
FROM openjdk:21 

WORKDIR /app

COPY --from=javabuild /backend/target/backend-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080 SPRING_DATA_MONGODB_URI= SPRING_DATASOURCE_URL= SPRING_DATASOURCE_USERNAME= SPRING_DATASOURCE_PASSWORD= SECURITY_JWT_SECRETKEY= SECURITY_JWT_EXPIRATIONTIME= STRIPE_API_PUBLICKEY= STRIPE_API_SECRETKEY= STRIPE_WEBHOOK_SECRET= SPRING_MVC_HIDDENMETHOD_FILTER_ENABLED=TRUE SPRING_MAIL_HOST= SPRING_MAIL_PORT= SPRING_MAIL_USERNAME= SPRING_MAIL_PASSWORD= SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=TRUE SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=TRUE
 

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar
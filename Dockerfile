ARG APP_NAME=payment-api
ARG APP_VER=0.0.1-SNAPSHOT

FROM eclipse-temurin:17-jdk as build
RUN mkdir /app
ADD .mvn /app/.mvn
ADD mvnw /app
ADD pom.xml /app
WORKDIR /app
RUN ./mvnw dependency:go-offline
COPY src /app/src
RUN ./mvnw -DskipTests clean install


FROM eclipse-temurin:17-jre
ARG APP_VER
ARG APP_NAME
USER nobody:nogroup
COPY --from=build /app/target/$APP_NAME-$APP_VER.jar /app/$APP_NAME-$APP_VER.jar
EXPOSE 8443
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/$APP_NAME-$APP_VER.jar"]

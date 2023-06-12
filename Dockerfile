ARG APP_NAME=payment-api
ARG APP_VER=0.0.1-SNAPSHOT
ARG APP_JAR=${APP_NAME}-${APP_VER}.jar

FROM eclipse-temurin:17-jdk as build
RUN mkdir /app
ADD .mvn /app/.mvn
ADD mvnw /app
ADD pom.xml /app
WORKDIR /app
RUN ./mvnw  --batch-mode --update-snapshots  dependency:go-offline
COPY src /app/src
RUN ./mvnw --batch-mode --update-snapshots  -DskipTests clean install


FROM eclipse-temurin:17-jre
ARG APP_JAR

USER nobody:nogroup
COPY --from=build /app/target/${APP_JAR} /app/app.jar
EXPOSE 8443

HEALTHCHECK --interval=10s --timeout=2s --start_period=10s --retries=5 \
  CMD curl --fail --silent localhost:8443/actuator/health | grep UP || exit 1"

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

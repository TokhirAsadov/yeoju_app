# define base docker image
#FROM openjdk:8
FROM adoptopenjdk/openjdk8:ubi as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


#FROM openjdk:8
FROM adoptopenjdk/openjdk8:ubi

ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","uz.yeoju.yeoju_app.YeojuAppApplication"]

#LABEL maintainer="tohirAsadov"
#ADD target/yeoju-erp.jar yeoju-erp.jar
#ENTRYPOINT ["java", "-jar", "yeoju-erp.jar"]
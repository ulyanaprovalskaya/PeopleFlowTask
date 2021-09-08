FROM bellsoft/liberica-openjdk-alpine-musl:11.0.3
WORKDIR /usr/local/app
ADD target/peopleflowtask.jar app.jar
CMD java -jar ./app.jar
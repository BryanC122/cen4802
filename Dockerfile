FROM eclipse-temurin:24-jre

WORKDIR /app

COPY target/cen4802-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:StartFlightRecording=filename=/app/task-tracker.jfr,duration=5m,settings=profile,dumponexit=true", "-jar", "app.jar"]
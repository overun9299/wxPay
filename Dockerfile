FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=9800"]

EXPOSE 9800

ENTRYPOINT ["java" , "-jar" , "/app.jar"]
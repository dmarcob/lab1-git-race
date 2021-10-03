FROM gradle:openj9

WORKDIR /app
COPY . .

CMD ["/app/gradlew", "bootRun"] 

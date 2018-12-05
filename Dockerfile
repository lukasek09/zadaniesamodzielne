FROM java:latest  
COPY . /
WORKDIR /  
RUN javac connectMySQL.java
CMD ["java", "-classpath", "mysql-connector-java-8.0.13.jar:.","connectMySQL"]

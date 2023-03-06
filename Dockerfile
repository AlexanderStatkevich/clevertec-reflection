FROM tomcat:8.5.84-jdk17-corretto
COPY build/libs/ReceiptTask-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
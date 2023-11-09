FROM tomcat:9.0.82-jdk8-corretto
COPY build/libs/testapi.war /usr/local/tomcat/webapps/

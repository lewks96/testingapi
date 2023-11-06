FROM tomcat

COPY build/libs/testapi.war /usr/local/tomcat/webapps/

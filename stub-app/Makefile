build:
	mvn package
run:
	java -javaagent:./agent/jacocoagent.jar=output=tcpserver,address=127.0.0.1,port=8084,includes=example.service.* -jar target/stub-app-1.0-SNAPSHOT.jar
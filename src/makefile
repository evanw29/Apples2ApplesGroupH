all:
	javac *.java
	jar cfm Agent.jar manifest.txt *.class
	echo "#!/usr/bin/java -jar" > Agent
	cat Agent.jar >> Agent
	chmod +x Agent

clean:
	rm *.class
	rm *.jar

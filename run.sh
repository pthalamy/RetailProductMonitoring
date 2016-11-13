downloadJSONJava() {
	curl http://central.maven.org/maven2/org/json/json/20160810/json-20160810.jar -o json-20160810.jar
}

if [ ! -f json-20160810.jar ]; then
	echo "Missing file json-20160810.java, downloading..." >&2
	downloadJSONJava;
fi

javac -cp .:json-20160810.jar ProductMonitoring.java
java -cp .:json-20160810.jar ProductMonitoring

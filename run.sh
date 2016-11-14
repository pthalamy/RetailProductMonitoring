
#!/bin/sh

downloadJSONJava() {
	curl http://central.maven.org/maven2/org/json/json/20160810/json-20160810.jar -o json-20160810.jar
}

# Check dependencies
if [ ! -f json-20160810.jar ]; then
	echo "Missing file json-20160810.java, downloading..." >&2
	downloadJSONJava;
fi

# Compile
javac -cp .:json-20160810.jar ProductMonitoring.java
if [ $? -eq 0 ]; then
    echo "Compilation Successful" >&2;
else
    echo "error: Compilation Failure. Fix compile errors and try again..." >&2;
    exit 1;
fi

# Fetch latest run json data
echo "Fetching latest run data..." >&2
curl -X GET "https://www.parsehub.com/api/v2/projects/tBoeejUffzav/last_ready_run/data?api_key=tJZiuyWfxtpF&format=json" \
    | gunzip > run_results.json

# Run
java -cp .:json-20160810.jar ProductMonitoring

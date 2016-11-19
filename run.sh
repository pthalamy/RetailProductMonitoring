
#!/bin/sh

# downloadJSONJava() {
#     curl http://central.maven.org/maven2/org/json/json/20160810/json-20160810.jar -o json-20160810.jar
# }

downloadJSoup() {
    curl https://jsoup.org/packages/jsoup-1.10.1.jar -o jsoup-1.10.1.jar
}

# Check dependencies
# if [ ! -f json-20160810.jar ]; then
#     echo "Missing file json-20160810.jar, downloading..." >&2
#     downloadJSONJava;
# fi
if [ ! -f jsoup-1.10.1.jar ]; then
    echo "Missing file jsoup-1.10.1.jar, downloading..." >&2
    downloadJSoup;
fi

# Compile
javac -cp .:jsoup-1.10.1.jar ProductMonitoring.java
if [ $? -eq 0 ]; then
    echo "Compilation Successful" >&2;
else
    echo "error: Compilation Failure. Fix compile errors and try again..." >&2;
    exit 1;
fi

# # Fetch latest run json data
# echo "Fetching latest run data..." >&2
# curl -X GET "https://www.parsehub.com/api/v2/projects/tBoeejUffzav/last_ready_run/data?api_key=tJZiuyWfxtpF&format=json" \
#      2> /dev/null | gunzip > run_results.json 

# Run
java -cp .:jsoup-1.10.1.jar ProductMonitoring

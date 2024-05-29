# Target to build the project using Maven
build: clean
	mvn verify

# Target to run tests using Maven
test:
	mvn test

# Target to run the program
run:
	java -jar target/capitalgainstaxcalculator.jar

# Target to clean up compiled classes and JAR file
clean:
	mvn clean

.PHONY: build test run clean
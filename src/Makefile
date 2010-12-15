export CLASSPATH=.:/usr/share/java/junit4.jar:/usr/share/java/hamcrest-core.jar


fresh: clean src

src: Dict.class

test: Dict.class
	java org.junit.runner.JUnitCore \
		tests.TestWordList \
		tests.TestIWordListChange

testold: Dict.class
	java -cp .:/usr/share/java/junit.jar Dict test

run: Dict.class
	java Dictionary

Dict.class:
	javac -g -Xlint \
		Dictionary.java \
		wordlists/*.java \
		wordlists/exceptions/*.java \
		utils/*.java \
		tests/*.java \
		dict/*.java \
		config/*.java \
		services/*.java \
		tests/runnable/*.java

clean:
	rm -f *.class */*.class */*/*.class

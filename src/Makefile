export CLASSPATH=.:/usr/share/java/junit4.jar:/usr/share/java/hamcrest-core.jar


fresh: clean src

src: Dict.class

test: Dict.class
	java org.junit.runner.JUnitCore tests.TestDWAFile

testold: Dict.class
	java -cp .:/usr/share/java/junit.jar Dict test

run: Dict.class
	java Dict

Dict.class:
	javac -g -Xlint \
		Dict.java \
		wordlists/*.java \
		wordlists/exceptions/*.java \
		utils/*.java \
		tests/*.java

clean:
	rm -f *.class */*.class */*/*.class

export CLASSPATH=.:/usr/share/java/junit.jar

fresh: clean src

src: Dict.class

test: Dict.class
	java Dict test

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

#compilateur
JC = javac

#flags
JFLAGS = -g

#cibles
all: Bob.class Alice.class Key.class Helper.class

%.class: fr/m2sili/rsa/%.java
	javac $^ $(JFLAGS)
	
#nettoyages
clean:
	rm -rf fr/m2sili/rsa/*.class
	
mrproper: clean


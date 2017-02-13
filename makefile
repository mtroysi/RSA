#compilateur
JC = javac

#flags
JFLAGS = -g

#cibles
all: Bob.class Alice.class PrivateKey.class PublicKey.class Helper.class

%.class: fr/m2sili/rsa/%.java
	$(JC) $^ $(JFLAGS)
	
#nettoyages
clean:
	rm -rf fr/m2sili/rsa/*.class
	
mrproper: clean


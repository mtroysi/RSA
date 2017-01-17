#compilateur
JC = javac

#flags
JFLAGS = -g

#cibles
all: client.class serveur.class

client.class: client.java
	javac $^ $(JFLAGS)
	
serveur.class: serveur.java
	javac $^ $(JFLAGS)
	
#nettoyages
clean:
	rm -rf *.class
	
mrproper: clean


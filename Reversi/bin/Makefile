JC = javac
JVM= java 
JFLAGS = -d
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) ../bin $*.java

CLASSES = \
	Reversi.java

default: classes

classes: $(CLASSES:.java=.class)

RM = rm -rf
clean:
	$(RM) ./bin/reversi/*.class
	$(RM) ./bin/game/*.class
	$(RM) ./bin/agent/*.class

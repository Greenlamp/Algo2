JFLAGS = -sourcepath src -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
    src/graph/Arc.java \
    src/graph/Graph.java \
    src/graph/GraphFile.java \
    src/graph/Node.java \
    src/graph/Vec.java \
    src/graphviz/GraphViz.java \
    src/core/Main.java

default: classes
	jar cfe dettes.jar core.Main -C src core -C src graphviz -C src graph

classes: $(CLASSES:.java=.class)

clean:
	$(RM) src/core/*.class
	$(RM) src/graphviz/*.class
	$(RM) src/graph/*.class
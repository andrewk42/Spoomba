# Makefile for SE382 Assignment 5

run: Trainer
	java Trainer

Trainer: 
	javac Trainer.java Environment.java Spoomba.java RobotPart.java Body.java ArmSegment.java Waldos.java

clean:
	rm *.class

# vim:set ts=4 sw=4 sts=4 noexpandtab:

#!/bin/bash
cd source
javac -cp .:javax.mail.jar:javax.activation-1.2.0.jar *.java
java -cp .:javax.mail.jar:javax.activation-1.2.0.jar Main

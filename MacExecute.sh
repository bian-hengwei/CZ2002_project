#!/bin/bash
javac -d ./class -cp ./src:./lib/javax.mail.jar:./lib/javax.activation-1.2.0.jar: ./src/app/*.java ./src/boundary/*.java ./src/controller/*.java ./src/model/*.java ./src/view/*.java;java -cp ./class:./lib/javax.mail.jar:./lib/javax.activation-1.2.0.jar: src.app.Main

# CE/CZ2002: Object-Oriented Design & Programming
CZ2002_SS12_G2 Group Assignment  
Build An OO Application: _My STudent Automated Registration System (MySTARS)_
  
## Targets:
The assignment for your group will be to design and develop a Console-based application (non-Graphical UI): My STudent Automated Registration System (MySTARS)

## Group Members (Alphabetical Order):
- Bian Hengwei
- He Yinan
- Qian Hui
- Tricia
- Yin Jiarui
  
## Report & Demonstration:
1. A detailed UML Class Diagram for the application (exported as an image)
2. A detailed UML Sequence Diagram (exported as an image)
3. A write-up on your design considerations, principles and the use of OO concepts
4. A duly signed Declaration of Original Work form (Appendix B)
5. Demonstration: a video and audio recording to demonstrate the working of the application
  
## Deadline:
- 25nd November 2020, 11.59pm (extended deadline)
  
## Java doc (Win):
"JAVADOC.EXE_DIR" -d ./html -author -private -noqualifier all -version -cp .src\;.\lib\javax.mail.jar;.\lib\javax.activation-1.2.0.jar; src.app src.boundary src.controller src.model src.view
  
## Compile and run:
WindowsExecute.bat (Win)  
OR  
javac -d .\class -cp .src/;.\lib\javax.mail.jar;.\lib\javax.activation-1.2.0.jar; .\src\app\*.java .\src\boundary\*.java .\src\controller\*.java .\src\model\*.java .\src\view\*.java  
java -cp .\class;.\lib\javax.mail.jar;.\lib\javax.activation-1.2.0.jar; src.app.Main  
  
MacExecute.sh(Mac)  
OR  
javac -d ./class -cp ./src:./lib/javax.mail.jar:./lib/javax.activation-1.2.0.jar: ./src/app/*.java ./src/boundary/*.java ./src/controller/*.java ./src/model/*.java ./src/view/*.java  
java -cp ./class:./lib/javax.mail.jar:./lib/javax.activation-1.2.0.jar: src.app.Main

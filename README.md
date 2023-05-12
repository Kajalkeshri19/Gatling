Project Name: Gatling Assignment

Project Description: Creating a gatling project using maven to create virtual users and making request using all the http methods i.e get, post and put. All the data inputs are fetching from external csv file. Assertions have been used.

Steps to set up Gatling project using maven:

Create a new Project in intellij or any preferred IDE
Choose MavenArchetype.
Go to maven repository and search for Gatling high-chart maven Archetype.
Now copy the group id and Artifact id and paste it in the maven archetype group id and archetype ID and Create it.
Now You will get the template. Now create a scala file and add scala sdk plugin.
Now Create a file by .scala and write your code.
At last add the scala sdk plugin.

Plugin Used:
Scala SDK.

Technologies used: 
Programming language – Scala 
Build tool – Maven 
Load Testing Tool – Gatling 
IDE – Intellij

Steps for execution:

Gatling Engine: You can directly run the code by Gatling Engine.
Terminal: Run the code by terminal by using the command “mvn gatling: test -Dsec=5 -DusersAtOnce=50 -DconstantUsers=20 -DrampUsers=100

Codes: For codes you can see the .scala file.

Output Report: For detailed understanding of output refer the OutputReport.doc in the repository.

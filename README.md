# Positional-Index
This repository contains Java implementaion of building positional index for 10 text files, takes a query of more than one word and prints the ids of the documents contains the query with its order

# Running this project
To use this project you will clone this repository and run the project on any Java IDE but if you want to run it on different files you have to put the files in the project beside src folder and put the name and the extension of the file in the following line in collection class
```
String filesnames [] = {"software engineering.txt", "software engineering2.txt","operating systems.txt","operating systems2.txt","cloud computing.txt","cloud computing2.txt","data structures.txt","data structures2.txt", "google assistant.txt" , "google assistant2.txt"};
```
Notes : some parts of the code assumes that always the number of the files is 10. 
The id of the file is its index in filesnames array +1.

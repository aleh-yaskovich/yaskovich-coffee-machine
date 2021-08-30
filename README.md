##Project "Coffee Machine"
by Aleh Yaskovich

#####The application "Coffee Machine" consists of two independent parts - server and client. Java 11+ and a web browser must be installed on your computer to run the application.
1. Download jar-archives from this links:
   https://drive.google.com/file/d/1N6UCvSMMajw-XsiU4pBQY6Hj_7YWYz1o/view?usp=sharing
   https://drive.google.com/file/d/1vVCzmI2aNERaFE8r_NbfWcowOxz7LgUo/view?usp=sharing
2. Go to the folder with the archives in Terminal
3. To run the server part write in the command line:  
> java -jar rest-app-1.0-SNAPSHOT.jar
4. To check that the server part of application is working 
write in a browser: http://localhost:8080/client, 
or open a new window in Terminal and write:  
> curl -v localhost:8080/client.  

If it is OK, you will see the list of beverages.
5. To run the client part write in Terminal:  
> java -jar web-app-1.0-SNAPSHOT.jar.  

Then write the link in your browser: http://localhost:8088/.  
If it is OK, you will see the page with a table of beverages in a browser.
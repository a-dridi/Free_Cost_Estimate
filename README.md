# Free Cost Estimate

Practical example for development in Java with the testing framework "testNG". 

## Testing
![Screenshot of Application Free Cost Estimate](https://raw.githubusercontent.com/a-dridi/Free_Cost_Estimate/master/testing_screenshot.PNG)

This project uses "testNG". 

Test cases are available in this package [test](https://github.com/a-dridi/Free_Cost_Estimate/tree/master/src/test/java/at/adridi/freecostestimate/service)

Test suite is located [here](https://github.com/a-dridi/Free_Cost_Estimate/tree/master/src/test/java) 

## Description

Alpha release. In development. 
* Production versions are also available per request. 

This is a web application where users can create cost estimate and sent it to their email address. It is written in JSF and uses Postgresql as a database.


## Configuration 

### Application settings
1. Go the package "util". 
2. Add your email settings in the class "EmailSettings".
3. Add services that your offer in the function getCostCategories() of the class "CostCategory"
4. Add the difficulty (of your offered services) in the function getRatioDifficulty() of the class "DefaultCostsValues". 


### Deployment settings
Go to the folder "WEB-INF". Open the file "web.xml" and change the "javax.faces.PROJECT_STAGE" from "Development" to "Production".
Add your database settings in the file "hibernate.cfg" which is in the resources folder. Please add also your Paypal SDK account credentials in the file "credentials.properties" in the folder "credentials". This folder is also in the resources folder.
You need to compile this application to get the "war" file, which is in the folder "target". 

## Installation
Copy this "war" file to the folder "webapps" of your web server "Apache Tomee Plume" Server. You have to configure also your web server. 


## Authors

* **A. Dridi** - [a-dridi](https://github.com/a-dridi/)
* See also License file



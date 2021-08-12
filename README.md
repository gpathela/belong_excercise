#Code test for Belong

This is a code exercise given by Belong for Backend developer position. 

#Developed By Gourav Pathela

#Technologies used
1. Java
2. Spring Boot

#Test Libraries
1. JUnit
2. Mockito

#Api Docs via Swagger
Docs url /swagger-ui/index.html

#End Points

1. /number : Provides list of all the numbers in DB along with their status
2. /customer: Provides list of all the customers in DB along with their active numbers
3. /customer/{name}: Search and provide particular customer details along with thier active numbers. If no customer found returns status Not_Found
4. /addcustomer: Adds a new customer in DB. If customer already exist returns with status CONFLICT
5. /activatenumber: Assigns a number to a customer. Returns status BAD_REQUEST if request fails due to wrong data

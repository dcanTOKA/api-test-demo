# User Functionality Specification

Create a User
-----------

Tags: CreateUser

>This scenario tests whether the user is created

>This scenario gets the user based on the username and tests whether the user fields in the json do match.

* Create a user
* Get the user whose username is "dogacan.toka" , expect the "200" status code and compare it in "swagger_users/users.json" file

Update User
-----------

Tags: UpdateUser

>This scenario updates the email and phone information of the user whose username is given in the scenario . In this case :
> dogacan.toka

>This scenario gets the updated user and check it is really updated or not. In this scenario only the email and phone have
>been changed. If you changed the username, the below scenario should be synthesized in proper way.


* Update the user whose username is "dogacan.toka"
* Get the user whose username is "dogacan.toka" , expect the "200" status code and compare it in "swagger_users/email-phone-updated-user.json" file

Delete User
----------

Tags: DeleteUser

>This scenario tests whether the user is deleted. The user in this case is dogacan.toka.
> 404 - User not Found is expected in this case.

* Delete the user whose username is "dogacan.toka"
* Get the user whose username is "dogacan.toka" , expect the "404" status code and compare it in "swagger_users/email-phone-updated-user.json" file




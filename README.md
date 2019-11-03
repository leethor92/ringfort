# Archaeological Fieldwork : Part I - Android based application

Name: Lee Thornton

## Overview.
For this assignment the aim was to allow users to create & view ringforts while setting locations.

Users are able to login or signup an account. Upon logging into the app the user can see a list of ringforts where name, details & an image is displayed.
Upon selecting a ringfort the user is taken to that view where they can edit ringfort data, set the location, add images & indicate wether they have visited the location.
There is also a settings view where users can edit there email or password, stats are also displayed in this view.

## UI Design.

![][splashscreen]

>> When the app is launched a splashscreen with the app logo is displayed.

![][signup]

>> User is taken to signup screen where they can register an account. If they already have an account they can click login and will be taken to that view.
App logo is displayed in the background.

![][login]

>> If the user already has an account they can login via this screen. They can also click the register button to sign up a new account of needed.

![][ringfortList]

>>Once the user is logged in they can view the list of all ringforts currently created. There is a navbar that allows the user to go to settings
& add ringfort views. They can also logout using the navbar logout button.

![][ringfort1]
![][ringfort2]

>>If the user clicks on a ringfort in the ringfortList they are taken to the ringfort view. Where the name, details & additional notes are displayed.
The user can click a visited checkbox that will display the date it was visited, also the user can selet the ringfort location & add multiple images.

![][map]

>>The map screen above allows the user to select the ringfort location & will display the GPS coordinates of the ringfort.

![][settings]

>>If the user clicks the settings icon they will be taken to this screen. This allows the user to edit their password & email.
Some stats are also displayed here, user can view the total number of ringforts & the amount of ringforts visited.

[ringfortList]: ./app/src/main/res/drawable/RingfortList.PNG
[login]: ./app/src/main/res/drawable/Login.PNG
[signup]: ./app/src/main/res/drawable/Signup.PNG
[ringfort1]: ./app/src/main/res/drawable/Ringfort1.PNG
[ringfort2]: ./app/src/main/res/drawable/Ringfort2.PNG
[settings]: ./app/src/main/res/drawable/settings.PNG
[splashscreen]: ./app/src/main/res/drawable/Splashscreen.PNG
[map]: ./app/src/main/res/drawable/map.PNG





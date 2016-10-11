Client API

AD430 Client Side Requirements 


Login Information
Skype accounts will be required
Link to a Skype registration page 
Google Plus would be the alternative if Skype isn’t available
Users will have their own separate account for logging in
Username details	
Age, full name, phone number verification
Passwords will need encryption 
Current location will be updated as a last known location


Video Calls
The use of front camera (or rear camera) in order to stream video 
After making a link between users, current app will push the meeting to a Skype call
Peer-to-peer conferencing
multiple people watching the video from a stream*
Skype URI in order to get video conferencing in the app natively
google hangout handoff will be used if Skype can't be used to initiate call
Video quality settings automatic (should be built into Skype)
Best, medium, low, and mobile quality should be supported
mobile quality will be the worst quality but available for people without good data plans
Voice quality settings automatic based on connection quality (or set manually)
Best, medium, low, and mobile quality should be supported
mobile quality will be the worst quality but available for people without good data plans

Speech to Text (only if there are no interpreters)
Android keyboard support
chat room integration
people can contact each other
Multiple people contacting the interpreter*
speech-to-text integration*
Use of Android built-in speech-to-text converter

Location/Event 
Location map with a radius
Only expose their location if the map is open
If the map is not open, the location of the user will not be displayed
getting current location 
User has to enable location
pinning the location on a map
1 mile radius will be displayed on the map around the current person’s location
Clicking on a pin to get a pop up to contact the person 
Map gets displayed and gets updated in real time
Map will display users (interpreters) that are currently online and available
Map will download all users online throughout the world, will be displayed in a world map in the beginning and will zoom in based on looking for your current location
Current location will be updated automatically and sent to the server which will be pinged to the rest of the users
set up meetups at events in order to get people gathered
interpreter can initiate a get together (or someone gathers people and requests an interpreter)
RSSfeed to default deaf events 


*extra features not needed to ship 

Client-api level requirements for Skype activities(JAMES):

public boolean isSkypeClientInstalled(Context myContext) public void goToMarket(Context myContext) When user clicks request video call, perform a quick check of whether or not skype is currently installed on the device. Assuming true, proceed. If false, give option to proceed to play store to install, or explain use of this feature is impossible.
Testing this function: try pushing request button without skype installed on the device.

When video call is requested by the HOH user, request list of active interpreter(INTP) users from server. int getCurrentActiveUsers( no params?) - returns the number of active users
If this function is working, the result should be > 0 or it should provide a helpful dialog (Do you wish to try again?). We may want to display the number of active and available INTP while waiting to connect.
Testing this function- if the user pushes request call, the number of active INTP displayed should match the number found in the database. If there are none, display alert.

string getUsername( no params?) - following a successful query of active users in the database, this function should indicate to the server that it needs the username of a user that has agreed to accept the call. This function should run repeatedly until it achieves a successfull result.
Testing: the function must return a valid username string. If the string is empty, it has failed and needs to run again until the server provides the username of a valid INTP.

initiateCall(string usernameINTP) - pass the username to the skype URI call (setData(Uri.parse("skype:" + usernameINTP + "?call"))
public void initiateSkypeUri(Context myContext, String mySkypeUri)
This should hand the user over to skype where the call will be automatically placed. There's the most room for error here, so if the call fails there should be an opportunity to attempt it again with the same username.
Testing: we will need a few dummy skype accounts to test this process out, and a live set of phones. we may want to hard-code some usernames in to test the process of connecting a call before trying to do it using the server list.

Client/API level Requirements for Login page:
Login Page
Verify Windows live account
Need to call to the windows live API to see if account exists.
Check if the client exists.
Login if exists
Setup option for new account needed
·      When no login information we will need to send them to the microsoft live account page
Create Microsoft account for application
·      We will need to register our application to be able to ping the Microsoft api
·      Create account
·      Test account for verification
- Get skype username from user



Duri Balat’s MileStone 1
Location Requirements
Following are the listed requirements for the Client API.  

HOH MenuPage - 
Physical Interpreter
- Once the Physical Interpreter button is clicked by user, the user's coordinates will be pulled from their current location by longitude and latitude GPS coordinates.  AndroidStudio uses LocationManager and pulls the coordinates of the user. 
- Needs to request permission of user to allow the application to use the users' coordinates
- User's location will update by locationManager.requestLocationUpdates(type of data, time to update (by milliseconds), min Distance the user needs to travel to update, appointed locationListener)

PhysicalRequest_Page - 
Required: User's current GPS Location is required; if not, prompt the user to enter desired address. 
- User will be prompted to entered distances to find interpreters within the vicinity. 
- A list of users sorted by distance (closest to farthest) shall be listed (perhaps along with past reviews from other users')
- If users, aren't available at the appointed address (current or desired by user); PhysicalRequest_Error page will appear asking the user if they would like to perhaps Skype with another interpreter or use Natural Language Processor (NLP); or even change the range of search.  

InterpreterMain_Page - 
- When the user signs in as an Interpreter, their location must be pulled as well.  

Completed requirements:
I have completed the Physical Interpreter button function allowing the pull of user's coordinates.

Requirements to complete by the next two weeks (Estimated hours used to completed - 2 hours per day):
- Look up Haversine function (server or client side) - make a decision with serverside (Grant)
- JSON to post onto user platform (sorted list of interpreters for the users)

I believe the group is still on track.  

Peer Eval - Everybody is proactive and doing their part to complete the task collectively.  

Samuel Requirement
Things that need to be completed for gps services 
(pages 3, 6 and 9) (HOHMenu_page)
-enabling gps
-need to ask for gps availability
-if gps is not enabled, disable the function to check by location
-forward to settings to enable gps services only if the location services are disabled, and location through the app is to be used
-if Physical Interpreter” button is clicked, gps services need to be enabled

-getting gps location data
-since gps gets updated whenever you move, we have to figure out when to update the server data 
-updates based on when user wants location to be sent out, user
doesn't want information to be sent
-setter for interpreter
-getter for HOH ping only when they request a physical interpreter
-auto-timeout 

-sending gps location data
-sends the data back every 5 minutes or unless manually updated
-sets the user location and sends the new latitude and longitude to the 
database since location is tied to user
-using a thread to update every 5 minutes here once the location services are enabled
-thread will run only when searching for physical interpreters in
order to keep location information out of reach when not needed
-turning off sending gps location

-calculating people within a certain radius given 
-check everyone latlng and compare to the user’s current latlng
-enable radius checking 


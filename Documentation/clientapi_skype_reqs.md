Client-api level requirements for Skype activities:
_____

public boolean isSkypeClientInstalled(Context myContext)
public void goToMarket(Context myContext)
When user clicks request video call, perform a quick check of whether or not skype is currently installed on the device. Assuming true, proceed. If false, give option to proceed to play store to install, or explain use of this feature is impossible.

Testing this function: try pushing request button without skype installed on the device.
____

When video call is requested by the HOH user, request list of active interpreter(INTP) users from server. int getCurrentActiveUsers( no params?) - returns the number of active users

If this function is working, the result should be > 0 or it should provide a helpful dialog (Do you wish to try again?). We may want to display the number of active and available INTP while waiting to connect.

Testing this function- if the user pushes request call, the number of active INTP displayed should match the number found in the database. If there are none, display alert.

_____

string getUsername( no params?) - following a successful query of active users in the database, this function should indicate to the server that it needs the username of a user that has agreed to accept the call. This function should run repeatedly until it achieves a successfull result.

Testing: the function must return a valid username string. If the string is empty, it has failed and needs to run again until the server provides the username of a valid INTP.
_____

initiateCall(string usernameINTP) - pass the username to the skype URI call (setData(Uri.parse("skype:" + usernameINTP + "?call"))

public void initiateSkypeUri(Context myContext, String mySkypeUri)

This should hand the user over to skype where the call will be automatically placed. There's the most room for error here, so if the call fails there should be an opportunity to attempt it again with the same username.

Testing: we will need a few dummy skype accounts to test this process out, and a live set of phones. we may want to hard-code some usernames in to test the process of connecting a call before trying to do it using the server list.

 


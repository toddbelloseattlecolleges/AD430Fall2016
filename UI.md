##UI Requirements:  
###Main Windows

####Signup/Login Page
- [ ] Allow users to sign in with Microsoft account, or create a Microsoft account, then proceed to Main Page.

####Main Page (Hard of Hearing)
- [ ] Has 3 buttons (NLP, Video, Physical)
- [ ] Clicking video button starts a video request and takes users to request page
- [ ] Clicking physical button takes users to physical request page
- [ ] Clicking NLP button takes user to the NLP chat window

####Main Page (Interpreter)
- [ ] Has 2 check boxes with buttons
- [ ] Video button toggles the indicator for video interpretation
- [ ] Physical button toggles the indicator for physical interpretation
- [ ] When video indicator is on, then interpreter user will be available to HOH users for video
- [ ] When video indicator is off, then interpreter user will **not** be available to HOH users for video
- [ ] When physical indicator is off, then interpreter user will be available to HOH users for physical interpretation
- [ ] When physical indicator is off, then interpreter user will **not** be available to HOH users for physical interpretation
- [ ] If user closes app. The indicators are still respected, and they be available based on indicators.

####NLP chat page will support Google Voice TTS, keyboard, and scrolling conversation history
- [ ] ???

####Physical Requests Page where Hard of Hearing people will select a radius for interpreters, and tap a "make request" button.
- [ ] A text field to add a range to check for
- [ ] A request now button
- [ ] Error state fragment in case there are no interpreters within the specified range
- [ ] On the error state fragment there are three buttons: Change Range, Try Video Chat, NLP
- [ ] Clicking on the Change range button goes to the main Physical Request view with the range text box selected
- [ ] Clicking on the Video button takes the user to the Video call flow
- [ ] Clicking on the NLP button takes the user to the NLP chat page
- [ ] Success state fragment that indicates an interpreter is on the way

####Settings Page
- [ ] Setting page can be accessed from any page.
- [ ] Setting will have a personal photo of the interrupter and the user.
- [ ]Next the photo the settings will have the user first and last name.
- [ ]rofile setting allow to update personal photo and name of user.
- [ ]	Do not disturb setting and Push notifications.
- [ ]	Advanced setting
- [ ]	Location enabled.
- [ ]	Acceptable radios.
- [ ]	Minimum rating.
- [ ]	 Auto timeout.
- [ ]	Change theme (use default vs custom settings)
- [ ]	Captions > language , text size , white/black
- [ ]	About > app version, send feedback, help(Q&A) and developer info 


###4 push notifications:

####New Video Request for interpreter
- [ ] Notification has accept and deny buttons
- [ ] Notification has icon from app
- [ ] Notification has text so user knows it a video request for interpretation
- [ ] Clicking accept starts app and takes user to video chat with HOH user
- [ ] Clicking accept also notifies HOH user that interpreter is ready for video
- [ ] Clicking deny dismisses the notification and HOH user is notified of rejection?

####Video request accepted for Hard of Hearing user  
- [ ] notification has icon for app
- [ ] notification lets user know that interpreter is ready for video
- [ ] Clicking notification takes user to video chat
- [ ] Dismissing notification?

####New Physical Request for interpreter
- [ ] Notification has accept and deny buttons
- [ ] Notification has icon from app
- [ ] Notification has text so user knows it a physical request for interpretation
- [ ] Clicking accept starts app and takes user to map with location?
- [ ] Clicking accept also notifies HOH user that someone is on the way
- [ ] Clicking deny dismisses the notification and HOH user is notified of rejection?

####Physical Interpreter on the way (no buttons necessary
- [ ] Notification has icon for app
- [ ] Notification lets user know that interpreter is on their way
- [ ] Clicking notification takes user to???
- [ ] Dismissing notification?

# MyRide
## 1.	Android Application: Bike Ride Mode:
### 1.1.	 Objective: 
 An Android Application that will allow user distraction free bike ride
#### 1.2.	Functional and Technical Specification: 
 1.2.1.	Functions:
 •	The Application will automatically start after speed is greater than 20kmph.
 •	When Ride Mode is ON all Calls will be Rejected* and automatically text will be sent to call as per user’s instructions.
 •	User will able to add list of contacts of whom call will not be rejected.
 •	If speed is greater than speed limit then user will be notified using vibrations and visual notification.
 •	User will be able to keep track of his previous ride information.
 •	User will be able decide his own preferences.
 1.2.2.	Technical Aspects:
 •	In this Project we used BroadcastReceiver and Location Manager class of android sdk so when an event of increase in speed and  change in location is occurred then automatically Ride Mode will be turned ON. 
 •	Using BroadcastReceiver when call is incoming the event will be caught and then by using SMS Manager text reply will be sent. 
 •	We used database to store previous ride data and contacts
#### 1.1.	Advantages:
 1.1.1.	Distraction free ride.
 1.1.2.	Automatic Ride Mode ON.
 1.1.1.	Automatic Text reply.
 1.1.4.	Previous ride details to keep track of use.
 1.1.5.	Notification if speed limit is crossed.
 1.1.6.	Emergency Calling Service.
#### 1.4.	Future Scope:
 1.4.1.	Android wear support.
 1.4.2.	Parental Controls i.e. parents will be able to keep track of speed of their children’s driving.
 1.4.1.	Car Mode with navigational features.
 1.4.4.	Fuel Tracking based on distance travelled and speed.

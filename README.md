# BikeBuddies
## Android SGV Project
### James Bowen, Sam Echevarria, Hannah Young
### jbowen@hmc.edu, sechevarria@hmc.edu, hyoung@hmc.edu

#### ABOUT THIS CODE

Our Android application Bike Buddies is meant as a method with which to get middle school students more active and healthy, and as a result, give kids a fun, healthy lifestyle choice. A user is able to invite one friend to go biking at a specific date, time and location, done with a few taps on the screen. Bike Buddies provides a way for students to see how they stack up against students in their class (see ‘Privacy and Security Built Around the Classroom’ below for more information), as well as see a log of their past rides and get a sense of their progress, information that could readily be visualized in future iterations of the application. Users can also record the time and distance of their ride via our integration of the Google Maps API. Everything in this repository is the frontend for the app; a full backend needs to be developed in order to make this app useable. 

#### CODE ORGANIZATION

The structure of our code consists of Java classes that define the different screens, or activities, and XML files that define the layouts associated with each of those activities. BikeBuddies contains the following activities: 
SplashActivity: Displays a welcome image introducing the app.
  * MainScreen: Provides the main menu to different tasks and a notifications bay.From the main screen the user can go to the following activities:
  * FindFriends: Displays a list of friends from which the user can choose when trying to schedule a meetup. Directs the user to...
  * SetDateTime: Allows the user to pick a date, time, and location at which they wish to meet up. Sends an invitation to the user’s friend when the form is complete.
  * NotificationsScreen: Displays any new notifications, such as a friend’s acceptance of an invite or a new invitation.
  * RideScreen: Records the user’s progress along the map as they ride their bike (time and distance). Upon completion of rides, displays those statistics.
  * LeaderboardScreen: Shows user’s statistics in relation to the rest of their classmates.
  * MyRideHistoryActivity: Displays user’s personal statistics over time.

Each of these activities is supported by a layout described in an XML file with a related name (for example, the XML for MainScreen is activity_main_screen.xml). 

In addition to the activity-driven files, there are other java and XML files to describe certain features within layouts. In regards to Java classes, there is CustumLeaderboardCustomAdaptor which dynamically creates the list of students in a class and displays their statistics. TimeRecord is also a Java helper class that works behind scenes and provides an easier way to manipulate times of rides for users, and aggregating their total time. For the XML files, there is also leaderboard_list.xml, notification_row.xml, and ride_history_row.xml which describe subcomponents of the layouts. In the drawable folder, there are more XML files which create the coloring for the buttons and notification system. All icons used in the app are also stored here, along with a dummy current user photo. Finally, the strings.xml file contains most of the hard-code strings within the app. There are other XML files, but these were pre-generated by Android Studio and not edited by us.

#### BUILDING THE CODE AND RELEASING THE APPLICATION

In order to be able to test, run and distribute the application, the code is built into an .apk file that can be distributed and downloaded onto Android devices. For Android projects, building is done using the Gradle plugin, which is automatically included with Android Studio. If a different development environment is used, Gradle can be downloaded from the Gradle website (https://gradle.org/) and a good resource can be found at Spring's Guide to Getting Started with Gradle (https://spring.io/guides/gs/gradle/) on utilizing Gradle with a non-Android Studio environment.
There are many ways to configure Gradle to create different kinds of releases, such as a final release or a version for debugging and testing:

  * References such as the Android Developer’s Guide for Configuring Gradle Builds (https://developer.android.com/tools/building/configuring-gradle.html) can provide a starting point for modifications and understanding different configurations. 
  * In development and testing a version of the code can be tested on the provided Android Studio emulator (see Android Developer’s Guide for Emulators at http://developer.android.com/tools/devices/emulator.html)  or on a development-enabled device (see Android Developer's Guide for Hardware Devices at http://developer.android.com/tools/device.html). To build a version that is usable on these instances of hardware, one can follow the steps outlined in the Android Developer’s Guide for Building and Running a Project in Android Studio (https://developer.android.com/tools/building/building-studio.html). 
  * In order to release the application to user’s hardware and through the Google Play store, multiple steps must be taken to prepare for a full release. These steps are outlined in the Android Developer’s Guide for Preparing for Release (http://developer.android.com/tools/publishing/app-signing.html) and must be carefully followed.

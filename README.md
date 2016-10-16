# USYDWall

===============================================================================
                                USYDWall
                         Social Media App in Android 
             and developed for social interactions between students
                              in a university.
===============================================================================

This document describes USYDWall, an app developed with Android Studio to create
a social media for the students of the University of Sydney. 

For information about Java and XML as a language, you can visit the web site
https://docs.oracle.com/javase/tutorial/

In the following we describe the build and installation of our Android
application written in Java (and in XML) using certain dependencies like
Parse, neokree and so on.

Note that the build instructions below applys to Windows environments.

All Android layouts and activities have been run successfully on an Android device
"API_23" configured as follows: 4GB RAM, 64GB Internal Storage, 810 Qualcomm Processor, etc..


Software Requirements
---------------------

In order to build/run our Android App we need to install the following
free software distributions (last tested versions and download sites are given
in parenthesis) :

1) Parse-1.8.1.jar    (https://github.com/yongjhih/parse-android-sdk.m2/tree/master/com/infstory/parse/1.8.1)
2) It.neokree:MaterialTabs:0.11 (https://github.com/neokree/MaterialTabs)
3) Android SDK 22 +   (https://developer.android.com/studio/index.html)
4) De.hdodenhof:circleimageview:+   (https://github.com/hdodenhof/CircleImageView)
5) android-support-v7-appcompat     (https://github.com/dandar3/android-support-v7-appcompat)


Project Structure
-----------------

* The "AndroidManifest.xml" file contains essential information the Android
  system needs to run the application's code.  
  
AndroidManifest.xml

This XML file describes to the Android platform what your application can do.
It is a required file, and is the mechanism you use to show your application
to the user (in the app launcher's list), handle data types, etc.

* The "build.gradle" file compiles all the dependencies and defines the default API level of the Android App. 

src/*

Under this directory are the Java source files for your application.

src/com/samsonjabin/uwall/BaseActivity.java
src/com/samsonjabin/uwall/DispatchActivity.java
src/com/samsonjabin/uwall/Dp.java
src/com/samsonjabin/uwall/HelpoutActivity.java
src/com/samsonjabin/uwall/Initialize.java
src/com/samsonjabin/uwall/LoginActivity.java
src/com/samsonjabin/uwall/MainActivity.java
src/com/samsonjabin/uwall/signupActivity.java
src/com/samsonjabin/uwall/VerifyMail.java

This is the implementation of the "activity" feature described in
AndroidManifest.xml.  The path each class implementation is
{src/PACKAGE/CLASS.java}, where PACKAGE comes from the name in the <package>
tag and CLASS comes from the class in the <activity> tag.


res/*

Under this directory are the resources for your application.

res/layout/activity_main.xml

The res/layout/ directory contains XML files describing user interface
view hierarchies.  The activity_main.xml file here is used by
MainActivity.java to construct its UI.  The base name of each file
(all text before a '.' character) is taken as the resource name;
it must be lower-case.

res/drawable/usyd.png

The res/drawable/ directory contains images and other things that can be
drawn to the screen.  These can be bitmaps (in .png or .jpeg format) or
special XML files describing more complex drawings.  The usyd.png file
here is used as the image to display in one of the views in
fragment_navigation_drawer.xml.  Like layout files, the base name is used for the
resulting resource name.

res/values/colors.xml
res/values/strings.xml
res/values/styles.xml

These XML files describe additional resources included in the application.
They all use the same syntax; all of these resources could be defined in one
file, but we generally split them apart as shown here to keep things organized.


Signing your Applications
-------------------------
All Android applications must be signed. The system will not install an
application that is not signed. Ensure to generate signed APK under Build 
Options in your Android Studio to run the app on your device. 

--
Blessings 
Samson Jabin

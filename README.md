# USYDWall

===============================================================================
                                USYDWall
                         Social Media App in Android 
                        and developed for limited users.
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
"API_23" configured as follows: 4GB RAM, 64GB Internal Storage, 810 Qualcomm Processor
and 1080 HD Display. 

Software Requirements
---------------------

In order to build/run our Android examples we need to install the following
free software distributions (last tested versions and download sites are given
in parenthesis) :

1) Java SDK 1.6 +     (1.7.0_03, www.oracle.com/technetwork/java/javase/downloads/)
2) Scala SDK 2.7.5 +  (2.10.0  , www.scala-lang.org/downloads/)
3) Android SDK 9 +    (19      , developer.android.com/sdk/)
4) Apache Ant 1.7.0 + (1.8.3   , ant.apache.org/)
5) ProGuard 4.4 +     (4.7     , www.proguard.com/)

NB. In this document we rely on Ant tasks featured by the Scala SDK, the
Android SDK and the ProGuard shrinker and obfuscator tool (we will say more
about ProGuard when we look at the modified Ant build script).


Project Structure
-----------------

The project structure of an Android application follows the directory layout
prescribed by the Android system (for more details see the documentation page
$ANDROID_SDK_ROOT/docs/guide/developing/other-ide.html#CreatingAProject).

In particular:

* The "AndroidManifest.xml" file contains essential information the Android
  system needs to run the application's code (for more details see the docu-
  mentation page $ANDROID_SDK_ROOT/docs/guide/topics/manifest/manifest-intro.html)

* The "ant.properties" file defines customizable Ant properties for the
  Android build system; in our case we need to define at least the following
  properties (please adapt the respective values to your own environment):

  Unix:                                   Windows:
    sdk.dir=/opt/android-sdk-linux_x86      sdk.dir=c:/Progra~1/android-sdk-windows
    scala.dir=/opt/scala                    scala.dir=c:/Progra~1/scala
    proguard.dir=${sdk.dir}/tools/proguard  proguard.dir=${sdk.dir}/tools/proguard

* The "default.properties" file defines the default API level of an Android
  (for more details see the documentation page
  $ANDROID_SDK_ROOT/docs/guide/appendix/api-levels.html).

* The "build.xml" Ant build script defines targets such as "clean", "install"
  and "uninstall" and has been slightly modified to handle also Scala source
  files. Concretely, we override the default behavior of the "-post-compile"
  target and modify its dependency list by adding the imported target
  "-post-compile-scala":

    <import file="build-scala.xml"/>

    <!-- Converts this project's .class files into .dex files -->
    <target name="-post-compile" depends="-post-compile-scala" />

* The "build-scala.xml" Ant build script defines the targets "compile-scala"
  and "-post-compile-scala" where respectively the "<scalac>" Ant task generates
  Java bytecode from the Scala source files and the "<proguard>" task creates a
  shrinked version of the Scala standard library by removing the unreferenced
  code (see next section for more details). Those two tasks are featured by
  the Scala and ProGuard software distributions respectively.


Project Build
-------------

We assume here the Android emulator is up and running; if not we start it
using the shell command (let us assume the existence of the "API_10"
virtual device) :

   android-app-dev> emulator -no-boot-anim -no-jni -avd API_10 &

Then we move for instance to the "TriviaQuiz" project directory and execute
one of the following Ant targets :

   android-app-dev> cd TriviaQuiz
   TriviaQuiz> ant clean
   TriviaQuiz> ant compile-scala
   TriviaQuiz> ant debug
   TriviaQuiz> ant installd
   (now let us play with our application on the emulator !)
   TriviaQuiz> ant uninstall


===============================================================================


Signing your Applications
-------------------------
All Android applications must be signed. The system will not install an
application that is not signed. You can use self-signed certificates to sign
your applications. No certificate authority is needed. For example:

$ keytool -genkey -v -keystore ~/.android/release.keystore
          -alias android-app-dev -keyalg RSA -validity 10000

See also http://developer.android.com/guide/publishing/app-signing.html


Have fun!
Stephane

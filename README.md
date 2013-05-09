wrapapp
=======

A tiny Android project that produces a wrapped webapp.

To bundle a webapp into an apk that can be installed on Android run (you may need to first
update local.properties to point to your copy of the Android SDK):

ant buildDebug -Dmanifest=http://url/of/my/manifest

The ant build script will do the work downloading your app icons, and preprocess
some source files for your app, and then build a debug build that you can install
on a phone with

adb install -r ./bin/WebApp-debug.apk

Building a release build requires that you can sign the build with a key. The easiest
way to generate a key is to use eclipse, and import your generated code (this folder)
into the project. There are instructions for that (and other build problems) at:

http://www.androidengineer.com/2010/06/using-ant-to-automate-building-android.html

and then add the keystore and alias to ant.properties. You can then build using the
same commands above:

ant buildDebug -Dmanifest=http://url/of/my/manifest
adb install -r ./bin/WebApp-debug.apk

# BioLogin-Dev
> This Is the Development Version of the BioLogin Project
## Main Idea

BioLogin is set to develop a cross-platform solution for multiple biometric authentication methods to be used on Computer Login, File Locking and Password Management. My Mission is to extinct the need of typing an un-secure password and getting towards the new world of biometric authentication.

My goal in this project was to use the Microsoft Azure services for biometric algorithms (because they already exist in the market) and connect them to one product that will support the operating system I created and a variety of other operating systems (macOS, Windows).

My project allows these operating systems to connect to the computer and lock files.
I built an inheritance tree that includes: Authentication, which includes Enroll and Verify functions, and the inheriting classes from general authentication: face scanning, voice scanning, and fingerprint scanning.
In addition, there are accompanied class for various scans, such as the Snap class, which is responsible for the face photography using the computer camera and record class, which is responsible for recording the voice of the user.

There is also the user class with all the necessary attributes that represent a user after a face / voice / fingerprint scan that I upload to a secure database (I use Google Firebase, the Firebase class I created responsible for uploading user information to the Google cloud) So that I can use it later (for example, when I want to connect to a computer).

To lock the files I use the FileLocker class to get File Path and biometric authentication to do Encrypt / Decrypt and disable / enable file / file permissions.
In this case, I use a strong AES encryption. At the moment, the key is randomly generated and securely stored in the cloud, but the ultimate goal is that the encryption key will be taken from user records (eg voice scanning), which are also stored in the cloud.

To enter the operating system, I use a method called Kiosk Mode
This method allows the app to appear in full screen without being able to exit this screen, my app run after the initial login as a startup item. As part of the instructions given to the user, I ask him to cancel the operating system password, so when he turns on the computer he will not have to type a password, but the program will open automatically in full screen without an option to exit until he enters with his biometric authentication.

As a continuation of the project, I want to use the PAM system in Unix operating systems, and its role is to enable various means for approved and safe authentication for the computer. 
I would also like the system to work offline, for that I need to use Open Source libraries instead of Azure, and to use local databases instead of Google Firebase.

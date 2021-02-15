

# IDChatSystem by [Rob174](https://github.com/Rob174) and [nsmaniotto](https://github.com/nsmaniotto/)

Designed and developed within [INSA Toulouse](http://www.insa-toulouse.fr/) OOD/OOP courses, this system based on a Model View Controller (MVC) conception allows to communicate with both private and public networks. Indeed, its hybrid particularity comes from the fact that it uses a distributed architecture within local networks and a centralized one to reach the outside world.


## v1.0.0 features:

- Logging in with a chosen username *(see preview #1)*
- Changing its username *(see preview #2)*
- See online/recent/offline users *(see preview #3/4/5)*
- Getting visually notified when there is an unseen message *(see preview #3)*
- Opening a conversation by selecting the associated correspondent *(see preview #4)*
- Sending and receiving messages *(see preview #4)*
- See messages from old conversations


## Deployment
##### Important 
- This project is using Java Development Kit 15.
- By default, INSA's server (accessible only with a VPN) is used to run the servlet. To have a local servlet and for debugging purposes, the tomcat server URL must be configured in `ServerSendMessage.java:24`.
#### MySQL configuration
In order to be able to retrieve old conversations and to manage logins, the system needs to operate on a local MySQL database.

 1. Create a MySQL user named `idchatsystem_usr` with password `idchatsystem_lgn`.
 2. Grant the listed permissions to `idchatsystem_usr`:
	 - CREATE
	 - DELETE
	 - INSERT
	 - SELECT

#### Shortcut configuration
In order to create a client we will use the same application (jar) for each client with different provided arguments, specifying which type (distant/local) of user will be launched.
There are three arguments to control the application:
 1. `local` : indicating whether if the client is a local (value: `local`) or a distant (value: `distant`) client
 2. `clean` : specifies whether if we want to clean the broadcast receivers ports database used to share them between clients. Can take values `clean` or `noClean`.
 3. `id` (string) : specifies which client we want to launch.

The following shortcut can be created based on the jar located in `\out\artifacts\IDChatSystem_jar\`.

`"C:\path\to\java-15.exe" -jar "C:\path\to\jar\IDChatSystem.jar" local clean id`


## Previews
Preview #1 - Logging in
![login_capture](https://user-images.githubusercontent.com/62234196/107979593-c1bc3480-6fb6-11eb-877d-a6f28e74ce38.png)
Preview #2 - Changing username
![changing_username_capture](https://user-images.githubusercontent.com/62234196/107979620-cbde3300-6fb6-11eb-8df8-640a5782bd0b.png)
Preview #3 - Sending/Receiving a message and displaying notification (conversation shortcut in blue)
![message_notification](https://user-images.githubusercontent.com/62234196/107979619-cbde3300-6fb6-11eb-888a-29ffc6c10b4f.png)
In order to view the previously received message, the user has to click on the `select` button or on the "blue area"
Preview #4 - Chatting / Opening a conversation by clicking on the "select" button of the wanted correspondent![message_view](https://user-images.githubusercontent.com/62234196/107979617-cb459c80-6fb6-11eb-8b4e-deb3c97ccd73.png)
Preview #5 - Viewing messages from an offline correspondent
![offline](https://user-images.githubusercontent.com/62234196/107979615-caad0600-6fb6-11eb-9e99-4a4c416f67f4.png)
#CIS 457 Project 1

##Simple Java FTP client/server implementation

###Overview: 
Project 2 is a peer-to-peer network application that is designed to provide a system for users to share files with one another using FTP. The basic architecture is that of a centralized server which stores a list of all files that users have currently registered with the centralized server. Users can connect to the central server to search for files hosted by other users (peers). Clients can then connect directly to the file host server and use FTP to retrieve the file. The remote clients and servers function as standard FTP clients and servers and operate per RFC 959.

###Design:
The design of our project is composed of six main java classes: FTP Client, FTP Server, Central Server and three data classes used to store and manipulate user and file information. The structure of the peer-to-peer network is created by the peer servers and peer clients, with peers communicating with one another directly via FTP protocol and connecting to a central server instance to register files and retrieve remote file details.

We utilized the FTP server class that was created in Project 1 to provide the services of a basic FTP Server that is run on all active peers. Its functionality is essentially identical to that of Project 1 with clients connecting to the server and sending requests and data back and forth using control and data TCP sockets.

The remote peer client functionality is implemented using a modified version of the FTP Client class from Project 1. The client class was expanded to implement a Swing GUI interface that allows the user easily interact with the underlying functions of the client class. Two separate instances of control TCP sockets were created to allow the client to simultaneously communicate with both the Central Server and a remote peer server. Multiple unique functions were created to handle connection to the central server, the passing of user data and files to the central server and processing of search results sent from the central server. Users are able to connect to a specified central server or remote (peer) server at will. They can perform keyword searches once connected to a central server and have the option to disconnect from the central server (without removing uploaded file information) or deregister their information from the central server entirely.

The central server aspect of this project was implemented using a modified version of our basic FTP server. We stripped out all functionality that was not necessary and added various functions that were designed to handle and process the data passed to and from the remote client. The central server implemented two data classes that were created to allow the easy storage and access of user information and registered file details. Users are automatically registered on the central server upon initial connection and with each subsequent connection their data is updated. Users also have the option to deregister from the central server, removing all references to their shared files.

All three main classes of Project 2 were implemented to be multi threaded, with multiple clients able to connect to both the remote (peer) server and the central server concurrently as well as access and modify data stored on the central server concurrently. Each server class is implemented using a threaded design with each unique connection from a client class being handled by its own independent logic thread. The two data classes utilized by the central server are both thread safe with each function that access or modifies stored data implemented as synchronized.

###Features:
*Project 2 features a GUI interface implemented with the javax.swing library.
*Concurrent remote and central servers implemented using a threaded architecture
*Synchronous access to data structures (all threads can modify and read from data structures concurrently)
*Users register relevant information to central server on connect (username, hostname (IP), connection speed)
*Users store list of local files on local document. This document is automatically synced to the central server on peer connect and is used to create and maintain a list of files available on each remote server.
*User information and file details are stored in database files on central server
*Clients can connect/disconnect from central server without affecting the data that the central server is tracking for that user
*Users can deregister from the central server, removing all tracked information for that user
*Search functionality that allows users to search for a particular file tracked by the central server using a keyword
*Object Output / Input Streams are used to send data vectors between central server and peer client

# Claim your Packtpub's daily free book. 

Packtpub-claim-free-book is a little app with only one purpose, claim your free book.
If you are tired to claim it everyday manually, save your time and try it. :)

### How it works
It just send a POST request using your credentials to be able to claim the book.


###Building it
```sh
$ git clone https://github.com/spolti/aleatory-projects.git
$ cd aleatory-projects/packtpub-claim-free-book
$ mvn package
```
It will generate 2 jars, we will use the jar with dependencies.

### Running it
In the very first time you execute it, you need to provide your packtpub's credentials, example:

```sh
$ java -jar /sources/packtpub-claim-free-book/target/packtpub-claim-free-book-1.0-SNAPSHOT-jar-with-dependencies.jar 
Properties File not foud. Please supply the username and password.
java -jar -Dcredentials=myemail@example.com:myPassword packtpub-claim-free-book.jar
```

To provide your credentials just use the **credentials** System Property, example:
```sh
$ java -jar -Dcredentials=myemail@.mydomain.com:MySecretPassword /sources/packtpub-claim-free-book/target/packtpub-claim-free-book-1.0-SNAPSHOT-jar-with-dependencies.jar
Credentials received [myemail@.mydomain.com:MySecretPassword] - Trying to create a new properties file [/home/fspolti/claim-free-book.properties].
File created.
Using the credentials [myemail@.mydomain.com:MySecretPassword]
Login form get: HTTP/1.1 200 OK
Trying to claim the free book against url [https://www.packtpub.com/freelearning-claim/16787/21478]
HTTP Response code [HTTP/1.1 200 OK]. Book successfully claimed, check your account. :)
```

IF something goes wrong or if your credentials is not valid, you will get the following message:
```sh
Failed to claim the book, verify your credentials and try again.
```

To override your credentials just provide your credentials using the **credentials** System property as the example above.

### Version
1-0.SNAPSHOT


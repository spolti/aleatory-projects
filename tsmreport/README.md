tsmreport
=========

This is a simple tool that's send through email the tsm backup reports.

Building:
=========

$ git clone https://github.com/spolti/tsmreport.git
$ cd tsmreport
$ mvn package

Encrypting an Password
=================

$ java -jar tsmreports.jar encode <your password>

Then paste the encoded password in the file application.properties on tsmserver.password field.


Configuring in a Windows Server schedule
========================================

Use the command java and as a paramter use "-jar tsmreport.jar"

Informations
============

For now this jar is for Windows only.



# grison
Java API for IP cameras such as those manufactured by Foscam

## Quick Start
```
$ git clone https://github.com/jtgeiger/grison.git
$ cd grison
$ mvn clean verify exec:java -Dexec.args="cameraAddress cameraPort username password"
```
Example:
```
$ mvn clean verify exec:java -Dexec.args="192.168.1.20 80 camuser myPassw0rd"
```

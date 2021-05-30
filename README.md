# grison

Java API for IP cameras such as those manufactured by Foscam

## Quick Start

### Clone the repo

```
$ git clone https://github.com/jtgeiger/grison.git
$ cd grison
```

### Spring Boot way

```
$ ./mvnw install && ./mvnw spring-boot:run -pl demo -Dspring-boot.run.arguments="--connection.host=<CAMERA_ADDRESS> --connection.port=<CAMERA_PORT> --connection.username=<CAMERA_USERNAME> --connection.password=<CAMERA_PASSWORD>"
```

Example:

```
$ ./mvnw install && ./mvnw spring-boot:run -pl demo -Dspring-boot.run.arguments="--connection.host=192.168.1.20 --connection.port=80 --connection.username=camuser --connection.password=myPassw0rd"
```

### From an existing jar

```
$ java -jar target/grison-<VERSION>.jar --connection.host=<CAMERA_ADDRESS> --connection.port=<CAMERA_PORT> --connection.username=<CAMERA_USERNAME> --connection.password=<CAMERA_PASSWORD>
```

## [Developers] Releasing

```
$ mvn release:prepare
$ git checkout grison-0.1.0
$ JAVA_HOME=/usr/lib/jvm/java-15-openjdk/ mvn clean javadoc:jar source:jar install
$ cd ~/.m2/repository/com/sibilantsolutions/grison/grison/0.1.0
$ gpg2 -ab grison-0.1.0.jar
$ gpg2 -ab grison-0.1.0-javadoc.jar
$ gpg2 -ab grison-0.1.0.pom
$ gpg2 -ab grison-0.1.0-sources.jar
$ jar -cvf bundle.jar grison-0.1.0*
```

- The `gpg2` commands will generate *.asc signature files. These should be included in `bundle.jar`.
- Deploy release artifacts into the staging repository https://oss.sonatype.org/service/local/staging/deploy/maven2
  - Folow instructions at https://central.sonatype.org/publish/release/
  - Note that the legacy host must be used: https://oss.sonatype.org/

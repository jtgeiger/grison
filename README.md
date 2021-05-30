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

## [Developers] Releasing

```
$ mvn release:prepare
$ git checkout grison-0.1.0
$ JAVA_HOME=/usr/lib/jvm/java-15-openjdk/ mvn clean javadoc:jar source:jar install
$ cd ~/.m2/repository/com/sibilantsolutions/grison/grison/0.1.0
$ jar -cvf bundle.jar grison-0.1.0*
```

- Deploy release artifacts into the staging repository https://oss.sonatype.org/service/local/staging/deploy/maven2
  - Folow instructions at https://central.sonatype.org/publish/release/
  - Note that the legacy host must be used: https://oss.sonatype.org/

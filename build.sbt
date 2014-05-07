organization := "tv.cntt"

name := "xitrum"

version := "3.8-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

// xitrum.util.FileMonitor requires Java 7
javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

// Put config directory in classpath for easier development (sbt console etc.)
unmanagedBase in Runtime <<= baseDirectory { base => base / "config" }

// Most Scala projects are published to Sonatype, but Sonatype is not default
// and it takes several hours to sync from Sonatype to Maven Central
resolvers += "SonatypeReleases" at "http://oss.sonatype.org/content/repositories/releases/"

// Projects using Xitrum must provide a concrete implentation of SLF4J (Logback etc.)
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.7" % "provided"

// An implementation of SLF4J is needed for log in tests to be output
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2" % "test"

// Netty is the core of Xitrum's HTTP(S) feature
libraryDependencies += "io.netty" % "netty-all" % "4.0.19.Final"

// Javassist boosts Netty 4 speed
libraryDependencies += "org.javassist" % "javassist" % "3.18.1-GA"

// For clustering SockJS; Akka is included here
libraryDependencies += "tv.cntt" %% "glokka" % "1.8"

// Redirect Akka log to SLF4J
// (akka-slf4j version should be the same as the Akka version used by Glokka above)
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.3.2"

// For file watch
// (akka-agent is added here, should ensure same Akka version as above)
libraryDependencies += "com.beachape.filemanagement" %% "schwatcher" % "0.1.1"

// For scanning routes
libraryDependencies += "tv.cntt" %% "sclasner" % "1.6"

// For binary (de)serializing
libraryDependencies += "com.twitter" %% "chill-bijection" % "0.3.6"

// For JSON (de)serializing
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.8"

// For i18n
libraryDependencies += "tv.cntt" %% "scaposer" % "1.3"

// For jsEscape
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.3.2"

// For compiling CoffeeScript to JavaScript
libraryDependencies += "tv.cntt" % "rhinocoffeescript" % "1.7.1"

// For metrics
libraryDependencies += "nl.grons" %% "metrics-scala" % "3.0.5_a2.3"

// For metrics
libraryDependencies += "com.codahale.metrics" % "metrics-json" % "3.0.2"

// For test
libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.3" % "test"

//------------------------------------------------------------------------------
// JSON4S uses scalap 2.10.0, which in turn uses scala-compiler 2.10.0, which in
// turn uses scala-reflect 2.10.0. We need to force "scalaVersion" above, because
// Scala annotations (used by routes and Swagger) compiled by a newer version
// can't be read by an older version.
//
// Also, we must release a new version of Xitrum every time a new version of
// Scala is released.

libraryDependencies <+= scalaVersion { sv => "org.scala-lang" % "scalap" % sv }

//------------------------------------------------------------------------------

// Skip API doc generation to speedup "publish-local" while developing.
// Comment out this line when publishing to Sonatype.
publishArtifact in (Compile, packageDoc) := false

name := "planning"

version := "1.0"

lazy val `planning` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(jdbc,
  cache,
  ws,
  specs2 % Test,
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.slick" %% "slick-extensions" % "3.1.0",
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "org.suecarter" % "freeslick_2.11" % "3.1.1.1",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0"
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"
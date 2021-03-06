name := "notepadApi"
 
version := "1.0" 
      
lazy val `notepadapi` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(ehcache , ws , specs2 % Test , guice,
  "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5",
  "com.h2database" % "h2" % "1.4.192",
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0"

)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      
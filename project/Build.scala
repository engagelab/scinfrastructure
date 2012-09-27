import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {
  val appName = "scinfrastructure"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.apache.camel" % "camel-core" % "2.9.2",
    "org.apache.camel" % "camel-csv" % "2.9.2",
    "org.apache.camel" % "camel-bindy" % "2.9.2",
    "org.apache.camel" % "camel-jackson" % "2.9.2",
    "org.apache.camel" % "camel-http" % "2.9.2",

    //Google Drive
    //"com.google.apis" % "google-api-services-drive" % "v2-rev9-1.8.0-beta2",
    //"com.google.apis" % "google-api-services-oauth2" %"v2-rev15-1.8.0-beta",

	//jbfilter
	//"org.jbfilter" % "jbfilter" % "1.6.1-SNAPSHOT",

	//flexJSON
	"net.sf.flexjson" % "flexjson" % "2.1",
	
      //Apache
    "commons-io" % "commons-io" % "2.3",
    
    
    // Metrics
    "com.yammer.metrics" % "metrics-core" % "2.1.2",

    // Guice
    "com.google.inject" % "guice" % "3.0",
    "com.google.inject.extensions" % "guice-assistedinject" % "3.0",
    "com.google.inject.extensions" % "guice-multibindings" % "3.0",
    "com.google.inject.extensions" % "guice-throwingproviders" % "3.0",

    // Morphia
    "com.google.code.morphia" % "morphia" % "0.99.1-SNAPSHOT", // checkout Morphia manually and execute 'mvn install'
    "com.google.code.morphia" % "morphia-logging-slf4j" % "0.99"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    lessEntryPoints <<= baseDirectory(_ ** "camelcode.less"),

    resolvers += "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository",
    resolvers += "Codehaus Repository" at "http://repository.codehaus.org/",
    resolvers += "Morphia Repository" at "http://morphia.googlecode.com/svn/mavenrepo/",
    resolvers += "Ibiblio" at "http://www.ibiblio.org/maven2/",
    resolvers += "Java.NET" at "http://download.java.net/maven/2"
    //resolvers += "google-api Repository" at "http://mavenrepo.google-api-java-client.googlecode.com/hg/"

    //SbtIdeaPlugin.defaultClassifierPolicy := true
  )
}
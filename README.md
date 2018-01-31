## bc-citynodes

This project uses an undirected graph to represent teleportation paths between cities. 
The goal is to be able to answer to following questions:
1. What cities can I reach from city X with a maximum of N jumps?
2. Can someone get from city X to city Y?
3. Starting in city X, is it possible to travel in a loop (leave the city on one route and return on another, without traveling along the same route twice)?

## Running
Running this sample requires git, a recent version of java and maven.

    $ git clone git@github.com:eacaps/bc-citynodes.git
    $ cd bc-citynodes
    $ ./build-and-run.sh data/sample.cities
    
It accepts a file name as the input and will output the statements to answer the questions.
    
## Sample output

    eacaps$ java -version
    java version "1.8.0_144"
    Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
    Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)
    eacaps$ mvn -version
    Apache Maven 3.5.2 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T03:58:13-04:00)
    Maven home: /usr/local/Cellar/maven/3.5.2/libexec
    Java version: 1.8.0_144, vendor: Oracle Corporation
    Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre
    Default locale: en_US, platform encoding: UTF-8
    OS name: "mac os x", version: "10.11.6", arch: "x86_64", family: "mac"
    eacaps$ ./build-and-run.sh data/sample.cities 
    [INFO] Scanning for projects...
    [WARNING] 
    [WARNING] Some problems were encountered while building the effective model for pub.eacaps:bc-citynodes:jar:1.0-SNAPSHOT
    [WARNING] 'dependencies.dependency.version' for junit:junit:jar is either LATEST or RELEASE (both of them are being deprecated) @ line 14, column 22
    [WARNING] 
    [WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
    [WARNING] 
    [WARNING] For this reason, future Maven versions might no longer support building such malformed projects.
    [WARNING] 
    [INFO] 
    [INFO] ------------------------------------------------------------------------
    [INFO] Building bc-citynodes 1.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO] 
    [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ bc-citynodes ---
    [WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
    [INFO] Copying 0 resource
    [INFO] 
    [INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ bc-citynodes ---
    [INFO] Changes detected - recompiling the module!
    [WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
    [INFO] Compiling 4 source files to /Users/eacaps/Documents/jobs/bytecubed/bc-citynodes/target/classes
    [INFO] 
    [INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ bc-citynodes ---
    [WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
    [INFO] skip non existing resourceDirectory /Users/eacaps/Documents/jobs/bytecubed/bc-citynodes/src/test/resources
    [INFO] 
    [INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ bc-citynodes ---
    [INFO] Nothing to compile - all classes are up to date
    [INFO] 
    [INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ bc-citynodes ---
    [INFO] Surefire report directory: /Users/eacaps/Documents/jobs/bytecubed/bc-citynodes/target/surefire-reports
    
    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running pub.eacaps.citynodes.CitiesTest
    Tests run: 17, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.229 sec
    Running pub.eacaps.citynodes.InputParserTest
    Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.054 sec
    
    Results :
    
    Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
    
    [INFO] 
    [INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ bc-citynodes ---
    [INFO] Building jar: /Users/eacaps/Documents/jobs/bytecubed/bc-citynodes/target/bc-citynodes-1.0-SNAPSHOT.jar
    [INFO] 
    [INFO] --- maven-install-plugin:2.4:install (default-install) @ bc-citynodes ---
    [INFO] Installing /Users/eacaps/Documents/jobs/bytecubed/bc-citynodes/target/bc-citynodes-1.0-SNAPSHOT.jar to /Users/eacaps/.m2/repository/pub/eacaps/bc-citynodes/1.0-SNAPSHOT/bc-citynodes-1.0-SNAPSHOT.jar
    [INFO] Installing /Users/eacaps/Documents/jobs/bytecubed/bc-citynodes/pom.xml to /Users/eacaps/.m2/repository/pub/eacaps/bc-citynodes/1.0-SNAPSHOT/bc-citynodes-1.0-SNAPSHOT.pom
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 5.006 s
    [INFO] Finished at: 2018-01-31T00:58:18-05:00
    [INFO] Final Memory: 17M/147M
    [INFO] ------------------------------------------------------------------------
    cities from Seattle in 1 jumps: Baltimore, New York
    cities from Seattle in 2 jumps: Baltimore, Washington, New York, Philadelphia
    can I teleport from New York to Atlanta: yes
    can I teleport from Oakland to Atlanta: no
    loop possible from Oakland: yes
    loop possible from Washington: no
    
## Testing
I wrote some extensive unit tests for as many cases as I could think of. You can run the tests with: 

    $ mvn test
    
## Questions
I handled a few extra cases where you ask questions about non-existant cities and if you attempt to teleport
to the same city but it is possible I missed something.
I didn't make the input parsing very resilient, so lines that are incomplete or don't follow the doc will break
the program. I also went a little deep attempting to optimize the city hopping search, might not have been the best
use of my time, but hopefully it improved the performance.
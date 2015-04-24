# Starfire Installation #
> ## 1. Install Java JDK6 ##
> > ### Windows: ###
      1. Download and install Java JDK 6: [JDK6](http://www.oracle.com/technetwork/java/javase/downloads/index.html). A 32bit version is needed for JpCap to work.
      1. Set 'JAVA\_HOME' environment variable to point to installation directory. _For example: 'C:\Program Files\Java\jdk1.6.0\_25'._
      1. Add '%JAVA\_HOME%\bin' to 'path' environment variable.
> > ### Ubuntu: ###
      1. Uninstall Open JDK if it's installed in your system.
      1. Download and install Java JDK 6: [JDK6](http://www.oracle.com/technetwork/java/javase/downloads/index.html). A 32bit version is needed for JpCap to work.
      1. Add this location to the path by modifying '.bashrc' file located in your home folder. For example, the following lines could be added to '.bashrc' file:
```
export JAVA_HOME=/home/username/Descargas/JAVA/jdk1.6.0_23
export PATH=$JAVA_HOME/bin:$PATH
```

> ## 2. Install Maven ##
> > ### Windows: ###
      1. Download the last version of Maven2 .zip from [Maven\_2.2.1](http://maven.apache.org/download.html#Maven_2.2.1).
      1. Unzip it in the location where you want to have it installed.
      1. Modify the path by adding the location of the bin directory located inside the unzipped folder. _For example: C:\Program Files\apache-maven-2.2.1\bin._
      1. Test Maven2 runs correctly by writing 'mvn' in the console.
> > ### Ubuntu: ###
> > > Go to Synaptics Package Manager and install _maven2_.


> ## 3. Install Eclipse and proper plugins ##
    1. Download and install Eclipse Classic: [Eclipse](http://www.eclipse.org/downloads/)
    1. Set in .ini file in eclipse folder, the launching option
```
    -vm
    C:/...path_to_java_jdk.../bin
```
    1. Install **Subclipse\_1.4** plugin: [Subclipse\_1.4](http://subclipse.tigris.org/)
    1. Install **m2eclipse** plugin: [m2eclipse](http://m2eclipse.sonatype.org/installing-m2eclipse.html)

> ## 4. Prepare the project and install dependencies: ##
    1. Download dependenciesInstaller project from svn.
    1. Run `Installer.java` class located in `installer` package. _Note: Modify class attributes according to your system and maven location._
    1. Download starfire project from svn.
    1. Execute `Maven Install` over the whole project.

> ## 5. Install Jpcap ##
> > ### Windows: ###
      1. Download WinPcap: [WinPcap](http://www.winpcap.org/install/default.htm).
      1. Download Jpcap 0.7: [Jpcap\_0.7](http://netresearch.ics.uci.edu/kfujii/Jpcap/doc/download.html).
> > ### Ubuntu: ###
      1. Go to Synaptics Package Manager and install _libpcap_.
      1. Download Jpcap 0.7: [Jpcap\_0.7](http://netresearch.ics.uci.edu/kfujii/Jpcap/doc/download.html). _Note: **x86 archictecture needed**._
      1. The application jar has to be executed with sudo, since it needs privileges.


> ## 6. Install VLC media player ##
    1. Download and install [VLC](http://www.videolan.org/vlc/). In Windowa PATH variable needs to be changed to run from console.

> ## 7. Changes in the project ##
> > In es.upm.dit.gsi.starfire.diagnosisAgent there is VlcLauncherPlan.java.
> > You need to set the values of the following variables: server ip address, video name, and operative system (Windows or Linux).
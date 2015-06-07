# pmd-bluej

This BlueJ extension was developed under Linux for both BlueJ for Linux and
BlueJ for Windows use.

Use:

In BlueJ, open to the main class window that displays your classes, right-click
on a class object, when the menu comes up, at the bottom of it you will see
PMD as an option, select that and click "check code"  A popup will then run
PMD against your class source file and then display a popup listing all of
the things PMD does not like about your code.

Installation:

The prior version of this extension from 2001 included PMD, so to use it PMD
did not have to be installed.  This version of this extension does not include PMD
so you must install PMD under your operating system of choice and you must get it
operating at the command line.

Normal command line operation of PMD produces an
HTML file loadable by web browser, a typical command invocation under Linux
for PMD installed in your home directory might be:

cd ~
pmd-bin-5.3.2/bin/run.sh pmd -dir  ~/MyProject -f html -rulesets java-basic,java-design > MyProject_pmd_report.html

Under Windows it is usually most convenient to install PMD in the root so as to
avoid spaces in directory names, (ie: Program Files) or PMD might not execute
properly.  A typical command invocation under Windows for PMD might be:

c:\pmd-bin-5.3.2\pmd -d c:\docume~1\tedm\mydocu~1\javaproj\myproj -f html -R java-unusedcode > myproj_report.html

You can install PMD from a prebuilt file or download it and build it yourself.

Since this extension is not portable between Linux or Windows there are 2 .jar files
here, PMDExecExt-win.jar for Windows and PMDExecExt.jar for Linux/Unix.

To install under Linux, assuming you have installed BlueJ in your home directory
(the standard location for version 3.1.5 of BlueJ for Linux) copy the
PMDExecExt.jar file to ~/bluej/lib/extensions directory, then close and open
BlueJ.

To install under Windows, assuming you have installed BlueJ into the normal location,
copy the PMDExecExt-Win.jar file to C:\Program Files\BlueJ\lib\extensions.

This BlueJ extension also requires a wrapper script to be created.  This script sets the
rulesets that PMD will run against your code and (optionally) sets the version of PMD
should you have multiple versions of PMD installed, as well as the version of Java your
checking against.  It MUST be tailored to the PMD location, version, and options on
your system!

Linux/Unix and Windows require different scripts!

For Linux the script must be located in your PATH, for example a private binary
directory off your home directory.  It must be named "pmd.sh" and there is an
example in the distribution.

For Windows, the preferred location for the script is in c:\windows.  (You can also put it
anywhere else that is available to the PATH)  The script must be named "pmdsh.bat" and
there is an example in the distribution.


Please see the PMD documentation for the rulesets available.

Building:

If you want to build it: (under Linux):

Install Maven

Find the BlueJ extensions jar file, this file (under Linux) is located
in ~\bluej\lib\ and (under Windows) it is located
in C:\Program Files\BlueJ\lib and is named bluejext.jar.

copy bluejext.jar from your BlueJ lib directory to the lib directory

mvn package

mv target/pmd-bluej-1.0-SNAPSHOT.jar PMDExecExt.jar

mvn clean

To build the Windows version:
(I know this is a bit of a hack)

Modify MenuBuilder.java to comment the Unixisms and uncomment the Windows
stuff

mvn package

mv target/pmd-bluej-1.0-SNAPSHOT.jar PMDExecExt-win.jar

mvn clean

Change the comments in MenuBuilder.java back and save file.

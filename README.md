# pmd-bluej

This BlueJ extension was developed under Linux for both BlueJ for Linux and
BlueJ for Windows use.

## Download

*   PMD can be downloaded here: <https://sourceforge.net/projects/pmd/files/pmd/>
*   The PMD Extension for BlueJ is here: <https://sourceforge.net/projects/pmd/files/pmd-bluej/>

## Use:

In BlueJ, open to the main class window that displays your classes, right-click
on a class object, when the menu comes up, at the bottom of it you will see
PMD as an option, select that and click "check code". A popup will then run
PMD against your class source file and then display a popup listing all of
the things PMD does not like about your code.

## Installation:

The prior version of this extension from 2001 included PMD, so to use it PMD
did not have to be installed.  This version of this extension does not include PMD
so you must install PMD under your operating system of choice.

First step is to install PMD.
You can install PMD from a prebuilt file or download it and build it yourself.
Remember the path under where you have extracted
the zip file, e.g. under Linux you could install it under `~/pmd-bin-5.3.2/`.
Under Windows it is usually most convenient to install PMD in the root so as to
avoid spaces in directory names, (ie: Program Files) or PMD might not execute
properly; the installation path could be `c:\pmd-bin-5.3.2\`.

Second step is to install the PMD Extension for BlueJ.
This extension works both under Linux and Windows and there is only one jar file (since version 2.1).

To install under Linux, assuming you have installed BlueJ in your home directory
(the standard location for version 3.1.5 of BlueJ for Linux) copy the
`PMDExecExt.jar` file to `~/bluej/lib/extensions` directory, then close and open
BlueJ.

To install under Windows, assuming you have installed BlueJ into the normal location,
copy the `PMDExecExt.jar` file to C:\Program Files\BlueJ\lib\extensions.

The third and last step is, to configure the BlueJ extension. In BlueJ, go to
"Tools / Preferences / Extensions". Select or enter the "Path to PMD installation"
that you have remembered from step one above. You can also fine-tune the
"PMD Options"; by default, the rulesets "java-basic" and "java-design" are
executed.

Please see the PMD documentation for the rulesets available.

## Contributing

### Pull requests

Easiest way to contribute is via pull requests on <https://github.com/pmd/pmd-bluej>. Pull Requests are always
welcome.

### Building

You need to have maven installed.

    mvn clean package

Take the generated jar file `target/PMDExecExt.jar` and copy it into
BlueJ's extension directory.

### Releasing a new version

*   Update [ReleaseNotes.md](https://github.com/pmd/pmd-bluej/blob/master/ReleaseNotes.md)
    with new version a release date
*   Update the version: `mvn versions:set -DnewVersion=2.1`
*   Commit: `git commit -a -m "Prepare new version 2.1"`
*   Tag: `git tag 2.1`
*   Push: `git push origin master; git push origin tag 2.1`
*   Build it: `mvn clean package`
*   Upload the jar file to: <https://sourceforge.net/projects/pmd/files/pmd-bluej/>. Also upload
    the ReleaseNotes as `README.md`.
*   Update [ReleaseNotes.md](https://github.com/pmd/pmd-bluej/blob/master/ReleaseNotes.md)
    and add a placeholder for the next version
*   Commit and Push
*   Post a news on <https://sourceforge.net/p/pmd/news/>.

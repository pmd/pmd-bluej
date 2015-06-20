# pmd-bluej Release Notes


## ????? - 2.1.0

* The extension detects the operating system (Windows vs. Linux) and
  handles both. So, from now on, there is only one jar file.
* There is no extra shell script needed anymore.
* The "Path to PMD Installation" and the "PMD Options" can be configured
  now.

### Installation

* Make sure, you have PMD installed. It is not anymore included with this extension.
* Copy the file `PMDExecExt.jar` to BlueJ's extension directory.
* Make sure, you have the "Path to PMD Installation" configured in BlueJ.

See <https://github.com/pmd/pmd-bluej/blob/master/README.md> for more information.


## 08-June-2015 - 2.0.0

* pmd-bluej extension uses now a PMD installed on the local pc
* This allows you to use the latest PMD version (e.g. 5.3.2)

### Installation

The prior version of this extension from 2001 included PMD, so to use it, PMD
did not have to be installed.  This version of this extension does not include PMD
so you must install PMD under your operating system of choice and you must get it
operating at the command line.

There are two versions of the extension:

1. `PMDExecExt.jar` for Linux
2. `PMDExecExt-win.jar` for Windows

See <https://github.com/pmd/pmd-bluej/blob/2.0/README.md> for more information.



# pmd-bluej Release Notes

## ??-?????-?? - 5.0.1

*   Support PMD 7 (in addition to PMD 6)
*   Changed default PMD options to `-f text -R rulesets/java/quickstart.xml`. Note: You might need to adjust this,
    if migrating from PMD 6 to PMD 7. The new default options work with both versions.
*   Fixed [#7: PMD distribution changed the name of the entry point script from run.sh to pmd](https://github.com/pmd/pmd-bluej/issues/7)

## 25-February-2021 - 5.0.0

*   Updated minimum required Java Version to **Java 11**.
*   The bluej extension now works with BlueJ 5.0.0
*   Fixed [#4: Support BlueJ 5](https://github.com/pmd/pmd-bluej/issues/4)

## 16-December-2017 - 2.2.0

*   Fixed [#1: no PMD item in context menu on BlueJ 4.0.1 on a Mac](https://github.com/pmd/pmd-bluej/issues/1)
*   Fixed [#2: BlueJ Won't Start](https://github.com/pmd/pmd-bluej/issues/2)
*   Updated minimum required Java Version to **Java 1.7**.
*   The bluej extension now works with BlueJ 4.1.2

## 27-June-2015 - 2.1

* The extension detects the operating system (Windows vs. Linux) and
  handles both. So, from now on, there is only one jar file.
* There is no extra shell script needed anymore.
* The "Path to PMD Installation" and the "PMD Options" can be configured
  now.

### Installation

* Make sure, you have PMD installed. It is not anymore included with this extension.
* Copy the file `PMDExecExt.jar` to BlueJ's extension directory.
* Make sure, you have the "Path to PMD Installation" configured in BlueJ.

See <https://github.com/pmd/pmd-bluej/blob/2.1/README.md> for more information.


## 08-June-2015 - 2.0

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



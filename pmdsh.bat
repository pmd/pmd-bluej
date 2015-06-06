@echo off
java -classpath c:\pmd-bin-5.3.2\lib\* net.sourceforge.pmd.PMD -dir " %1 %2 %3 %4 %5 %6 %7 " -format text -R java-basic,java-design -version 1.7 -language java
pause
exit
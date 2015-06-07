#!/bin/sh
cd  /home/tedm/pmd-bin-5.3.2/bin
./run.sh pmd -dir $1 -rulesets java-basic,java-design

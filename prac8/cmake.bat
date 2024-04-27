@echo off
rem batch file for making a Coco/R Java application - CSC 301 2012
rem you may need to set CLASSPATH
rem set CLASSPATH=.
if "%1" == "" goto missing
if not exist %1 md %1
java -jar Coco.jar %1.atg -options m %2 %3 %4 %5 %6
if errorlevel 1 goto stop
if not exist %1.java goto stop
move Parser.java %1
move Scanner.java %1
move %1.java %1
javac -d . -nowarn %1\*.java 2>errors
if errorlevel 1 goto errors
echo Compiled
goto stop
:errors
echo Java errors - see file "errors"
goto stop
:missing
echo no grammar file specified - use cmake ATGFile [options]
:stop

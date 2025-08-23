@echo off
cd /d "%~dp0.."

REM create bin directory if it doesn't exist
if not exist bin mkdir bin

REM delete output from previous run

REM compile starting from main class; sourcepath points at the source root
javac -Xlint:none -d bin -sourcepath src\main\java src\main\java\brobot\BroBot.java

IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath bin brobot.BroBot < text-ui-test\input.txt > text-ui-test\ACTUAL.txt

REM compare the output to the expected output
FC text-ui-test\ACTUAL.txt text-ui-test\EXPECTED.txt

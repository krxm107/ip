@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run

REM compile task first
javac -Xlint:none -d ..\bin ..\src\main\java\brobot\task\*.java

REM compile brobot, with classpath
javac -cp ..\bin -Xlint:none -d ..\bin ..\src\main\java\brobot\*.java

IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin brobot.BroBot < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT

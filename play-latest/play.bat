:begin
@echo off

setlocal

set p=%~dp0
set p=%p:\=/%
set buildScript=%~dp0framework\build.bat
set additionalArgs=%*

if exist "conf\application.conf" goto existingApplication

:noApplication
java -Dsbt.ivy.home=%~dp0repository -Dplay.home=%~dp0framework -Dsbt.boot.properties="file:///%p%framework/sbt/play.boot.properties" -jar %~dp0framework\sbt\sbt-launch.jar %*

goto end

:existingApplication
if not "%1" == "clean-all" goto runCommand

:cleanCache
if exist "target" rmdir /s /q target
if exist "tmp" rmdir /s /q tmp
if exist "logs" rmdir /s /q logs
if exist "project\target" rmdir /s /q project\target
if exist "project\project" rmdir /s /q project\project
if exist "dist" rmdir /s /q dist

shift
set additionalArgs=%additionalArgs:*clean-all=%
if "%1" == "" goto endWithMessage

:runCommand
if "%1" == "" goto enterConsole

if "%1" == "debug" goto setDebug
goto enterConsoleWithCommands

:setDebug
set JPDA_PORT=9999
shift
set additionalArgs=%additionalArgs:*debug=%

if "%1" == "" goto enterConsole

:enterConsoleWithCommands

call %buildScript% %additionalArgs%
goto end

:enterConsole

call %buildScript% play 
goto end

:endWithMessage
echo [info] Done!

:end
endlocal
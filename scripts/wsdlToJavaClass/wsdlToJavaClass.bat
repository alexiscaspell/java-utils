@echo off

rem ====================================
rem USER VARS
rem ====================================

set JAVA_PACKAJE_NAME=$1
set WSDL_FILE_NAME=$2


rem ====================================
rem BACTH VARS
rem ====================================
set IN_PATH=%CD%\in
set OUT_PATH=%CD%\out
set CXF_PATH=%CD%\cxf
set WSDL_FULL_PATH=%IN_PATH%\%WSDL_FILE_NAME%.wsdl

echo RUTAS:
echo %IN_PATH%
echo %OUT_PATH%
echo %CXF_PATH%
echo %WSDL_FULL_PATH%

rem ====================================
rem EXECUTION
rem ====================================
call %CXF_PATH%\bin\wsdl2java.bat -p %JAVA_PACKAJE_NAME% -d %OUT_PATH% -compile %WSDL_FULL_PATH%

pause
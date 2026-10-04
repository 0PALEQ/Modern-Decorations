@echo off
setlocal EnableExtensions EnableDelayedExpansion

set "PROJECT_ROOT=%~dp0"
set "ORIGINAL_PATH=%PATH%"
set "FAILED_TARGETS="

call :find_java 17 JAVA17_HOME
if errorlevel 1 exit /b 1
call :find_java 21 JAVA21_HOME
if errorlevel 1 exit /b 1
call :find_java 25 JAVA25_HOME
if errorlevel 1 exit /b 1

echo Using Java 17: %JAVA17_HOME%
echo Using Java 21: %JAVA21_HOME%
echo Using Java 25: %JAVA25_HOME%
echo.

if /I "%~1"=="--check-jdks" (
    echo All required JDKs were found.
    exit /b 0
)

call :build fabric_1.20.1 "%JAVA17_HOME%"
call :build fabric_1.21.1 "%JAVA21_HOME%"
call :build fabric_1.21.11 "%JAVA21_HOME%"
call :build fabric_26.1.2 "%JAVA25_HOME%"
call :build fabric_26.2 "%JAVA25_HOME%"
call :build forge_1.20.1 "%JAVA17_HOME%"
call :build neoforge_1.21.1 "%JAVA17_HOME%"
call :build neoforge_1.21.11 "%JAVA17_HOME%"
call :build neoforge_26.1.2 "%JAVA17_HOME%"

echo.
if defined FAILED_TARGETS (
    echo Build failed for:%FAILED_TARGETS%
    exit /b 1
)

echo All versions built successfully.
exit /b 0

:build
set "TARGET=%~1"
set "JAVA_HOME=%~2"
set "PATH=%JAVA_HOME%\bin;%ORIGINAL_PATH%"
set "TARGET_DIR=%PROJECT_ROOT%versions\%TARGET%"

echo ============================================================
echo Building %TARGET% with %JAVA_HOME%
echo ============================================================
call "%TARGET_DIR%\gradlew.bat" -p "%TARGET_DIR%" clean build --no-daemon --console=plain
if errorlevel 1 (
    echo FAILED: %TARGET%
    set "FAILED_TARGETS=!FAILED_TARGETS! %TARGET%"
) else (
    echo PASSED: %TARGET%
)
echo.
exit /b 0

:find_java
set "WANTED_MAJOR=%~1"
set "OUTPUT_VAR=%~2"

for /f "tokens=2 delims==" %%J in ('set %OUTPUT_VAR% 2^>nul') do (
    call :accept_java "%%J" "%WANTED_MAJOR%" "%OUTPUT_VAR%"
    if not errorlevel 1 exit /b 0
)

if defined JAVA_HOME (
    call :accept_java "%JAVA_HOME%" "%WANTED_MAJOR%" "%OUTPUT_VAR%"
    if not errorlevel 1 exit /b 0
)

for /f "delims=" %%J in ('where java.exe 2^>nul') do (
    for %%K in ("%%~dpJ..") do call :accept_java "%%~fK" "%WANTED_MAJOR%" "%OUTPUT_VAR%"
    if not errorlevel 1 exit /b 0
)

call :scan_pattern "%USERPROFILE%\.jdks\*%WANTED_MAJOR%*" "%WANTED_MAJOR%" "%OUTPUT_VAR%"
if not errorlevel 1 exit /b 0
call :scan_pattern "%USERPROFILE%\.gradle\jdks\*%WANTED_MAJOR%*" "%WANTED_MAJOR%" "%OUTPUT_VAR%"
if not errorlevel 1 exit /b 0
call :scan_pattern "%ProgramFiles%\Eclipse Adoptium\jdk-%WANTED_MAJOR%*" "%WANTED_MAJOR%" "%OUTPUT_VAR%"
if not errorlevel 1 exit /b 0
call :scan_pattern "%ProgramFiles%\Java\jdk-%WANTED_MAJOR%*" "%WANTED_MAJOR%" "%OUTPUT_VAR%"
if not errorlevel 1 exit /b 0
call :scan_pattern "%ProgramFiles%\Microsoft\jdk-%WANTED_MAJOR%*" "%WANTED_MAJOR%" "%OUTPUT_VAR%"
if not errorlevel 1 exit /b 0

echo ERROR: Could not find a Java %WANTED_MAJOR% JDK.
echo Set %OUTPUT_VAR% to its installation directory and run this script again.
exit /b 1

:scan_pattern
for /d %%J in ("%~1") do (
    call :accept_java "%%~fJ" "%~2" "%~3"
    if not errorlevel 1 exit /b 0
    for /d %%K in ("%%~fJ\*") do (
        call :accept_java "%%~fK" "%~2" "%~3"
        if not errorlevel 1 exit /b 0
    )
)
exit /b 1

:accept_java
set "CANDIDATE=%~f1"
if not exist "%CANDIDATE%\bin\java.exe" exit /b 1

set "VERSION_FILE=%TEMP%\mdm-java-version-%RANDOM%-%RANDOM%.tmp"
"%CANDIDATE%\bin\java.exe" -version 2>"%VERSION_FILE%"
set "JAVA_VERSION="
for /f "usebackq tokens=3" %%V in ("%VERSION_FILE%") do if not defined JAVA_VERSION set "JAVA_VERSION=%%~V"
del /q "%VERSION_FILE%" >nul 2>&1
if not defined JAVA_VERSION exit /b 1

for /f "tokens=1 delims=." %%M in ("%JAVA_VERSION%") do set "JAVA_MAJOR=%%M"
if not "%JAVA_MAJOR%"=="%~2" exit /b 1
set "%~3=%CANDIDATE%"
exit /b 0

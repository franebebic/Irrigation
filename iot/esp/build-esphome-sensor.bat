@echo off
setlocal

REM ----- Lokacije -----
set SOURCE_YAML=C:\Projects\irrigation-project\iot\esp\esp_sensor.yaml
set WORK_DIR=C:\esp-build
set VENV_PATH=C:\Projects\irrigation-project\iot\esp\esphome-env
set SERIAL_PORT=COM7
set SENSOR_ID=2
set SENSOR_NAME=sensor%SENSOR_ID%
set /A IP_SUBADDRESS=120+%SENSOR_ID%

REM ----- Forsiraj PlatformIO build direktorije BEZ dijakritika -----
set PLATFORMIO_CORE_DIR=C:\esp-platformio
set PLATFORMIO_GLOBALLY_INSTALLED_PACKAGES_DIR=C:\esp-platformio\packages

REM ----- Priprema build foldera -----
echo [INFO] Kopiram YAML u %WORK_DIR%
if not exist "%WORK_DIR%" mkdir "%WORK_DIR%"
xcopy /Y /I /Q "C:\Projects\irrigation-project\iot\esp\*" "%WORK_DIR%\"

REM ----- Aktivacija virtualnog okruženja -----
echo [INFO] Aktiviram virtualno okruženje...
call "%VENV_PATH%\Scripts\activate.bat"

REM ----- Pokretanje builda -----
cd /D "%WORK_DIR%"
echo [INFO] Pokrećem esphome compile...
esphome -s sensor_name %SENSOR_NAME% -s ip_subaddress %IP_SUBADDRESS% compile esp_sensor.yaml

IF %ERRORLEVEL% EQU 0 (
    echo [SUCCESS] Build je uspješno završen.

    REM ---- Flashanje ESP32 uređaja ----
    echo [INFO] Flasham uređaj na %SERIAL_PORT%...
    esphome upload esp_sensor.yaml --device %SERIAL_PORT%

    echo [INFO] Otvaram build folder...
    start .
) ELSE (
    echo [ERROR] Build nije uspio.
)

endlocal
pause

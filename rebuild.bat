@echo off
setlocal

echo ==== Stopping existing containers ====
docker-compose down

echo ==== Removing unused images ====
docker image prune -f

echo ==== Building containers from scratch ====
REM docker-compose build --no-cache
docker-compose build

IF ERRORLEVEL 1 GOTO error

echo ==== Starting containers ====
docker-compose up -d
IF ERRORLEVEL 1 GOTO error

echo ==== Done; containers are up with latest local code ====
GOTO end

:error
echo [ERROR] Something went wrong during build or startup!

:end
endlocal

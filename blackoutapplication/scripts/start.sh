#!/bin/bash

# variable
APP="BlackoutApplication-1.0.0.jar"

# start app
DISPLAY=:0 XAUTHORITY=/home/pi/.Xauthority sudo -E java -Xms1G -Xmx4G -Dglass.platform=gtk -jar ~/blackout/$APP
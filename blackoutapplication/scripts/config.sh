#!/bin/bash

# variable
APP="BlackoutApplication-1.0.0.jar"
JDK="bellsoft-jdk17.0.7+7-linux-aarch64-full.deb"
JDK_URL="https://download.bell-sw.com/java/17.0.7+7/$JDK"

# update system and remove old java
sudo apt update -y && sudo apt upgrade -y
sudo apt remove openjdk-17-jdk -y

# create structure
mkdir ~/blackout
cp $APP ~/blackout/$APP
cp start.desktop ~/blackout/start.desktop
cp start.desktop ~/Desktop/start.desktop
cp start.sh ~/blackout/start.sh
cd ~/blackout
chmod +x .start.sh

# copy desktop file to autostart
mkdir ~/.config/autostart
cp start.desktop ~/.config/autostart/start.desktop

# install jdk with fx
wget $JDK_URL
sudo dpkg -i $JDK
rm $JDK

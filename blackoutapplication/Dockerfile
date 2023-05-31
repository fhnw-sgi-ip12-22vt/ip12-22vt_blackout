FROM debian:latest
# install wget and x11-apps
RUN apt update -y
RUN apt install wget -y
RUN apt install x11-apps -y
RUN apt install libasound2 -y
RUN apt install libxtst6 -y
RUN apt install libxi6 -y

# install bellsoft liberica opendjk 17.0.7-7
RUN wget "https://download.bell-sw.com/java/17.0.7+7/bellsoft-jdk17.0.7+7-linux-amd64-full.deb"
RUN dpkg -i "bellsoft-jdk17.0.7+7-linux-amd64-full.deb"

# install maven 3.9.2
RUN wget "https://dlcdn.apache.org/maven/maven-3/3.9.2/binaries/apache-maven-3.9.2-bin.tar.gz"
RUN tar xzvf "apache-maven-3.9.2-bin.tar.gz"
ENV PATH="${PATH}:${PWD}/apache-maven-3.9.2/bin"

# print java and maven versions
CMD java -v
CMD mvn -v
#################### STAGE 1###############
FROM centos:centos6
MAINTAINER lreeder
 

 
#Helpful utils, but only sudo is required
#RUN yum -y install tar
#RUN yum -y install vim
#RUN yum -y install nc

RUN yum -y install sudo
 
######## JDK8
 
#Note that ADD uncompresses this tarball automatically
ADD jdk-8u151-linux-x64.tar.gz /opt
WORKDIR /opt/jdk1.8.0_151
RUN alternatives --install /usr/bin/java java /opt/jdk1.8.0_151/bin/java 1
RUN alternatives --install /usr/bin/jar jar /opt/jdk1.8.0_151/bin/jar 1
RUN alternatives --install /usr/bin/javac javac /opt/jdk1.8.0_151/bin/javac 1
RUN echo "JAVA_HOME=/opt/jdk1.8.0_151" >> /etc/environment
 
######## MAVEN


 ADD apache-maven-3.5.2-bin.tar.gz /usr/local/apache-maven/
 WORKDIR /usr/share
 ENV MAVEN_HOME /usr/local/maven
 ENV M2_HOME /usr/local/apache-maven/apache-maven-3.5.2
 ENV M2 $M2_HOME/bin
 ENV PATH $M2:$PATH 

#RUN  yum install wget
#RUN  wget http://mirror.olnevhost.net/pub/apache/maven/maven-3/3.5.0/binaries/apache-maven-3.5.0-bin.tar.gz
 
######## TOMCAT
 
#Note that ADD uncompresses this tarball automatically
ADD apache-tomcat-8.0.23.tar.gz /usr/share


WORKDIR /usr/share/

RUN mv  apache-tomcat-8.0.23 tomcat8

RUN echo "JAVA_HOME=/opt/jdk1.8.0_151/" >> /etc/default/tomcat8
RUN groupadd tomcat
RUN useradd -s /bin/bash -g tomcat tomcat
RUN chown -Rf tomcat.tomcat /usr/share/tomcat8
EXPOSE 8080


WORKDIR /home 


#RUN mvn clean install
















#FROM maven:3.5-jdk-8-onbuild
#CMD ["do-something-with-built-packages"]

########################  STAGE 2   ##############################


#FROM springindocker:latest
WORKDIR /home 
COPY /WebTimeSheetCalculator.war /usr/share/tomcat8/webapps/WebTimeSheetCalculator.war
















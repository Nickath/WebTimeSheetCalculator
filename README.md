# WebTimeSheetCalculator
A web application to calculate the working mean of the month

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development. See *deployment* for notes on how to deploy the project on a live system (either this is your
Windows host, either this is a Docker container running inside a Vagrant Box.


### Prerequisites

A host OS containing Oracle Virtual box and Vagrant (compatible versions)

### Build and develop 

1) git clone https://github.com/Nickath/WebTimeSheetCalculator.git

2) git checkout master

3) mvn clean install #  to build the project 

4) copy the .war file in /target folder of the project after the successful build into tomcat /webapps folder

5) Go to tomcat bin folder and run startup.bat (WIndows host) or ./startup.sh (Linux)


### Build and run using the docker image

1) Go to the folder where Vagrantfile is placed and run
   "vagrant up && vagrant ssh" to start the vagrant machine (ubuntu-trusty)
   
2) Go 2 folders back cd ../.. 
   cd vagrant/ the folder you want
   
3) curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
   
   sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu       $(lsb_release -cs) stable"

   sudo apt-get update

   apt-cache policy docker-ce
   
   sudo apt-get install -y docker-ce
   
4) Create a docker user using "sudo usermod -aG docker ${USER}"

5) run docker run hello-world

6) build the image using the command "docker build -t <<img_name>> ." where the docker file exists

7) Find the image using "docker image ls"

8) run the docker image using docker run <<image_id>> (f.e docker run -t -p 8080:8080 myapp and to expose the port 8080

9) enter into the container using the command "docker exec -it <<container_id>> bash"
"



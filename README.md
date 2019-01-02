# Laboratorio - Jenkins

Instalación de Jenkins empleando Docker. Creación de Pipeline empleando 'Scripted Pipeline'

## Crear maquina de pruebas 
Crear una instancia EC2 (Amazon Linux 2 AMI (HVM), SSD Volume Type - x86) del tipo t2.micro. Y abrir el puerto 8089
en las políticas de seguridad.

Entrar por SSH y ejecutar:

```
sudo yum update -y


Instalar docker community-edition
```
sudo amazon-linux-extras install docker -y
```


Agregar usuario ec2-user a el grupo docker para que pueda ejecutar commandos de docker sin usar sudo:

```
sudo groupadd docker
sudo usermod -a -G docker ec2-user
```

Cerrar sesión ssh y volver a abrirla con usuario `ec2-user` para que el sistema operativo tome los cambios
```
exit
ssh -i ruta/a/tu/llave/privada ec2-user@tu_direccion_IP
```

Verificar que el usuario `ec2-user` fue agregado al grupo de usuarios:
```
ssh -i ruta/a/tu/llave/privada ec2-user@tu_direccion_IP
```

Iniciar el servicio de docker
```
sudo service docker start

```

Validar que el usuario `ec2-user` tiene acceso a docker:
```
[ec2-user@ip-172-31-17-132 ~]$ docker run hello-world

Hello from Docker!
This message shows that your installation appears to be working correctly.

To generate this message, Docker took the following steps:
 1. The Docker client contacted the Docker daemon.
 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
    (amd64)
 3. The Docker daemon created a new container from that image which runs the
    executable that produces the output you are currently reading.
 4. The Docker daemon streamed that output to the Docker client, which sent it
    to your terminal.

To try something more ambitious, you can run an Ubuntu container with:
 $ docker run -it ubuntu bash

Share images, automate workflows, and more with a free Docker ID:
 https://hub.docker.com/

For more examples and ideas, visit:
 https://docs.docker.com/get-started/
```

## Descargar repositorio

```
sudo yum install git-core -y
git clone https://github.com/alfredocambera/bootcamp-lab-jenkins.git
```

## Instalar docker-compose
```
sudo curl -L "https://github.com/docker/compose/releases/download/1.23.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/bin/docker-compose
sudo chgrp docker /usr/bin/docker-compose
sudo chmod 554 /usr/bin/docker-compose
```


## Construir imagen de Jenkins
```
cd bootcamp-lab-jenkins
docker build --no-cache -t local:jenkins .
```

## Crear directorio para jenkins

```
mkdir jenkins-home
sudo chgrp docker jenkins-home
```

## Iniciar Jenkins

```
docker-compose up -d
```

## Consultar la interfaz gráfica de la aplicación

Abrir en el navegador la dirección IP del servidor en el puerto 8090:

http://IP_DE_SERVIDOR:8089/


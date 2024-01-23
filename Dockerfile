# Es obligatorio. Al principio de cada Dockerfile. Indica cual es la imagen base.
FROM debian:latest

# Ejecuta comandos en una nueva capa y crea un nuevo contenedor intermedio. Normalmente es usado para instalar nuevos paquetes.
RUN apt-get update

# El comando que se especifique a continuación, se ejecutara cuando haga docker run si no se especificar otro comando.
CMD apt-get update

# Indica (no expone) el puerto del contenedor. Útil para que los puertos no se expongan aleatoriamente.
EXPOSE 8080
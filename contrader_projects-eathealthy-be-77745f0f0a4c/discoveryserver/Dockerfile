FROM openjdk:11
COPY /build/discoveryserver.jar /app.jar

EXPOSE 8761
CMD ["java","-jar","/app.jar"]

#docker build -t nomeImmagine .
#docker images
#docker run -p portaEsternaADocker:portaInterna(applicazione) --name nomeContainer nomeImmagine(da eseguire nel Container)
###docker run -p 8080:8761 --name discoveryserver testapp

#docker container ls (Fa vedere quelli in esecizione)
#docker container ls -a (fa vedere tutti i container)
#docker rm nomeContainer (elimina container esistente)
#docker ps ( fa vedere i container in esecuzione)

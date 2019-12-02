<h1>Explications</h1>
- <b>Contener Docker n°1 :</b> <br>
Base de données HSQL. Le contener est déployé sur une instance ECS AWS (image sur Docker Hub).<br>
- <b>Contener Docker n°2 : </b><br>
Application Web. Le contener est également déployé sur une instance ECS AWS (image sur Docker Hub).<br>
- <b>Blacklist</b><br>
Base de données RDS Mysql.<br>
- <b>File Kafka</b><br>
Serveur Zookeeper sur une instance AWS EC2 avec un broker kafka (topic = "topic-tp-progcomp")<br>
- <b>Fonction Lambda</b><br>
Accessible uniquement en local (problème de VPC AWS) <br>

<h1>Execution en local :</h1> 
 - Modifier l'adresse de la base de données dans application.properties en localhost<br>
 - Générer le jar de l'application avec Gradle (gradle clean build)<br>
 - docker-compose up  <br>
 
 <b>OU</b>
 Pour lancer l'application en utilisant la base de données distante<br>
 - Laisser le lien vers la base de données distante<br>
 - docker build -f Dockerfile ... <br>
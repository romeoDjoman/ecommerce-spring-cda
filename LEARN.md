# LEARN SPRING

## SPRING BOOT INITIALIZER
### Ajout des dépendances 
1. Spring web 
2. MariaDB Driver 
3. Spring Data JPA (pour créer des requêtes de la base de données) 
4. Spring Boot Dev Tools (pour réinitiliser le projet si besoin)
5. Java Mail Sender 
6. Lombok (pour faciliter les injections de dépendances)

## DOCKER COMPOSE
 Docker c'est la solution logiciel qui permet de déployer une application sur n'importe quelle environnemnt (windows, linus, macOS). 
 Avec Docker on va pouvoir créer des images (ce qu'on appelle des images) qui sont des livrables de notre applicaition. L'image Docker contiendra tout ce qu'il faut pour que l'application puisse fonctionner. Si on développe une application par exemple en Java 17, Node 18 ou PHP 7, quand l'applicatio nsera dans une image Docker, peut importe le server, l'application fonctionnera. 
 
En gros, 
- On écris le code
- On génére le livrable 
- On embarque le livrable dans une image Docker 
- On stocke l'image dans une un repository, un hub qui contient toutes les images publiques
- On va pouvoir ensuite exécuter l'image sur un serveur.
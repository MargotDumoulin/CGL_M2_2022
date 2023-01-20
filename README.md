## Projet CGL - M2GI 2022/2023

### Etudiants
Ce projet a été réalisé par DULUYE Antony, DUMOULIN Margot et Bastien SCHAFFHAUSER.

### Guide d'installation
Pour lancer ce projet, il est nécessaire de créer deux bases de données.
La première (base de données de l'application) porte le nom de "projet_cgl",
la deuxième (base de données de tests) porte le nom de "test_projet_cgl".

Afin de créer les tables ainsi que les paramètres par défaut, un script projet_cgl.sql est fourni dans 
ce repository (il est nécessaire de l'utiliser pour les deux bases de données). 

Pour se connecter à la base de données, il est nécessaire de remplir les bonnes
informations de connexion à la base dans le fichier "application.properties".
Il existe deux fichiers du même nom (un fichier pour se connecter à la base de 
tests et un fichier pour se connecter à la base de données de l'application).
Il est nécessaire de remplir les deux fichiers. 

### Outils utilisés

Pour réaliser ce projet d'un serveur Tomcat 10 et d'une base de données MariaDB.
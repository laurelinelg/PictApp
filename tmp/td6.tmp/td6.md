
Le but de ce TP est de vous sensibiliser et de vous familiariser avec certaines notions de sécurité informatique.
Les pratiques que vous allez mettre en oeuvre ne sont autorisées que sur du matériel vous appartenant ou pour lequel
vous avez les autorisations écrites nécessaires émanant des autorités idoines.

**Article 323-1 du code pénal, Modifiée par LOI 2012-410 du 27 mars 2012 - art. 9}**

Le fait d'accéder ou de se maintenir, frauduleusement, dans tout ou partie d'un système de traitement automatisé de données est puni de deux ans d'emprisonnement et de 30000 euros d'amende.

Lorsqu'il en est résulte soit la suppression ou la modification de données contenues dans le système, soit une altération du fonctionnement de ce système, la peine est de trois ans d'emprisonnement et de 45000 euros d'amende.

Lorsque les infractions prévues aux deux premiers alinèas ont été commises à l'encontre d'un système de traitement automatisée de données à caractère personnel mis en oeuvre par l'Etat, la peine est portée à cinq ans d'emprisonnement et à 75 000 euros d'amende.

### Contexte

Pour maximiser leur impact, les malwares dissimulent leurs comportements malicieux aux yeux de l'utilisateur en 
se faisant passer pour des applications dites bénignes.
Cela permet à une application mal intentionnée de rester plus longtemps en activité sur le téléphone de la cible et donc
de faire plus de dégâts.

Dans ce TP, il vous est demandé d'exploiter les faiblesses du système d'exploitation Android pour
voler des données personnelles de l'utilisateur à son insu.
Plus particulièrement, vous allez exploiter les vulnérabilités de *Webview* pour concevoir une application Android
qui envoie des données collectées sur un téléphone à un serveur pirate distant.

Vous devrez développer votre propre malware maquillé sous la forme d'un carnet d'adresses.

**Important**\
En plus des différentes implémentations, il vous est demandé de produire un rapport complet qui rend fidèlement compte de votre travail.
Dans ce rapport, vous écrirez ce que vous comprenez de l'exercice, les différents problèmes que vous rencontrez et 
comment vous réussissez à les résoudre.
Vous devrez aussi montrer que vous comprenez les différents enjeux de sécurité 
présentés dans ce TP.
La rapport est à rendre sous forme d'un fichier `.md` à dans le répertoire *td6* : `td6/rapport.md`.


### Partie I: Implémenter un carnet d'adresses

Dans cette partie, il vous est demandé de développer la partie inoffensive de l'application, qui servira plus tard 
de camouflage pour déjouer la vigilance de l'utilisateur.

Voici les caractéristiques de l'application:

L'application est un carnet d'adresses qui permet à l'utilisateur de parcourir les différents contacts de son téléphone.
Lors de la sélection d'un contact, l'utilisateur peut au choix lui envoyer un sms ou composer son numéro de téléphone.

* Ecrivez le Javascript et Java nécessaire pour récupérer les contacts de l'utilisateur et les afficher sous forme de 
liste au sein de la Webview.
* Ecrivez le code Javascript nécessaire pour pouvoir saisir le contenu d'un SMS après sélection d'un contact.
* Ecrivez le bridge qui permet d'envoyer un SMS à un contact à partir du contenu saisi lors de l'action précédente.
* Ecrivez le code nécessaire qui permet de notifier l'utilisateur que le SMS a bien été envoyé.
* Ecrivez le bridge qui permet de composer le numéro de téléphone d'un contact sélectionné.


### Partie II: Voler les contacts de l'utilisateur

Dans certains scénarios malicieux, un pirate subtilise les contacts de plusieurs centaines de téléphones pour 
ensuite effectuer des tentatives phishing avec les numéros de téléphones collectés.
<!--
A partir du travail effectué lors du TD5, il vous est demandé de mettre à jour le code de votre application pour 
collecter les contacts de l'utilisateur.
-->

Pour garantir l'unicité des données, les contacts que vous collectez sur chaque téléphone doivent être associés
à un identifiant unique.
Chaque Android device possède un identifiant unique qu'il est possible d'obtenir en appelant la méthode 
`TelephonyManager.getImei()`. (https://developer.android.com/reference/android/telephony/TelephonyManager#getImei())

<!--
1. Ecrivez le code nécessaire pour implémenter le bridge qui permet de collecter les contacts de l'utilisateur dans 
le contexte de la *Webview*.
2. Ecrivez le bridge nécessaire qui permet de récupérer l'identifiant unique du téléphone dans le contexte de la Webview
-->

#### Etape 1: Envoyer les contacts à un serveur distant.

Dans un premier temps, il vous est demandé d'écrire le code Javascript nécessaire pour envoyer la liste de contacts
ainsi que le numéro `imei` collecté à un serveur distant.

Le message envoyé doit être au format `JSON` et aura la structure suivante:
```json
{
	"imei": "string",
	"contacts": [
		{
			"name": "string",
			"phone_number": "string",
			"mail_address": "string"
		}
	]
}
```

* Ecrivez le code Javascript nécessaire pour envoyer une requête `http` contenant le message décrit précédemment.

#### Etape 2: Développer un simple serveur web pirate

L'objectif de cette partie est de concevoir un serveur pirate capable de recevoir des données au format `json` au travers
du protocol `http`.
Vous écrirez le code nécessaire dans le répertoire suivant : `td6/pirate_server`.

Voici les spécifications requises pour le serveur : 

- Le serveur doit utiliser la plate-forme `node.js`.
- Le serveur doit communiquer avec ses clients au travers du protocole `http`.
- Le serveur doit être capable d'agir correctement en fonction de la requête qui lui est envoyée (`http` routing).
- Le serveur doit être capable de *parser* le contenu de la requête reçue.
- Pour chaque message reçu, le serveur doit écrire le contenu du champ `contacts` dans un fichier nommé avec le numéro
`imei` correspondant (`{imei}.txt`). Ces fichiers doivent être stockés dans le répertoire `td6/pirate_server/stolen_contacts`.
- Le serveur doit renvoyer une erreur au client si la requête qui lui est envoyée n'est pas prise en charge par celui-ci.
- Le serveur doit renvoyer une erreur au client si l'information contenue dans la requête n'est pas dans un format valide (`json`)

Pour vous aider, vous pouvez consulter :

- la documentation de l'interface `http` de nodejs : [https://nodejs.org/api/http.html](https://nodejs.org/api/http.html)
- les exemples fournis dans la documentation de nodejs : [https://nodejs.org/api/synopsis.html](https://nodejs.org/api/synopsis.html)

### Partie III: Envoyer des sms surtaxés

Dans cette partie, vous devrez modifier le code de votre application malicieuse pour permettre au serveur pirate d'envoyer
une commande qui ordonne à votre application d'envoyer des sms à un numéro de téléphone surtaxé à l'insu de l'utilisateur.

Pour que le serveur pirate puisse facilement envoyer des messages à l'application malicieuse, il vous est demandé d'ouvrir
une connexion websocket entre le serveur et l'application.

* Ecrivez le code Javascript nécessaire sur le serveur et le client pour ouvrir une connexion websocket.
* Ecrivez le code Javascript nécessaire sur le serveur pour envoyer une commande `send_sms`.
Cette commande est un message dont la structure est la suivante:
```json
{
	"command": "send_sms",
	"payload":
		{
			"phone_number": "string",
			"content": "string"
		}
}
```
* Ecrivez le code Javascript nécessaire sur le client pour traiter correctement la réception de la commande `send_sms`.
* Utilisez le bridge implémenté précédemment pour envoyer un sms à un contact pour envoyer un sms surtaxé à partir du 
contenu du message de la commande `send_sms`.

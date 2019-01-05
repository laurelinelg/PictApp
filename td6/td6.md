
Le but de ce TP est de vous sensibiliser et de vous familiariser avec certaines notions de sécurité informatique.
Les pratiques que vous allez mettre en oeuvre ne sont autorisées que sur du matériel vous appartenant ou pour lequel
vous avez les autorisations écrites nécessaires émanant des autorités idoines.

**Article 323-1 du code pénal, Modifiée par LOI 2012-410 du 27 mars 2012 - art. 9}**

Le fait d'accéder ou de se maintenir, frauduleusement, dans tout ou partie d'un système de traitement automatisé de données est puni de deux ans d'emprisonnement et de 30000 euros d'amende.

Lorsqu'il en est résulte soit la suppression ou la modification de données contenues dans le système, soit une altération du fonctionnement de ce système, la peine est de trois ans d'emprisonnement et de 45000 euros d'amende.

Lorsque les infractions prévues aux deux premiers alinèas ont été commises à l'encontre d'un système de traitement automatisée de données à caractère personnel mis en oeuvre par l'Etat, la peine est portée à cinq ans d'emprisonnement et à 75 000 euros d'amende.

### Contexte

Dans ce TP, il vous est demandé d'exploiter les faiblesses du système d'exploitation Android pour
voler des données personnelles de l'utilisateur à son insu.
Plus particulièrement, vous allez exploiter les vulnérabilités de *Webview* pour concevoir une application Android
qui envoie des données collectées sur un téléphone à un serveur pirate distant.

### Partie I: Récupérer les contacts de l'utilisateur

Dans certains scénarios malicieux, un pirate subtilise les contacts de plusieurs centaines de téléphones pour 
ensuite effectuer des tentatives phishing avec les numéros de téléphones collectés.
A partir du travail effectué lors du TD5, il vous est demandé de mettre à jour le code de votre application pour 
collecter les contacts de l'utilisateur.

Pour garantir l'unicité des données, les contacts que vous collectez sur chaque téléphone doivent être associés
à un identifiant unique.
Chaque Android device possède un identifiant unique qu'il est possible d'obtenir en appelant la méthode 
`TelephonyManager.getDeviceId()`. (https://developer.android.com/reference/android/telephony/TelephonyManager#getDeviceId())

1. Ecrivez le code nécessaire pour implémenter le bridge qui permet de collecter les contacts de l'utilisateur dans 
le contexte de la *Webview*.
2. Ecrivez le bridge nécessaire qui permet de récupérer l'identifiant unique du téléphone dans le contexte de la Webview

### Partie II: Envoyer les contacts à un serveur pirate

#### Etape 1: Envoyer les contacts à un serveur distant.

Dans un premier temps, il vous est demandé d'écrire le code Javascript nécessaire pour envoyer la liste de contacts
ainsi que le `deviceId` collecté à un serveur distant.

Le message envoyé doit être au format `JSON` et aura la structure suivante:
```json
{
	"device_id": "string",
	"contacts": [
		{
			"name": "string",
			"phone_number": "string",
			"mail_address": "string"
		}
	]
}
```

#### Etape 2: Développer un simple serveur pirate

L'objectif de cette partie est de concevoir un serveur pirate capable de recevoir des données au format `json` au travers
du protocol `http`.

Voici les spécifications requises pour ce serveur : 

- Le serveur doit utiliser la plateforme Node.js
- Le serveur doit communiquer avec ses clients au travers du protocole `http`
- Le serveur doit être capable d'agir correctement en fonction de la requête qui lui est envoyée (`http` routing).
- Le serveur doit être capable de *parser* le contenu de la requête reçue.
- Le serveur doit être capable d'écrire les informations qu'il reçoit dans le bon fichier (un fichier par type 
d'information)
- Le serveur doit renvoyer une erreur au client si la requête qui lui est envoyée n'est pas prise en charge par celui-ci
- Le serveur doit renvoyer une erreur au client si l'information contenue dans la requête n'est pas dans un format valide (`json`)

Pour vous aider, vous pouvez consulter :

- la documentation de l'interface `http` de nodejs : [https://nodejs.org/api/http.html](https://nodejs.org/api/http.html)
- les exemples fournis dans la documentation de nodejs : [https://nodejs.org/api/synopsis.html](https://nodejs.org/api/synopsis.html)

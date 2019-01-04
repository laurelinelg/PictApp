


## Partie I - Développer une application hybride

### Contexte

Pour développer cette application Android, nous allons utiliser le concept de *Webview*, (*mWebview* sur IOS).
Une Webview est un composant du framework Android qui permet d'afficher du contenu web (*HTML/CSS/Javascript*) sur l'interface
utilisateur.
Le composant repose sur [Webkit](https://webkit.org/) pour afficher des pages web et permet d'afficher du contenu local
(code contenu dans l'application) ou distant (code provenant d'un serveur distant).
Enfin, le composant *Webview* possède des méthodes qui lui permettent de s'interfacer avec le code natif de l'application
(le code java de l'application Android). Ces interfaces s'appellent *JavascriptInterfaces* et permettent au code
Javascript exécuté dans le contexte d'une *Webview* d'exécuter du code Java présent dans l'application.

Le concept de *Webview* est particulièrement utilisé aujourd'hui pour développer des applications dites *hybrides*,
c'est-à-dire des applications qui sont développées à la fois avec du code natif (Java pour Android) et du code Javascript
au travers du composant *Webview*. De nombreux frameworks tels que Cordova permettent de développer une application
Android et/ou IOS entièrement avec du code web standard (HTML/CSS/Javascript).

### Objectif
 Se familiariser avec une webview...

Voici les pré-requis pour commencer à développer l'app :

- Avoir *Android Studio* ou *Intellij* installé sur sa machine
- Avoir une machine virtuelle avec Android OS installé dessus (émulateur Android)

### Etape 1: Simple application avec Webview

Pour commencer, on vous demande de développer une simple application Android avec *Webview* pour se familiariser
avec l'approche. L'application devra être être capable de s'ouvrir sur l'émulateur Android et d'afficher le contenu
d'une page web donnée, par exemple [https://news.ycombinator.com/](https://news.ycombinator.com/).

1. Créez une simple application avec une `Activity` principale qui sera le point d'entrée de votre application.
2. En vous inspirant de la [documentation officielle](https://developer.android.com/guide/webapps/webview) d'Android,
créez votre propre instance de `WebView` au sein de votre activité principale.
3. Utilisez une des méthodes fournies par l'interface `WebView` pour afficher le site web de votre choix à l'écran.

***NOTE**: L'application doit avoir la permission d'accéder à internet pour pouvoir récupérer et afficher le contenu de la page web.*

### Etape 2: Afficher son propre contenu web au sein d'une instance Webview

Nous allons maintenant voir qu'il est possible d'exécuter notre propre code au sein de l'instance `WebView` créée
précédemment.

1. Créez un répertoire nommé `assets` dans le dossier `main` du code source de l'application.
2. Dans ce répertoire, créez un fichier HTML ainsi qu'un simple script Javascript. Faites en sorte
que le fichier HTML charge le fichier javascript.
```html
<!--/assets/index.html-->
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>My super webpage</title>
</head>
<body>
Hello World
</body>
<script type="text/javascript" src="main.js"></script>
</html>
```

```javascript
// /assets/main.js
console.log('Javascript code executed');
```

***NOTE**: [cette page](https://developer.android.com/guide/webapps/debugging) vous indique comment débugguer et savoir si votre code javascript a été exécuté ou pas.*

3. Faites le nécessaire dans l'activité principale de votre application pour qu'elle charge le fichier HTML que vous
avez créé à la place de l'actuelle page web.
4. Lancez l'application et vérifiez que le code HTML est bien executé. Le code Javascript appelé par le fichier HTML n'est
pas exécuté, pourquoi ? la réponse est [ici](https://developer.android.com/guide/webapps/webview).
5. Faites les changements nécessaires dans l'application et vérifiez que votre code javascript est bien exécuté


*Questions :*

1. peut-on exécuter du code Javascript dans le contexte d'une *Webview* sans que l'application Android ait la permission
d'accéder à internet ?
2. Expliquer pourquoi le verrou de sécurité supplémentaire de l'étape 4 est important d'un point de vue sécurité.
3. Citez une faille de sécurité qui possible dès lors que l'exécution de code Javascript est autorisé sur une application Android.

### Etape 3: Utiliser Javascript pour exécuter du code Java de l'application.

Dans cette partie, il vous est demandé de tirer parti du mécanisme d'interface `Java-Javascript` pour exécuter
du code Java à partir de votre code javascript.

*ressources : [https://developer.android.com/guide/webapps/webview](https://developer.android.com/guide/webapps/webview)*

1. Ecrivez le code Java nécessaire pour exposer des méthodes Java à votre code Javascript et montrer qu'à l'exécution de
l'application, le code javascript exécuté par votre WebView est déclenche bien le code Java associé. (En déclenchant une alerte
sur l'interface par exemple)

2.



3.



4. 

<!--## Partie I - Développer une application hybride-->

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
L'obectif de ce TP est de se familiariser avec le composant *Webview* et de comprendre comment l'intégrer au sein 
d'une application Android.

### Pré-requis

- Avoir *Android Studio* ou *Intellij* installé sur sa machine 
- Avoir une machine virtuelle avec Android OS installé dessus (émulateur Android)

### Partie I: Simple application avec Webview

Pour commencer, on vous demande de développer une simple application Android avec *Webview* pour se familiariser
avec l'approche. 
L'application devra être être capable de s'ouvrir sur l'émulateur Android et d'afficher le contenu
d'une page web donnée, par exemple [https://news.ycombinator.com/](https://news.ycombinator.com/).

1. Créez une simple application avec une `Activity` principale qui sera le point d'entrée de votre application.
2. En vous inspirant de la [documentation officielle](https://developer.android.com/guide/webapps/webview) d'Android,
créez votre propre instance de `WebView` au sein de votre activité principale.
3. Utilisez une des méthodes fournies par l'interface `WebView` pour afficher le site web de votre choix à l'écran.

<!--***NOTE**: L'application doit avoir la permission d'accéder à internet pour pouvoir récupérer et afficher le contenu de la page web.*-->

### Partie II: Exécuter du code local au sein d'une instance Webview

Il est possible d'exécuter notre propre code au sein de l'instance `WebView` créée
précédemment.

1. Créez un répertoire nommé `assets` dans le dossier `main` du code source de l'application.
2. Dans ce répertoire, créez un fichier HTML ainsi qu'un simple script Javascript. Faites en sorte
que le fichier HTML charge le fichier javascript. 
	- [index.html](index.html) 
	- [main.js](main.js)
3. Faites le nécessaire dans l'activité principale de votre application pour qu'elle charge le fichier HTML que vous
avez créé à la place de l'actuelle page web.
4. Lancez l'application et vérifiez que le code HTML est bien executé. Le code Javascript appelé par le fichier HTML n'est
pas exécuté, pourquoi ? la réponse est [ici](https://developer.android.com/guide/webapps/webview).
5. Faites les changements nécessaires dans l'application et vérifiez que votre code javascript est bien exécuté.

***NOTE**: [cette page](https://developer.android.com/guide/webapps/debugging) vous indique comment débugguer et savoir si votre code javascript a été exécuté ou pas.*

*Questions :*

1. peut-on exécuter du code Javascript dans le contexte d'une *Webview* sans que l'application Android ait la permission
d'accéder à internet ?
2. Expliquer pourquoi le verrou de sécurité supplémentaire de l'étape 4 est important d'un point de vue sécurité.
3. Citez une faille de sécurité potentielle dès lors que l'exécution de code Javascript est autorisé sur une application Android.

### Partie III: Exécuter du code Java à partir de Javascript

Dans cette partie, il vous est demandé de tirer parti du mécanisme d'interface `Java-Javascript` (appelé bridge) pour exécuter
du code Java à partir de votre code javascript.
Pour cela, il vous est demandé d'écrire le code nécessaire pour exposer des méthodes Java au code Javascript à l'aide 
du concept de `JavaScriptInterface`.

##### Installation de MobileUI
Afin de rendre votre application plus crédible, il vous est aussi demandé d'implémenter l'interface utilisateur de l'application.
Pour faciliter le développement de cette interface utilisateur, vous pouvez utiliser [`mobileui`](https://mobileui.github.io/).

1.Installer `mobileui` grâce à `npm` sur la machine : `npm install -g mobileui`
2. Se placer dans le répertoire `assets` de votre projet et créer un dossier `www`.
3. Exécuter la commande `mobileui install template cordova-blank` qui permet d'injecter les fichiers nécessaires au 
bon fonctionnement des composants de `mobileui`.
La commande précédente crée des fichiers `html`, `css` et `js` par défaut que vous pouvez modifier à votre guise pour
développer votre interface utilisateur.
4. Effectuer les modifications dans le code java de votre application pour que la `Webview` pointe vers le fichier 
`index.html` du dossier `www`. Ce sera le nouveau point d'entrée de votre application.

##### Implémentations demandées

1. Ecrire le bridge qui permet de déclencher une alerte (Android Toast) dans le contexte de Java. Sur l'interface, 
un bouton devra permettre de déclencher cette alerte.
2. Ecrire le code Javascript et Java nécessaire pour que l'alerte affiche une chaine de caractères renseignée par 
l'utilisateur dans un `<input>` sur la page html.
3. Ecrire le bridge qui permet d'ouvrir la caméra de l'appareil. Implémenter le code qui permet de prendre une photo 
et de l'afficher sur l'interface utilisateur.
4. Ecrire le code javascript et Java nécessaire pour afficher les SMS de l'utilsateur dans le contexte de la *Webview*.
Vous pourrez utiliser le composant [List](https://mobileui.github.io/#list) du framework `mobileui`.
5. Ecrire le bridge qui permet d'afficher l'identifiant unique de l'appareil dans le contexte de la Webview.
6. Chiffrer le contenu du répertoire photo du téléphone à l'aide de la [`Java Cryptography Architecture`](https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html)
Vérifier qu'il est impossible pour l'utilisateur d'accéder à ses photos après cette opération.
7. Ecrire le code HTML/JS ainsi que le bridge nécessaire pour permettre à l'utilisateur de composer le numéro de 
téléphone de son choix.

ressources : [https://developer.android.com/guide/webapps/webview](https://developer.android.com/guide/webapps/webview)*

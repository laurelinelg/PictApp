

## Partie I

1.	Lancer le programme GNS3.
2.	Utiliser GNS3 en tant que serveur local uniquement.
3.	Importer les images des routeurs Cisco
4.	Ajouter le répertoire dans lequel se trouvent les images des routeurs Cisco dans l’onglet General -> Binary images. Puis configurer les images dans l’onglet Dynamips/IOS Routers/New. Si vous êtres curieux, vous pouvez consulter la fiche technique des routeurs simuler sur le site de Cisco.
5.	GNS3 est maintenant fonctionnel, créer un projet. Il vous est dorénavant possible de glisser/déposer un routeur de la série 7200 sur le plan de travail (voir Figure ci-dessous). 

![Alt text](gns3.png?raw=true "Détail de la fenêtre du simulateur")

6.	Faire un clique droit sur le routeur déposé sur le plan de travail et démarrer le.  Par défaut, le simulateur consomme 100% du CPU car il n’a aucune connaissance des moments d’inactivité des machines virtuelles en cours d’exécution. Pour résoudre ce problème, on fixe une valeur idle-pc pour forcer la mise en veille des machines virtuelles lorsqu’elles rentrent dans des cycles d’inactivité. 
7.	Pour fixer la valeur idle-pc la plus adéquate, faire un clique droit sur le routeur et sélectionner l’action idle-pc, le simulateur calculera automatiquement la valeur la plus judicieuse.
8.	Comme décrit sur le site du constructeur , les routeurs de la série 7200 sont des routeurs modulaires 
ce qui les rend facilement extensible. Vérifier que votre routeur est équipé du module C7200-IO-2FE (2 ports Fast Ethernet / Ethernet).
  - Faire un clique droit sur le routeur ; sélectionner configure ; Dans la boite de dialogue, 
    sélectionner le routeur concerné; puis dans l’onglet slots, vérifier que dans le slot0 se trouve bien le module C7200-IO-2FE.

## Partie II

**Configuration interne d'un routeur**
On rappel qu’un routeur se distingue des autres entités réseaux comme les Ponts, les Hubs, et les Switch par le fait qu’il embarque un processeur, un système d’exploitation et de la mémoire. La Figure ci-dessous, illustre la structure interne généralement observée d’un routeur.

![Alt text](gns3-structure-interne-router.png?raw=true "Structure interne d’un routeur")

Dans la suite de cette partie, nous allons nous intéresser aux différents principes de fonctionnement/configuration de l’OS des routeurs de type Cisco (IOS).

**Les modes de l’IOS Cisco**

L’OS des routeurs Cisco dispose de deux principaux modes de fonctionnement : (i) le mode utilisateur et (ii) le mode privilège. Par défaut, lorsque l’on se connecte au routeur, l’utilisateur se trouve dans le mode utilisateur ; Le mode utilisateur est caractérisé par l’invite de commande ‘>’ (voir ci-dessous, 1) tandis que le mode privilège est caractérisé par l’invite de commande ‘#’ (voir ci-dessous, 2) . Un mode peut être décomposer en sous modes. Par exemple, la configuration d’une interface réseau se fait par le sous mode config-if du mode configuration (voir ci-dessous, 4) .

1.	Connectez vous au routeur précédemment déposé sur le plan de travail. Faire un clique droit sur le routeur et sélectionner console pour ouvrir la console du routeur. A la question **Would you like to enter the initial configuration dialog ?**, répondre **no**.

2.	Chaque mode de fonctionnement dispose d’une liste limitée de commandes. Pour afficher les commandes disponibles dans un mode, utilisez la commande **?**. Saisir la commande **exit** ou tapez **Ctrl-Z** pour sortir d’un mode. Comparez les commandes disponibles dans le mode utilisateur (Figure ci-dessus,1) , privilège (Figure ci-dessus,2) et configuration (Figure ci-dessus,3) .

3.	Suivant les modes de fonctionnement, les commandes n’offrent pas les mêmes options. Comparer le résultat des commandes **show ?** dans le mode utilisateur et privilège. Exécutez la commande **show running-config** dans le mode utilisateur. Que se passe t’il ? Réitérez dans le mode privilégié. Quelles sont les caractéristiques des interfaces réseaux du routeur ? Identifiez le nom du routeur. Changer le nom du routeur.

4.	Changer le message du jour. Dans le mode configuration, tapez **banner motd #** puis saisir une phrase sur 2 lignes. Sortir du mode faisant un **exit** ou **Ctrl-Z**

5.	Protéger l’accès au *mode privilège* en spécifiant un mot de passe. Dans le mode configuration, utiliser la commande **enable password**.

6.	On cherche à modifier la description de l’interface FastEthernet du slot 0/0. Quel mode devez vous utiliser ? Une fois dans ce mode, chercher la commande adéquate, ajouter une description puis sortir du mode faisant un **exit** ou **Ctrl-Z**.

7.	Toutes les commandes précédentes modifient la configuration active du routeur. Selon vous dans quelle zone mémoire se situe la configuration active du routeur ? Que se passe t’il si vous redémarrez le routeur ?

8.	Quelle est la principale caractéristique de la mémoire NVRAM ? Dans le mode privilège, vérifier le contenu de la mémoire NVRAM du routeur (**dir nvram:**). Tapez **copy running-config startup-config**. Quel est l’effet de cette commande ? vérifier.

9.	En cas de mauvaises manipulations, vous pouvez toujours effacer le fichier startup-config par la commande **erase startup-config**.

10.	Tapez les commandes ci-dessous et précisez à quel niveau OSI les commandes suivantes collectent les informations : 
  a.	show interface
  b.	show ip interface
  c.	show ip route
  d.	ping
Ces commandes vous seront utiles ultérieurement pour vérifier la configuration du routeur.

## Partie III

**Configuration basique du routeur**
1.	Lancer le simulateur GNS3. Glisser/déposer deux routeurs sur le plan de travail. Un routeur du type c7200 fera office de routeur et un autre de type c3600 fera office de PC. Reliez les deux entités par un câble *FastEthernet*.

2.	Changer le nom du routeur c7200 afin qu’il se nomme R0 et changer le nom du routeur c3600 afin qu’il se nomme PC1.

3.	Quelles sont les caractéristiques IP par défaut du routeur ?

4.	Assigner l’addresse IP 192.168.1.1 au routeur R0. Rappeler à quoi sert le *netmask*, et indiquer/justifier quel *netmask* avez vous choisi. 

5.	Vérifier la configuration IP de l’interface et activer là.

**Initiation à l’analyse de protocole : exemple de CDP et ECTP**

6.	Activer la capture de l’interface *FastEthernet* du routeur connecté à PC1 (R0 (f1/0) -> PC1 (f0/0)). Patienter quelques minutes et lancer *Wireshark* sur la capture réalisée. Identifier les protocoles utilisés sur lien réseau. 

7.	Identifier les trames encapsulant le protocole CDP (*Cisco Discovery Protocol*). En analysant la trame vous devriez comprendre le rôle de ce protocole et pouvoir répondre aux questions suivantes. A chaque réponse indiquer à quelle couche du modèle OSI vous vous situez.
  a.	 A qui est destiné le message envoyé ? Quelle est l’adresse IP du destinataire ? Pourquoi ?
  b.	Quelle est la fréquence de ces messages ?
  c.	Quelles informations fondamentales ce protocole véhicule ?
8.	Réinitialiser la capture du trafic réseau de *Wireshark* (arrêter, puis démarrer une nouvelle capture). Assigner dorénavant une adresse IP à la station PC1 de façon à ce qu’elle soit sur le même réseau IP que le routeur R0. La station PC1 utilise t’elle le protocole CDP ? Pourquoi ?  

9.	Tester alternativement les commandes relatives au protocole CDP sur le routeur R0 et la station PC1 via les commandes **show cdp trafic, show cdp neighbors, show cdp neighbors détail, show cdp**. Ces commandes génèrent-elles de nouveaux messages CDP sur le réseau ? Quel est le mode de fonctionnement de ce protocole ?

10.	Le protocole CDP ne nous sera plus utile momentanément. Désactiver CDP de l’interface, sur laquelle le protocole a été perçu, à l’aide de la commande **no cdp run** sur chaque routeur. Cette commande génère t’elle du trafic réseau ? Pourquoi ? L’usage du protocole CDP peut-il être gênant sur un réseau de production ? Pourquoi ?

11.	A terme, vous ne devriez capturer plus que des messages réseaux issus du protocole ECTP (Ethernet Configuration Testing Protocol).  A votre avis, suivant la capture *Wireshark* obtenue, dans quel but le protocole ECTP est-il utilisé par les routeurs CISCO ? 

12.	Désactiver le protocole ECTP sur chacune des interfaces utilisées via la commande **no keepalive**. Normalement vous ne devriez ne plus avoir de trafic capturé.


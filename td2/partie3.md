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




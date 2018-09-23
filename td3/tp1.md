

## Partie I - Configuration de la distribution

1.	Utiliser [Etcher](https://etcher.io) pour flasher votre carte SD avec [Kali Linux](https://images.offensive-security.com/arm-images/kali-linux-2018.3-rpi2.img.xz)

1. Qu'est ce qu'une *link-local address*? Voir la RFC 3927.

1. Faire un montage de la carte SD sur votre station hôte. Modifier le fichier *cmdline.txt* afin de spécifier une adresse IP de démarrage. Ajouter à la fin de la ligne la directive **ip=x.x.x.x**. Spécifier une adresse IP suivant la RFC 3927.

1. Connecter la Raspberry Pi à votre station hôte à l'aide d'un cable Ethernet. Puis se connecter en **ssh** en tant que **root**. Utilisez le login **toor**.

1. A quoi sert le protocole **SSH**?

## Partie II - Configuration wifi

### Concepts
1. Utiliser la commande **ifconfig** pour activer la carte réseau sans fil **wlan0**. Faire un **man ifconfig** pour plus d'informations.

1. Faire un **ifconfig wlan0**. Quelles informations peut-on obtenir sur l'interface sans fil?

1. Faire un **iwconfig wlan0**. Quelles informations peut-on obtenir sur l'interface sans fil?

1. Le point d'accès utilise WPA2 pour s'authentifier et encrypter les communications.
    - A quoi sert une **PSK**? Pour quelles raisons utilise-t-on une **PSK**?

1. La station et le point d'accès doivent partager une même **PMK** (*Pairwise Master Key*), sachant que dans notre contexte **PMK=hash(ssid,psk)**.
    - Pourquoi la **PMK** n'est elle pas utiliser directement pour encrypter les communications?

1. Une **PTK** (*Pairwise Transcient Key*) doit être générée pour encrypter les données. Pour information la PTK correspond à un ensemble de 3 clés **KCK, KEK et TK** dérivé à l'aide de la PMK et d'un **4-way handshake**.
    - KCK = Key Confirmation Key, permet de garantir l'authenticité de l'origin
    - KEK = Key Encryption Key, permet de garantir la confidentialité dans les échanges de clés.
    - TK  = Temporal Key, permet d'encrypter les données.

    ![Alt text](images/pmk.png?raw=true "Pairwise Master Key")

    - Le **4-way handshake** fonctionne de la manière suivante:

| N° Phase         | Explication |
| :--------------- |:---------------:|
|  (Phase 1)       | Le point d'accès (A, comme *Access Point*) génère et envoie *msg1* qui contient un nombre aléatoire *ANonce* sans le chiffrer et sans l'authentifier.|
|  (Phase 2)       |A la réception de *msg1*, le client (S, comme *Supplicant*) génère son propre nombre aléatoire *SNonce*. Et calcule une **PTK** basé sur: PTK= G(Anonce,SNonce,@MAC A,@MAC S). Attention, une **PTK** n'est pas juste une seule clé mais correspond à hiérarchie de 3 clés: KCK, KEK et une TK. Le client envoie alors un message *msg2* contenant SNonce et les paramètres de sécurité. La totalité du message est soumis à une vérification d'authentification grâce à la clé KCK. Cela correspond au **MIC** (Message Integrity Check).
|  (Phase 3)      |A la réception du *msg2*, le point d'accès doit pouvoir extraire le *SNonce* pour calculer, à son tour, la **PTK**, et vérifier le **MIC** du *msg2* afin de s'assurer que le client connaît bien la **PMK**. Enfin le point d'accès peut envoyer le *msg3* qui contient un clé **GTK** pour cypter les traffic en diffusion. La clé **GTK** est cryptée avec la **KEK**. La totalité du message est vérifiée *via* le **MIC** calculé grâce à la clé KCK.|
|  (Phase 4)      |Enfin le dernier message *msg4* est envoyé par le client afin d'acquitter la réussite du 4-way Handshake, et indique que le client a correctement installé les clés et qu'il est prêt à commencer le chiffrement des données.|

### Configuration

1. Mainentant que l'on a compris globalement le fonctionnement du WPA2, on souhaite analyser/comprendre l'authentification auprès du point d'accès **TP-Link_39C5**. Pour ce faire:
    - Lancer dans une 2e console, une deuxième connexion **ssh**.
    - Lancer **tshark** pour capturer le traffic. On souhaite capturer les 10 premiers paquets de l'interface **wlan0** dans un fichier de capture *wpa.pcap*.
    - Faire un **man tshark** pour plus d'informations.

1. Générer une **PSK** (*Pre Shared Key*) à l'aide de l'outil **wpa_passphrase** dans un fichier nommé **wpa.conf**. Faire un **man wpa_passphrase** pour plus d'informations.

1. Configurer la carte wifi intégrée au Raspberry pour se connecter au réseau **TP-Link_39C5** avec le mot de passe **18048452**.
    - Utiliser la commande **wpa_supplicant** sur l'interface **wlan0** en background en utilisant le fichier *wpa.pcap* généré précédemment.
    - **wpa_supplicant** va s'occuper de mettre en place la **PTK** pour encrypter les données échangées entre la station et le point d'accès.

1. Une fois la **PTK** installer, vous devriez obtenier le message suivant:
    ```console
    Successfully initialized wpa_supplicant
    ```
1. Lancer dans une 3e console, une troisième connxion ssh.
    - Analyser le traffic échanger à l'aide de **tshark** et du fichier *wpa.pcap* généré précédemment. Cette fois-ci **tshark** n'est plus utilisé pour capturer el traffic mais pour analyser le traffic contenu dans le fichier *wpa.pcap*.
    - Identifier les trames correspondantes au 4-way handshake.  
    - Trouver la commande adéquate de **tshark** permettant de filtrer dans le fichier *wpa.pcap* uniqumement les trames du 4-way handshake. Analyser en détail ces trames en fonction de l'explication donnée auparavant. C'est à dire identifier les différents éléments/champs dans le contenu de chacune des trames.
    - Schématiser.

1. Refaire un **ifconfig** et un **iwconfig** sur l'interface **wlan0**. Indiquer les nouvelles informations obtenues.

1. La station ne peut toujours pas interagir car elle ne dispose pas encore d'adresse IP.
    - Capturer le traffic à l'aide de **tshark** dans un fichier *dhcp.pcap*
    - Utiliser le client DHCP avec la commande **dhclient** pour obtenir dynamiquement une @IP à partir du point d'accès.
    - Faire un **man dhclient** pour plus d'informations

1. Refaire un **ifconfig**. Vérifier dorénavant qu'une @IP vous a été affectée.

## Partie III - Exploration du réseau

1. Comment feriez-vous pour collecter l'ensemble des @IP des stations connectées au réseau WIFI?
    - Vérifier vos hypothèses en utilisant l'utilitaire **nmap** avec l'option **-sn**.
    - Faire un **man nmap** pour plus d'infomations.
    - Capturer le traffic AVANT de lancer la commande afin de déterminer la technique utilisée par nmap.
    - Expliquer.

1. On souhaite utiliser une autre technique pour détecter les stations sur le réseau.
    - Utiliser **tshark** pour capturer le traffic dans un fichier *arp-scan.pcap*
    - Utilier la commande **arp-scan** sur l'interface **wlan0** avec les options **--interface** et **--localnet**
    - Analyser le fichier *arp-scan.pcap*. Expliquer. Quelles sont les différences avec **nmap**?

1. Sur les différents hôtes trouver, on souahite détecter quels sont les services disponibles.
    - Utiliser **tshark** pour capturer le traffic dans un fichier *port-scan.pcap*
    - Utiliser l'utilitaire **nmap** avec l'option **-sV**.
    - Analyser le fichier *port-scan.pcap*. Expliquer le fonctionnement.

## Partie IV - Première attaque WIFI JAM

On souhaite réaliser un déni de service sur le réseau.
Une attaque de type DOS. Pour se faire, nous allons utiliser une nouvelle interface réseau en USB. La particularité de cette carte réseau est de supporter deux modes de fonctionnement fondamentaux:

  - Mode monitor
  - Injection de trames 802.11

1. Connecter la carte réseau Alfa sur votre Raspberry.
    - Vérifier que la nouvelle interface est bien détectée.
    - Lister l'ensemble des interfaces réseaux.
      ```
      root@kali:~# airmon-ng
      ```

    - Démarrer **airmon-ng** sur **wlan1**
      ```console
      root@kali:~# airmon-ng start wlan1
      ```

    - Cette commande crée une nouvelle interface en mode monitor ayant comme nom, par exemple,  *wlan1mon* suivant votre système. Vérifier l'existence de cette interface en faisant un **ifconfig**.

1. Analyser le traffic sur cette nouvelle interface.
    - Lancer l'analyse à l'aide de **airodump**
    ```console
    root@kali:~# airodump-ng wlan1mon
    ```

    - A partir de l'analyse de **airodump-ng**, récupérer le **BSSID** (**@MAC** du point d'accès) et **le numéro de canal de fréquence** utilisé par le point d'accès. Quitter.
    - Capturer avec **tshark** uniquement le traffic sur l'interface **wlan1mon** provenant uniquement du point d'accès TP-Link_39C5 en utilisant un filtre de capture (-f) dans un fichir airodump.pcap.
    ```console
    root@kali:~# tshark -i wlan1mon -f "wlan host BSSID"
    ```

    - Dorénavant, analysons spécifiquement notre point d'accès en spécifiant le canal (-c), et le bssid (--bssid).
    - Faire un *man airodump-ng* pour plus d'information.
    - Faire un ping à partir de votre interface *wlan0* pour simuler éventuellement du traffic.
    - Vous devriez obtenir toutes les stations connectées.
    - Notez le BSSID du point d'accès et les @MAC des stations connectées.
    - Arrêter la capture, étudier le fichier airodump.pcap. Comparer cette technique avec **nmap** et **arp-scan**.

  ![Alt text](images/airodump1.png?raw=true "Pairwise Master Key")

1. Dorénavant, nous allons injecter des trames afin de forcer la déconnection de tous les clients du point d'accès TP-Link_39C5.

  - Capturer avec **tshark** le traffic sur l'interface **wlan1mon** provenant uniquement du point d'accès TP-Link_39C5 en utilisant un filtre de capture (-f) dans un fichier deauth.pcap.

  - Nous allons injecter du traffic avec **aireplay-ng** en utilisant l'attaque 0.

  - Choisissez l'@MAC d'une station et déconnecter la du réseau.
    ```console
    aireplay-ng -0 0 -a  BSSID -c @MAC_STATION wlan1mon
    ```

  - Vérifier que la station est bien déconnectée. Arrêter la capture, et l'attaque. Analyser la capture deauth.pcap. Expliquer le fonctionnement de l'attaque. Illustrer avec des fragments de la capture de traffic *deauth.pcap*. En particulier, identifier les trames correspondant à l'attaque et les trames de reauthentification.

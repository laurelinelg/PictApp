Une partie de ce TP consiste à monter une attaque réseau. Cette pratique n'est autorisée que sur un réseau vous appartenant ou pour lequel vous avez les autorisations écrites nécessaires émanant des autorités idoines. Il va sans dire que toutes tentatives d'effectuer ce genre de chose sur les réseaux de l'ESIR, l'ISTIC, Rennes 1 ou autre et passible de sanctions disciplinaires et d'exclusion définitive, voire d'un passage par la case prison sans toucher 20 000 euros.


**Article 323-1 du code pénal, Modifiée par LOI 2012-410 du 27 mars 2012 - art. 9}**

Le fait d'accéder ou de se maintenir, frauduleusement, dans tout ou partie d'un système de traitement automatisé de données est puni de deux ans d'emprisonnement et de 30000 euros d'amende.

Lorsqu'il en est résulte soit la suppression ou la modification de données contenues dans le système, soit une altération du fonctionnement de ce système, la peine est de trois ans d'emprisonnement et de 45000 euros d'amende.

Lorsque les infractions prévues aux deux premiers alinèas ont été commises à l'encontre d'un système de traitement automatisée de données à caractère personnel mis en oeuvre par l'Etat, la peine est portée à cinq ans d'emprisonnement et à 75 000 euros d'amende. 

> A la racine, vous devrez fournir un fichier `td1-Prenom-Nom.pdf` qui contient le rapport du **TD** et l'explication des commandes que vous avez produit, et/ou des résultats attendus (Le nom fichier doit contenir votre prénom et nom)!. 

## Partie I - Configuration de la distribution

1.	Utiliser [Etcher](https://etcher.io) pour flasher votre carte SD avec [Kali Linux](https://images.offensive-security.com/arm-images/kali-linux-2019.3-rpi3-nexmon.img.xz
)

1. Qu'est ce qu'une *link-local address*? Voir la RFC 3927.

1. Faire un montage de la carte SD sur votre station hôte. Modifier le fichier *cmdline.txt* afin de spécifier une adresse IP de démarrage. Ajouter à la fin de la ligne la directive **ip=x.x.x.x**. Spécifier une adresse IP suivant la RFC 3927. **Attention, le fichier ne doit contenir qu'une seule ligne** et doit être uniquement éditer avec un éditeur de texte.

1. Connecter la Raspberry Pi à votre station hôte à l'aide d'un cable Ethernet. Puis se connecter en **ssh** en tant que **root**. Utilisez le login **toor**.

1. A quoi sert le protocole **SSH**?

## Partie II - Configuration wifi 

### Concepts
1. Utiliser la commande **ifconfig** pour activer la carte réseau sans fil **wlan0**. Faire un **man ifconfig** pour plus d'informations.

1. Faire un **ifconfig wlan0**. Quelles informations peut-on obtenir sur l'interface sans fil?

1. Faire un **iwconfig wlan0**. Quelles informations peut-on obtenir sur l'interface sans fil?

1. Le point d'accès utilise WPA2 pour s'authentifier et encrypter les communications.
    - A quoi sert une **PSK** (*Pre Shared Key*)? Pour quelles raisons utilise-t-on une **PSK**?

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

1. Générer une **PSK** (*Pre Shared Key*) à l'aide de l'outil **wpa_passphrase** dans un fichier nommé **wpa.conf**.
    - Faire un **man wpa_passphrase** pour plus d'informations.

1. Configurer la carte wifi intégrée au Raspberry pour se connecter au réseau **TP-Link_39C5** avec le mot de passe **18048452**.
    - Utiliser la commande **wpa_supplicant** sur l'interface **wlan0** en background en utilisant le fichier *wpa.conf* généré précédemment.
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


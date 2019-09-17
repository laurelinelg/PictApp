## Informations légales 

Une partie de ce TP consiste à monter une attaque réseau. Cette pratique n'est autorisée que sur un réseau vous appartenant ou pour lequel vous avez les autorisations écrites nécessaires émanant des autorités idoines. Il va sans dire que toutes tentatives d'effectuer ce genre de chose sur les réseaux de l'ESIR, l'ISTIC, Rennes 1 ou autre et passible de sanctions disciplinaires et d'exclusion définitive, voire d'un passage par la case prison sans toucher 20 000 euros.


**Article 323-1 du code pénal, Modifiée par LOI 2012-410 du 27 mars 2012 - art. 9}**

Le fait d'accéder ou de se maintenir, frauduleusement, dans tout ou partie d'un système de traitement automatisé de données est puni de deux ans d'emprisonnement et de 30000 euros d'amende.

Lorsqu'il en est résulte soit la suppression ou la modification de données contenues dans le système, soit une altération du fonctionnement de ce système, la peine est de trois ans d'emprisonnement et de 45000 euros d'amende.

Lorsque les infractions prévues aux deux premiers alinèas ont été commises à l'encontre d'un système de traitement automatisée de données à caractère personnel mis en oeuvre par l'Etat, la peine est portée à cinq ans d'emprisonnement et à 75 000 euros d'amende. 

## Rappels

> A la racine, vous devrez fournir un fichier `td2-Prenom-Nom.pdf` qui contient le rapport du **TD** et l'explication des commandes que vous avez produit, et/ou des résultats attendus (Le nom fichier doit contenir votre prénom et nom)!. 

## Partie I - Première attaque WIFI JAM

>On part du principe que la carte réseaux wifi du Raspberry Pi a bien été configurée (Voir [TD1](../td1/td1.md)). 

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
    - Capturer avec **tshark** le traffic sur l'interface **wlan1mon** provenant uniquement du point d'accès TP-Link_39C5 en utilisant un filtre de capture (-f) dans un fichir airodump.pcap.
    ```console
    root@kali:~# tshark -i wlan1mon -f "wlan host BSSID"
    ```

    - Dorénavant, analysons spécifiquement notre point d'accès en spécifiant le canal (-c), et le bssid (--bssid).
    - Faire un *man airodump-ng* pour plus d'information.
    - Faire un ping à partir de votre interface *wlan0* pour simuler éventuellement du traffic.
    - Vous devriez obtenir toutes les stations connectées.
    - Notez le BSSID du point d'accès et les @MAC des stations connectées.
    ![Alt text](images/airodump1.png?raw=true "Pairwise Master Key")
    - Arrêter la capture, étudier le fichier airodump.pcap. Comparer cette technique avec **nmap** et **arp-scan**.

1. Dorénavant, nous allons injecter des trames afin de forcer la déconnection de tous les clients du point d'accès TP-Link_39C5.
    - Capturer avec **tshark** le traffic sur l'interface **wlan1mon** provenant uniquement du point d'accès TP-Link_39C5 en utilisant un filtre de capture (-f) dans un fichier deauth.pcap.
    - Nous allons injecter du traffic avec **aireplay-ng** en utilisant l'attaque 0.
    - Choisissez l'@MAC d'une station et déconnecter la du réseau.
    ```console
    aireplay-ng -0 0 -a  BSSID -c @MAC_STATION wlan1mon
    ```

    - Vérifier que la station est bien déconnectée. Arrêter la capture, et l'attaque. Analyser la capture deauth.pcap. Expliquer le fonctionnement de l'attaque. Illustrer avec des fragments de la capture de traffic *deauth.pcap*. En particulier, identifier les trames correspondant à l'attaque et les trames de reauthentification.

1. Quel est l'intérêt du **WIFI JAM**? 


## Partie II - Brute force attaque
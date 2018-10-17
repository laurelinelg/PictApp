Une partie de ce TP consiste à monter une attaque réseau. Cette pratique n'est autorisée que sur un réseau vous appartenant ou pour lequel vous avez les autorisations écrites nécessaires émanant des autorités idoines. Il va sans dire que toutes tentatives d'effectuer ce genre de chose sur les réseaux de l'ESIR, l'ISTIC, Rennes 1 ou autre et passible de sanctions disciplinaires et d'exclusion définitive, voire d'un passage par la case prison sans toucher 20 000 euros.


**Article 323-1 du code pénal, Modifiée par LOI 2012-410 du 27 mars 2012 - art. 9}**

Le fait d'accéder ou de se maintenir, frauduleusement, dans tout ou partie d'un système de traitement automatisé de données est puni de deux ans d'emprisonnement et de 30000 euros d'amende.

Lorsqu'il en est résulte soit la suppression ou la modification de données contenues dans le système, soit une altération du fonctionnement de ce système, la peine est de trois ans d'emprisonnement et de 45000 euros d'amende.

Lorsque les infractions prévues aux deux premiers alinèas ont été commises à l'encontre d'un système de traitement automatisée de données à caractère personnel mis en oeuvre par l'Etat, la peine est portée à cinq ans d'emprisonnement et à 75 000 euros d'amende.


## Partie I - Configuration WIFI

1. Configurer la carte wifi native de votre Raspberry PI de sorte qu'elle soit connectée à la borne `WIFI` TP-Link_39C5.
2. Conecter la carte réseau Alfa sur votre Raspberry PI. Activer la nouvelle interface réseau en mode moniteur.

## Partie II - Fake AP
3. Analyser le traffic sur la carte `Alfa` grâce à la commande `airodump-ng`.
1. Noter le channel, l'adresse `MAC`, et le nom de la borne `WIFI` de votre cible (par exemple TP-Link_39C5).
1. Créer une borne `WIFI` sur le même canal avec le même nom à l'aide de la commande `airbase-ng`. Fair un `man airbase-ng`.
  - Des que `airbase-ng` est lancé, une nouvelle interface apparaît `atX`, `X` étant le numéro de l'interface correspondante.
  - Cette interface sera celle de votre AP!
1. Configurer l'interface réseau de votre AP.
1. Analyser le traffic, comparer les résultats avec la précédente analyse. Que remarquez-vous? Expliquer
1. Que se passe t'il si un client se connecte sur votre fake AP?
1. Mettez en place un serveur `DHCP` sur votre Raspberry PI.
  - Vérifier l'existance de l'executable `dnsmasq`
  - Si non présent, installer `dnsmasq`. (Faire un apt-get install)
  - Modifier le fichier de configuration de `dnsmasq` de façon adequate
      - Supprimer la fonction `dns`
      - Indiquer sur quelle interface écouter les requêtes `DHCP`
      - Configurer judicieusement la plage d'adresse IP
      - A quoi sert l'option 3 de DHCP? Faire notamment un `dnsmasq --help dhcp` pour avoir plus de détail, et/ou un `man dnsmasq`.
  - Une fois le fichier de configuration bien modifier, lancer le serveur `dnsmsq` *via* la commande `dnsmasq -C fichier.conf -d`
1. Activer le routage de paquets entre interfaces dans le kernel linux, faire un `echo 1 > /proc/sys/net/ipv4/ip_forward`
1. Transferer le traffic venant du fake AP vers la borne Wifi qui dispose d'Internet: `iptables -t nat -A POSTROUTING -o VotreInterface -j MASQUERADE`
1. Capturez le traffic des clients
1. Tester
1. Le Fake AP n'est pas parfait pourquoi? Que proposez-vous?

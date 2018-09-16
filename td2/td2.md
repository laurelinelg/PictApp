# Rappels Réseaux: de la théorie à la pratique

## Objectifs

Ce TD/TP consiste à rappeler les concepts théoriques concernant les routeurs à un cas pratique afin d’assimiler les notions élémentaires de protocole, de routage, et plus largement  l’activité des éléments actifs d’un réseau. 
Ce TD/TP se décompose en trois parties : 

**1- Partie I – Mise en place d’un laboratoire virtuel.**
  - Installation/Manipulation d’un simulateur réseau graphique afin de simuler la mise en œuvre de topologie réseau complexe. 
  - Simulation du déploiement d’un routeur Cisco

**2- Partie II – Analyse des propriétés fonctionnelles et non fonctionnelles d’un routeur.**
  - Etude des différents éléments d’un routeur
  - Analyse des différents modes de fonctionnement d’un routeur 

**3- Partie III – Mise en pratique des concepts fondamentaux des réseaux.**
  - Réalisation d’une configuration basic de routeur
  - Tester le réseau mis en œuvre
  - Analyse (capture et détramage) d’un protocole réseau.
  - Rappels des protocoles CDP, ARP, DHCP
  - Principe du résolveur DNS
**Résumé des commandes apprises.**

Ce TD/TP repose sur deux principaux outils open source : Dynamips  et gns3 . 

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

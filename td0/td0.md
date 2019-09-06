## Partie I - Configuration de la distribution

1.      Utiliser [Etcher](https://etcher.io) pour flasher votre carte SD avec [Kali Linux](https://images.offensive-security.com/arm-images/kali-linux-2018.3-rpi2.img.xz)

1. Qu'est ce qu'une *link-local address*? Voir la RFC 3927.

1. Faire un montage de la carte SD sur votre station hôte. Modifier le fichier *cmdline.txt* afin de spécifier une adresse IP de démarrage. Ajouter à la fin de la ligne la directive **ip=x.x.x.x**. Spécifier une adresse IP suivant la RFC 3927. **Attention, le fichier ne doit contenir qu'une seule ligne** et doit être uniquement éditer avec un éditeur de texte.

1. Connecter la Raspberry Pi à votre station hôte à l'aide d'un cable Ethernet. Puis se connecter en **ssh** en tant que **root**. Utilisez le login **toor**.

1. A quoi sert le protocole **SSH**?
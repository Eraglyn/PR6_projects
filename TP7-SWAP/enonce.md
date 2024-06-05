**L3 Informatique -- Programmation efficace**

## Swap space
 
Vous administrez un grand cluster de machines dont les disques utilisent
des systèmes de fichiers affreusement variés. Vous décidez de tout
homogénéiser, donc de reformater tous les disques. L'opération est
compliquée car tous ces disques sont actuellement remplis de données
importantes qu'il est hors de question de perdre. De plus, reformater un
disque peut changer significativement sa capacité -- en bien ou en mal!
Vous allez donc avoir besoin de racheter des disques, et naturellement
vous devez éviter de gaspiller inutilement les fonds de l'entreprise,
donc minimiser la taille du stockage supplémentaire à acheter.

Vous pouvez reformater les disques dans n'importe quel ordre. Vous devez
impérativement sauvegarder le contenu d'un disque avant de le reformater,
et vous pouvez fractionner les données sur plusieurs disques si
nécessaire. Une fois reformaté, le disque est à nouveau opérationnel et
peut servir à stocker les données des disques restant à reformater. En
fin d'opération, les données initiales doivent toutes persister, mais 
peuvent se trouver sur n'importe quel disque sans aucune contrainte.


##### Objectif 

Déterminer l'espace disque supplémentaire **minimal** nécessaire.


##### Entrée 

Les instances sont fournies dans des fichiers au format suivant : 
```bash
n           # nombre de disques du cluster
a1 b1       # capacités du disque 1 avant et après reformatage (en Go)
a2 b2       # capacités du disque 2 avant et après reformatage (en Go)
...
an bn       # capacités du disque n avant et après reformatage (en Go)
```

Bornes sur les paramètres : 1 ≤ `n` ≤ 10^6, 1 ≤ `ai`, `bi` ≤ 10^9

##### Sortie

Le fichier de sortie est constitué d'une seule ligne contenant un entier `c`, 
capacité de stockage supplémentaire nécessaire (toujours en Go).

##### Exemples

Pour le fichier d'entrée suivant :
```bash
4
6 6
1 7
3 5
3 5
```
la sortie attendue est
```bash
1
```

alors que pour le fichier d'entrée
```bash
4
2 2
3 3
5 1
5 10
```
la sortie attendue est
```bash
5
```


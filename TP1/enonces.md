# Séance nº1

**L3 Informatique -- Programmation efficace**

### Problème nº1

Pour préparer un gâteau, on dispose d’une longue liste d’ingrédients. Et
pour chacun d’eux, de deux informations : 
- la quantité requise pour faire **un** gâteau 
- le stock disponible.

##### Objectif
Calculer le nombre maximal de gâteaux que vous pouvez faire
avec le stock actuel...

##### Entrée 
Les fichiers d’entrée ont le format suivant :
```bash
N           # nb d’ingrédients
a1  s1      # ai : quantité de l’ingrédient i pour un gâteau
a2  s2      # si : stock de l'ingrédient i
…
aN  sN
```

##### Sortie
Un fichier contenant une ligne avec la solution.

[Fichiers de test](GATEAU)


### Problème nº2 (spécial dédicace à Gabin, Yago et Yanis !)

Le championnat du monde de XX voit `N` équipes s’affronter. `K` matchs vont
être joués à cette occasion. Chaque match oppose deux équipes : l'une va gagner
(et marquer un point), l’autre va perdre (et perdre un point).

##### Objectif

Décider, pour chaque match, de l’équipe gagnante (et de l’équipe perdante!) 
de manière à obtenir in fine le championnat le plus serré possible
_(c'est-à-dire tel que l’écart entre le score des mieux classés et
celui des moins bien classés soit minimal)_

##### Entrée 
Les fichiers d’entrée ont le format suivant :
```bash
N   K       # nb d’équipes et d'ingrédients
e1  e’1     # équipes s’affrontant lors du premier match
e2  e’2     # équipes s’affrontant lors du deuxième match
…
eK  e’K
```
Pour chaque `i` entre 1 et `K`, on a : 1 ≤ `ei`, `e’i` ≤ `N`, `ei` ≠ `e’i`.

##### Sortie
Un fichier contenant K lignes indiquant le vainqueur de chaque match (ligne 1 -> match 1, _etc_).

[Fichiers de test](MATCH)

**L3 Informatique -- Programmation efficace**

## Trouvons la signature !

L'OuMoPo (Ouvroir de Mozaïque Potentielle) lance un grand concours un peu
particulier : chaque participant doit créer une frise, la plus splendide
possible, à l'aide d'un maximum de 26 modèles de carreaux, en y faisant
discrètement apparaître sa **signature** (une suite de carreaux
spécifique) **une et une seule fois**, et en faisant en sorte qu'aucune
autre suite de carreaux plus courte n'ait la même propriété. On tolère
que d'autres suites de même longueur apparaissent également exactement
une fois, mais dans ce cas la signature de l'artiste doit impérativement
être la plus proche du début de la frise.

Le jury élit ensuite la plus belle frise, sans connaître l'identité de
l'auteur. À l'heure de la remise du prix, il faut donc déterminer
celle-ci.

##### Objectif 

Déterminer le vainqueur, c'est-à-dire trouver la signature de la frise
élue par le jury.

##### Entrée 

Les instances sont fournies dans des fichiers constitués d'une seule
ligne contenant une chaîne `s` de longueur `n` (1≤`n`≤300000) sur
l'alphabet `{ 'A', ..., 'Z' }` représentant les 26 modèles de carreaux, 
et terminée par un caractère de fin de ligne qui ne fait pas partie de 
la chaîne.

##### Sortie

Le fichier de sortie attendu doit contenir la plus courte sous-chaîne
apparaissant une unique fois dans `s`. S'il en existe plusieurs de même 
longueur, la sous-chaîne attendue est celle qui apparaît en premier dans
`s`.

##### Exemple

Pour le fichier d'entrée suivant :
```bash
AABAABB
```
la sortie attendue est
```bash
BA
```


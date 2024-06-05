**L3 Informatique -- Programmation efficace**

## Une frise équilibrée

Vous disposez d'un stock de carreaux de faïence de `m` modèles
différents, avec lesquels vous souhaitez réaliser une frise mêlant
harmonieusement les `m` modèles de carreaux : vous voulez placer les
carreaux les uns après les autres en faisant en sorte de respecter à tout
moment de la construction, pour chaque modèle `i`, une fréquence
d'apparition aussi proche que possible d'une valeur `fi` fixée parmi les
carreaux déjà placés.

Plus précisément, notons `si` le nombre de carreaux du modèle `i`
utilisés à un moment donné, et `n` = `s1` + ... + `sm`. Alors la frise
est équilibrée si, à tout moment et pour chaque `i`, `si` est
**strictement** compris entre `n`·`fi` − 1 et `n`·`fi` + 1. 

Notons qu'un début de frise équilibré ne peut pas toujours être prolongé
de manière équilibrée.

##### Objectif 

Déterminer, pour une séquence initiale donnée, le nombre **maximal** de
carreaux qui peuvent être ajoutés à la frise sans enfreindre les règles.

##### Entrée 

Les instances sont fournies dans des fichiers au format suivant : 
- une première ligne formée de deux entiers : le nombre `m` de modèles de
  carreaux, et le nombre `k` de carreaux déjà placés (avec 1 ≤ `m` ≤ 10^5
  et 0 ≤ `k` ≤ 10^5), 
- une deuxième ligne formée de `m` entiers strictement positifs `a1`,
  ..., `am` proportionnnels aux fréquences attendues (avec `a1` + ... +
  `am` ≤ 10^5),
- une troisième ligne formée de `k` entiers entre 1 et `m` donnant les
  modèles des `k` premiers carreaux de la frise déjà posés; il est admis
  que ce début de frise est bien équilibré.

##### Sortie

Le fichier de sortie est constitué d'une seule ligne contenant soit un
entier `l` donnant le nombre maximal de carreaux pouvant être ajoutés,
soit le mot `forever` si la frise peut être poursuivie indéfiniment.

##### Exemples

Pour le fichier d'entrée suivant :
```bash
6 5
2 1 6 3 5 3
1 2 5 3 5
```
la sortie attendue est
```bash
1
```

alors que pour le fichier d'entrée
```bash
6 4
2 1 6 3 5 3
1 2 5 3
```
la sortie attendue est
```bash
forever
```


##### Modalités de rendu

Vous devrez faire un _commit_ en fin de séance, que vous pourrez
compléter ensuite jusqu'à mardi 12 mars au soir.

Votre soumission doit inclure, dans le répertoire `TP4-EQUILIBRE`, 
un script `test.sh` dont l'exécution :
- compile les fichiers sources (si nécessaire),
- crée un sous-répertoire `tmp` (si nécessaire),
- pour chaque fichier `sample/*.in`, exécute votre programme pour créer
  le fichier `tmp/*.out` correspondant,
- vérifie que chaque fichier `tmp/*.out` produit est identique au fichier
  `sample/*.out` fourni.


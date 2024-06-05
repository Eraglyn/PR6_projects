**L3 Informatique -- Programmation efficace**

## Azulejos

Deux céramistes portugais s'associent pour ouvrir une boutique
d'azulejos. Ils ont une petite vitrine où ils veulent exposer certaines
de leurs oeuvres de la manière la plus attrayante possible -- et
équitable : chacun exposera le même nombre d'azulejos. Pour rentabiliser
la place disponible, ils veulent exposer deux rangées d'azulejos, une
pour les oeuvres de chacun des deux artistes, en prenant bien garde que
tous soient bien visibles, y compris ceux de la rangée du fond.  Chaque
azulejo de la rangée du fond doit donc  être plus haut que celui qui se
trouve juste devant lui. Un autre point important est de ranger les
oeuvres par ordre de prix : un client attiré par une oeuvre pourra ainsi
en admirer d'autres qu'il peut également s'offrir, pour finalement
céder à la tentation et pousser la porte du magasin.

##### Objectif 

Trouver un agencement compatible, ou déterminer qu'il n'en existe pas.

##### Entrée 

Les instances sont fournies dans des fichiers constitués de 5 lignes au
format suivant :

```bash
n                   # nombre d'azulejos sur chaque rangée
p11 p12 ... p1n     # prix des azulejos 1 à n à placer dans la rangée du fond
h11 h12 ... h1n     # hauteurs des azulejos 1 à n à placer dans la rangée du fond
p21 p22 ... p2n     # prix des azulejos 1 à n à placer dans la rangée du devant
h21 h22 ... h2n     # hauteurs des azulejos 1 à n à placer dans la rangée du devant
```

Bornes sur les paramètres : 1≤`n`≤5.10^5, `pij`≤10^9, `hij`≤10^9

##### Sortie

S'il existe un agencement valide, le fichier de sortie doit en décrire
un, sous forme de 2 lignes de `n` entiers, chacune décrivant une
permutation des numéros des `n` carreaux d'une rangée : sur la première
ligne, la rangée du fond; sur la deuxième, la rangée du devant. 

S'il n'en existe pas, le fichier de sortie doit contenir le mot
`impossible`.

##### Exemple

Pour le fichier d'entrée suivant :
```bash
4
3 2 1 2
2 3 4 3
2 1 2 1
2 2 1 3
```
une sortie possible est
```bash
3 2 4 1
4 2 1 3
```

tandis que pour le fichier d'entrée :
```bash
2
1 2
2 3
2 8
2 1
```
la sortie attendue est
```bash
impossible
```


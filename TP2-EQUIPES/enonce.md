**L3 Informatique -- Programmation efficace**

## Constituer des équipes !

Dans ce problème, on considère `m` joueurs, et un arbitre, placés sur
certains sommets d'un graphe orienté et pondéré à `n` sommets (`n`>`m`)
et `r` arcs.

On cherche à répartir les `m` joueurs dans `p` équipes
(avec `p`≤`m`) de telle sorte qu'ils puissent communiquer le plus 
efficacement possible entre eux, tout en respectant des règles strictes :
chaque jour, chaque joueur de chaque équipe doit envoyer un message
(différent) à chacun des autres joueurs de son équipe. 

Pour garantir le respect des règles, tous les messages passent par l'arbitre.
Pour garantir leur confidentialité, ils sont cryptés de manière assez
basique : chaque joueur `x` dispose d'une clé personnelle `k_x` connue de
lui seul et de l'arbitre; pour envoyer un message au joueur `y`, `x` code
son message avec `k_x`, l'envoie à l'arbitre qui le décode puis le recode
avec `k_y` et le transmet à `y`. 

Le coût d'un envoi est la somme des poids des arcs par lesquels transite 
le message.

##### Objectif

Répartir les `m` joueurs dans les `p` équipes afin d'avoir un coût de
communication globale minimal. 


##### Entrée

Les fichiers d’entrée ont le format suivant :
- une première ligne formée de 4 entiers séparés par des espaces :
```bash
n m p r
```
où `n` est le nombre de sommets du graphe (2≤`n`≤5000),
`m` le nombre de joueurs (1≤`m`<`n`),
`p` le nombre d'équipes à constituer (1≤`p`≤`m`)
et `r` le nombre d'arcs dans le graphe (1≤`r`≤50000).

- `r` lignes formées de 3 entiers séparés par des espaces décrivant les
  arcs :
```bash
x y l
```
où `x` et `y` sont des sommets du graphe (1≤`x,y`≤`n`) 
et `l` le poids de l'arc `(x,y)` (avec 0≤`l`≤10000).

Aucun arc n'apparaît plus d'une fois, et le graphe est fortement connexe.

Les `m` joueurs sont placés sur les sommets `1` à `m`, et l'arbitre sur
le sommet `m+1`.

##### Sortie
Un fichier contenant une ligne donnant le coût minimal.


##### Exemples

Par exemple, pour l'entrée ci-dessous, le résultat optimal est 13.

```bash
5 4 2 10 
5 2 1 
2 5 1 
3 5 5 
4 5 0 
1 5 1 
2 3 1 
3 2 5 
2 4 5 
2 1 1 
3 4 2
```

Et pour l'exemple ci-dessous, on doit obtenir 24 :

```bash
5 4 2 10 
5 2 1 
2 5 1 
3 5 5 
4 5 10 
1 5 1 
2 3 1 
3 2 5 
2 4 5 
2 1 1 
3 4 2
```

[Fichiers de test](sample)

**L3 Informatique -- Programmation efficace**

## Brisons les chaînes !


### Problème nº1

Pour s'échauffer, on considère le problème suivant : nous disposons d'une
chaîne de caractères `s` sur un alphabet `Σ`, et de `n` chaînes de
caractères `s1`, `s2`, ... `sn` (toujours sur `Σ`).

##### Objectif 

Trouver *une plus courte sous-chaîne de `s` qui contient tous les `si`*
(sa valeur, sa position et sa longueur), et `⊥` si cette sous-chaîne n'existe pas.

##### Exemples

Prenons `s = "aabcaaadabbbccabadaaabaaacd"`

* si `s1 = "ab"`, `s2 = "c"` et `s3 = "d"`, alors le résultat est
  `("cabad", 5, 13)` : la sous-chaîne minimale de `s` contenant les 3
  sous-chaînes `si` est `"cabad`, de longueur 5, commençant à la position 13.

* si `s1 = "ad"`, `s2 = "da"` et `s3 = "ab"`, alors le résultat est `("adab", 4, 6)`.

* si `s1 = "add"`, `s2 = "ab"` et `s3 = "a"`, alors le
  résultat est `⊥`.

L'alphabet `Σ` est inclus dans `{ 'a', ..., 'z', 'A', ..., 'Z' }`.

##### Entrée 
Les instances sont fournies dans des fichiers au format suivant : 
- une première ligne contenant le nombre `n` de sous-chaînes (avec `n`≤10000) 
- la description des chaînes dans l'ordre `s1`, `s2`, ..., `sn`, `s`
  (avec `len(si)≤5000` et `len(s)≤2000000`).

La longueur de chaque ligne est limitée à 80 caractères : chaque chaîne
peut donc être découpée sur plusieurs lignes consécutives, la dernière se
terminant par le marqueur de fin de chaîne `#` (qui n'est pas dans
l'alphabet).

Par exemple, le premier exemple ci-dessus sera décrit avec :

```bash
3
ab#
c#
d#
aabcaaadabbbccabadaaabaaacd#
```

##### Sortie

Le format du fichier de sortie attendu est le suivant :
- une première ligne contenant deux entiers `l` et `p`, où `l` est la
  longueur de la sous-chaîne minimale `m` et `p` sa position dans la
  chaîne `s`,
- la description de la chaîne `m` selon le format précédent (avec le
  marqueur de fin `#`).

Lorsque le problème n'a pas de solution, `l = p = 0` et `m = ""` (la
deuxième ligne est donc réduite au caractère `#`).

### Problème nº2

Cette fois nous disposons (seulement) de `n` chaînes de caractères `s1`,
`s2`, ... `sn` sur un alphabet `Σ`


##### Objectif 

Trouver *une plus courte chaîne sur `Σ` qui contient tous les `si`* (et
sa longueur).


##### Exemples

* si `s1 = "ab"`, `s2 = "c"` et `s3 = "d"`, alors *un* résultat possible serait `"abcd"`.
* si `s1 = "ad"`, `s2 = "da"` et `s3 = "ab"`, alors le résultat sera `"adab"`.
* si `s1 = "add"`, `s2 = "dda"` et `s3 = "a"`, alors le résultat sera `"adda"`.

##### Entrée

Les instances sont fournies dans des fichiers au format suivant : 
- une première ligne contenant le nombre `n` de sous-chaînes,
- la description des sous-chaînes dans l'ordre `s1`, `s2`, ..., `sn`.

On suppose que l'alphabet est défini implicitement par les lettres
utilisées dans les chaînes.

On peut réutiliser les fichiers d'entrée du problème précédent en
omettant la dernière chaîne `s`. 


##### Sortie

Le format du fichier de sortie attendu est le suivant :
- une première ligne contenant un entier `l` et `p`, où `l` est la
  longueur de la sous-chaîne minimale `m`,
- la description de la chaîne `m` selon le format précédent (avec le
  marqueur de fin `#`).


## Problème nº3

Toujours plus fort... on considère maintenant *deux* séquences de chaînes
de caractères `s1, ..., sm` et `t1, ..., tn`, et on cherche une plus
courte chaîne qui contient tous les `si` mais aucun `tj`. 

Pour le format, on donnera les deux entiers `m` et `n` sur la première
ligne du fichier d'entrée, et les `m + n` chaînes ensuite.


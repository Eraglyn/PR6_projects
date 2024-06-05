#!/bin/bash

# Déclaration des compteurs
total_tests=0
successful_tests=0

# Compilons les fichiers sources Java
javac Rail.java

# Créons le sous-répertoire tmp si nécessaire
if [ ! -d "tmp" ]; then
  mkdir tmp
fi

# Pour chaque fichier d'entrée dans le répertoire sample
for input in ../RAIL-SUD/EX/*.in; do
  # Exécutons le programme pour créer le fichier de sortie correspondant
  java Rail $input
  total_tests=$((total_tests+1))
done

for input in ../RAIL-SUD/EX/*.out; do
    # Déplaçons le fichier .sol dans le répertoire tmp
    mv $input tmp/
done

# Vérifions que chaque fichier de sortie produit est identique à la sortie attendue
# for output in tmp/*.out; do
#     if ! diff -q $output ../RAIL-SUD/EX/$(basename $output .out).in > /dev/null; then
#         echo "Test échoué pour $(basename $output .in)"
#     else
#         echo "Test réussi pour $(basename $output .in)"
#         successful_tests=$((successful_tests+1))
#     fi
# done

# Nettoyage des fichiers .class
rm Rail.class
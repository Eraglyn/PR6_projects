#!/bin/bash

# Déclaration des compteurs
total_tests=0
successful_tests=0

# Compilons les fichiers sources Java
javac Signature.java

# Créons le sous-répertoire tmp si nécessaire
if [ ! -d "tmp" ]; then
  mkdir tmp
fi

# Pour chaque fichier d'entrée dans le répertoire sample
for input in sample/*.in; do
  # Exécutons le programme pour créer le fichier de sortie correspondant
  java Signature $input
  total_tests=$((total_tests+1))
done

for input in sample/*.sol; do
    # Déplaçons le fichier .sol dans le répertoire tmp
    mv $input tmp/
done

# Vérifions que chaque fichier de sortie produit est identique à la sortie attendue
for output in tmp/*.sol; do
    if ! diff -q $output sample/$(basename $output .sol).out > /dev/null; then
        echo "Test échoué pour $(basename $output .sol)"
    else
        echo "Test réussi pour $(basename $output .sol)"
        successful_tests=$((successful_tests+1))
    fi
done

# Affichage du nombre de tests réussis sur le total
echo "$successful_tests / $total_tests réussis"
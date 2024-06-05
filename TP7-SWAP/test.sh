#!/bin/bash

# Déclaration des compteurs
total_tests=0
successful_tests=0

# Compilons les fichiers sources Java
javac Swap.java

# Créons le sous-répertoire tmp si nécessaire
if [ ! -d "tmp" ]; then
  mkdir tmp
fi

# Pour chaque fichier d'entrée dans le répertoire sample
for input in sample/*.in; do
  # Exécutons le programme pour créer le fichier de sortie correspondant
  java Swap $input
  total_tests=$((total_tests+1))
done

for input in sample/*.sol; do
    # Déplaçons le fichier .sol dans le répertoire tmp
    mv $input tmp/
done

# Vérifions que chaque fichier de sortie produit est identique à la sortie attendue
for output in tmp/*.sol; do
        echo "$(basename $output .sol).in" 
        cat $output
        
done


# Nettoyage des fichiers .class
rm Swap.class
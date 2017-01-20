#!/bin/bash

size=`seq 1 20`

echo "Debut..."

for s in $size; do
	java fr.m2sili.rsa.Alice
done

echo "Fin..."

#!/bin/bash

# Script que corre los 81 escenarios del simulador
# Debe estar en la misma carpeta que los .txt y el simulador.jar

OUTPUT="resultados81.txt"
> "$OUTPUT"  # limpiar archivo si existe

ARCHIVOS=("8x8tp64.txt" "8x8tp256.txt" "8x8tp1024.txt" "16x16tp64.txt" "16x16tp256.txt" "16x16tp1024.txt" "128x128tp64.txt" "128x128tp256.txt" "128x128tp1024.txt")
MARCOS=(4 8 16)
POLITICAS=("FIFO" "FIFOModified" "LRU")

for archivo in "${ARCHIVOS[@]}"; do
    for marcos in "${MARCOS[@]}"; do
        for politica in "${POLITICAS[@]}"; do
            echo "=== Escenario: $archivo | Marcos: $marcos | Política: $politica ===" >> "$OUTPUT"
            java -jar simulador.jar "$archivo" "$marcos" "$politica" 2>&1 | grep -E "Total|fallo|exito|Simulación" | tail -3 >> "$OUTPUT"
            echo "" >> "$OUTPUT"
        done
    done
done

echo "Listo. Resultados en $OUTPUT"

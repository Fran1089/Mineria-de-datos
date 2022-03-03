from efficient_apriori import apriori
import pandas as pd

#Se configura el dataset de entrada, en este caso se utiliza un archivo .csv con el formato deseado
data_de_entrada = pd.read_csv('hola.csv', header=None)

#Especificamos el soporte minimo a utilizar
soporte_minimo = 0.3

#Especificamos la confianza minima a utilizar
confianza_minima = 0.7

#El arreglo donde almacenaremos las transacciones
transacciones = []


#Llenaremos el arreglo de transacciones, es necesario recorrer la informacion de esta forma debido a que al leer el archivo
#Con la libreria PD, el mismo nos queda como un DataFrrame panda, es necesario utilizar una lista de listas para tener el input correcto

#Es importante configurar de forma manual el tamaño de nuestro documento en la linea 23 se configura de 0 a la cantidad maxima de filas que tenemos, en este caso son 5
for i in range(0, 5): 
  transacciones.append([str(data_de_entrada.values[i,j]) for j in range(0, len(data_de_entrada.columns))])

#Es necesario eliminar los elementos sobrantes de la lista, los cuales son completados por la variable "nan"
aux = "nan"
#Obtenemos el dataset filtrado a partir de las transacciones
dataset_filtrado = [[ele for ele in sub if ele != aux] for sub in transacciones]


#Hacemos uso de la libreria apriori y almacenamos su resultado en reglas, le pasamos el dataset con las transacciones, el soporte minimo y el nivel de confianza minimo con el que estamos trabajando
itemsets, reglas = apriori(dataset_filtrado, min_support=soporte_minimo, min_confidence=confianza_minima)


# Orden ascendente segun la cantidad de items
print("=========================== Ordenado por cantidad de items ===============================")
#Filtramos las reglas para mostrar todos los campos de las mismas, es posible limitar la cantidad de items que tengan nuestras reglas, pero en este caso no es necesario
#Unicamente realizamos el ordenamiento de las reglas segun la cantidad de items.
rules_rhs = filter(lambda rule: len(rule.lhs) >= 1 and len(rule.rhs) >= 1, reglas)
for rule in sorted(rules_rhs, key=lambda rule: rule.confidence):
  print(rule)  # Imprimos la regla adicionando los valores de confianza, soporte , lift y conv asociados a la misma



# Orden descendente segun la confianza
print("=========================== Ordenado por confianza de forma descendente ===============================")
#Filtramos las reglas para mostrar todos los campos de las mismas, es posible limitar la cantidad de items que tengan nuestras reglas, pero en este caso no es necesario
#Unicamente realizamos el ordenamiento de las reglas segun los niveles de confianza de forma descendiente.
rules_rhs = filter(lambda rule: len(rule.lhs) >= 1 and len(rule.rhs) >= 1, reglas)
for rule in sorted(rules_rhs, key=lambda rule: rule.confidence, reverse=True):
  print(rule)  # Imprimos la regla adicionando los valores de confianza, soporte , lift y conv asociados a la misma
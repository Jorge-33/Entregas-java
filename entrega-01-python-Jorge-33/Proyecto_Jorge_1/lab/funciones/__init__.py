"""
Ejercicio 1
"""
def es_primo(n):
    if n <= 1:
        return False
    if n == 2:
        return True
    if n % 2 == 0:
        return False
    for i in range(3, int(n**0.5) + 1, 2):
        if n % i == 0:
            return False
    return True

n = int(input("Inserte un número entero y positivo: "))

if es_primo(n):
    print("El {} es un número primo".format(n))
else:
    print("El {} no es un número primo".format(n))
"""
Ejercicio 2
"""
from math import factorial
def num_combinatorio(n,k):
    if n >= k:
        return factorial(n)//(factorial(k)*factorial(n-k))
    else:
        return("n debe ser mayor a k para poder realizar la operación")
n= int(input("Inserte el primer número entero, siendo el primero mayor que el segundo: "))   
k= int(input("Inserte el segundo número entero, siendo el primero mayor que el segundo: "))  
print ("El resultado del numero combinatorio de {} y {} es {}".format(n,k,num_combinatorio(n,k)))
        
"""
Ejercicio 3
"""       
def funcion_S(n,k):
    if n >= k:
        resultado = 0
    
        for i in range(k+1):
            resultado += ((-1) ** i) * num_combinatorio(k,i) * (2 ** (k - i)) * factorial(k - i)
    
            return (1 / factorial(k)) * resultado
    else:
        return("n debe ser mayor a k para poder realizar la operación")
n= int(input("Inserte el primer número entero, siendo el primero mayor que el segundo: "))   
k= int(input("Inserte el segundo número entero, siendo el primero mayor que el segundo: "))
print ("El resultado de la función S de {} y {} es {}".format(n,k,funcion_S(n,k)))
"""
Ejercicio 4
"""
def dif_num_lista(lista):
    for i in range (0, len(lista)-1):
        lista[i]= abs(int(lista[i])-int(lista[i+1]))
    return lista[:-1]
entrada = input("Introduzca la lista a Continuación (separada por espacios): ")
lista = entrada.split()
try:
    lista = list(map(int, lista))
    print("La lista resultante es: {}".format(dif_num_lista(lista)))
except ValueError:
    print("Por favor, asegúrate de ingresar solo números separados por espacios.")
"""
Ejercicio 5
"""

def cadena_mas_larga(lista_cadenas):
    if not lista_cadenas:
        return None
    
    cadena_mas_larga = lista_cadenas[0]
    for cadena in lista_cadenas:
        if len(cadena) > len(cadena_mas_larga):
            cadena_mas_larga = cadena
    
    return cadena_mas_larga
lista_cadenas=input("Introduzca la lista a Continuación: ")
print("La cadena más grande de la lista de cadenas {} es: {}".format(lista_cadenas,cadena_mas_larga(lista_cadenas)))


'''
Created on 17 oct 2023

@author: Jorge-Feo
'''
from lab.tipos.Fechas import Fecha
from datetime import date

def test_es_año_bisiesto():
    assert Fecha.es_año_bisiesto(2024) == True
    assert Fecha.es_año_bisiesto(2023) == False
    assert Fecha.es_año_bisiesto(2000) == True
    assert Fecha.es_año_bisiesto(1900) == False

def test_días_en_mes():
    assert Fecha.días_en_mes(2023, 2) == 28
    assert Fecha.días_en_mes(2024, 2) == 29
    assert Fecha.días_en_mes(2023, 4) == 30
    assert Fecha.días_en_mes(2023, 13) == 0

def test_día_semana():
    fecha1 = Fecha(2023, 10, 15)
    fecha2 = Fecha(2023, 10, 14)
    assert fecha1.dia_semana == "Domingo"
    assert fecha2.dia_semana == "Sábado"

def test_sumar_días():
    fecha = Fecha(2023, 10, 15)
    nueva_fecha = fecha.sumar_días(5)
    assert nueva_fecha.dia == 20

def test_restar_días():
    fecha = Fecha(2023, 10, 15)
    nueva_fecha = fecha.restar_días(5)
    assert nueva_fecha.dia == 10

def test_diferencia_en_días():
    fecha1 = Fecha(2023, 10, 15)
    fecha2 = Fecha(2023, 10, 20)
    assert fecha1.diferencia_en_días(fecha2) == 5

def test_fecha_tipo_date():
    fecha = Fecha(2023, 10, 15)
    fecha_date = date(2023, 10, 15)
    assert fecha.año == fecha_date.year
    assert fecha.mes == fecha_date.month
    assert fecha.dia == fecha_date.day

if __name__ == "__main__":
    test_es_año_bisiesto()
    test_días_en_mes()
    print("Primeras pruebas completadas.")
    test_día_semana()
    test_sumar_días()
    test_restar_días()
    test_diferencia_en_días()
    test_fecha_tipo_date()
    print("Pruebas completadas.")
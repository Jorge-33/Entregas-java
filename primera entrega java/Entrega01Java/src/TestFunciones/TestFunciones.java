package TestFunciones;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import funciones.Funciones;

public class TestFunciones {
	public static void main(String[] args) {
		
		//Test1 (esPrimo)
	System.out.println("Test esPrimo:");
	System.out.println(Funciones.esPrimo(4));
	System.out.println(Funciones.esPrimo(552));
	System.out.println(Funciones.esPrimo(7));
	System.out.println(Funciones.esPrimo(0));
	
	System.out.println("---------------------------------------------");
	
		//Test2 (Combinatorio)
	System.out.println("Test combinatorio:");
	System.out.println("El número combinatorio es:"+Funciones.combinatorio(8,7));
	System.out.println("El número combinatorio es:"+Funciones.combinatorio(7,7));
	System.out.println("El número combinatorio es:"+Funciones.combinatorio(0,4));
	System.out.println("El número combinatorio es:"+Funciones.combinatorio(7,9));
	
	System.out.println("---------------------------------------------");
	
		//Test3 (funcion S)
	System.out.println("Test funcioón S");
	System.out.println("El resultado de la función S es:"+Funciones.S(7,4));
	System.out.println("El resultado de la función S es:"+Funciones.S(7,0));
	
	System.out.println("---------------------------------------------");
	
		//Test4  (listaDiferencias)
	System.out.println("Test listaDiferencias");
	List<Double> numeros = Arrays.asList(1.0, 3.0, 6.0, 10.0);
    List<Double> resultadoEsperado = Arrays.asList(2.0, 3.0, 4.0);
    List<Double> resultadoObtenido = Funciones.listaDiferencias(numeros);
    if (resultadoEsperado.equals(resultadoObtenido)) {
        System.out.println("Funciona");
    } else {
        System.out.println("No funciona");
    }
    
    System.out.println("---------------------------------------------");
    
    //Test5 (palabramaslarga)
    System.out.println("Test palabramaslarga");
  
    //Lista vacía
    List<String> listaVacia = new ArrayList<>();
    String resultado1 = Funciones.palabramaslarga(listaVacia);
    System.out.println("Lista vacía");
    System.out.println("Resultado esperado: ");
    System.out.println("Resultado actual: " + resultado1);
    if ("".equals(resultado1)) {
        System.out.println("Funciona");
    } else {
        System.out.println("No funciona");
    }
    System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
    System.out.println("Test palabramaslarga");
    //Lista con palabras de diferentes longitudes
    List<String> listaConPalabras = Arrays.asList("plato", "caja", "armario", "pato");
    String resultado3 = Funciones.palabramaslarga(listaConPalabras);
    System.out.println("Lista con palabras de diferentes longitudes");
    System.out.println("Resultado esperado: armario");
    System.out.println("Resultado actual: " + resultado3);
    if ("armario".equals(resultado3)) {
        System.out.println("Funciona");
    } else {
        System.out.println("No funciona");
    }
    }

}




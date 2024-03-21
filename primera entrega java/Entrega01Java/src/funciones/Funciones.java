package funciones;

import java.util.ArrayList;
import java.util.List;

public class Funciones {
	
	    public static String esPrimo(int n) {
	        if (n < 2) {
	            return "n debe ser mayor o igual a 2 para determinar si es primo";
	        } else if (n == 2 || n == 3 || n == 5 || n == 7 || n == 11 || n == 13 || n == 17) {
	            return "El número es primo";
	        } else if (n % 2 == 0 || n % 3 == 0 || n % 5 == 0 || n % 7 == 0 || n % 11 == 0 || n % 13 == 0 || n % 17 == 0) {
	            return "El número no es primo";
	        } else {
	            return "El número es primo";
	        }
	    }
	    public static Long fact(int n) {
	        try {
	            if (n < 0) {
	                throw new IllegalArgumentException("El factorial no está definido para números negativos");
	            }
	            long resultado = 1;
	            while (n > 1) {
	                resultado *= n;
	                n--;
	            }
	            return resultado;
	        } catch (IllegalArgumentException e) {
	            System.out.println("Se ha producido una excepción: " + e.getMessage());
	            return null;
	        }
	    }
	    public static Double combinatorio(int n, int k) {

	        try {
	            if (n == 0 || k == 0 || n < 0 || k < 0) {
	                throw new IllegalArgumentException("Ninguno de los números introducidos pueden ser 0 o negativos");
	            }
	            if (n < k) {
	                throw new IllegalArgumentException("n debe ser mayor o igual que k para poder hacer la operación");
	            } else {
	                int h = n - k;
	                double p = (fact(n)) / (fact(k) * fact(h));
	                return p;
	            }
	        } catch (IllegalArgumentException e) {
	            System.out.println("Se ha producido una excepción: " + e.getMessage());
	            return null;
	        }
	    }
	    public static Double S(int n, int k) {

	        try {
	            double sumatorio = 0;
	            int i =0;
	            int h= n - k;
	            while (i >= 0 && i <= k) {
	                sumatorio = sumatorio + Math.pow(-1, i) * (fact(k)) / (fact(i) * fact(h)) * Math.pow((k - i), n);
	                i++;
	            }
	            double resultado = sumatorio / fact(k);
	            return resultado;

	        } catch (IllegalArgumentException e) {
	            System.out.println("Se ha producido una excepción: " + e.getMessage());
	            return null;
	        }
	    }
	    public static List<Double> listaDiferencias(List<Double> numeros) {
	        List<Double> diferencias = new ArrayList<>();
	        if (numeros.size() < 2) {
	        	System.out.println("Se ha producido una excepción: La lista debe contener al menos dos elementos para calcular las diferencias.");
	        }
	        for (int i = 1; i < numeros.size(); i++) {
	            double diferencia = numeros.get(i) - numeros.get(i - 1);
	            diferencias.add(diferencia);
	        }
	        return diferencias;
	    }
	    public static String palabramaslarga(List<String> texto) {
	        if (texto.isEmpty()) {
	            return "";
	        }
	        String palabralarga = texto.get(0);
	        for (String palabra : texto) {
	            if (palabra.length() > palabralarga.length()) {
	                palabralarga = palabra;
	            }
	        }
	        return palabralarga;
	    }    
}


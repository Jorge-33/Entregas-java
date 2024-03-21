package TestFunciones;
import funciones.Fecha;

public class TestFechas {

	 public static void main(String[] args) {

		 	// Prueba nombreMes

	        System.out.println("---------------------------");

	        System.out.println("Prueba nombreMes:");
	        try {
	            System.out.println("Mes 1: " + Fecha.nombreMes(1));
	            System.out.println("Mes 6: " + Fecha.nombreMes(6));
	            System.out.println("Mes 12: " + Fecha.nombreMes(12));
	            //La siguiente línea debería lanzar una excepción
	            System.out.println("Mes 0: " + Fecha.nombreMes(0));

	        } catch (IllegalArgumentException e) {
	            System.out.println(e.getMessage());
	        }

	        System.out.println("---------------------------");

	        // Prueba diaSemana
	        System.out.println("Prueba diaSemana:");
	        System.out.println("Día de la semana de 2024-03-21: " + Fecha.diaSemana(2024, 3, 21));
	        
	        System.out.println("---------------------------");

	        // Prueba zeller
	        System.out.println("Prueba zeller:");
	        System.out.println("Zeller de 2024-03-21: " + Fecha.zeller(2024, 3, 21));
	        System.out.println("---------------------------");

	        // Prueba esAñoBisiesto
	        System.out.println("Prueba esAñoBisiesto:");
	        System.out.println("2024 es bisiesto: " + Fecha.esAñoBisiesto(2024));
	        System.out.println("2023 es bisiesto: " + Fecha.esAñoBisiesto(2023));
	        
	        System.out.println("---------------------------");

	        // Prueba diasEnMes
	        System.out.println("Prueba diasEnMes:");
	        System.out.println("Días en marzo de 2024: " + Fecha.diasEnMes(3, 2024));
	        System.out.println("Días en febrero de 2023: " + Fecha.diasEnMes(2, 2023));

	        System.out.println("---------------------------");

	        // Prueba sumarDias
	        System.out.println("Prueba sumarDias:");
	        Fecha fecha1 = new Fecha(2024, 3, 21);
	        Fecha fechaSumada = Fecha.sumarDias(fecha1, 5);
	        System.out.println("Fecha sumada: " + Fecha.representacionComoCadena(fechaSumada));

	        System.out.println("---------------------------");



	        // Prueba restarDias
	        System.out.println("Prueba restarDias:");
	        Fecha fechaRestada = Fecha.restarDias(fecha1, 10);
	        System.out.println("Fecha restada: " + Fecha.representacionComoCadena(fechaRestada));

	        System.out.println("---------------------------");

	        // Prueba diferenciaEnDias
	        System.out.println("Prueba diferenciaEnDias:");
	        Fecha fecha2 = new Fecha(2024, 4, 1);
	        System.out.println("Diferencia en días entre " + Fecha.representacionComoCadena(fecha1) + " y " + Fecha.representacionComoCadena(fecha2) + ": " + Fecha.diferenciaEnDias(fecha1, fecha2));

	        System.out.println("---------------------------");

	        // Prueba del método of
	        System.out.println("Prueba del método of:");
	        Fecha fecha5 = Fecha.of(2024, 3, 21);
	        System.out.println("Fecha creada con of: " + fecha5.año + "-" + fecha5.mes + "-" + fecha5.dia);
	        
	        System.out.println("---------------------------");

	        // Prueba del método parse
	        System.out.println("Prueba del método parse:");
	        String cadenaFecha = "2024-03-21";
	        Fecha fecha6 = Fecha.parse(cadenaFecha);
	        System.out.println("Fecha creada con parse: " + fecha6.año + "-" + fecha6.mes + "-" + fecha6.dia);
		        
		        // Prueba del método parse con formato de cadena inválido
		        try {
		            String cadenaFechaInvalida = "20-03-2024"; // Formato inválido
		            Fecha fechaInvalida = Fecha.parse(cadenaFechaInvalida);
		            System.out.println("Fecha creada con parse: " + fechaInvalida.año + "-" + fechaInvalida.mes + "-" + fechaInvalida.dia);
		            
		        } catch (IllegalArgumentException e) {
		            System.out.println("Excepción esperada: " + e.getMessage());
		        }
		        
		        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
		        
		        // Prueba del método parse con componentes no enteros
		        try {
		            String cadenaFechaNoEntera = "2024-03-twenty"; // Componentes no enteros
		            Fecha fechaNoEntera = Fecha.parse(cadenaFechaNoEntera);
		            System.out.println("Fecha creada con parse: " + fechaNoEntera.año + "-" + fechaNoEntera.mes + "-" + fechaNoEntera.dia);
	
		        } catch (IllegalArgumentException e) {
		            System.out.println("Excepción esperada: " + e.getMessage());
		        }
		 }
	
	}
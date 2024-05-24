package banco;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExamenEntrega3 {
 static List<Empleado> empleadosEntreDiaMes(Banco banco, String ini, String fin){
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

	        LocalDate fechaInicio;
	        LocalDate fechaFin;

	        try {
	            fechaInicio = LocalDate.parse(ini, formatter);
	            fechaFin = LocalDate.parse(fin, formatter);
	        } catch (DateTimeParseException e) {
	            throw new IllegalArgumentException("Formato de fecha inválido, use 'DD/MM'");
	        }

	        if (fechaInicio.isAfter(fechaFin)) {
	            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
	        }

	        
	        int arbitraryYear = 2000;
	        fechaInicio = fechaInicio.withYear(arbitraryYear);
	        fechaFin = fechaFin.withYear(arbitraryYear);

	        
				return banco.empleados().todos().stream()
				        .filter(empleado -> {
				            LocalDate fechaNacimiento = empleado.fechaNacimiento();
				            LocalDate fechaNacimientoArbitraria = fechaNacimiento.withYear(arbitraryYear);
				            return !fechaNacimientoArbitraria.isBefore(fechaInicio) && !fechaNacimientoArbitraria.isAfter(fechaFin);
				        })
				        .collect(Collectors.toList());
			}
 public static Double clientesEdadMedia(Banco banco, Integer m) {
     
     if (banco == null || m == null || m <= 0) {
         throw new IllegalArgumentException("Los parámetros deben ser válidos.");
     }

     Set<Persona> todosClientes = banco.personas().todos();

     
     double sumaEdades = todosClientes.stream()
             .filter(persona -> persona.getEdad() >= m)
             .mapToInt(Persona::getEdad)
             .sum();

     
     long cantidadClientes = todosClientes.stream()
             .filter(persona -> persona.getEdad() >= m)
             .count();

     
     return cantidadClientes == 0 ? 0.0 : sumaEdades / cantidadClientes;
 }

 
 public Personas personas() {
     return new Personas(); 
 }
}

class Persona {
 private String dni;
 private int edad;

 public Persona(String dni, int edad) {
     this.dni = dni;
     this.edad = edad;
 }

 public int getEdad() {
     return edad;
 }
}

class Personas {
 private Set<Persona> personas;

 public Personas() {
     
     personas = Set.of(
         new Persona("12345678A", 25),
         new Persona("23456789B", 30),
         new Persona("34567890C", 20)
     );
 }

 public Set<Persona> todos() {
     return personas;
 }

	     
	     
 	static Map<Character, List<Empleado>> empleadosConLetraDNI (Banco banco, Set<Character> letras){
 		        
 		        if (letras == null || letras.isEmpty()) {
 		            throw new IllegalArgumentException("El parámetro 'letras' no puede estar vacío.");
 		        }
 		        for (Character letra : letras) {
 		            if (!Character.isLetter(letra)) {
 		                throw new IllegalArgumentException("El parámetro 'letras' solo puede contener caracteres alfabéticos.");
 		            }
 		        }

 		        // Obtener los empleados
 		        List<Empleado> todosEmpleados = (List<Empleado>) banco.empleados().todos();

 		        
 		        Map<Character, List<Empleado>> empleadosPorLetra = todosEmpleados.stream()
 		                .filter(empleado -> letras.contains(Character.toUpperCase(empleado.getDni().charAt(empleado.getDni().length() - 1))))
 		                .collect(Collectors.groupingBy(empleado -> Character.toUpperCase(empleado.getDni().charAt(empleado.getDni().length() - 1))));

 		        return empleadosPorLetra;
 		    }

 		}

 		class Empleado {
 		    private String dni;
 		    private String fechaNacimiento; 
 		    public Empleado(String dni, String fechaNacimiento) {
 		        this.dni = dni;
 		        this.fechaNacimiento = fechaNacimiento;
 		    }

 		    public String getDni() {
 		        return dni;
 		    }

 		    public String getFechaNacimiento() {
 		        return fechaNacimiento;
 		    }
 			
 		   public static Set<String> clientesConMenosPrestamos(Banco banco, int n) {
 		        // Validar que los parámetros no sean nulos y que 'n' sea mayor que cero
 		        if (banco == null || n <= 0) {
 		            throw new IllegalArgumentException("Los parámetros deben ser válidos.");
 		        }

 		     
 		        Set<Persona> todasPersonas = banco.personas().todos();

 		        
 		        Comparator<Persona> comparadorPorPrestamos = Comparator.comparingInt(persona -> banco.prestamos().cantidadPrestamosPorCliente(persona.getDni()));
 		        Set<Persona> personasOrdenadas = todasPersonas.stream()
 		                .sorted(comparadorPorPrestamos)
 		                .collect(Collectors.toSet());

 		      
 		        return personasOrdenadas.stream()
 		                .limit(n)
 		                .map(Persona::getDni)
 		                .collect(Collectors.toSet());
 		        
 		    }
 		   
 		    	
 	
	    

	   
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

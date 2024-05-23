package banco;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Banco {
	

	private static Banco gestorDeBanco = null;
	private String nombre;
	private Integer codigoPostal;
	private String email;
	private Personas personas;
	private Empleados empleados;
	private Cuentas cuentas;
	private Prestamos prestamos;

	private Banco(String nombre, Integer codigoPostal, String email, Personas personas, Empleados empleados,
			Cuentas cuentas, Prestamos prestamos) {
		super();
		this.nombre = nombre;
		this.codigoPostal = codigoPostal;
		this.email = email;
		this.personas = personas;
		this.empleados = empleados;
		this.cuentas = cuentas;
		this.prestamos = prestamos;
	}

	public static Banco of() {
		String nombre = "Reina Mercedes";
	    Integer codigoPostal = 41012;
	    String email = "bib@us.es";
		String fp = "src/banco_ficheros/personas.txt";
		String fe = "src/banco_ficheros/empleados.txt";
		String fc = "src/banco_ficheros/cuentas.txt";
		String fpt = "src/banco_ficheros/prestamos.txt";
		return Banco.of(nombre, codigoPostal, email, fp, fe, fc, fpt);
	}

	public static Banco of(String nombre, Integer codigoPostal, String email, String fp, String fe, String fc,
			String fpt) {
		if (Banco.gestorDeBanco == null) {
			Personas personas = Personas.parse(fp);
			Empleados empleados = Empleados.parse(fe);
			Cuentas cuentas = Cuentas.parse(fc);
			Prestamos prestamos = Prestamos.parse(fpt);
			Banco.gestorDeBanco = new Banco(nombre, codigoPostal, email, personas, empleados, cuentas, prestamos);
		}
		return Banco.gestorDeBanco;
	}

	public Personas personas() {
		return this.personas;
	}

	public Empleados empleados() {
		return this.empleados;
	}

	public Cuentas cuentas() {
		return this.cuentas;
	}

	public Prestamos prestamos() {
		return this.prestamos;
	}

	public String nombre() {
		return nombre;
	}

	public Integer codigoPostal() {
		return codigoPostal;
	}

	public String email() {
		return email;
	}

    public Set<Prestamo> prestamosDeEmpleado(Empleado empleado) {
        return prestamos.todos()
        		.stream()
                .filter(prestamo -> prestamo.dniEmpleado().equals(empleado))
                .collect(Collectors.toSet());
    }
	

    public Set<Prestamo> prestamosDeCliente(String dni) {
        return prestamos.todos()
        		.stream()
                .filter(prestamo -> prestamo.dniCliente().equals(dni))
                .collect(Collectors.toSet());
    }

    public Optional<Empleado> empleadoMasJoven() {
        return empleados.todos()
                .stream()
                .min(Comparator.comparing(Empleado::edad));
    }
    

    public Map<String, Integer> numeroDeCuentasDeCliente() {
        return cuentas().todas()
                .stream()
                .collect(Collectors.groupingBy(
                    Cuenta::getDni,
                    Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }
	
	
	public void test() {
		Set<String> dnis = this.personas().dnis();
		Set<String> dniCuentas = 
				this.cuentas().todas().stream()
				.map(c->c.getDni())
				.filter(d->dnis.contains(d))
				.collect(Collectors.toSet());
		System.out.println("dniCuentas");
		System.out.println(dniCuentas);
		Set<String> dniEmpleados = 
				this.empleados().todos().stream()
				.map(c->c.dni())
				.filter(d->dnis.contains(d))
				.collect(Collectors.toSet());
		System.out.println("dniEmpleados");
		System.out.println(dniEmpleados);
		Set<String> dniPrestamosClientes = 
				this.prestamos().todos().stream()
				.map(c->c.dniCliente())
				.filter(d->dnis.contains(d))
				.collect(Collectors.toSet());
		System.out.println("dniPrestamosClientes");
		System.out.println(dniPrestamosClientes);
		Set<String> dniPrestamosEmpleados = 
				this.prestamos().todos().stream()
				.map(c->c.dniEmpleado())
				.filter(d->dnis.contains(d))
				.collect(Collectors.toSet());
		System.out.println("dniPrestamosEmpleados");
		System.out.println(dniPrestamosEmpleados);
	}
	
	public static void main(String[] args) {
		System.out.println("******* TestBancos.java *******");
		Banco banco = Banco.of();
		
		
        // Pruebas de los métodos de banco
		System.out.println("\n");
        System.out.println("Vencimiento de prestamos de cliente: " + Questions.vencimientoDePrestamosDeCliente(banco,"35529655Z"));//bien
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("Cliente con más préstamos: " + Questions.clienteConMasPrestamos(banco));//bien
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("Cantidad de préstamos por empleado: " + Questions.cantidadPrestamosEmpleado(banco, "35529655Z"));//deberia de dar 4640299.11 y da 0.0
        System.out.println("\n");System.out.println("\n");
        System.out.println("Empleado más longevo: " + Questions.empleadoMasLongevo(banco)); //bien
        System.out.println("\n");System.out.println("\n");
        System.out.println("Intereses de préstamos: " + Questions.interesPrestamos(banco));//con el cambio ese
        System.out.println("\n");System.out.println("\n");
        System.out.println("Rango de intereses de préstamos: " + Questions.rangoDeIntereseDePrestamos(banco));//bien
        System.out.println("\n");System.out.println("\n");
        System.out.println("Número de préstamos por mes y año: " + Questions.numPrestamosPorMesAño(banco));//bien
        System.out.println("\n");System.out.println("\n");
        System.out.println("Vencimiento de prestamos de cliente: " + banco.prestamosDeCliente("86202090E"));
        System.out.println("Prestamos de empleado: " + banco.prestamosDeEmpleado(new Empleado("34759012D", null, 4950.36)));
        System.out.println("Empleado más joven: " + banco.empleadoMasJoven());
        
        //System.out.println("Empleado más mayor: " + banco.empleados());
        //System.out.println("Empleado más mayor: " + banco.prestamos() );
        //System.out.println("Empleado más mayor: " + banco.numeroDeCuentasDeCliente());
        System.out.println("Número de cuentas de cliente: " + banco.numeroDeCuentasDeCliente());

        System.out.println("*** test en Banco.java ***");
        banco.test();//bien
    }

	}
	


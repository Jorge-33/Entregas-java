package banco;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Questions {


	public static Set<LocalDate> vencimientoDePrestamosDeCliente(Banco banco, String dni) {
	    return banco.prestamosDeCliente(dni)
	                .stream()
	                .map(Prestamo::fechaVencimiento)
	                .collect(Collectors.toSet());
	}


	public static Persona clienteConMasPrestamos(Banco banco) {
	    Map<String, Long> prestamosPorCliente = banco.prestamos().todos()
	            .stream()
	            .collect(Collectors.groupingBy(Prestamo::dniCliente, Collectors.counting()));

	    String dniConMasPrestamos = prestamosPorCliente.entrySet()
	            .stream()
	            .max(Map.Entry.comparingByValue())
	            .map(Map.Entry::getKey)
	            .orElse(null);

	    return dniConMasPrestamos != null ? banco.personas().personaDni(dniConMasPrestamos).orElse(null) : null;
	}
	

	public static Double cantidadPrestamosEmpleado(Banco banco, String dni) {
	    return banco.prestamos().todos()
	                .stream()
	                .filter(prestamo -> prestamo.dniEmpleado().equals(dni))
	                .mapToDouble(Prestamo::cantidad)
	                .sum();
	}


	public static Persona empleadoMasLongevo(Banco banco) {
	    return banco.empleados().todos()
	                .stream()
	                .max(Comparator.comparing(Empleado::edad))
	                .flatMap(empleado -> banco.personas().personaDni(empleado.dni()))
	                .orElse(null);
	}

	
    public static record Info(Double min, Double max, Double mean) {
        public String toString() {
            return String.format("(%.2f,%.2f,%.2f)", this.min(), this.max(), this.mean());
        }
    }
    
	public static Info rangoDeIntereseDePrestamos(Banco banco) {
	    DoubleSummaryStatistics stats = banco.prestamos().todos()
	            .stream()
	            .mapToDouble(Prestamo::interes)
	            .summaryStatistics();

	    return new Info(stats.getMin(), stats.getMax(), stats.getAverage());
	}

	public static Info interesPrestamos(Banco banco) {
	    DoubleSummaryStatistics stats = banco.prestamos().todos()
	            .stream()
	            .mapToDouble(Prestamo::interes)
	            .summaryStatistics();
	    
	    double min = stats.getMin();
	    double max = stats.getMax();
	    double rango = max - min;

	    return new Info(min, max, rango);
	}


    public static record Info2(Integer mes, Integer año) {
        public String toString() {
            return String.format("(%d,%d)", this.mes(), this.año());
        }
    }

    public static Map<Info2, Integer> numPrestamosPorMesAño(Banco banco) {
        return banco.prestamos().todos()
                .stream()
                .collect(Collectors.groupingBy(
                    prestamo -> new Info2(prestamo.fechaComienzo().getMonthValue(), prestamo.fechaComienzo().getYear()),
                    Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }
}

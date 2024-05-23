package banco;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Cuenta {
    private String iban;
    private String dni;
    private LocalDate fechaCreacion;
    private Double saldo;

    private Cuenta(String iban, String dni, LocalDate fechaCreacion, Double saldo) {
        this.iban = iban;
        this.dni = dni;
        this.fechaCreacion = fechaCreacion;
        this.saldo = saldo;
    }

    public String getIban() {
        return iban;
    }

    public String getDni() {
        return dni;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void ingresar(Double c) {
        this.saldo += c;
    }

    public void retirar(Double c) {
        if (this.saldo >= c) {
            this.saldo -= c;
        } else {
            System.out.println("Saldo insuficiente");
        }
    }

    public static Cuenta of(String iban, String dni, LocalDate fechaCreacion, Double saldo) {
        return new Cuenta(iban, dni, fechaCreacion, saldo);
    }

    public static Cuenta parse(String text) {
    	 DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 String[] parts = text.split(",");
	     String iban = parts[0].trim();
	     String dni = parts[1].trim();
	     LocalDate fechaCreacion = LocalDate.parse(parts[2], fm);
	     Double saldo = Double.parseDouble(parts[3].trim());
	     return Cuenta.of(iban, dni, fechaCreacion, saldo);
	 }


    @Override
    public String toString() {
        return iban + ", " + saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cuenta cuenta = (Cuenta) o;

        if (!iban.equals(cuenta.iban)) return false;
        if (!dni.equals(cuenta.dni)) return false;
        if (!fechaCreacion.equals(cuenta.fechaCreacion)) return false;
        return saldo.equals(cuenta.saldo);
    }

    @Override
    public int hashCode() {
        int result = iban.hashCode();
        result = 31 * result + dni.hashCode();
        result = 31 * result + fechaCreacion.hashCode();
        result = 31 * result + saldo.hashCode();
        return result;
    }
}



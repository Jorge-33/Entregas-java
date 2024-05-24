package banco;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import libreria.File2;

public class Cuentas {
    private static Cuentas gestorDeCuentas = null;

    private Set<Cuenta> cuentas;
    private Map<String, Cuenta> cuentasIban;

    private Cuentas(Set<Cuenta> cuentas) {
        this.cuentas = cuentas;
        this.cuentasIban = this.cuentas.stream().collect(Collectors.toMap(Cuenta::getIban, c -> c));
    }

    public static Cuentas of() {
        if (Cuentas.gestorDeCuentas == null)
            Cuentas.gestorDeCuentas = Cuentas.parse("ruta/al/fichero.txt");
        return Cuentas.gestorDeCuentas;
    }

    public static Cuentas parse(String fichero) {
    	Set<Cuenta> cuentas = File2.streamDeFichero(fichero,"UTF-8")
    			.map(ln->Cuenta.parse(ln))
    			.collect(Collectors.toSet());
        Cuentas.gestorDeCuentas = new Cuentas(cuentas);
        return Cuentas.gestorDeCuentas;
    }

    public Set<Cuenta> todas() {
        return this.cuentas;
    }

    public Optional<Cuenta> cuentaIban(String iban) {
        return Optional.ofNullable(cuentasIban.get(iban));
    }

    public Integer size() {
        return cuentas.size();
    }

    public Cuenta cuentaIndex(Integer n) {
        return cuentas.stream().toList().get(n);
    }

    @Override
    public String toString() {
        return cuentas.stream().map(Cuenta::toString).collect(Collectors.joining("\n"));
    }
}

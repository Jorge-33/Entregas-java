package funciones;


public class Fecha {
	public Integer año;
	public Integer mes;
	public Integer dia;

	   public Fecha(Integer año,Integer mes,Integer dia){
		   this.año = año;
		   this.mes = mes;
		   this.dia=dia;
	   }

	   public static Fecha of(Integer año, Integer mes, Integer dia) {
	        return new Fecha(año, mes, dia);
	    }

	    public static Fecha parse(String cadenaFecha) {
	        String[] partes = cadenaFecha.split("-");
	        if (partes.length != 3) {
	            throw new IllegalArgumentException("Formato de cadena de fecha inválido. Debe ser YYYY-MM-DD.");
	        }
	        try {
	            Integer año = Integer.parseInt(partes[0]);
	            Integer mes = Integer.parseInt(partes[1]);
	            Integer dia = Integer.parseInt(partes[2]);
	            return new Fecha(año, mes, dia);
	            
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Formato de cadena de fecha inválido. Los componentes deben ser números enteros.");
	        }
	    }

	   public static String nombreMes(Integer mes) {
		    try {
		        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		        if (mes < 1 || mes > 12) {
		            throw new IllegalArgumentException("El mes debe ser mayor que cero y menor o igual que 12");
		        }
		        return meses[mes - 1];
		    } catch (IllegalArgumentException e) {
		        System.out.println("Se ha producido una excepción: " + e.getMessage());
		        return null;
		    }
		}

	  public static String diaSemana(Integer año, Integer mes, Integer dia) {
		    String[] diassemana = {"Sábado", "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
		    return diassemana[zeller(año, mes, dia)];
		}

		public static Integer zeller(Integer año, Integer mes, Integer dia) {
		    if (mes < 3) {
		        año--;
		        mes += 12;
		    }
		    Integer m = mes;
		    Integer j = año / 100;
		    Integer k = año % 100;
		    Integer resultado = (dia + (13 * (m + 1)) / 5 + k + k / 4 + j / 4 - 2 * j) % 7;
		    return (resultado + 7) % 7;
		}

	  public static Boolean esAñoBisiesto(Integer año) {
	        if ((año % 4 == 0 && año % 100 != 0) || (año % 400 == 0)) {
	            return true;
	        } else {
	            return false;
	        }
	  }

	  public static Integer diasEnMes(Integer mes, Integer año) {
		  if(mes<1 || mes>12) {
			  throw new IllegalArgumentException ("El mes debe ser mayor que cero y menor que 12");
		  }
		  else if(esAñoBisiesto(año)==true) {
			  Integer[] diasEnMes={31,29,31,30,31,30,31,31,30,31,30,31};
			  return diasEnMes[mes-1];
		  }
		  else {
			  Integer[] diasEnMes={31,28,31,30,31,30,31,31,30,31,30,31};
			  return diasEnMes[mes-1];
		  }
	  }

	  public static Fecha sumarDias(Fecha fecha, Integer n) {
		    Integer p = fecha.dia + n;
		    while (p > diasEnMes(fecha.mes, fecha.año)) {
		        fecha.mes = fecha.mes + 1;
		        if (fecha.mes > 12) {
		            fecha.año = fecha.año + 1;
		            fecha.mes = 1; 
		        }
		        p = p - diasEnMes(fecha.mes - 1, fecha.año); 
		    }
		    fecha.dia = p;
		    return fecha;
		}

		public static Fecha restarDias(Fecha fecha, Integer n) {
		    Integer p = fecha.dia - n;
		    while (p < 1) {
		        fecha.mes = fecha.mes - 1;
		        if (fecha.mes < 1) {
		            fecha.año = fecha.año - 1;
		            fecha.mes = 12;
		        }
		        p = p + diasEnMes(fecha.mes, fecha.año);
		    }
		    fecha.dia = p;
		    return fecha;
		}
		
		public static Integer diferenciaEnDias(Fecha fecha1, Fecha fecha2) {
		    Integer dias1 = fecha1.dia + fecha1.mes * 31 + fecha1.año * 365;
		    Integer dias2 = fecha2.dia + fecha2.mes * 31 + fecha2.año * 365;
		    return Math.abs(dias1 - dias2);
		}
	  public static String representacionComoCadena(Fecha fecha) {
		  return diaSemana(fecha.año,fecha.mes,fecha.dia)+", "+fecha.dia+" de "+nombreMes(fecha.mes)+" de "+fecha.año;
	  }
}	  

package testeDataFrame;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import prueba_entrega2java.DataFrameImpl;
public class Test {
    public static void main(String[] args) {
        try {
            // Leer un DataFrame desde el archivo CSV
            DataFrameImpl<String> df = DataFrameImpl.parse("src/prueba_entrega2java/personas.csv");
            System.out.println("\t \t ____________PERSONAS_____________");
            System.out.println(df);   
            
            //PRUEBAS DEL FUNCIONAMIENTO CORRECTO DE LOS METODOS
            
            // Imprimir el DataFrame
            // Obtener nombres de columna
            //System.out.println("\nNombres de columna:");
            //System.out.println(df.getColumnNames());
            // Obtener número de columnas
            //System.out.println("\nNúmero de columnas: " + df.getColumnNumber());
            // Obtener columna por nombre
            //System.out.println("\nColumna 'Nombre':");
            //System.out.println(df.getColumn("Id"));
            //System.out.println(df.getColumn("Nombre"));
            //System.out.println(df.getColumn("Apellidos"));
            //System.out.println(df.getColumn("Altura"));
            //System.out.println(df.getColumn("Fecha_Nacimiento"));
            // Obtener columna por índice
            //System.out.println("\nPrimera columna:");
            //System.out.println(df.getColumnIndex(0));
            // Verificar si todos los valores de una columna son diferentes
            //System.out.println("\nTodos los valores de la columna 'Id' son diferentes: " + df.isColumnAllDifferent("Id"));
            // Obtener el valor de una celda
            //System.out.println("\nValor de la celda en la fila 1 y columna 'Altura': " + df.getCell(0, "Altura"));
            // Obtener el número de filas
            //System.out.println("\nNúmero de filas: " + df.getRowNumber());
            // Obtener una fila por índice
            //System.out.println("\nSegunda fila:");
            //System.out.println(df.getRow(1));
            // Obtener una fila por valor de columna
            //System.out.println("\nFila con valor 'Maria' en la columna 'Nombre':");
            //System.out.println(df.getRowName("Nombre", "Maria"));
            // Testeo del método rowValue
            //System.out.println("Valor de la primera fila como Integer:");
            //Integer firstRowValue = df.rowValue(0, values -> Integer.parseInt(values.get(0)));
            //System.out.println(firstRowValue);
            // Testeo del método getRows
            //System.out.println("\nTodas las filas del DataFrame:");
            //List<List<String>> allRows = df.getRows();
            //allRows.forEach(System.out::println);
            // Testeo del método head
            //System.out.println("\nPrimeras 3 filas del DataFrame:");
            //DataFrame<String> headDF = df.head(3);
            //System.out.println(headDF);
            // Testeo del método tail
            //System.out.println("\nÚltimas 3 filas del DataFrame:");
            //List<List<String>> tailDF=(df.tail(3));
            //tailDF.forEach(System.out::println);
            
            //PERSONAS

            // 1. Devolver las diez primeras filas del dataframe
	        DataFrameImpl primerasDiez = df.head(10);
	        System.out.println("Las diez primeras filas del DataFrame:");
	        System.out.println(primerasDiez);
	        System.out.println("\n");

	        // 2. Devolver las diez últimas filas del dataframe
	        DataFrameImpl ultimasDiez = df.tail(10);
	        System.out.println("Las diez últimas filas del DataFrame:");
	        System.out.println(ultimasDiez);
	        System.out.println("\n");

	        // 3. Devolver las cinco primeras filas del dataframe
	        DataFrameImpl primerasCinco = df.head(5);
	        System.out.println("Las cinco primeras filas del DataFrame:");
	        System.out.println(primerasCinco);
	        System.out.println("\n");

	        // 4. Devolver las cinco últimas filas del dataframe
	        DataFrameImpl ultimasCinco = df.tail(5);
	        System.out.println("Las cinco últimas filas del DataFrame:");
	        System.out.println(ultimasCinco);
	        System.out.println("\n");

	        // 5. Devolver una porción entre dos valores enteros que indican las filas
	        DataFrameImpl porcion = df.slice(5, 15);
	        System.out.println("Porción del DataFrame entre las filas 5 y 15:");
	        System.out.println(porcion);
	        System.out.println("\n");

	        // 6. Eliminar una columna, por ejemplo, la columna "Nombre"
	        DataFrameImpl sinColumnaNombre = df.removeColumn("Nombre");
	        System.out.println("DataFrame después de eliminar la columna 'Nombre':");
	        System.out.println(sinColumnaNombre);
	        System.out.println("\n");
	        
	        System.out.println("Personas mayores de sesenta años:");
	        System.out.println(df.filtrarsesentaaños());
	        System.out.println("\n");
	        
	        
	        //MASCOTAS
	        System.out.println("\t \t ____________MASCOTAS_____________");
	        System.out.println("\n");
	        DataFrameImpl df2 = DataFrameImpl.parse("src/prueba_entrega2java/mascotas.csv");
	        // Imprimir la representación de cadena del DataFrame original
	        System.out.println(df2.toString());
	        System.out.println("\n");

	        // 1. Devolver las diez primeras filas del dataframe
	        DataFrameImpl primerasDiez2 = df2.head(10);
	        System.out.println("Las diez primeras filas del DataFrame:");
	        System.out.println(primerasDiez2);
	        System.out.println("\n");

	        // 2. Devolver las diez últimas filas del dataframe
	        DataFrameImpl ultimasDiez2 = df2.tail(10);
	        System.out.println("Las diez últimas filas del DataFrame:");
	        System.out.println(ultimasDiez2);
	        System.out.println("\n");

	        // 3. Devolver las cinco primeras filas del dataframe
	        DataFrameImpl primerasCinco2 = df2.head(5);
	        System.out.println("Las cinco primeras filas del DataFrame:");
	        System.out.println(primerasCinco2);
	        System.out.println("\n");

	        // 4. Devolver las cinco últimas filas del dataframe
	        DataFrameImpl ultimasCinco2 = df2.tail(5);
	        System.out.println("Las cinco últimas filas del DataFrame:");
	        System.out.println(ultimasCinco2);
	        System.out.println("\n");

	        // 5. Devolver una porción entre dos valores enteros que indican las filas
	        DataFrameImpl porcion2 = df2.slice(5, 15);
	        System.out.println("Porción del DataFrame entre las filas 5 y 15:");
	        System.out.println(porcion2);
	        System.out.println("\n");
	        
	        // 6. Eliminar una columna, por ejemplo, la columna "Nombre"
	        DataFrameImpl sinColumnaNombre2 = df2.removeColumn("IdMascota");
	        System.out.println("DataFrame después de eliminar la columna 'IdMascota':");
	        System.out.println(sinColumnaNombre2);
	        System.out.println("\n");
	        
	        //PP
	        System.out.println("\t \t ____________PELICULAS_____________");
	        
	        DataFrameImpl df3 = DataFrameImpl.parse("src/prueba_entrega2java/pp.csv");
	        System.out.println("\n");
	     // Imprimir la representación de cadena del DataFrame original
	        System.out.println(df3.toString());
	        System.out.println("\n");
	        // 1. Devolver las diez primeras filas del dataframe
	        DataFrameImpl primerasDiez3 = df3.head(10);
	        System.out.println("Las diez primeras filas del DataFrame:");
	        System.out.println(primerasDiez3);
	        System.out.println("\n");

	        // 2. Devolver las diez últimas filas del dataframe
	        DataFrameImpl ultimasDiez3 = df3.tail(10);
	        System.out.println("Las diez últimas filas del DataFrame:");
	        System.out.println(ultimasDiez3);
	        System.out.println("\n");

	        // 3. Devolver las cinco primeras filas del dataframe
	        DataFrameImpl primerasCinco3 = df3.head(5);
	        System.out.println("Las cinco primeras filas del DataFrame:");
	        System.out.println(primerasCinco3);
	        System.out.println("\n");

	        // 4. Devolver las cinco últimas filas del dataframe
	        DataFrameImpl ultimasCinco3 = df3.tail(5);
	        System.out.println("Las cinco últimas filas del DataFrame:");
	        System.out.println(ultimasCinco3);
	        System.out.println("\n");

	        // 5. Devolver una porción entre dos valores enteros que indican las filas
	        DataFrameImpl porcion3 = df3.slice(5, 15);
	        System.out.println("Porción del DataFrame entre las filas 5 y 15:");
	        System.out.println(porcion3);
	        System.out.println("\n");

	        // 6. Eliminar una columna, por ejemplo, la columna "Nombre"
	        DataFrameImpl sinColumnaNombre3 = df3.removeColumn("Titulo");
	        System.out.println("DataFrame después de eliminar la columna 'Titulo':");
	        System.out.println(sinColumnaNombre3);
	        System.out.println("\n");

	        
	        
                        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    
    }
}

package prueba_entrega2java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import prueba_entrega2java.DataFrameImpl;


public class ExamenEntrega2<E extends Comparable<E>> {
    private final LinkedHashMap<String, List<String>> df;

    public ExamenEntrega2(LinkedHashMap<String, List<String>> data) {
    	//borrar para q pueda imprimir vacio
        if (data.isEmpty()) {
            throw new IllegalArgumentException("El argumento está vacío");
        }

        if (new HashSet<>(data.keySet()).size() != data.keySet().size()) {
            throw new IllegalArgumentException("Los nombres de columna coinciden");
        }

        boolean allSameLength = data.values().stream().allMatch(values -> values.size() == data.get(data.keySet().iterator().next()).size());

        if (!allSameLength) {
            throw new IllegalArgumentException("Todas las listas deben ser del mismo tamaño");
        }

        this.df = data;
    } //Algunos metodos de dataframeimpl que utilizan los nuevos metodos

    public String getCell(int row, String column) {
        return df.get(column).get(row);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<String> columnNames = getColumnNames();
        int columnWidth = 20;
        String headerFormat = "| %" + columnWidth + "s ";
        String dataFormat = "| %" + columnWidth + "s ";
        for (String columnName : columnNames) {
            headerFormat += String.format("| %" + columnWidth + "s ", columnName.replaceAll("-", "_"));
            dataFormat += "| %" + columnWidth + "s ";
        }
        headerFormat += "|\n";
        dataFormat += "|\n";

        int maxRowNumberWidth = String.valueOf(getRowNumber() - 1).length(); // Ajuste aquí

        sb.append(String.format(headerFormat, ""));
        sb.append("|").append("_".repeat(columnWidth + 2));
        for (int i = 1; i < columnNames.size(); i++) {
            sb.append("|").append("_".repeat(columnWidth + 2));
        }
        sb.append("|").append("_".repeat(columnWidth + 2)).append("|\n");

        for (int i = 0; i < getRowNumber(); i++) {
            List<String> rowData = new ArrayList<>();
            rowData.add(String.format("%" + maxRowNumberWidth + "s", i)); // Ajuste aquí
            for (String columnName : columnNames) {
                rowData.add(String.format("%" + columnWidth + "s", getCell(i, columnName).replaceAll("-", "_")));
            }
            sb.append(String.format(dataFormat, rowData.toArray()));
        }

        return sb.toString();
    }
	
	public int getColumnNumber() {
        return df.keySet().size();
    }
	public List<String> getColumnNames() {
        return new ArrayList<>(df.keySet());
    }
	public List<String> getColumn(String name) {
        return df.get(name);
    }
	public int getRowNumber() {
        return df.values().iterator().next().size();
    }
	public DataFrameImpl slice(int n, int m) {
        if (n > df.values().iterator().next().size() || m > df.values().iterator().next().size()) {
            System.out.println("No hay suficientes filas en el DataFrame para la porción especificada.");
            return null;
        }

        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : df.entrySet()) {
            newData.put(entry.getKey(), entry.getValue().subList(n - 1, Math.min(m, entry.getValue().size())));
        }
        return new DataFrameImpl(newData);
    }
//comienzo de los nuevos metodos estaticos de la defensa
	   
    //dataframe dado vacio y solo imprime columnas
    public static DataFrameImpl emptyDataFrame(DataFrameImpl df) {
        List<String> columnNames = df.getColumnNames();
      
        // secrea un DataFrame vacío con las mismas columnas
        LinkedHashMap<String, List<String>> data = new LinkedHashMap<>();
        for (String columnName : columnNames) {
            data.put(columnName, new ArrayList<>());
        }
        return new DataFrameImpl(data);
    }
    public static DataFrameImpl addDataFrame(DataFrameImpl df1, DataFrameImpl df2) {
        List<String> columnNames1 = df1.getColumnNames();
        List<String> columnNames2 = df2.getColumnNames();
        // Crear una lista de todas las columnas presentes en ambos DataFrames
        Set<String> allColumnNames = new LinkedHashSet<>(columnNames1);
        allColumnNames.addAll(columnNames2);
        
        // se crea un nuevo DataFrame 
        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>();

        for (String columnName : allColumnNames) {
            List<String> values = new ArrayList<>();
            if (columnNames1.contains(columnName)) {
                values.addAll(df1.getColumn(columnName));
            } else {
                values.addAll(Collections.nCopies(df1.getRowNumber(), ""));
            }
            newData.put(columnName, values);
        }
        // Se añade las filas del DataFrame df2 al nuevo DataFrame
        List<List<String>> rows = df2.getRows();
        for (List<String> row : rows) {
            for (int i = 0; i < columnNames2.size(); i++) {
                String columnName = columnNames2.get(i);
                String cellValue = row.get(i);
                newData.get(columnName).add(cellValue);
            }
            for (String columnName : allColumnNames) {
                if (!columnNames2.contains(columnName)) {
                    newData.get(columnName).add("");
                }
            }
        }
        
        return new DataFrameImpl(newData);
    }


        
    public static DataFrameImpl removeColumnIndex(DataFrameImpl df, int ci) {
    	//verifica que el indice sea valido
        if (ci < 0 || ci >= df.getColumnNumber()) {
            throw new IllegalArgumentException("Índice de columna inválido: " + ci);
        }
        List<String> columnNames = df.getColumnNames();
        String columnNameToRemove = columnNames.get(ci);
        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>();
        
        for (String columnName : columnNames) {
            if (!columnName.equals(columnNameToRemove)) {
                newData.put(columnName, df.getColumn(columnName));
            }
        }  
        return new DataFrameImpl(newData);
    }
         
    public static List<DataFrameImpl> divideDataFrame(DataFrameImpl df, int ci) {
        // Verifica que el índice sea válido
        if (ci < 0 || ci >= df.getColumnNumber()) {
            throw new IllegalArgumentException("El índice de división está fuera de rango.");
        }
        List<String> columnNames = df.getColumnNames();
        List<String> columnNames1 = columnNames.subList(0, ci);
        List<String> columnNames2 = columnNames.subList(ci, df.getColumnNumber());
        // Crea DataFrames para cada parte
        DataFrameImpl df1 = filterColumns(df, columnNames1);
        DataFrameImpl df2 = filterColumns(df, columnNames2);
        
        return Arrays.asList(df1, df2);
    }

    private static DataFrameImpl filterColumns(DataFrameImpl df, List<String> columnNames) {
        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>();
        for (String columnName : columnNames) {
            newData.put(columnName, df.getColumn(columnName));
        }
        return new DataFrameImpl(newData);
    }        
      
        public static void main(String[] args) {
    		// TODO Auto-generated method stub
        	try {
	        	DataFrameImpl<String> df = DataFrameImpl.parse("src/prueba_entrega2java/personas.csv");
	        	DataFrameImpl df2 = DataFrameImpl.parse("src/prueba_entrega2java/mascotas.csv");
	        	DataFrameImpl df3 = DataFrameImpl.parse("src/prueba_entrega2java/pp.csv");
	        	
	        	System.out.println("DEFENSA");System.out.println("\n");
	        	
	        	System.out.println("DataFrame pp vacio:");
	        	DataFrameImpl vacio = emptyDataFrame(df3);
		        System.out.println(vacio);
		        System.out.println("\n");
		        System.out.println("________________________________________________________________________________________________");
		        System.out.println("\n");
		        System.out.println("DataFrame resultante de unir el DataFrame pp con el DataFrame Mascotas:");
		        DataFrameImpl añadido = addDataFrame(df3,df2);
		        System.out.println(añadido);
		        System.out.println("\n");
		        System.out.println("________________________________________________________________________________________________");
		        System.out.println("\n");
		        System.out.println("DataFrame Mascotas sin la columna 3:");
		        DataFrameImpl remove= removeColumnIndex(df2,2);
		        System.out.println(remove);
		        System.out.println("\n");
		        System.out.println("________________________________________________________________________________________________");
		        System.out.println("\n");
		        System.out.println("DataFrame Mascotas dividido por la columna 3:");
		        List<DataFrameImpl> divide= divideDataFrame(df2,2);
		        System.out.println(divide);
		        System.out.println("\n");
		        System.out.println("Fin del testeo");
		        //el resto de casos dan error por las restriciones existentes en cada metodo
		        
    	}
        catch (IOException e) {
            e.printStackTrace();
        }
}}

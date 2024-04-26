package prueba_entrega2java;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataFrameImpl<E extends Comparable<E>> {
    private final LinkedHashMap<String, List<String>> df;

    public DataFrameImpl(LinkedHashMap<String, List<String>> data) {
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
    }

    public static DataFrameImpl ofDict(Map<String, List<String>> data) {
        return new DataFrameImpl(new LinkedHashMap<>(data));
    }

    public static DataFrameImpl of(List<String> columnNames, List<List<String>> rows) {
        int numColumns = columnNames.size();

        for (int i = 0; i < numColumns; i++) {
            final int index = i; 
            if (rows.stream().anyMatch(row -> row.size() != numColumns)) {
                throw new IllegalArgumentException("El número de columnas no coincide con el número de elemementos en las filas.");
            }
        }
        LinkedHashMap<String, List<String>> data = new LinkedHashMap<>();
        for (int i = 0; i < numColumns; i++) {
            final int index = i; 
            data.put(columnNames.get(i), rows.stream().map(row -> row.get(index)).collect(Collectors.toList()));
        }
        return new DataFrameImpl(data);
    }


    private static boolean existeFichero(String file) {
        File fichero = new File(file);
        return fichero.exists();
    }

    public static DataFrameImpl parse(String file) throws IOException {
        if (!existeFichero(file)) {
            throw new IllegalArgumentException("El fichero " + file + " no existe");
        }

        LinkedHashMap<String, List<String>> data = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("El archivo está vacío");
            }
            String[] header = line.split(",");
            for (String key : header) {
                data.put(key, new ArrayList<>());
            }
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                for (int i = 0; i < header.length; i++) {
                    data.get(header[i]).add(row[i]);
                }
            }
        }
        return new DataFrameImpl(data);
    }

    public boolean allDifferent(Iterable<?> values) {
        Set<Object> set = new HashSet<>();
        for (Object value : values) {
            if (!set.add(value)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getColumnNames() {
        return new ArrayList<>(df.keySet());
    }

    public int getColumnNumber() {
        return df.keySet().size();
    }

    public List<String> getColumn(String name) {
        return df.get(name);
    }

    public List<String> getColumnIndex(int index) {
        String columnName = new ArrayList<>(df.keySet()).get(index);
        return getColumn(columnName);
    }

    public boolean isColumnAllDifferent(String name) {
        return allDifferent(df.get(name));
    }

    public String getCell(int row, String column) {
        return df.get(column).get(row);
    }

    public String getCellIndex(int row, int column) {
        String columnName = new ArrayList<>(df.keySet()).get(column);
        return getCell(row, columnName);
    }

    public int getRowNumber() {
        return df.values().iterator().next().size();
    }

    public List<String> getRow(int n) {
        return df.values().stream().map(row -> row.get(n)).collect(Collectors.toList());
    }

    public List<String> getRowName(String column, String row) {
        int index = df.get(column).indexOf(row);
        if (index == -1) {
            return new ArrayList<>();
        }
        return df.values().stream().map(values -> values.get(index)).collect(Collectors.toList());
    }

    public <R> R rowValue(int n, Function<List<String>, R> f) {
        List<String> rowValues = df.values().stream().map(values -> values.get(n)).collect(Collectors.toList());
        return f.apply(rowValues);
    }

    public List<List<String>> getRows() {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < getRowNumber(); i++) {
            rows.add(getRow(i));
        }
        return rows;
    }

    public DataFrameImpl head(int n) {
        if (n > df.values().iterator().next().size()) {
            System.out.println("No hay suficientes filas en el DataFrame para las filas especificadas. ");
            return null;
        }
        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : df.entrySet()) {
            newData.put(entry.getKey(), entry.getValue().subList(0, Math.min(n, entry.getValue().size())));
        }
        return new DataFrameImpl(newData);
    }

    public DataFrameImpl tail(int n) {
        if (n > df.values().iterator().next().size()) {
            System.out.println("No hay suficientes filas en el DataFrame para las filas especificadas. ");
            return null;
        }
        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : df.entrySet()) {
            List<String> values = entry.getValue();
            newData.put(entry.getKey(), values.subList(Math.max(0, values.size() - n), values.size()));
        }
        return new DataFrameImpl(newData);
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

    public DataFrameImpl filter(Predicate<List<String>> p) {
        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : df.entrySet()) {
            String columnName = entry.getKey();
            List<String> columnValues = entry.getValue();
            List<String> filteredColumnValues = new ArrayList<>();
            for (int i = 0; i < columnValues.size(); i++) {
                List<String> row = new ArrayList<>();
                for (List<String> values : df.values()) {
                    row.add(values.get(i));
                }
                if (p.test(row)) {
                    filteredColumnValues.add(columnValues.get(i));
                }
            }
            newData.put(columnName, filteredColumnValues);
        }
        return new DataFrameImpl(newData);
    }

    public DataFrameImpl filtrarsesentaaños() {
        Predicate<List<String>> ageFilter = row -> {
            int index = getColumnNames().indexOf("Edad");
            if (index == -1) {
                return false;
            }
            String ageStr = row.get(index);
            try {
                int ageValue = Integer.parseInt(ageStr.trim());
                return ageValue > 60;
            } catch (NumberFormatException e) {
                return false;
            }
        };
        return this.filter(ageFilter);
    }
    
    public DataFrameImpl filtrarCuatroLetras() {
        Predicate<List<String>> nameFilter = row -> {
            int index = getColumnNames().indexOf("Nombre");
            if (index == -1) {
                return false;
            }
            String name = row.get(index);
            return name.length() == 4;
        };
        return this.filter(nameFilter);
    }

    public DataFrameImpl sortBy(Function<List<String>, E> f) {
        List<Map.Entry<String, List<String>>> sortedEntries = df.entrySet().stream()
                .sorted(Comparator.comparing(entry -> f.apply(entry.getValue())))
                .collect(Collectors.toList());

        LinkedHashMap<String, List<String>> sortedData = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : sortedEntries) {
            sortedData.put(entry.getKey(), entry.getValue());
        }
        return new DataFrameImpl(sortedData);
    }
    
    public String getProperty(String row, String column) {
        if (!df.containsKey(column)) {
            throw new IllegalArgumentException("Column '" + column + "' does not exist in the DataFrame");
        }
        List<String> columnValues = df.get(column);
        int rowIndex = columnValues.indexOf(row);
        if (rowIndex == -1) {
            throw new IllegalArgumentException("Row '" + row + "' does not exist in the DataFrame");
        }
        return columnValues.get(rowIndex);
    }
    
    public DataFrameImpl addRow(Map<String, String> newRow) {
        for (String column : newRow.keySet()) {
            if (!df.containsKey(column)) {
                throw new IllegalArgumentException("Column '" + column + "' does not exist in the DataFrame");
            }
            df.get(column).add(newRow.get(column));
        }
        return this;
    }
    
    public DataFrameImpl removeRow(int index) {
        if (index < 0 || index >= getRowNumber()) {
            throw new IllegalArgumentException("Invalid row index: " + index);
        }
        for (List<String> columnValues : df.values()) {
            columnValues.remove(index);
        }
        return this;
    }

    public DataFrameImpl addColumn(String name, List<String> values) {
        if (df.containsKey(name)) {
            throw new IllegalArgumentException("Column with name '" + name + "' already exists");
        }
        if (values.size() != df.values().iterator().next().size()) {
            throw new IllegalArgumentException("Number of values does not match the number of rows");
        }
        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>(df);
        newData.put(name, values);
        return new DataFrameImpl(newData);
    }

    public DataFrameImpl addCalculatedColumn(String name, Function<String, String> f, List<String> values) {
        if (df.containsKey(name)) {
            throw new IllegalArgumentException("Column with name '" + name + "' already exists");
        }
        if (values.size() != df.values().iterator().next().size()) {
            throw new IllegalArgumentException("Number of values does not match the number of rows");
        }
        List<String> calculatedValues = values.stream().map(f).collect(Collectors.toList());
        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>(df);
        newData.put(name, calculatedValues);
        return new DataFrameImpl(newData);
    }

    public DataFrameImpl removeColumn(String name) {
        if (!df.containsKey(name)) {
            throw new IllegalArgumentException("Column with name '" + name + "' does not exist");
        }
        LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>(df);
        newData.remove(name);
        return new DataFrameImpl(newData);
    }

    public DataFrameImpl groupBy(List<String> groupColumns, String valueColumn, Function<List<String>, String> op) {

        for (String col : groupColumns) {
            if (!df.containsKey(col)) {
                throw new IllegalArgumentException("Column '" + col + "' does not exist in the DataFrame");
            }
        }
        if (!df.containsKey(valueColumn)) {
            throw new IllegalArgumentException("Column '" + valueColumn + "' does not exist in the DataFrame");
        }

        Map<List<String>, List<String>> groupedData = new HashMap<>();
        for (int i = 0; i < df.get(valueColumn).size(); i++) {
            List<String> groupKey = new ArrayList<>();
            for (String col : groupColumns) {
                groupKey.add(df.get(col).get(i));
            }
            groupedData.computeIfAbsent(groupKey, k -> new ArrayList<>()).add(df.get(valueColumn).get(i));
        }

        LinkedHashMap<String, List<String>> resultData = new LinkedHashMap<>();
        for (List<String> groupKey : groupedData.keySet()) {
            for (int i = 0; i < groupColumns.size(); i++) {
                resultData.computeIfAbsent(groupColumns.get(i), k -> new ArrayList<>()).add(groupKey.get(i));
            }
            resultData.computeIfAbsent(valueColumn, k -> new ArrayList<>()).add(op.apply(groupedData.get(groupKey)));
        }

        return new DataFrameImpl(resultData);
    }

    private List<List<String>> zipValues(List<String> columns) {
        List<List<String>> result = new ArrayList<>();
        int numRows = df.get(columns.get(0)).size();
        for (int i = 0; i < numRows; i++) {
            List<String> row = new ArrayList<>();
            for (String col : columns) {
                row.add(df.get(col).get(i));
            }
            result.add(row);
        }
        return result;
    }

    @Override
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
    
    /// COMIENZO DE LA DEFENSA
   
    //dataframe vacio
    public static DataFrameImpl emptyDataFrame() {
        return new DataFrameImpl(new LinkedHashMap<>());
    }
    //dataframe vacio a partir de otro solo imprime columnas
    public DataFrameImpl emptyDataFrameFromDataFrame(DataFrameImpl df) {
        // Obtener los nombres de las columnas del DataFrame existente
        List<String> columnNames = df.getColumnNames();
        
        // Crear un DataFrame vacío con las mismas columnas
        LinkedHashMap<String, List<String>> data = new LinkedHashMap<>();
        for (String columnName : columnNames) {
            data.put(columnName, new ArrayList<>());
        }
        return new DataFrameImpl(data);
    }
    // añade
    public void addDataFrame(DataFrameImpl df2) {
        // Obtener los nombres de las columnas del DataFrame actual y del DataFrame a añadir
        List<String> columnNames = getColumnNames();
        List<String> columnNames2 = df2.getColumnNames();
        
        // Verificar si los nombres de las columnas son iguales en ambos DataFrames
        if (!columnNames.equals(columnNames2)) {
            throw new IllegalArgumentException("Los DataFrames tienen diferentes columnas y no se pueden añadir.");
        }
        
        // Añadir las filas del DataFrame df2 al DataFrame actual
        List<List<String>> rows = df2.getRows();
        for (List<String> row : rows) {
            for (int i = 0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                String cellValue = row.get(i);
                // Añadir el valor de la celda a la columna correspondiente
                getColumn(columnName).add(cellValue);
            }}}
        
        //mismo pero cambiado
        /*public DataFrameImpl addDataFrame(DataFrameImpl df2) {
            // Obtener las columnas de ambos DataFrames
            List<String> columnNames1 = getColumnNames();
            List<String> columnNames2 = df2.getColumnNames();
            
            // Crear una lista con todas las columnas
            List<String> allColumnNames = new ArrayList<>(columnNames1);
            allColumnNames.addAll(columnNames2);
            
            // Verificar si hay columnas comunes
            Set<String> commonColumns = new HashSet<>(columnNames1);
            commonColumns.retainAll(columnNames2);
            if (!commonColumns.isEmpty()) {
                throw new IllegalArgumentException("Hay columnas comunes en los DataFrames: " + commonColumns);
            }
            
            // Crear un nuevo DataFrame con todas las columnas
            LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>();
            for (String columnName : allColumnNames) {
                List<String> values = new ArrayList<>();
                values.addAll(getColumn(columnName));
                values.addAll(df2.getColumn(columnName));
                newData.put(columnName, values);
            }
            
            return new DataFrameImpl(newData);
        }*/
        //otro
        public DataFrameImpl removeColumnByIndex(int index) {
            if (index < 0 || index >= getColumnNumber()) {
                throw new IllegalArgumentException("Índice de columna inválido: " + index);
            }
            
            String columnName = getColumnNames().get(index);
            LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>(df);
            newData.remove(columnName);
            
            return new DataFrameImpl(newData);
        }
        //divide por las columnas 
        public List<DataFrameImpl> splitDataFrame(int c1, int c2) {
            // Verificar que los índices sean válidos
            if (c1 < 0 || c1 >= getColumnNumber() || c2 < 0 || c2 >= getColumnNumber()) {
                throw new IllegalArgumentException("Los índices de división están fuera de rango.");
            }
            
            // Obtener las columnas del DataFrame
            List<String> columnNames = getColumnNames();
            
            // Verificar que los índices de división sean coherentes
            if (c1 >= c2) {
                throw new IllegalArgumentException("El índice de inicio debe ser menor que el índice de finalización.");
            }
            
            // Dividir las columnas en las partes indicadas por los índices
            List<String> columnNames1 = columnNames.subList(0, c1);
            List<String> columnNames2 = columnNames.subList(c1, c2 + 1);
            List<String> columnNames3 = columnNames.subList(c2 + 1, getColumnNumber());
            
            // Crear DataFrames para cada parte
            DataFrameImpl df1 = filterColumns(columnNames1);
            DataFrameImpl df2 = filterColumns(columnNames2);
            DataFrameImpl df3 = filterColumns(columnNames3);
            
            return Arrays.asList(df1, df2, df3);
        }

        private DataFrameImpl filterColumns(List<String> columnNames) {
            LinkedHashMap<String, List<String>> newData = new LinkedHashMap<>();
            for (String columnName : columnNames) {
                newData.put(columnName, getColumn(columnName));
            }
            return new DataFrameImpl(newData);
        }
///divide por filas
        public List<DataFrameImpl> splitDataFrameByRows(int c1, int c2) {
            if (c1 < 0 || c2 < 0 || c1 >= getRowNumber() || c2 >= getRowNumber() || c1 >= c2) {
                throw new IllegalArgumentException("Los índices proporcionados están fuera de rango o no son válidos");
            }
            List<DataFrameImpl> result = new ArrayList<>();
            DataFrameImpl firstPart = slice(0, c1);
            DataFrameImpl secondPart = slice(c1 + 1, c2 + 1); // Agregar 1 a c2 para incluirlo en la segunda parte
            DataFrameImpl thirdPart = slice(c2 + 1, getRowNumber()); // Agregar 1 a c2 para iniciar desde la fila siguiente
            if (firstPart.getRowNumber() > 0) {
                result.add(firstPart);
            }
            if (secondPart.getRowNumber() > 0) {
                result.add(secondPart);
            }
            if (thirdPart.getRowNumber() > 0) {
                result.add(thirdPart);
            }
            return result;
        }



}
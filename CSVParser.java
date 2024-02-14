import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static List<List<String>> parseCSV(String input) {
        List<List<String>> result = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        List<String> row = new ArrayList<>();
        boolean inQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (inQuotes) {
                if (ch == DEFAULT_QUOTE) {
                    if (i < input.length() - 1 && input.charAt(i + 1) == DEFAULT_QUOTE) {
                        field.append(DEFAULT_QUOTE);
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else if (ch == '\\') {
                    if (i < input.length() - 1) {
                        char nextChar = input.charAt(i + 1);
                        if (nextChar == 'n') {
                            field.append('\n');
                            i++;
                        } else if (nextChar == '\\') {
                            field.append('\\');
                            i++;
                        } else {
                            field.append(ch);
                        }
                    } else {
                        field.append(ch);
                    }
                } else {
                    field.append(ch);
                }
            } else {
                switch (ch) {
                    case DEFAULT_QUOTE:
                        inQuotes = true;
                        break;
                    case DEFAULT_SEPARATOR:
                        row.add(field.toString());
                        field.setLength(0);
                        break;
                    case '\n':
                        row.add(field.toString());
                        result.add(new ArrayList<>(row));
                        row.clear();
                        field.setLength(0);
                        break;
                    default:
                        field.append(ch);
                        break;
                }
            }
        }

        if (field.length() > 0) {
            row.add(field.toString());
        }
        if (!row.isEmpty()) {
            result.add(row);
        }

        return result;
    }

    public static void printCSV(List<List<String>> data) {
        for (List<String> row : data) {
            System.out.println(row);
        }
    }

    public static void printCSVFormatted(List<List<String>> data) {
        System.out.print("[");
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            System.out.print("[");
            for (int j = 0; j < row.size(); j++) {
                String fieldValue = row.get(j).replace("\n", "\\n");
                fieldValue = fieldValue.replace("\"\"", "\"");
                System.out.print("'" + fieldValue + "'");
                if (j < row.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("]");
            if (i < data.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVFileReader {

    public static void main(String[] args) {
        String filePath = "src/texte.csv";
        try {
            String content = readFile(filePath);
            List<List<String>> parsedData = CSVParser.parseCSV(content);
            CSVParser.printCSVFormatted(parsedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}

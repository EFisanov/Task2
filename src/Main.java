import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {
    private static final String CONFIG_FILE = "config.properties";
    static Properties properties = new Properties();
    static Path configPath = Paths.get(CONFIG_FILE);

    public static void main(String[] args) throws InvalidDataException {
        String filePath = "";
        String delimiter = "";
        if (args.length < 2) {
            if (Files.exists(configPath)) {
                try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
                    properties.load(in);
                    System.out.println("Настройки загружены из файла config.properties");
                    filePath = String.valueOf(properties.get("filepath"));
                    delimiter = String.valueOf(properties.get("delimiter"));
                } catch (IOException e) {
                    System.out.println("Ошибка при загрузке настроек: " + e.getMessage());
                }
            } else {
                System.out.println("Не указаны имя файла и разделитель.");
                return;
            }
        } else {
            if (Files.exists(configPath)) {
                properties.setProperty("filepath", filePath);
                properties.setProperty("delimiter", delimiter);
            }
            filePath = args[0];
            delimiter = args[1];
            saveProperties(filePath, delimiter);
        }

        calculateEquation(getData(filePath, delimiter));
    }

    public static void saveProperties(String filePath, String delimiter) {
        properties.setProperty("filepath", filePath);
        properties.setProperty("delimiter", delimiter);
        try (FileOutputStream out = new FileOutputStream("config.properties")) {
            properties.store(out, "Program Configuration");
            System.out.println("Настройки успешно сохранены в файл config.properties");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении настроек: " + e.getMessage());
        }
    }

    public static String[] getData(String filePath, String delimiter) {
        String buff;
        String[] array = new String[4];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            buff = reader.readLine();
            array = buff.split(delimiter);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            exception.getStackTrace();
        }
        return array;
    }

    public static void calculateEquation(String[] array) throws InvalidDataException {
        if (array.length < 4) {
            throw new InvalidDataException("Не достаточно данных для решения уравнения");
        }
        double a = Integer.parseInt(array[0]);
        double b = Integer.parseInt(array[1]);
        double c = Integer.parseInt(array[2]);
        double e = Integer.parseInt(array[3]);

        double x;
        double x1;
        double x2;

        if (e != 0) {
            c -= e;
        }

        double D = Math.pow(b, 2) - 4 * a * c;

        if (D > 0) {
            x1 = (-b + Math.sqrt(D)) / (2 * a);
            x2 = (-b - Math.sqrt(D)) / (2 * a);
            System.out.println("D= " + D);
            System.out.println("x1= " + x1 + "\nx2= " + x2);
        } else if (D == 0) {
            x = -b / (2 * a);
            System.out.println("D= " + D);
            System.out.println("x= " + x);
        } else {
            System.out.println("D= " + D);
            System.out.println("Действительных корней нет");
        }
    }
}

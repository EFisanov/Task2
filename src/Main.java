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

        if (args.length == 4) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--delimiter")) {
                    delimiter = args[++i];
                }
                if (args[i].equals("--filepath")) {
                    filePath = args[++i];
                }
            }
            if (Files.exists(configPath)) {
                properties.setProperty("filepath", filePath);
                properties.setProperty("delimiter", delimiter);
            }
            saveProperties(filePath, delimiter);
        } else {
            if (Files.exists(configPath)) {
                try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
                    properties.load(in);
                    System.out.println("Настройки загружены из файла config.properties");
                    filePath = String.valueOf(properties.get("filepath"));
                    delimiter = String.valueOf(properties.get("delimiter"));
                } catch (IOException e) {
                    System.err.println("Ошибка при загрузке настроек: " + e.getMessage());
                }
            } else {
                System.out.println("Запустите приложение со следующими параметрами:\n" +
                        "--filepath  - путь к файлу с данными\n" +
                        "--delimiter  - символ, использующийся для разделения значений данных\n");
                return;
            }
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
            System.err.println("Ошибка при сохранении настроек: " + e.getMessage());
        }
    }

    public static Double[] getData(String filePath, String delimiter) {
        String buff;
        Double[] numbers = new Double[4];
        String[] strings = new String[4];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            buff = reader.readLine();
            strings = buff.split(delimiter);
            for (int i = 0; i < strings.length; i++) {
                numbers[i] = parseNumber(strings[i]);
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            exception.getStackTrace();
        }
        return numbers;
    }

    public static Double parseNumber(String number) throws InvalidArgumentException {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("Не верный формат числа: " + number);
        }
    }

    public static void calculateEquation(Double[] array) throws InvalidDataException {
        if (array.length < 4) {
            throw new InvalidDataException("Не достаточно данных для решения уравнения");
        }
        double a = array[0];
        double b = array[1];
        double c = array[2];
        double e = array[3];

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

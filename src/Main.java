import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws InvalidDataException {
        String filePath;
        String delimiter;
        if (args.length == 1) {
            System.out.println("Не указан разделитель.");
            return;
        } else if (args.length == 0) {
            System.out.println("Не указаны имя файла и разделитель.");
            return;
        } else {
            filePath = args[0];
            delimiter = args[1];
        }

        calculateEquation(getData(filePath, delimiter));
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

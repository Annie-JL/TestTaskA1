import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;


import java.io.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;



public class task3 {
    public static void main(String[] args) throws CsvException, IOException, SQLException, ParseException {

        System.out.println("Menu: " +
                "\n/1 Прочитать файл logins.csv с локальной файловой системы" +
                "\n/2 Прочитать файл postings.csv с локальной файловой системы (строки со значениями в поле Mat. Doc.)" +
                "\n/3 Добавить булевое поле \"авторизованная поставка\" в данные из postings.csv, которое будет указывать, что User Name (postings.csv) находится в списке AppAccountName (logins.csv) и IsActive" +
                "\n/4 Cохранить в SQL СУБД данные файла logins.csv" +
                "\n/5 Сохранить в SQL СУБД данные файла postings.csv (с дополнительным полем)" +
                "\nВыберите пункт меню: ");


        Scanner sc = new Scanner(System.in);
        int v = sc.nextInt();
        if (v > 0 && v < 6) {
            switch (v) {
                case 1:
                    f1();
                    break;
                case 2:
                    f2();
                    break;
                case 3:
                    f3();
                    break;
                case 4:
                    f4();
                    break;
                case 5:
                    f5();
                    break;
                default:
                    break;
            }
        } else {

        }
    }

    static void f1() throws IOException, CsvValidationException {

        String csvFile = "D:/logins.csv";
        InputStreamReader isr = new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(isr);
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            for (String cell : nextLine) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    static void f2() throws IOException, CsvValidationException {

        String csvFile = "D:/postings.csv";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
        String line = bufferedReader.readLine();
        String[] columnNames = line.split(";");

        int targetIndex = -1;
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].compareTo("Mat. Doc.") == 0) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex == -1) {
            System.out.println("Column Mat. Doc. not found!");
            return;
        }

        // Считываем каждую строку и выводим значение в колонке "Mat. Doc."
        while ((line = bufferedReader.readLine()) != null) {

            String[] parts = line.split(";");
            if (parts.length > targetIndex) {
                System.out.println(parts[targetIndex]);
            }

        }
        bufferedReader.close();
    }

    static void f3() throws IOException {
        BufferedReader loginsReader = new BufferedReader(new FileReader("D:/logins.csv"));
        String loginsLine = loginsReader.readLine(); // пропускаем заголовок
        String[] loginsColumns;
        List<String> activeUsers = new ArrayList<>();
        while ((loginsLine = loginsReader.readLine()) != null) {
            loginsColumns = loginsLine.split(",");
            if (loginsColumns[2].contains("True")) {
                activeUsers.add(loginsColumns[1]);
            }
        }

        loginsReader.close();

        // Обходим файл postings.csv, добавляем столбец и записываем данные в новый файл
       BufferedReader postingsReader = new BufferedReader(new FileReader("D:/postings.csv"));
        FileWriter writer = new FileWriter("D:/postings_with_authorization.csv");
        String postingsLine = postingsReader.readLine();
        String[] postingsColumns = postingsLine.split(";");
        postingsLine += ";Authorized Delivery"; // добавляем заголовок нового столбца
        writer.write(postingsLine + "\n");
        boolean isFirstLine = true; // добавляем переменную-флаг для проверки первой строки
        while ((postingsLine = postingsReader.readLine()) != null) {
            if (isFirstLine) { // пропускаем первую строку
                isFirstLine = false;
                continue;
            }
            postingsColumns = postingsLine.split(";");
            boolean isAuthorized = activeUsers.contains(postingsColumns[9]);
            postingsLine += ";" + (isAuthorized ? "true" : "false"); // добавляем значение нового столбца
            writer.write(postingsLine + "\n");
        }
        postingsReader.close();
        writer.close();
    }
    static void f5() throws IOException, SQLException, ParseException {
        // Установка параметров подключения через JDBC
        String url = "jdbc:mysql://localhost:3306/postings?useUnicode=true&serverTimezone=UTC";
        String user = "root";
        String password = "root";

        // Создание подключения к базе данных
        Connection conn = DriverManager.getConnection(url, user, password);

        Statement createTableStmt = conn.createStatement();
        createTableStmt.executeUpdate("CREATE TABLE IF NOT EXISTS postings (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "matDoc VARCHAR(255) NOT NULL," +
                "item INT NOT NULL," +
                "docDate DATE NOT NULL," +
                "pstngDate DATE NOT NULL," +
                "matDescription VARCHAR(255) NOT NULL," +
                "quantity INT NOT NULL," +
                "BUn VARCHAR(255) NOT NULL," +
                "amountLC DOUBLE NOT NULL," +
                "Crcy VARCHAR(255) NOT NULL," +
                "userName VARCHAR(255) NOT NULL," +
                "authorizedDel BOOLEAN NOT NULL," +
                "PRIMARY KEY (id))");

        // Чтение данных из файла logins.csv и добавление их в таблицу logins
        BufferedReader reader = new BufferedReader(new FileReader("D:/postings_with_authorization.csv"));

        String line = reader.readLine(); // пропускаем заголовок
        PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO postings (matDoc, item, docDate, pstngDate, matDescription,quantity,BUn,amountLC,Crcy,userName,authorizedDel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(";");
            String matDoc = fields[0];
            String item = fields[1];
            String docDate = fields[2];
            String pstngDate = fields[3];
            String matDescription = fields[4];
            String quantity = fields[5];
            String BUn = fields[6];
            String amountLC = fields[7];
            String Crcy = fields[8];
            String userName = fields[9];
            boolean authorizedDel = fields[10].contains("True");
            insertStmt.setString(1, matDoc);
            insertStmt.setString(2, item);

            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date date = format.parse(docDate);
            java.sql.Date date1 = new java.sql.Date(date.getTime());

            Date datee = format.parse(pstngDate);
            java.sql.Date date2 = new java.sql.Date(date.getTime());

            insertStmt.setDate(3, date1);
            insertStmt.setDate(4, date2);
            insertStmt.setString(5, matDescription);
            insertStmt.setInt(6, Integer.parseInt(quantity.trim()));
            insertStmt.setString(7, BUn);

            NumberFormat format2 = NumberFormat.getInstance(Locale.getDefault());
            Number number = format2.parse(amountLC.trim());
            double d = number.doubleValue();
            insertStmt.setDouble(8, d);
            insertStmt.setString(9, Crcy);
            insertStmt.setString(10, userName);
            insertStmt.setBoolean(11, authorizedDel);
            insertStmt.executeUpdate();
        }

        insertStmt.close();
        reader.close();
        conn.close();
    }

    static void f4() throws IOException, SQLException {
        // Установка параметров подключения через JDBC
        String url = "jdbc:mysql://localhost:3306/logins?useUnicode=true&serverTimezone=UTC";
        String user = "root";
        String password = "root";

        // Создание подключения к базе данных
        Connection conn = DriverManager.getConnection(url, user, password);

        // Создание таблицы logins, если она не существует
        Statement createTableStmt = conn.createStatement();
        createTableStmt.executeUpdate("CREATE TABLE IF NOT EXISTS logins (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "application VARCHAR(255) NOT NULL," +
                "appAccountName VARCHAR(255) NOT NULL," +
                "is_active BOOLEAN NOT NULL," +
                "jobTitle VARCHAR(255) NOT NULL," +
                "department VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (id))");

        // Чтение данных из файла logins.csv и добавление их в таблицу logins
        BufferedReader reader = new BufferedReader(new FileReader("D:/logins.csv"));
        String line;
        PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO logins (application, appAccountName, is_active, jobTitle, department) VALUES (?, ?, ?, ?, ?)");
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String application = fields[0];
            String appAccountName = fields[1];
            boolean is_active = fields[2].contains("True");
            String jobTitle = fields[3];
            String department = fields[4];
            insertStmt.setString(1, application);
            insertStmt.setString(2, appAccountName);
            insertStmt.setBoolean(3, is_active);
            insertStmt.setString(4, jobTitle);
            insertStmt.setString(5, department);
            insertStmt.executeUpdate();
        }

        // Закрытие ресурсов
        insertStmt.close();
        reader.close();
        conn.close();
    }


}


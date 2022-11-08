package com.heyxdelaram;

import com.heyxdelaram.Entity.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static Integer year = LocalDate.now().getYear();
    public static final Integer totalCount;

    static {
        try {
            totalCount = getCapacityByYear(year);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getCapacityByYear(Integer year) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        ResultSet resultSet = statement.executeQuery("SELECT amount FROM capacity WHERE year = " + year +";");
        if (resultSet.next())
            return resultSet.getInt("amount");
        connection.close();
        connection.commit();

        return 0;
    }

    public static Connection connection;

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        try {


            while (true) {
                System.out.println("--- MENU ---");
                System.out.println("[1]: Register new student");
                System.out.println("[2]: Inquire remaining capacity");
                System.out.println("[3]: Edit student information");
                System.out.println("[4]: Cancel registration");
                System.out.println("[5]: Inquire student information");
                System.out.println("[0]: exit");

                int menu = input.nextInt();

                switch (menu) {
                    case 1:

                        regNewStudent();
                        break;
                    case 2:
                        showRemain();
                        break;
                    case 3:
                        editStudentInfo();
                        break;
                    case 4:
                        cancelReg();
                        break;
                    case 5:
                        showStudentInfo();
                        break;
                    case 0:
                        System.out.println("Goodbye.");
                        return;
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void regNewStudent() throws SQLException {
        Student student = new Student();

        System.out.println("First name: ");
        student.setFirstName(input.next());

        System.out.println("Last name: ");
        student.setLastName(input.next());

        System.out.println("Last year's average grade: ");
        student.setAvgScore(input.nextFloat());

        System.out.println("Student number: ");
        student.setStudentNum(input.nextInt());

        System.out.println("Address: ");
        student.setAddress(input.next());

        System.out.println("National code: ");
        student.setnCode(input.next());

        student.setYear(year);
        student.setIsDeleted(false);

        insertIntoDB(student);
        System.out.println("Student added successfully!");
    }

    private static void insertIntoDB(Student student) throws SQLException {
        connection = getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        String command = "INSERT INTO school.students(" +
                "first_name, last_name, average_score, student_number, address, national_code, is_deleted, year)" +
                " VALUES ('" + student.getFirstName() + "', '" + student.getLastName() + "', "
                + student.getAvgScore() + ", " + student.getStudentNum() + ", '" + student.getAddress() + "','" + student.getnCode() + "', " +
                student.isDeleted() + ", " + student.getYear() + ");";
        statement.executeUpdate(command);
        statement.close();
        connection.commit();

    }

    private static void showRemain() throws SQLException {
        int remainCapacity = totalCount - getNotDeletedStudentsByYear(year);
        System.out.println("Remaining capacity for " + year + ": " + remainCapacity);
    }

    private static int getNotDeletedStudentsByYear(int year) throws SQLException {
        int count = 0;
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        ResultSet resultSet = statement.executeQuery("SELECT id from students where is_deleted = false and year = "+year+";");
        while (resultSet.next())
            count++;
        statement.close();
        connection.commit();
        return count;

    }

    private static void editStudentInfo() throws SQLException {
        Student student = loadStudentByNcode();
        System.out.println("* To Edit enter the new information otherwise press Enter *");
        System.out.println("First name = " + student.getFirstName());
        String firstName = input.next();
        if (!firstName.equals(null))
            student.setFirstName(firstName);
        System.out.println("Last name = " + student.getLastName());
        String lastName = input.next();
        if (!lastName.equals(null))
            student.setLastName(lastName);
        System.out.println("Address = " + student.getAddress());
        String addr = input.next();
        if (!addr.equals(null))
            student.setAddress(addr);
        updateDB(student);
        System.out.println("Student information updated successfully!");
    }

    private static Student loadStudentByNcode() throws SQLException {

        System.out.println("National code: ");
        String nCode = input.next();

        Student student = selectStudentByNcode(nCode);
        if (student == null) {
            System.out.println("Student not found.");
            return null;
        }
        return student;
    }

    private static Student selectStudentByNcode(String nCode) throws SQLException {
        Student student = new Student();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE national_code = '" + nCode + "';");
        if (resultSet.next()){
            student.setFirstName(resultSet.getString(2));
            student.setLastName(resultSet.getString(3));
            student.setAvgScore(resultSet.getFloat(4));
            student.setStudentNum(resultSet.getInt(5));
            student.setAddress(resultSet.getString(6));
            student.setnCode(resultSet.getString(7));
            student.setIsDeleted(resultSet.getBoolean(8));
            student.setYear(resultSet.getInt(9));
        }
        statement.close();
        connection.commit();
        return student;
    }

    private static void cancelReg() throws SQLException {
        Student student = loadStudentByNcode();
        student.setIsDeleted(true);
        updateDB(student);
    }

    private static void updateDB(Student student) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        connection.setAutoCommit(false);
        statement.executeUpdate("UPDATE students SET first_name = '" + student.getFirstName()
        + "', last_name = '" + student.getLastName() + "', address = '" + student.getAddress() + "', is_deleted = "
        + student.isDeleted() + ";");
        statement.close();
        connection.commit();
    }

    private static void showStudentInfo() throws SQLException {
        Student student = loadStudentByNcode();
        System.out.println(student.toString());
    }


    public static Connection getConnection() throws SQLException {
        return connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "1073");
    }
}
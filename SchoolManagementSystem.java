import java.sql.*;

public class SchoolManagementSystem {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/school";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Create tables if they don't exist
            createTables(connection);

            // Perform operations
            addStudent(connection, "John Doe", 15);
            addStudent(connection, "Jane Smith", 14);
            addTeacher(connection, "John Smith", "Mathematics");
            addTeacher(connection, "Emma Johnson", "Science");

            // Retrieve and display student and teacher information
            displayStudents(connection);
            displayTeachers(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Create tables if they don't exist
    private static void createTables(Connection connection) throws SQLException {
        String createStudentTableQuery = "CREATE TABLE IF NOT EXISTS students (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "age INT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createStudentTableQuery);
        }

        String createTeacherTableQuery = "CREATE TABLE IF NOT EXISTS teachers (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "subject VARCHAR(100))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTeacherTableQuery);
        }
    }

    // Add a student to the database
    private static void addStudent(Connection connection, String name, int age) throws SQLException {
        String insertStudentQuery = "INSERT INTO students (name, age) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertStudentQuery)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.executeUpdate();
            System.out.println("Student added successfully");
        }
    }

    // Add a teacher to the database
    private static void addTeacher(Connection connection, String name, String subject) throws SQLException {
        String insertTeacherQuery = "INSERT INTO teachers (name, subject) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertTeacherQuery)) {
            statement.setString(1, name);
            statement.setString(2, subject);
            statement.executeUpdate();
            System.out.println("Teacher added successfully");
        }
    }

    // Retrieve and display all students
    private static void displayStudents(Connection connection) throws SQLException {
        String selectStudentsQuery = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectStudentsQuery)) {
            System.out.println("Students:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
        }
    }

    // Retrieve and display all teachers
    private static void displayTeachers(Connection connection) throws SQLException {
        String selectTeachersQuery = "SELECT * FROM teachers";
        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectTeachersQuery)) {
            System.out.println("Teachers:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String subject = resultSet.getString("subject");
                System.out.println("ID: " + id + ", Name: " + name + ", Subject: " + subject);
            }
        }
    }
}
    

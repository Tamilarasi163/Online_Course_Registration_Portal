package CourseEnrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Admin {
    private Connection connection;

    public Admin(Connection connection) {
        this.connection = connection;
    }

    public void addCourse(Course course) {
        String insertQuery = "INSERT INTO courses(course_id, course_name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, course.getCourseId());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.executeUpdate();
            System.out.println("Course added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to add the course: " + e.getMessage());
        }
    }

    public void viewCourse() {
        String selectQuery = "SELECT course_id, course_name FROM courses";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Courses available:");
            while (resultSet.next()) {
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("course_name");
                System.out.println(courseId + ": " + courseName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve courses: " + e.getMessage());
        }
    }

    public void removeCourse(String courseId) {
        String deleteQuery = "DELETE FROM courses WHERE course_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, courseId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course with ID " + courseId + " removed successfully.");
            } else {
                System.out.println("Course with ID " + courseId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to remove the course: " + e.getMessage());
        }
    }
}

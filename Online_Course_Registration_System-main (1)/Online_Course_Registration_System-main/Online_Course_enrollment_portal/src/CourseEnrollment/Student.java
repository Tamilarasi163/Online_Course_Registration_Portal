package CourseEnrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

interface Enrollable {
	void enrollCourse(String courseId, String name);

	void viewEnrolledCourses(String name);
}

class Student extends Person implements Enrollable {
	private List<Course> enrolledCourses;
	private Connection connection;

	public Student(String name, Connection connection) {
		super(name);
		enrolledCourses = new ArrayList<>();
		this.connection = connection;
		insertStudentIntoDatabase();
	}

	private void insertStudentIntoDatabase() {
		String insertQuery = "INSERT INTO students (student_name) VALUES (?)";
		try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
			insertStatement.setString(1, getName());
			insertStatement.executeUpdate();
			System.out.println("New student '" + getName() + "' added to the database.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to add student to the database: " + e.getMessage());
		}
	}

	private Course getCourseById(String courseId) {
		String selectQuery = "SELECT course_name FROM courses WHERE course_id = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setString(1, courseId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					String courseName = resultSet.getString("course_name");
					return new Course(courseId, courseName);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to retrieve course details from the database: " + e.getMessage());
		}
		return null;
	}

	public void enrollCourse(String courseId, String name) {
		Course courseToEnroll = getCourseById(courseId);
//        System.out.print(courseToEnroll.getCourseId());
//        System.out.print(courseToEnroll.getCourseName());
		if (courseToEnroll != null) {
			enrolledCourses.add(courseToEnroll);
			System.out.println(getName() + " has enrolled in the course: " + courseToEnroll.getCourseName());

			String insertQuery = "INSERT INTO enrollments (student_name, course_id, course_name) VALUES (?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, courseId);
				preparedStatement.setString(3, courseToEnroll.getCourseId());
				preparedStatement.executeUpdate();
				System.out.println("Enrollment information added to the database.");
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Failed to add enrollment information to the database: " + e.getMessage());
			}
		} else {
			System.out.println("Course with ID '" + courseId + "' not found!");
		}
	}

	public void viewEnrolledCourses(String name) {
//		String selectQuery = "SELECT courses.course_id, courses.course_name FROM courses "
//				+ "INNER JOIN enrollments ON courses.course_id = enrollments.course_id "
//				+ "INNER JOIN students ON students.student_name = enrollments.student_name "
//				+ "WHERE students.student_name = ?";
		
		String selectQuery = "SELECT * FROM enrollments WHERE student_name = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
			preparedStatement.setString(1, name);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					System.out.println(getName() + " has not enrolled in any courses yet.");
				} else {
					System.out.println(getName() + "'s Enrolled Courses:");
					while(resultSet.next()) {
						String courseId = resultSet.getString("course_id");
						String courseName = resultSet.getString("course_name");
						System.out.println(courseId + ": " + courseName);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to retrieve enrolled courses from the database: " + e.getMessage());
		}
	}

}

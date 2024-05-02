package CourseEnrollment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseEnrollmentPortal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {
            connection = DB.getConnection(); // Get the database connection
            Admin admin = new Admin(connection);

        

            while (true) {
                System.out.println("\nWelcome to College Course Enrollment Portal!");
                System.out.println("1. Admin");
                System.out.println("2. Student");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 1) {
                    System.out.println("\nAdmin Operations:");
                    System.out.println("1. Add Course");
                    System.out.println("2. Remove Course");
                    System.out.println("3. View Courses");
                    System.out.print("Enter your choice: ");
                    int adminChoice = Integer.parseInt(scanner.nextLine());

                    if (adminChoice == 1) {
                        System.out.print("Enter course ID: ");
                        String courseName = scanner.nextLine();
                        System.out.print("Enter course Name: ");
                        String courseId = scanner.nextLine();
                        admin.addCourse(new Course(courseId, courseName));
                    } else if (adminChoice == 2) {
                        System.out.print("Enter course ID to remove: ");
                        String courseId = scanner.nextLine();
                        admin.removeCourse(courseId);
                    } else if (adminChoice == 3) {
                        admin.viewCourse();
                    } else {
                        System.out.println("Invalid choice!");
                    }

                } else if (choice == 2) {
                    // Student operations
                    System.out.print("Enter your name: ");
                    String studentName = scanner.nextLine();
                    Student student = new Student(studentName,connection);

                    while (true) {
                        System.out.println("\nStudent Operations:");
                        System.out.println("1. Enroll in Course");
                        System.out.println("2. View Enrolled Courses");
                        System.out.println("3. Go Back");
                        System.out.print("Enter your choice: ");
                        int studentChoice = Integer.parseInt(scanner.nextLine());

                        if (studentChoice == 1) {
                            System.out.print("Enter course ID to enroll: ");
                            String courseId = scanner.nextLine();
                            student.enrollCourse(courseId, studentName);
                        } else if (studentChoice == 2) {
                            student.viewEnrolledCourses(studentName);
                        } else if (studentChoice == 3) {
                            break;
                        } else {
                            System.out.println("Invalid choice!");
                        }
                    }

                } else if (choice == 3) {
                    DB.closeConnection(); // Close the database connection
                    break;
                } else {
                    System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeConnection(); // Ensure that the database connection is closed in case of an exception.
            scanner.close();
        }
    }
}

package CourseEnrollment;

public class Course {
    private String courseName;
    private String courseId;
    

    public Course(String courseName, String courseId) {
        this.courseName = courseName;
        this.courseId = courseId;
        
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseId() {
        return courseId;
    }
}

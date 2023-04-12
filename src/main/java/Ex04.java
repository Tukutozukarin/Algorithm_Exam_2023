import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Ex04 {


    public static class Program {
        public String programName;
        public ArrayList<Course> courses;

        public ArrayList<Course> getCourses() {
            return courses;
        }

    }

    public static class Course {
        public String courseName;
        public String courseCode;
        public HashMap<Integer, Student> students;

    }

    public static class Student {
        public String firstName;
        public String lastName;
        public Integer studentId;
        public HashMap<String, Integer> grades;

    public Student(String firstName, String lastName, Integer studentId,
                        HashMap<String, Integer> grades) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.studentId = studentId;
            this.grades = grades;
        }
    }


    public static ArrayList<String> ex04(Program program) {
        return new ArrayList<>(program.getCourses().stream()
                .map(c -> c.courseName + " - " +
                        c.students.values().stream()
                                .map(s -> s.grades.values().stream()
                                        .collect(Collectors.averagingInt(g -> g)) )
                                .collect(Collectors.averagingDouble(avg -> avg))
                )
                .collect(Collectors.toList()) );
    }

}

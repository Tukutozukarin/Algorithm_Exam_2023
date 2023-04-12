import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.AssertJUnit.assertEquals;

public class Ex04Test {
    @Test
    public void testStudent() {

        // Ex04.Program studentsTest = new Ex04.Program();

        var studentsTest = new HashMap<Integer, Ex04.Student>();
        var studentsTest2 = new HashMap<Integer, Ex04.Student>();

        var grades1 = new HashMap<String, Integer>();
        grades1.put("grade1", 2);
        grades1.put("grade2", 3);
        grades1.put("grade3", 4);
        Ex04.Student s1 = new Ex04.Student("Harald", "Hadrada", 1066, grades1);
        studentsTest.put(s1.studentId, s1);

        var grades2 = new HashMap<String, Integer>();
        grades2.put("grade1", 4);
        grades2.put("grade2", 5);
        grades2.put("grade3", 3);
        Ex04.Student s2 = new Ex04.Student("Nicolas", "Davout", 1823, grades2);
        studentsTest.put(s2.studentId, s2);

        var grades3 = new HashMap<String, Integer>();
        grades3.put("grades1",5);
        grades3.put("grades2",3);
        grades3.put("grades3",4);
        Ex04.Student s3 = new Ex04.Student("Nikephoros", "Phokas", 969, grades3);
        studentsTest2.put(s3.studentId, s3);

        var grades4 = new HashMap<String, Integer>();
        grades4.put("grades1",4);
        grades4.put("grades2",5);
        grades4.put("grades3",3);
        Ex04.Student s4 = new Ex04.Student("Nicolas", "Davout", 1823, grades4);
        studentsTest2.put(s4.studentId, s4);


        var c1 = new Ex04.Course();
        c1.courseName = "PG4200";
        c1.students = studentsTest;

        var c2 = new Ex04.Course();
        c2.courseName = "PGR112";
        c2.students = studentsTest2;

        ArrayList<Ex04.Course> progCourse = new ArrayList<>();
        progCourse.add(c1);
        progCourse.add(c2);

        var prog = new Ex04.Program();
        prog.programName = "PG4200 - ";
        prog.courses = progCourse;

        var prog2 = new Ex04.Program();
        prog2.programName = "PGR112 - ";
        prog2.courses = progCourse;

        var results = Ex04.ex04(prog2);


        System.out.println(results);




        //assertEquals(String.valueOf(3), avg, 0.01);
        assertEquals(results, 3.5);

    }

    public Ex04.Student generateStudent(Double grade, String name) {
        var grades1 = new HashMap<String, Integer>();
        grades1.put("grade1", 2);
        grades1.put("grade2", 6);
        grades1.put("grade3", 3);
        Ex04.Student s1 = new Ex04.Student("Harald", "Hadrada", 1066, grades1);


        return s1;

    }


}

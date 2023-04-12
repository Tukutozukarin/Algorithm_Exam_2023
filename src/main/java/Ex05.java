import java.util.HashMap;
import java.util.Map;

// Inspired and changed the code to fit the exercise
// https://github.com/bogdanmarculescu/algorithms/blob/master/solutions/src/main/java/org/pg4200/sol11/ArchiveCompressor.java
public class Ex05 {

    static HashMap<Integer, String> months = new HashMap<>();

    enum Step {
        CourseCodeLetters, CourseCodeNumbers, StudentCode, DateYear, DateMonth, DateDay, FileSkip, FileExtension
    }

    static {
        months.put(1, "JAN");
        months.put(2, "FEB");
        months.put(3, "MAR");
        months.put(4, "APR");
        months.put(5, "MAY");
        months.put(6, "JUN");
        months.put(7, "JUL");
        months.put(8, "AUG");
        months.put(9, "SEP");
        months.put(10, "OCT");
        months.put(11, "NOV");
        months.put(12, "DEC");

    }

    public static int month(String mon) {
        int result = -1;
        for(Map.Entry<Integer, String> e : months.entrySet()) {
            if(e.getValue().equalsIgnoreCase(mon)) {
                result = e.getKey();
            }
        }
        return result;
    }

    public String month(int i) {
        return months.get(i);
    }


   public static byte[] compress(String data) {

       BitWriter writer = new BitWriter();
       Step step = Step.CourseCodeLetters;

       String courseCodeN = "";
       String studentCode = "";
       String dateYear = "";
       String month = "";
       String day = "";

       // Compression based of the following inputs
       for(int i=0; i<data.length(); i++) {
           char c = data.charAt(i);

           if (step == Step.CourseCodeLetters && c >= 'A' && c <= 'Z') {
               // write course letters (will come here two times)
               writer.write( c - 'A', 5);
           }
           else if ( (step == Step.CourseCodeLetters || step == Step.CourseCodeNumbers) && c >= '0' && c <= '9') {
               step = Step.CourseCodeNumbers;
               courseCodeN += c;
           }
           else if (step == Step.CourseCodeNumbers && c == '-') {
               step = Step.StudentCode;
               // write courseCodeN
               writer.write(Integer.parseInt(courseCodeN), 14);
               courseCodeN = "";
           }
           else if (step == Step.StudentCode && c >= '0' && c <= '9') {
               studentCode += c;
           }
           else if (step == Step.StudentCode && c == '-') {
               step = Step.DateYear;
               // write studentCode
               writer.write(Integer.parseInt(studentCode), 20);
               studentCode = "";
           }
           else if (step == Step.DateYear && c >= '0' && c <= '9') {
               dateYear += c;
           }
           else if (step == Step.DateYear && c == '-') {
               step = Step.DateMonth;
               // write year
               writer.write(Integer.parseInt(dateYear)-2000, 5); // supports years 2000 - 2031
               dateYear = "";
           }
           else if (step == Step.DateMonth && c >= 'A' && c <= 'Z') {
               month += c;
           }
           else if (step == Step.DateMonth && c == '-') {
                step = Step.DateDay;
                // write month
               var m = month(month);
               writer.write(m, 4);
               month = "";
           }
           else if (step == Step.DateDay && c >= '0' && c <= '9') {
                day += c;
           }

           else if (step == Step.DateDay && c == '.') {
               step = Step.FileSkip;
               // write day
               writer.write(Integer.parseInt(day), 5);
               day = "";
           }
           else if (step == Step.FileSkip && c == '.') {
                step = Step.FileExtension;
                i++; // skip next char, so we dont get confused on last f/p from pdf/zip
           }
           else if (step == Step.FileExtension && (c == 'f' || c == 'p') ) {
               // write filetype
                writer.write(c == 'f'); // true = pdf, false = zip
               step = Step.CourseCodeLetters;
           }

       }

       return writer.extract();
   }

   public static String decompress(byte[] data) {
       BitReader reader = new BitReader(data);
       String result = "";
       int entries = data.length / 8;

       for (int i=0; i < entries; i++) {
           // read course the two course letters
           String code = ""+(char)(reader.readInt(5) + 'A');
           code += (char)(reader.readInt(5) + 'A');
           // read course code
           code += reader.readInt(14);
           if (code.length() == 3) {
               code = "0" + code;
           }
           result += code;
           result += "-";
           // unique id
           String student = ""+reader.readInt(20);
           result += student;
           result += "-";
           // year
           String year = ""+(reader.readInt(5) + 2000);
           result += year;
           result += "-";

           // Get the months format from HashMap
           int month = reader.readInt(4);
           result += months.get(month);
           result += "-";
           String sMonth = ""+month;
           if (sMonth.length() == 1) {
               sMonth = "0" + sMonth;
           }

           // read day
           String day = ""+reader.readInt(5);
           if (day.length() == 1) {
               day = "0" + day;
           }
           result += day;
           // check filetype and use the result of
           // the data from code, date and student
           result += ". File: exam-"+code+"-"+year+sMonth+day+"-"+student;
           if(reader.readBoolean() == true) {
               result += ".pdf";
           }
           else {
               result += ".zip";
           }
           result += ";";

       }

       return result;
   }

}

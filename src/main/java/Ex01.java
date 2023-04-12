import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Ex01 {

    File rootFolder;

    public void setRootFolder(File rootFolder) {
        this.rootFolder = rootFolder;
    }


    public ArrayList<String> regexPartA(String input){

        if (rootFolder == null) {
            throw new RuntimeException("rootFolder must be set");
        }

        String matcher = "(?i).*(/lessons|exercises|solutions)/src/test/.*"+input + ".*(\\.java|\\.cpp|\\.kt)$";

        ArrayList<String> matches = new ArrayList<>();
        matchFiles(matches, matcher, rootFolder);

        return matches;
    }

    private void matchFiles(List<String> matches, String matcher, File parent) {
        if (parent.isDirectory()) {
            for (var fileOrFolder : parent.listFiles()) {
                matchFiles(matches, matcher, fileOrFolder);
            }
        }
        else {
            var path = parent.getAbsolutePath().replace(rootFolder.getAbsolutePath(), "");
            if (path.matches(matcher)) {
                matches.add(path);
            }
        }
    }

    public String regexPartB() {
        return "(?i)^@[a-z]{1,}:(?=.*((pg)[a-z]{0,1}[0-9]{3,4})|(.*programming)|(.*programmering))(?=(.*eksamen)|(.*exam))[^?]*\\?\\s*$";
    }
}

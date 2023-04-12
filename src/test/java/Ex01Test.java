
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class Ex01Test {

    @Test
    public void RegExB() {
        //String reg = "(?i).*/src/test/.*(les10|sol10).*(\\.java|\\.cpp|\\.kt)$";

        Ex01 ex = new Ex01();
        ex.rootFolder = new File("/Users/benjaminle/Documents/programming/algorithm_v2");

        ArrayList<String> matches = ex.regexPartA("txt");

        for (var found : matches) {
            System.out.println(found);
        }




    }

    @Test
    public void RegExC() {
        String reg = "(?i)^@[a-z]{1,}:(?=.*((pg)[a-z]{0,1}[0-9]{3,4})|(.*programming)|(.*programmering))(?=(.*eksamen)|(.*exam))[^?]*\\?\\s*$";


        assertTrue("@Harald: Is the PG4200 re-exam coming soon?".matches(reg));
        assertTrue("@Bogdan: Has everyone done the sample exercises for the PGR112 exam that are on the repo?".matches(reg));
        assertTrue("@Sven: The exam for PG4200 has been set already?".matches(reg));
        assertTrue("@Harald: Does the programmering eksamen end on 2023-02-25?".matches(reg));

        assertFalse("@Harald Where can I find more exercises for the programmering eksamen?".matches(reg));
        assertFalse("@Hardrada: Where can I find more ships to invade England?".matches(reg));
        assertFalse("@Napoleon: It is too late to learn what programmering means! –".matches(reg));
        assertFalse("@Armfeld: Should I start my travel to get to Trondheim by 1718-12-31, for the HIS4230 exam?".matches(reg));
        assertFalse("@Harald: I am ready for any programming exam!".matches(reg));

    }
}

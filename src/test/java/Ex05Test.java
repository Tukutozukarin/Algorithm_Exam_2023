import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


    public class Ex05Test {
        /**
         * Created by arcuri82 on 30-Oct-17.
         */



        /**
         *   Having a compression "c" and a decompression "d",
         *   then, whatever the input "x" is, the following should
         *   always be true
         *
         *   x = d(c(x))
         *
         *   ie, if we compress and then decompress, we should get
         *   back the original input
         */
        private void checkPreserveInformation(String dna ){

            byte[] compressed = Ex05.compress(dna);

            String decompressed = Ex05.decompress(compressed);

            assertEquals(dna, decompressed);


        }

        @Test
        public void testPreserveInformation(){
            checkPreserveInformation("PG4200-987456-2022-JUN-06. File: exam-PG4200-20220606-987456.pdf;");
        }

        @Test
        public void testDecreaseSize(){

            String dna = "PG4200-987456-2022-JUN-06. File: exam-PG4200-20220606-987456.pdf;";
            checkPreserveInformation(dna);

            int nonCompressedSize = dna.getBytes(StandardCharsets.UTF_8).length;

            byte[] compressed = Ex05.compress(dna);

            assertTrue(compressed.length < nonCompressedSize);

            double ratio = (double) compressed.length / (double) nonCompressedSize;
        /*
            we get a good compression, which gets better for longer text,
            as the overhead of storing the trie has less impact
         */
            System.out.println(ratio);
            assertTrue(ratio < 0.33);
        }


    }



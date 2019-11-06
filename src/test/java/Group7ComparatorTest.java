import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.RepeatedTest;


public class Group7ComparatorTest {

    public static int compareFracGroup7(String s1, String s2) {
        Group7.Data testItem,testItem2;
        Group7.FracComparator comparator=new Group7.FracComparator();
        testItem=new Group7.Data(s1); testItem2=new Group7.Data(s2);
        return comparator.compare(testItem,testItem2);
    }
    public static int compareFracGroup0(String s1, String s2) {
        Group0.Data testItem,testItem2;
        Group0.FracComparator comparator=new Group0.FracComparator();
        testItem=new Group0.Data(s1); testItem2=new Group0.Data(s2);
        return comparator.compare(testItem,testItem2);
    }

    public void testCompare(int numElements) {
        String[] data = DataGenerator2019.generateDataArray(numElements);
        for(String s1 : data) {
            for(String s2 : data) {
                assertEquals(compareFracGroup0(s1, s2), compareFracGroup7(s1, s2));
            }
        }
    }

    @RepeatedTest(value = 1000, name = RepeatedTest.LONG_DISPLAY_NAME) 
    public void testCompare100() {
        testCompare(100);
    }

    @Test public void testCompare1000() {
        testCompare(1000);
    }
    
    @Test public void testFracComparator1() {
        // Two pure fractions in lowest form that are unequal
        assertTrue(compareFracGroup7("3/4","2/3") > 0);
        assertTrue(compareFracGroup7("2/3","3/4") < 0);
    }
    
    @Test public void testFracComparator2() {
        // Two pure fractions, unequal, one in lowest form
        assertTrue(compareFracGroup7("3/4","4/6") > 0);
        assertTrue(compareFracGroup7("4/6","3/4") < 0);
    }
    
    @Test public void testFracComparator3() {
        /// Two pure fractions with equal values
        assertTrue(compareFracGroup7("3/4","6/8") < 0);
        assertTrue(compareFracGroup7("6/8","3/4") > 0);
    }

    @Test public void testFracComparator4() {
        // One pure fraction and one decimal with equal values
        assertTrue(compareFracGroup7("7/4","1.75") > 0);
        assertTrue(compareFracGroup7("1.75","7/4") < 0);
    }

    @Test public void testFracComparator5() {
        // One pure fraction and one decimal with unequal values
        assertTrue(compareFracGroup7("7/4","1.74") > 0);
        assertTrue(compareFracGroup7("1.74","7/4") < 0);
    }

    @Test public void testFracComparator6() {
        // Two unequal decimals
        assertTrue(compareFracGroup7("1.74","1.75") < 0);
        assertTrue(compareFracGroup7("1.75","1.74") > 0);
    }

    @Test public void testFracComparator7() {
        // One mixed fraction, one decimal with equal values 
        assertTrue(compareFracGroup7("1 3/4","1.75") > 0);
        assertTrue(compareFracGroup7("1.75","1 3/4") < 0);
    }

    @Test public void testFracComparator8() {
        // Pure fraction to mixed fraction with same value
        assertTrue(compareFracGroup7("7/4","1 3/4") > 0);
        assertTrue(compareFracGroup7("1 3/4","7/4") < 0);
    }

    @Test public void testFracComparator9() {
        // Mixed fraction to mixed fraction.  Same whole number
        assertTrue(compareFracGroup7("1 3/4","1 6/8") < 0);
        assertTrue(compareFracGroup7("1 6/8","1 3/4") > 0);
    }

    @Test public void testFracComparator10() {
        // Two mixed factions with equal values
        assertTrue(compareFracGroup7("1 7/4","2 3/4") < 0);
        assertTrue(compareFracGroup7("2 3/4","1 7/4") > 0);
    }

    @Test public void testFracComparator11() {
        // Mixed fraction to unequal pure fraction
        assertTrue(compareFracGroup7("1 7/4","13/4") < 0);
        assertTrue(compareFracGroup7("13/4","1 7/4") > 0);
    }

    @Test public void testFracComparator12() {
        assertTrue(compareFracGroup7("6346791669/6375951545","4807334027/4829420976") < 0);
        assertTrue(compareFracGroup7("4807334027/4829420976","6346791669/6375951545") > 0);
    }

    // Sanity check Group 0 comparator
    @Test public void testGroup0FracComparator() {
        assertTrue(compareFracGroup0("3/4","2/3") > 0);
        assertTrue(compareFracGroup0("2/3","3/4") < 0);

        // Two pure fractions, unequal, one in lowest form
        assertTrue(compareFracGroup0("3/4","4/6") > 0);
        assertTrue(compareFracGroup0("4/6","3/4") < 0);

        /// Two pure fractions with equal values
        assertTrue(compareFracGroup0("3/4","6/8") < 0);
        assertTrue(compareFracGroup0("6/8","3/4") > 0);

        // One pure fraction and one decimal with equal values
        assertTrue(compareFracGroup0("7/4","1.75") > 0);
        assertTrue(compareFracGroup0("1.75","7/4") < 0);

        // One pure fraction and one decimal with unequal values
        assertTrue(compareFracGroup0("7/4","1.74") > 0);
        assertTrue(compareFracGroup0("1.74","7/4") < 0);

        // Two unequal decimals
        assertTrue(compareFracGroup0("1.74","1.75") < 0);
        assertTrue(compareFracGroup0("1.75","1.74") > 0);

        // One mixed fraction, one decimal with equal values 
        assertTrue(compareFracGroup0("1 3/4","1.75") > 0);
        assertTrue(compareFracGroup0("1.75","1 3/4") < 0);

        // Pure fraction to mixed fraction with same value
        assertTrue(compareFracGroup0("7/4","1 3/4") > 0);
        assertTrue(compareFracGroup0("1 3/4","7/4") < 0);

        // Mixed fraction to mixed fraction.  Same whole number
        assertTrue(compareFracGroup0("1 3/4","1 6/8") < 0);
        assertTrue(compareFracGroup0("1 6/8","1 3/4") > 0);

        // Two mixed factions with equal values
        assertTrue(compareFracGroup0("1 7/4","2 3/4") < 0);
        assertTrue(compareFracGroup0("2 3/4","1 7/4") > 0);

        // Mixed fraction to unequal pure fraction
        assertTrue(compareFracGroup0("1 7/4","13/4") < 0);
        assertTrue(compareFracGroup0("13/4","1 7/4") > 0);

        assertTrue(compareFracGroup0("6346791669/6375951545","4807334027/4829420976") < 0);
        assertTrue(compareFracGroup0("4807334027/4829420976","6346791669/6375951545") > 0);

    }
    // @Test public void testFracComparatorNegative1() {
    //     assertTrue(compareFracGroup7("-5394608174567520344/-182296925182177504","9903601889/314275584") > 0);
    //     assertTrue(compareFracGroup7("9903601889/314275584","-5394608174567520344/-182296925182177504") < 0);
    // }

    // @Test public void testFracComparatorNegative2() {
    //     assertTrue(compareFracGroup7("-1.74","-1.75") > 0);
    //     assertTrue(compareFracGroup7("-1.75","-1.74") < 0);
    // }
    

    public static void print_test(String s1,String s2){
        Group7.Data testItem,testItem2;
        Group7.FracComparator comparator=new Group7.FracComparator();
        testItem=new Group7.Data(s1); testItem2=new Group7.Data(s2);
        System.out.println("Compare: "+s1+" to "+s2+": ");
        System.out.println("Result="+comparator.compare(testItem,testItem2));
        System.out.println("Compare: "+s2+" to "+s1+": ");
        System.out.println("Result="+comparator.compare(testItem2,testItem));
        System.out.println("---");
    }
    
    public static void test_Data() {
        
        String s1,s2;
        
        print_test("3/4","2/3");      // Two pure fractions in lowest form that are unequal
        print_test("3/4","4/6");      // Two pure fractions, unequal, one in lowest form
        print_test("3/4","6/8");      // Two pure fractions with equal values
        print_test("7/4","1.75");     // One pure fraction and one decimal with equal values
        print_test("7/4","1.74");     // One pure fraction and one decimal with unequal values
        print_test("1.74","1.75");    // Two unequal decimals
        print_test("1 3/4","1.75");   // One mixed fraction, one decimal with equal valeus 
        
        print_test("7/4","1 3/4");    //Pure fraction to mixed fraction with same value
        print_test("1 3/4","1 6/8");  //Mixed fraction to mixed fraction.  Same whole number
        print_test("1 7/4","2 3/4"); // Two mixed factions with equal values
        print_test("1 7/4","13/4");  // Mixed fraction to unequal pure fraction
        System.out.println("---");
    }
}

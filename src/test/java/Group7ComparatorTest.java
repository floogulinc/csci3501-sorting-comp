import org.junit.Test;


import static org.junit.Assert.*;

public class Group7ComparatorTest {
    @Test public void testAppHasAGreeting() {
        Group7 classUnderTest = new Group7();
        //assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }

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

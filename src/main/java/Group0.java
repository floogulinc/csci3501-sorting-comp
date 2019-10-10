import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import java.math.BigInteger;

public class Group0 {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException,IOException {
        // testing the comparator:
        //Data.test_Data(); // This MUST be commented out for your submission to the competition!
        
        if (args.length < 2) {
            System.out.println("Please run with two command line arguments: input and output file names");
            System.exit(0);
        }
        
        String inputFileName = args[0];
        String outFileName = args[1];
        
        // read as strings
        String [] data = readData(inputFileName);
        String [] toSort = data.clone();
        Data [] sorted = sort(toSort); // Warm up the VM
        toSort = data.clone();
        Thread.sleep(10); //to let other things finish before timing; adds stability of runs
        
        long start = System.currentTimeMillis(); // Begin the timing
        sorted = sort(toSort);
        long end = System.currentTimeMillis();   // End the timing
        
        System.out.println(end - start);         // Report the results
        writeOutResult(sorted, outFileName);
    }
    
    // YOUR SORTING METHOD GOES HERE.
    // You may call other methods and use other classes.
    // You may ALSO modify the methods, innner classes, etc, of Data[]
    // But not in way that transfers information from the warmup sort to the timed sort.
    // Note: you may change the return type of the method.
    // You would need to provide your own function that prints your sorted array to
    // a file in the exact same format that my program outputs
    public static Data[] sort(String[] toSort) {
        Data[] toSortData = new Data[toSort.length];
        for (int i = 0; i < toSort.length; ++i) {
            toSortData[i] = new Data(toSort[i]);
        }
        Arrays.sort(toSortData, new FracComparator());
        return toSortData;
    }
    
    private static void printArray(String[] Arr, int n) {
        for(int i = 0; i < n; i++) {
            System.out.println(Arr[i]);
        }
    }
    private static String[] readData(String inFile) throws FileNotFoundException,IOException {
        List<String> input = Files.readAllLines(Paths.get(inFile));
        // the string array is passed just so that the correct type can be created
        return input.toArray(new String[0]);
    }
    
    private static void writeOutResult(Data[] sorted, String outputFilename) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(outputFilename);
        for (Data s : sorted) {
            out.println(s.exprLine);
        }
        out.close();
    }
    
    static class FracComparator implements Comparator<Data> {
        private static final BigInteger zero= new BigInteger("0"); 
        
        @Override
        public int compare(Data s1, Data s2) {
            int cmp=0;
            
            cmp=(s1.bigNumerator.multiply(s2.denominator)).compareTo(s2.bigNumerator.multiply(s1.denominator));  // Compare a/b to c/d by finding ad-bc
            
            if(cmp!=0){return(cmp);}         // Value of s1 != value of s2
            if(s1.type<s2.type){return(-1);} // Same value, type of s1 "comes before" s2			
            if(s1.type>s2.type){return(1);}  // Same value, type of s1 "comes after" s2			
            
            switch(s1.type){                 // s1.type==s2.type
                case 0:                  // Same value both are decimal expressions they must be equal
                return(0);
                case 1:                  // Mixed fractions
                cmp=(s1.whole).compareTo(s2.whole); // Compare whole numbers
                if(cmp!=0){return(cmp);} // Sort off whole number
                // NOTE:  There is no return or break in this case.  This is on purpose
                case 2:                  // Pure fraction or equal whole numbers
                cmp=(s1.numerator).compareTo(s2.numerator); // Compare numerators of fraction portion
                if(cmp!=0){return(cmp);} // Sort off numerator
                return(0);
            }			
            System.err.println("DANGER.  Bad input parsed");
            return(0); // This should never be reached
        }
    }
    static class Data {             
        public BigInteger numerator;    // Arbitrary Precision for Numerator
        public BigInteger denominator;  // Arbitrary Precision for Denominator
        public BigInteger whole;        // Arbitrary Prection for whole number (not needed... but makes the Big Integer arithmetic easier)
        public int type=-1;             // -1 unspecified, 0 decimal, 1 mixed, 2 pure
        public BigInteger bigNumerator; // The value of all expressions can be internally represented as bigNumerator/denominator
        
        public String exprLine;         // The original string-- useful to outputting at the end.
        
        public Data(String line){
            int posSlash=-1;       // Assume no slash
            int posSpace=-1;       // No space
            int posDot=-1;         // And no period
            String powerTen="1";   // Decimals need a denominator with a power of 10 but we represent as a string.  This is the leading 1
            exprLine = new String(line); // Make a copy of the string
            // NOTE:  If the input is well-formed only one of the following three situations can arise
            //        to ensure mutual exclusion we could use nested if-else... but I think this is easier to read
            //        
            posSpace=exprLine.indexOf(" "); // Find the space (if any)
            posSlash=exprLine.indexOf("/"); // Find the slash (if any)
            if(posSlash!=-1){ // We found a slash so expression is either mixed or pure
                if(posSpace!=-1){ // We found a slash *and* a space
                    type=1;   // Set to type mixed
                    whole=new BigInteger(exprLine.substring(0,posSpace));// Get everything before the space
                } else {         // We found a slash *but* no space
                    type=2;  //Set to type pure fraction
                    whole=new BigInteger("0"); // Not really defined for pure fractions... but simplifies bigNumerator calculation below
                }
                numerator=new BigInteger(exprLine.substring(posSpace+1,posSlash));// Get everything before the slash and after the space (if any)
                denominator=new BigInteger(exprLine.substring(posSlash+1));       // Get everything after the slash
            }
            posDot=exprLine.indexOf("."); //Find the period (if any)
            if(posDot!=-1){  // We found a period
                type=0;  // Set to type Decimal Expression
                whole=new BigInteger(exprLine.substring(0,posDot));              // Get everything before the decimal point
                numerator=new BigInteger(exprLine.substring(posDot+1));          // Get everything after the decimal point
                for(int i=1;i<exprLine.length()-posDot;i++){                     //Build the power of 10
                    powerTen+="0";
                }
                denominator=new BigInteger(powerTen); // Number of places in the decimal expression				
            }
            bigNumerator = (whole.multiply(denominator)).add(numerator);// Make the big numerator
        }
        
        public static void print_test(String s1,String s2){
            Data testItem,testItem2;
            FracComparator comparator=new FracComparator();
            testItem=new Data(s1); testItem2=new Data(s2);
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
}

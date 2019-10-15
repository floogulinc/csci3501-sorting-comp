import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.math.BigInteger;

public class Group7 {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException,IOException {
        

        Arrays.fill(FracComparator.count, 0);
        //System.out.println(Runtime.getRuntime().availableProcessors());

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
        
        System.out.println(end - start);
        
        for(int i : FracComparator.count) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Report the results
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
        
        return Arrays.stream(toSort).map(Data::new).sorted(new FracComparator()).toArray(Data[]::new);

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
    
    public static class FracComparator implements Comparator<Data> {
        //private static final BigInteger zero= new BigInteger("0"); 


        public static int count[] = new int[10];
        

        @Override
        public int compare(Data s1, Data s2) {

            // This is much cheaper than the full precision check so we do it first

            double approxDiff = s1.approx - s2.approx;

            if(approxDiff > 0.00000001) { // About 1/2 of comparisons
                count[0]++;
                return 1;
            } else if (approxDiff < -0.00000001) { // About 1/2 of comparisons
                count[1]++;
                return -1;
            }

            

            if(s1.exprLine.equals(s2.exprLine)) {
                count[2]++;
                return 0;
            }
            
            int cmp = (s1.bigNumerator.multiply(s2.denominator)).compareTo(s2.bigNumerator.multiply(s1.denominator));  // Compare a/b to c/d by finding ad-bc
        
            
            if(cmp!=0) { // True for 99.96% of comparisons from before approx added, now very few
                count[3]++;
                return(cmp);
            }

            

            // Only 0.03% of compares do this section, rest are unequal values

            if(s1.type<s2.type) {
                count[4]++;
                return(-1); // Same value, type of s1 "comes before" s2	
            }
                
            		
            if(s1.type>s2.type) {
                count[5]++;
                return(1); // Same value, type of s1 "comes after" s2	
            }
                	
                
            
            
            switch(s1.type){                 // s1.type==s2.type
                case 0:                  // Same value both are decimal expressions they must be equal
                    count[6]++;
                    return(0);
                case 1:                  // Mixed fractions
                    count[7]++;
                    cmp=(s1.whole).compareTo(s2.whole); // Compare whole numbers
                    if(cmp!=0){return(cmp);} // Sort off whole number
                    // NOTE:  There is no return or break in this case.  This is on purpose
                case 2:                  // Pure fraction or equal whole numbers
                    count[8]++;
                    return (s1.numerator).compareTo(s2.numerator); // Compare numerators of fraction portion
                    //if(cmp!=0){return(cmp);} // Sort off numerator
                    //return(0);
            }			
            System.err.println("DANGER.  Bad input parsed");
            count[9]++;
            return(0); // This should never be reached
        }
    }
    public static class Data {             
        public BigInteger numerator;    // Arbitrary Precision for Numerator
        public BigInteger denominator;  // Arbitrary Precision for Denominator
        public BigInteger whole;        // Arbitrary Prection for whole number (not needed... but makes the Big Integer arithmetic easier)
        public int type=-1;             // -1 unspecified, 0 decimal, 1 mixed, 2 pure
        public BigInteger bigNumerator; // The value of all expressions can be internally represented as bigNumerator/denominator
        
        public String exprLine;         // The original string-- useful to outputting at the end.

        public double approx;
        
        public Data(String line){
            int posSlash=-1;       // Assume no slash
            int posSpace=-1;       // No space
            int posDot=-1;         // And no period
            exprLine = line; // Make a copy of the string WHY!!
            // NOTE:  If the input is well-formed only one of the following three situations can arise
            //        to ensure mutual exclusion we could use nested if-else... but I think this is easier to read
            //        

            
            posSlash=exprLine.indexOf("/"); // Find the slash (if any)
            if(posSlash!=-1){ // We found a slash so expression is either mixed or pure
                posSpace=exprLine.indexOf(" "); // Find the space (if any)
                if(posSpace!=-1){ // We found a slash *and* a space
                    type=1;   // Set to type mixed
                    whole=new BigInteger(exprLine.substring(0,posSpace));// Get everything before the space
                    numerator=new BigInteger(exprLine.substring(posSpace+1,posSlash));// Get everything before the slash and after the space (if any)
                } else {         // We found a slash *but* no space
                    type=2;  //Set to type pure fraction
                    numerator=new BigInteger(exprLine.substring(0,posSlash));// Get everything before the slash
                    whole = BigInteger.ZERO; // Not really defined for pure fractions... but simplifies bigNumerator calculation below
                }
                denominator=new BigInteger(exprLine.substring(posSlash+1));       // Get everything after the slash
            } else {
                posDot=exprLine.indexOf("."); //Find the period (if any)
                type=0;  // Set to type Decimal Expression
                whole=new BigInteger(exprLine.substring(0,posDot));              // Get everything before the decimal point
                numerator=new BigInteger(exprLine.substring(posDot+1));       // Get everything after the decimal point
                //for(int i=1;i<exprLine.length()-posDot;i++){                     //Build the power of 10
                //    powerTen+="0";
                //}
                denominator = BigInteger.TEN.pow(exprLine.length()-posDot-1);	// Number of places in the decimal expression		
            }
            
            bigNumerator = (whole.multiply(denominator)).add(numerator);// Make the big numerator
            //approx = bigNumerator.longValue() / denominator.longValue();
            //approx = bigNumerator.divide(denominator).doubleValue();
            approx = bigNumerator.doubleValue() / denominator.doubleValue();

        }
        
        
        
    }
}

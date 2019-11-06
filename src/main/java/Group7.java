import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class Group7 {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException,IOException {

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
        
        // Get the array of strings as a stream, map it to the Data class, 
        // sort it with FracComparator, and put the results into a sorted array
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

        @Override
        public int compare(Data s1, Data s2) {

            // Calculate difference in the approximate (double) values of the two numbers
            // This is much cheaper than the full precision check so we do it first

            double approxDiff = s1.approx - s2.approx;

            // If it is within a reasonable margin we can assume they are different values 
            // and return the appropriate comparison
            if(approxDiff < -0.00000000000001) { // About 50% of comparisons
                return -1;
            } else if (approxDiff > 0.00000000000001) { // About  50% of comparisons
                return 1;
            }


            // Catches exactly equal values, mostly decimal
            if(s1.exprLine.equals(s2.exprLine)) { // About 0.5% of comparisons
                return 0;
            }

            // We assume that if the approx values were equal and the strings aren't equal, 
            // they are equal value but different representations, which nearly always is two pure fractions
    
            // If both are pure fractions, compare numerators
            if(s1.type == Data.NumType.PURE && s2.type == Data.NumType.PURE) { //About 0.15% of comparisons
                return Double.compare(s1.numerator, s2.numerator);
            }

            // If both are mixed fractions, compare whole numbers then numerators
            if(s1.type == Data.NumType.MIXED && s2.type == Data.NumType.MIXED) { // Usually no comparisons match this
                int cmp = Double.compare(s1.whole, s2.whole);
                if(cmp != 0) return cmp;
                else return Double.compare(s1.numerator, s2.numerator);
            }

            // Compare type of number (enum compares based on order) and if different, that comparison is returned
            // Usually no comparisons
            int typecmp = s1.type.compareTo(s2.type);
            if(typecmp != 0) {
                return(typecmp);
            }

            
            System.err.println("DANGER.  Bad input parsed");
            return(0); // This should never be reached
        }
    }

    public static class Data {             
        public long numerator;

        public long whole; 

        static enum NumType {
            DECIMAL, MIXED, PURE;
        }

        public NumType type = null; 
        
        public String exprLine; // The original string, useful for outputting at the end

        public double approx; // Approximate double value of the number
        
        public Data(String line){

            exprLine = line;      

            int posSlash = exprLine.indexOf("/"); // Find the slash (if any)
            if(posSlash!=-1){ // We found a slash so expression is either mixed or pure
                int posSpace = exprLine.indexOf(" "); // Find the space (if any)
                if(posSpace!=-1){ // We found a slash *and* a space
                    type = NumType.MIXED;   // Set to type mixed
                    whole = Long.parseLong(exprLine.substring(0,posSpace)); // Get everything before the space
                    numerator = Long.parseLong(exprLine.substring(posSpace+1,posSlash)); // Get everything before the slash and after the space (if any)
                    
                    double den = Double.parseDouble(exprLine.substring(posSlash+1));

                    approx = ((((double) whole) * den) + ((double) numerator)) / den; // Make the approximate (double) value

                } else {         // We found a slash *but* no space
                    type = NumType.PURE;  //Set to type pure fraction
                    numerator = Long.parseLong(exprLine.substring(0,posSlash)); // Get everything before the slash

                    approx = ((double) numerator) / Double.parseDouble(exprLine.substring(posSlash+1)); // Make the approximate (double) value
                }
                 
            } else {
                type = NumType.DECIMAL;  // Set to type Decimal Expression
                approx = Double.parseDouble(exprLine); // Make the approximate (double) value
            }
        } 
    }
}

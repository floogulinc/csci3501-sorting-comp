import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataGenerator2019 {
    /**
    * See Readme for an overview of the data generating process. The result is
    * being written to the file given as the first command line argument.
    *
    * @param args
    *
    *
    *
    *            Author: Peter Dolan (based off work by Elena Machkasova)
    */
    
    // relative probabilities of each length up to maxLength; add up to 1500:
    private static Random rand = new Random();
    
    private static long nextLong(){
        return(1+Math.floorMod(rand.nextLong(),9999999999L));
    }
    
    public static void main(String[] args) throws FileNotFoundException,IOException {
        //makeSet("data1.txt",1000);
        //makeSet("data2.txt",1000);
        //makeSet("data3.txt",10000);
        makeSet("data5.txt",10000000);
        
    }
    
    private static void makeSet(String outFileName, int n) throws FileNotFoundException,IOException {
        PrintWriter out = new PrintWriter(outFileName);
        generateData(n, out);
        
        if (out != null) {
            out.close();
        }
        
    }
    // generate data and print it to a file:
    
    public static void generateData(int numElts, PrintWriter out) {
        
        // Elements are generated one-by-one
        // And written to `out`
        
        int type;
        long numerator=0;
        long denominator=0;
        long whole=0;
        long decimal=0;
        int length=0;
        int m;
        boolean generatePair=false;
        
        String exprLine;
        
        for (int i = 0; i < numElts; ++i) {
            // determine type:
            //		if(i%10==0){System.out.print(".");}
            if(generatePair){
                type=0; //Make another pure fraction
            } else {
                type=rand.nextInt(4); // random int 0,1,2 or 3
            }
            
            switch(type){
                case 0: // Pure Fraction
                if(generatePair){ //numerator and denominator already exist and are valid
                    m=rand.nextInt(99)+2; // 2<=m<99+2=101 so 2 <= m <= 100
                    numerator*=m;
                    denominator*=m;
                    generatePair=false;
                } else {
                    numerator=nextLong();
                    denominator=nextLong();
                }
                if(rand.nextInt(10)==0){ //1 out of 10 chance to generate a "pair" next round
                generatePair=true;
            }
            exprLine=Long.toString(numerator)+"/"+Long.toString(denominator);
            break;
            case 1: // Decimal Expression
            length=rand.nextInt(6);
            if(length==0){
                whole=0;
            } else{
                whole=rand.nextInt(9)+1; // First digit [0,9) -> [1,10) = [1,9]
                for(int j=2;j<=length;j++){
                    m=rand.nextInt(5); //Primitive value [0,4]
                    if(rand.nextInt(3)==0){ // 1 out of 3
                        m=2*m+1; // m is odd from 1 to 9
                    } else { // 2 out of 3 (aka twice as likely)
                        m=2*m; // m is even from 0 to 8
                    }
                    whole=whole*10+m; //add the digit
                }
            }
            length=rand.nextInt(10)+1; // [0,11) -> [1,10]  1<=length<=10
            decimal=0;
            for(int k=1;k<length;k++){ //Generate all but last digit-- note it doesn't matter in what order we add the digits
            m=rand.nextInt(5); //Primitive value [0,4]
            if(rand.nextInt(3)==0){ // 1 out of 3
                m=2*m+1; // m is odd from 1 to 9
            } else { // 2 out of 3 (aka twice as likely)
                m=2*m; // m is even from 0 to 8
            }
            decimal=decimal*10+m; //add the digit
        }
        decimal=10*decimal+rand.nextInt(9)+1; // [0,9)->[1,10)=[1,9] // Add last digit to end
        exprLine=Long.toString(whole)+"."+Long.toString(decimal);
        
        break;
        default: //Mixed Fraction
        numerator=nextLong();
        denominator=nextLong();
        whole=nextLong();
        exprLine=Long.toString(whole)+" "+Long.toString(numerator)+"/"+Long.toString(denominator);
        break;
    }
    
    if (out == null) { // no file specified, so print to standard output
        System.out.println(exprLine);
    } else { // print to the print writer:
        out.println(exprLine);
    }
}
}

public static String[] generateDataArray(int numElts) {
    
    // Elements are generated one-by-one
    // And written to `out`
    
    String[] generated = new String[numElts];
    
    int type;
    long numerator=0;
    long denominator=0;
    long whole=0;
    long decimal=0;
    int length=0;
    int m;
    boolean generatePair=false;
    
    String exprLine;
    
    for (int i = 0; i < numElts; ++i) {
        // determine type:
        //		if(i%10==0){System.out.print(".");}
        if(generatePair){
            type=0; //Make another pure fraction
        } else {
            type=rand.nextInt(4); // random int 0,1,2 or 3
        }
        
        switch(type){
            case 0: // Pure Fraction
            if(generatePair){ //numerator and denominator already exist and are valid
                m=rand.nextInt(99)+2; // 2<=m<99+2=101 so 2 <= m <= 100
                numerator*=m;
                denominator*=m;
                generatePair=false;
            } else {
                numerator=nextLong();
                denominator=nextLong();
            }
            if(rand.nextInt(10)==0){ //1 out of 10 chance to generate a "pair" next round
                generatePair=true;
            }
            exprLine=Long.toString(numerator)+"/"+Long.toString(denominator);
            break;
            case 1: // Decimal Expression
                length=rand.nextInt(6);
                if(length==0){
                    whole=0;
                } else{
                    whole=rand.nextInt(9)+1; // First digit [0,9) -> [1,10) = [1,9]
                    for(int j=2;j<=length;j++){
                        m=rand.nextInt(5); //Primitive value [0,4]
                        if(rand.nextInt(3)==0){ // 1 out of 3
                            m=2*m+1; // m is odd from 1 to 9
                        } else { // 2 out of 3 (aka twice as likely)
                            m=2*m; // m is even from 0 to 8
                        }
                        whole=whole*10+m; //add the digit
                    }
                }
                length=rand.nextInt(10)+1; // [0,11) -> [1,10]  1<=length<=10
                decimal=0;
                for(int k=1;k<length;k++){ //Generate all but last digit-- note it doesn't matter in what order we add the digits
                    m=rand.nextInt(5); //Primitive value [0,4]
                    if(rand.nextInt(3)==0){ // 1 out of 3
                        m=2*m+1; // m is odd from 1 to 9
                    } else { // 2 out of 3 (aka twice as likely)
                        m=2*m; // m is even from 0 to 8
                    }
                    decimal=decimal*10+m; //add the digit
                }
                decimal=10*decimal+rand.nextInt(9)+1; // [0,9)->[1,10)=[1,9] // Add last digit to end
                exprLine=Long.toString(whole)+"."+Long.toString(decimal);
                
                break;
            default: //Mixed Fraction
                numerator=nextLong();
                denominator=nextLong();
                whole=nextLong();
                exprLine=Long.toString(whole)+" "+Long.toString(numerator)+"/"+Long.toString(denominator);
                break;
        }

        if(exprLine.contains("-")) { // Ignore negatives
            i--;
        } else {
            generated[i] = exprLine;
        }
        
    }


    return generated;
}

}

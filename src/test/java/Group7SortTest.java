import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;


public class Group7SortTest {

    public void testSort(int numElements) {
        String[] data = DataGenerator2019.generateDataArray(numElements);
        String[] toSort1 = data.clone();
        String[] toSort2 = data.clone();

        String[] sorted1 = Arrays.stream(Group0.sort(toSort1)).map(x -> x.exprLine).toArray(String[]::new);
        String[] sorted2 = Arrays.stream(Group7.sort(toSort2)).map(x -> x.exprLine).toArray(String[]::new);

        assertEquals(numElements, sorted2.length);
        assertTrue(Arrays.asList(sorted2).containsAll(Arrays.asList(data)));
        assertArrayEquals(sorted1, sorted2);
    }
    @Test public void testSort100() {
        testSort(100);
    }

    @Test public void testSort1000() {
        testSort(1000);
    }

    @Test public void testSort10000() {
        testSort(10000);
    }

    //@Test public void testSort100000() {
    //    testSort(100000);
    //}

    @RepeatedTest(value = 100, name = RepeatedTest.LONG_DISPLAY_NAME) 
    public void testSort10000Multi() {
        testSort(10000);
    }

    @RepeatedTest(value = 1000, name = RepeatedTest.LONG_DISPLAY_NAME) 
    public void testSort1000Multi() {
        testSort(1000);
    }
}
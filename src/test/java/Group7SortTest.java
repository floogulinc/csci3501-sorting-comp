import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;


public class Group7SortTest {
    @Test public void testSort100() {
        String[] data = DataGenerator2019.generateDataArray(100);
        String[] toSort1 = data.clone();
        String[] toSort2 = data.clone();

        String[] sorted1 = Arrays.stream(Group0.sort(toSort1)).map(x -> x.exprLine).toArray(String[]::new);
        String[] sorted2 = Arrays.stream(Group7.sort(toSort2)).map(x -> x.exprLine).toArray(String[]::new);

        assertEquals(100, sorted2.length);
        assertTrue(Arrays.asList(sorted2).containsAll(Arrays.asList(data)));
        assertArrayEquals(sorted1, sorted2);
    }

    @Test public void testSort10000() {
        String[] data = DataGenerator2019.generateDataArray(10000);
        String[] toSort1 = data.clone();
        String[] toSort2 = data.clone();

        String[] sorted1 = Arrays.stream(Group0.sort(toSort1)).map(x -> x.exprLine).toArray(String[]::new);
        String[] sorted2 = Arrays.stream(Group7.sort(toSort2)).map(x -> x.exprLine).toArray(String[]::new);

        assertEquals(10000, sorted2.length);
        assertTrue(Arrays.asList(sorted2).containsAll(Arrays.asList(data)));
        assertArrayEquals(sorted1, sorted2);
    }
}
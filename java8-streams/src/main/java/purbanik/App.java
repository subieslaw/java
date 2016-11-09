package purbanik;

import purbanik.forkjoin.ParseToIntegerTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Hello world!
 */
public class App {

    private ForkJoinPool forkJoinPool = new ForkJoinPool(4);

    public App() {

    }

    public List<Integer> parseToIntegers(List<String> nubmerStrings) {
        return forkJoinPool.invoke(new ParseToIntegerTask(nubmerStrings));
    }

    public static void main(String[] args) {

        List<String> testStrings = generateTestStrings(200);

        App app = new App();
        long start = System.currentTimeMillis();
        List<Integer> integers = app.parseToIntegers(testStrings);
        long end = System.currentTimeMillis();

        System.out.println("Took:" + (end - start));

    }

    private static List<String> generateTestStrings(int limit) {
        List<String> ret = new ArrayList<>(limit);
        for (int i = 0; i < limit; i++) {
            ret.add(String.valueOf(i));
        }
        return ret;
    }
}

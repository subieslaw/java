package purbanik.forkjoin;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.math.IntMath;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by subieslaw on 09.11.2016.
 */
public class ParseToIntegerTask extends RecursiveTask<List<Integer>> {

    private static final int THRESHOLD = 100;

    private final List<String> numbers;

    public ParseToIntegerTask(List<String> numberString) {
        this.numbers = numberString;
    }

    @Override
    protected List<Integer> compute() {
        if (numbers.size() <= THRESHOLD){
            return parseToIntegers();
        }

        System.out.println("Splitting into smaller parts");
        List<List<String>> partition = Lists.partition(numbers, IntMath.divide(numbers.size(), 2, RoundingMode.UP));

        //divide problem
        ParseToIntegerTask left = new ParseToIntegerTask(partition.get(0));
        ParseToIntegerTask right = new ParseToIntegerTask(partition.get(1));

        //fork
        left.fork();
        right.fork();

        ArrayList<Integer> join = Lists.newArrayList(Iterables.concat(left.join(), right.join()));
        return join;
    }

    private List<Integer> parseToIntegers() {
        System.out.println("Calculating directly for size:" + numbers.size());
        List<Integer> ret = new ArrayList<>(numbers.size());
        for (String s : numbers){
            ret.add(Integer.valueOf(s));
        }
        return ret;
    }
}

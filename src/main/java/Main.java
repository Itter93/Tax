import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

public class Main {

    private static int SIZE = 8;
    private static int HIGH = 100;
    private static int LOW = 0;
    private static int SHOPS = 3;

    public static void main(String[] args) throws InterruptedException {

        List<Integer[]> shops = new ArrayList<>();

        for (int i = 0; i < SHOPS; i++) {
            shops.add(new Random().ints(SIZE, LOW, HIGH)
                    .boxed().toArray(Integer[]::new));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        LongAdder longAdder = new LongAdder();

        for (Integer[] shop : shops) {
            IntStream.range(0, shop.length)
                    .forEach(i -> executorService.submit(() -> longAdder.add(shop[i])));
        }

        executorService.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("\nРезультат: " + longAdder.sum());
        executorService.shutdown();


    }
}

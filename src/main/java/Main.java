import com.google.common.math.DoubleMath;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Kohonovsky Alexander
 * @since 7/21/15
 * <p/>
 * Есть поток данных с полями (value: Long, timestamp: Long).
 * Для потока данных надо поминутно (timestamp определяет минуту) в БД формировать
 * максимальное, среднее и минимальное значение.
 */

public class Main {

    public static void main(String[] args) throws IOException {
        Jedis jedis = new Jedis("localhost");
        Scanner scanner = new Scanner(new File("input.txt"));
        Long value, timestamp;
        int lastMinute, currentMinute;
        ArrayList<Long> list = new ArrayList<Long>();

        value = scanner.nextLong();
        timestamp = scanner.nextLong();
        lastMinute = new Date(timestamp).getMinutes();

        while (scanner.hasNextLong()) {
            list.add(value);
            value = scanner.nextLong();
            timestamp = scanner.nextLong();
            currentMinute = new Date(timestamp).getMinutes();

            if (lastMinute != currentMinute) {
                lastMinute = currentMinute;
                Long min = Collections.min(list);
                Long max = Collections.max(list);
                Double average = DoubleMath.mean(list);
                list.clear();

                // update values in Database
                jedis.set("mininum", String.valueOf(min));
                jedis.set("maximum", String.valueOf(max));
                jedis.set("average", String.valueOf(average));
                jedis.flushAll();
                System.out.println("Minimum: " + jedis.get("minimum") +
                        ", Maximum: " + jedis.get("maximum") +
                        ", Average: " + jedis.get("average"));
            }
        }
        jedis.close();
    }

}

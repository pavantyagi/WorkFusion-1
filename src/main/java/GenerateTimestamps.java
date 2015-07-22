import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author Kohonovsky Alexander
 * @since 7/21/15
 */

public class GenerateTimestamps {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
        PrintWriter writer = new PrintWriter("input.txt", "UTF-8");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Random rand = new Random();

        for (int i = 0; i < 1000; i++) {
            calendar.add(Calendar.SECOND, 10);
            long randomNum = rand.nextInt(100);
            writer.write(randomNum + " " + calendar.getTime().getTime() + "\n");
        }

        writer.close();
    }

}

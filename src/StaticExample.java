import java.util.Arrays;
import java.util.Random;

public class StaticExample {
    static int count = 0;
    int test = 5;

    StaticExample() {
        count++;
    }

    public static void main(String[] args) {
        Random randomGenerator = new Random();
        int [] array1 = new int[100];
        for(int i = 0; i != 100; i++){
            array1 [i] = randomGenerator.nextInt(0,100);
        }

        System.out.println(Arrays.toString(array1));


    }

}


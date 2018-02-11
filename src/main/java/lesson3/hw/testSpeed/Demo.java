package lesson3.hw.testSpeed;

public class Demo {
    public static void main(String[] args) {
        Solution solution = new Solution();
        long before = System.currentTimeMillis();


        for (int i = 0; i < 1000; i++) {
            // solution.testSavePerformance();            // 424583 ms == 7,33 minutes
            // solution.testDeleteByIdPerformance();      // 454375 ms == 7,57 minutes
            //  solution.testSelectByIdPerformance();     // 413280 ms == 6,89 minutes
        }

        // solution.testDeletePerformans();  // 3684 ms == 0,06 minutes
        // solution.testSelectPerformance(); // 3421 ms == 0,06 minutes


        long after = System.currentTimeMillis();
        System.out.println("time in millis: " + (after - before));

        //   solution.deleteAllDataInTable();


    }
}

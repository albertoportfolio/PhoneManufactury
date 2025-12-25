import java.util.ArrayList;

public class Factory {

    public static void main(String[] args) {

        int basicQty = Integer.parseInt(args[0]);
        int proQty = Integer.parseInt(args[1]);
        int config = Integer.parseInt(args[2]);

        int basicCounter = 0;
        int proCounter = 0;
        int total = basicQty + proQty;

        ArrayList<String> orderList = new ArrayList<>();
        ArrayList<Integer> priceList = new ArrayList<>();
        ArrayList<String> orders = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();

        /*
         * Fill the orders list according to quantities
         * Basic model is added twice per iteration
         */
        for (int i = 0; i < total; i++) {
            if (basicCounter != basicQty) {
                orders.add("basic");
                orders.add("basic");
                basicCounter += 2;
            }
            if (proCounter != proQty) {
                orders.add("pro");
                proCounter++;
            }
        }

        /*
         * Execute threads according to the selected configuration
         */
        switch (config) {
            case 1:
                // Sequential execution with join after each thread
                for (int i = 0; i < total; i++) {
                    ThreadMobiles tm = new ThreadMobiles(orders.get(i), orderList, priceList);
                    Thread t = new Thread(tm);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;

            case 2:
                // Limited concurrent threads: join after 10 threads
                for (int i = 0; i < total; i++) {
                    ThreadMobiles tm = new ThreadMobiles(orders.get(i), orderList, priceList);
                    Thread t = new Thread(tm);
                    threads.add(t);
                    t.start();

                    if (threads.size() == 10) {
                        try {
                            for (Thread thread : threads) {
                                thread.join();
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        threads.clear();
                    }
                }
                break;

            case 3:
                // Fully concurrent execution
                for (int i = 0; i < total; i++) {
                    ThreadMobiles tm = new ThreadMobiles(orders.get(i), orderList, priceList);
                    Thread t = new Thread(tm);
                    t.start();
                }
                break;
        }

        // Output results
        System.out.println("\nNUMBER OF mobiles manufactured: " + orderList.size());

        int totalPrice = 0;
        for (Integer price : priceList) {
            totalPrice += price;
        }

        /*
         * Profit is calculated based on the time manufacturing + the mobile price*/
        System.out.println("Total profit: " + totalPrice);
    }
}

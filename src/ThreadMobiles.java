import java.util.ArrayList;
import java.util.Random;

public class ThreadMobiles implements Runnable {

    String modelType;
    final ArrayList<String> orderList;
    final ArrayList<Integer> totalPriceList;
    int mobilePrice = 0;
    int fabricationTime = 0;

    public ThreadMobiles(String modelType, ArrayList<String> orderList, ArrayList<Integer> totalPriceList) {
        this.modelType = modelType;
        this.orderList = orderList;
        this.totalPriceList = totalPriceList;
    }

    @Override
    public void run() {
        Random random = new Random();
        int basicTime = random.nextInt(50, 80);
        int proTime = random.nextInt(200, 250);
        int[] fabricationTimes = {basicTime, proTime};
        int fabricationDuration = 0;

        switch (modelType) {
            case "basic":
                fabricationDuration = fabricationTimes[0];
                fabricationTime = fabricationDuration;
                mobilePrice = 100;
                break;
            case "pro":
                fabricationDuration = fabricationTimes[1];
                fabricationTime = fabricationDuration;
                mobilePrice = 300;
                break;
            default:
                break;
        }

        String record = modelType + "-" + fabricationDuration;
        int unitPrice = mobilePrice + fabricationTime;

        // Synchronize access to shared lists
        synchronized (orderList) {
            System.out.println(record);
            orderList.add(record);

        }
        synchronized (totalPriceList) {
            totalPriceList.add(unitPrice);
        }
    }
}

import java.util.Date;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class HashtableExperiment {

    private static final int MIN_PRIME = 95500;
    private static final int MAX_PRIME = 96000;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java HashtableExperiment <dataSource> <loadFactor> [<debugLevel>]");
            return;
        }

        int dataSource = Integer.parseInt(args[0]);
        double loadFactor = Double.parseDouble(args[1]);
        int debugLevel = (args.length == 3) ? Integer.parseInt(args[2]) : 0;

        int tableSize = TwinPrimeGenerator.generateTwinPrime(MIN_PRIME, MAX_PRIME);
        int numElements = (int) Math.ceil(loadFactor * tableSize);

        System.out.printf("HashtableExperiment: Found a twin prime table capacity: %d\n", tableSize);
        System.out.printf("HashtableExperiment: Input: %s   Loadfactor: %.2f\n\n",
                getDataSourceName(dataSource), loadFactor);

        LinearProbing<Object> linearProbing = new LinearProbing<>(tableSize);
        DoubleHashing<Object> doubleHashing = new DoubleHashing<>(tableSize);

        switch (dataSource) {
            case 1:
                loadRandomData(numElements, linearProbing, doubleHashing, debugLevel);
                break;
            case 2:
                loadDateData(numElements, linearProbing, doubleHashing, debugLevel);
                break;
            case 3:
                loadWordListData(numElements, linearProbing, doubleHashing, debugLevel);
                break;
            default:
                System.out.println("Invalid data source.");
                return;
        }

        printSummary("Linear Probing", linearProbing);
        if (debugLevel == 1) {
            linearProbing.dumpToFile("linear-dump.txt");
            System.out.println("HashtableExperiment: Saved dump of hash table");
        }

        printSummary("Double Hashing", doubleHashing);
        if (debugLevel == 1) {
            doubleHashing.dumpToFile("double-dump.txt");
            System.out.println("HashtableExperiment: Saved dump of hash table");
        }
    }

    private static void loadRandomData(int numElements, Hashtable<Object> linear, Hashtable<Object> doubleHashing, int debugLevel) {
        Random rand = new Random();

        for (int i = 0; i < numElements; i++) {
            Integer value = rand.nextInt();

            insertValue(linear, value, debugLevel);
            insertValue(doubleHashing, value, debugLevel);
        }
    }

    private static void loadDateData(int numElements, Hashtable<Object> linear, Hashtable<Object> doubleHashing, int debugLevel) {
        long currentTime = new Date().getTime();

        for (int i = 0; i < numElements; i++) {
            Date date = new Date(currentTime);
            currentTime += 1000;

            insertValue(linear, date, debugLevel);
            insertValue(doubleHashing, date, debugLevel);
        }
    }

    private static void loadWordListData(int numElements, Hashtable<Object> linear, Hashtable<Object> doubleHashing, int debugLevel) {
        try (Scanner scanner = new Scanner(new File("word-list.txt"))) {
            int count = 0;

            while (scanner.hasNext() && count < numElements) {
                String word = scanner.nextLine();

                insertValue(linear, word, debugLevel);
                insertValue(doubleHashing, word, debugLevel);

                count++;
            }
        } catch (IOException e) {
            System.err.println("Error reading word list: " + e.getMessage());
        }
    }

    private static void insertValue(Hashtable<Object> table, Object value, int debugLevel) {
        int beforeInsert = table.getNumElements();
        table.insert(value);
        int afterInsert = table.getNumElements();

        if (debugLevel == 2) {
            if (afterInsert > beforeInsert) {
                System.out.printf("Inserted: %s\n", value);
            } else {
                System.out.printf("Duplicate: %s\n", value);
            }
        }
    }

    private static void printSummary(String type, Hashtable<Object> table) {
        System.out.printf("Using %s\n", type);
        System.out.printf("Inserted %d elements, of which %d were duplicates\n",
                table.getNumElements(), table.getDuplicates());
        System.out.printf("Avg. no. of probes = %.2f\n\n", table.getAverageProbes());
    }

    private static String getDataSourceName(int dataSource) {
        switch (dataSource) {
            case 1: return "Random Numbers";
            case 2: return "Date Values";
            case 3: return "Word-List";
            default: return "Unknown";
        }
    }
}

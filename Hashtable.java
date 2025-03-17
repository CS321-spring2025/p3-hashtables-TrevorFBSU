import java.io.PrintWriter;
import java.io.IOException;

public abstract class Hashtable<T> {
    protected HashObject<T>[] table;
    protected int size;
    protected int numElements;
    protected int duplicates;
    protected int totalProbes;

    @SuppressWarnings("unchecked")
    public Hashtable(int size) {
        this.size = size;
        table = (HashObject<T>[]) new HashObject[size];
        numElements = 0;
        duplicates = 0;
        totalProbes = 0;
    }

    protected int positiveMod(int value, int mod) {
        int result = value % mod;
        if (result < 0) {
            result += mod;
        }
        return result;
    }

    public double getAverageProbes() {
        return (numElements == 0) ? 0 : (double) totalProbes / numElements;
    }

    public int getDuplicates() {
        return duplicates;
    }

    public int getNumElements() {
        return numElements;
    }

    public void dumpToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (int i = 0; i < size; i++) {
                if (table[i] != null) {
                    writer.printf("table[%d]: %s\n", i, table[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void insert(T key);

    public abstract HashObject<T> search(T key);
}

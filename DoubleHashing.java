public class DoubleHashing<T> extends Hashtable<T> {

    public DoubleHashing(int size) {
        super(size);
    }

    private int hash2(T key) {
        int value = positiveMod(key.hashCode(), size - 2);
        return 1 + value;
    }

    @Override
    public void insert(T key) {
        int hash = positiveMod(key.hashCode(), size);
        int stepSize = hash2(key);
        int probes = 0;

        while (table[hash] != null) {
            probes++;
            if (table[hash].getKey().equals(key)) {
                table[hash].incrementFrequency();
                duplicates++;
                return;
            }
            hash = positiveMod(hash + stepSize, size);
        }

        table[hash] = new HashObject<>(key);
        numElements++;
        totalProbes += probes;
    }

    @Override
    public HashObject<T> search(T key) {
        int hash = positiveMod(key.hashCode(), size);
        int stepSize = hash2(key);

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                return table[hash];
            }
            hash = positiveMod(hash + stepSize, size);
        }
        return null;
    }
}

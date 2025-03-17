public class LinearProbing<T> extends Hashtable<T> {

    public LinearProbing(int size) {
        super(size);
    }

    @Override
    public void insert(T key) {
        int hash = positiveMod(key.hashCode(), size);
        int probes = 0;

        while (table[hash] != null) {
            probes++;
            if (table[hash].getKey().equals(key)) {
                table[hash].incrementFrequency();
                duplicates++;
                return;
            }
            hash = (hash + 1) % size;
        }

        table[hash] = new HashObject<>(key);
        numElements++;
        totalProbes += probes;
    }

    @Override
    public HashObject<T> search(T key) {
        int hash = positiveMod(key.hashCode(), size);

        while (table[hash] != null) {
            if (table[hash].getKey().equals(key)) {
                return table[hash];
            }
            hash = (hash + 1) % size;
        }
        return null;
    }
}

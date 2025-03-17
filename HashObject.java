public class HashObject<T> {
    private T key;
    private int frequency;

    public HashObject(T key) {
        this.key = key;
        this.frequency = 1;
    }

    public T getKey() {
        return key;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequency() {
        frequency++;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HashObject) {
            HashObject<?> other = (HashObject<?>) obj;
            return key.equals(other.getKey());
        }
        return false;
    }

    @Override
    public String toString() {
        return key + " " + frequency;
    }
}

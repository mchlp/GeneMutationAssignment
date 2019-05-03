import java.util.ArrayList;

public abstract class BinaryList<T> extends ArrayList<T> {

    boolean sorted;

    @Override
    public boolean add(T element) {
        sorted = false;
        return super.add(element);
    }

    public void sort() {
        this.sort(this::compare);
        sorted = true;
    }

    public abstract int compare(T ele1, T ele2);

    @Override
    public boolean contains(Object o) {

        if (!sorted) {
            throw new AssertionError("The list must be sorted before this method can be called.");
        }

        Comparable<T> element = (Comparable<T>) o;

        int start = 0;
        int end = this.size() - 1;

        while (start <= end) {
            int middle = (start + end) / 2;
            int diff = element.compareTo(this.get(middle));
            if (diff > 0) {
                start = middle + 1;
            } else if (diff < 0) {
                end = middle - 1;
            } else {
                return true;
            }
        }
        return false;
    }
}

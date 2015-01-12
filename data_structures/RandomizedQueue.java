import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] array;
    private int size;

    public RandomizedQueue() {
        this.size = 0;
        this.array = (Item[]) new Object[this.size];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return this.size;
    }

    private void resize(int capacity) {
        assert capacity >= this.size;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < this.size; i++) {
            temp[i] = this.array[i];
        }
        this.array = temp;
    }

    public void enqueue(Item item) {
        if (item == null) { throw new NullPointerException(); }
        if (isEmpty()) {resize(1);}
        if (this.size == this.array.length) { resize(2*this.size); }
        this.array[this.size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        int n = StdRandom.uniform(this.size);
        Item item = this.array[n];
        for (int i = n; i < this.size-1; i++) {
            this.array[i] = this.array[i+1];
        }
        this.size--;
        return item;
    }

    public Item sample() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        int n = StdRandom.uniform(this.size);
        return this.array[n];
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>();
    }

    private class ListIterator<I> implements Iterator<Item> {

        public boolean hasNext()  { return !isEmpty();  }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return dequeue();
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        q.enqueue(2);
        for (int i : q)
            System.out.print(i + ",");
        q.dequeue();
        q.dequeue();
        System.out.print("\nsize: " + q.size + "\n");
        for (int i : q)
            System.out.print(i + ",");
    }
}

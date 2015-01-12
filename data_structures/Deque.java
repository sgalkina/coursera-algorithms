import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    private void addTheVeryFirstElement(Node<Item> node) {
        this.first = node;
        this.last = node;
    }

    public void addFirst(Item item) {
        if (item == null) { throw new NullPointerException(); }
        Node<Item> oldfirst = this.first;
        Node<Item> first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        first.previous = null;
        this.first = first;
        if (oldfirst != null) { oldfirst.previous = first; }
        if (this.size == 0) { addTheVeryFirstElement(first); }
        this.size++;
    }

    public void addLast(Item item) {
        if (item == null) { throw new NullPointerException(); }
        Node<Item> oldlast = this.last;
        Node<Item> last = new Node<Item>();
        last.item = item;
        last.previous = oldlast;
        last.next = null;
        this.last = last;
        if (oldlast != null) { oldlast.next = last; }
        if (this.size == 0) { addTheVeryFirstElement(last); }
        this.size++;
    }

    public Item removeFirst() {
        if (this.first == null) { throw new NoSuchElementException(); }
        Node<Item> oldfirst = this.first;
        Item item = oldfirst.item;
        this.first = oldfirst.next;
        if (this.first != null) { this.first.previous = null; }
        this.size--;
        return item;
    }

    public Item removeLast() {
        if (this.last == null) { throw new NoSuchElementException(); }
        Node<Item> oldlast = this.last;
        Item item = oldlast.item;
        this.last = oldlast.previous;
        if (this.last != null) { this.last.next = null; }
        this.size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator<Item>(this.first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        Node<Item> current;

        public ListIterator(Node<Item> first) {
            this.current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addLast(7);
        System.out.print("size: " + deque.size + "\n");
        for (int i : deque)
            System.out.print(i + ",");
        deque.removeFirst();
        deque.removeLast();
        System.out.print("\nsize: " + deque.size + "\n");
        for (int i : deque)
            System.out.print(i + ",");
        deque.removeFirst();
        System.out.print("\nsize: " + deque.size + "\n");
        for (int i : deque)
            System.out.print(i + ",");
        deque.removeFirst();
        System.out.print("\nsize: " + deque.size + "\n");
    }
}

package co.edu.uptc.doubleList;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

@Setter
@Getter
public class DoubleList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public DoubleList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void addFirst(T data) {
        Node<T> newData = new Node<T>(data);
        if (head == null) {
            head = newData;
            tail = newData;
        } else {
            newData.setNext(head);
            head.setPrev(newData);
            head = newData;
        }
        size++;
    }

    public void addLast(T data) {
        Node<T> newData = new Node<T>(data);
        if (head == null) {
            head = newData;
            tail = newData;
        } else {
            newData.setPrev(tail);
            tail.setNext(newData);
            tail = newData;
        }
        size++;
    }

    public void addMiddle(T data) {
        int mid = size / 2;
        if (mid < 1) {
            addFirst(data);
        } else {
            Node<T> newData = new Node<T>(data);
            Node<T> current = getNode(mid);
            Node<T> prev = current.getPrev();

            newData.setNext(current);
            newData.setPrev(prev);
            prev.setNext(newData);
            current.setPrev(newData);
            size++;
        }

    }

    public void addSort(T data, Comparator<T> comparator){
        Node<T> newData = new Node<T>(data);
        if (head == null) {
            head = newData;
            tail = newData;
            size++;
            return;
        }

        if (comparator.compare(data, head.getData()) <= 0) {
            newData.setNext(head);
            head.setPrev(newData);
            head = newData;
            size++;
            return;
        }

        Node<T> current = head;

        while (current.getNext() != null &&
                comparator.compare(data, current.getNext().getData()) > 0) {
            current = current.getNext();
        }

        newData.setNext(current.getNext());
        newData.setPrev(current);

        if (current.getNext() != null) {
            current.getNext().setPrev(newData);
        } else {
            tail = newData;
        }

        current.setNext(newData);

        size++;
    }

    public T removeFirst(){
        if (head==null)return null;
        T data = head.getData();

        unlink(head);

        return data;
    }

    public T removeLast(){
        if (tail==null)return null;
        T data = tail.getData();

        unlink(tail);

        return data;
    }

    public T remove(int index){
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Indice fuera de rango " + index);

        Node<T> current = getNode(index);
        T data = current.getData();

        unlink(current);

        return data;
    }

    public boolean remove(T data){
        Node<T> current = head;
        while (current!=null){
            if (Objects.equals(current.getData(),data)){
                unlink(current);
                return true;
            }
            current = current.getNext();
        }
        return  false;
    }

    public DoubleList<DoubleList<T>> divideMiddle(){

        DoubleList<DoubleList<T>> lists = new DoubleList<DoubleList<T>>();
        DoubleList<T> firstMiddle = new DoubleList<T>();
        DoubleList<T> lastMiddle = new DoubleList<T>();

        int middle= size/2;
        Node<T> current = head;

        for (int i = 0; i < middle; i++) {
            firstMiddle.addLast(current.getData());
            current = current.getNext();
        }

        while (current != null) {
            lastMiddle.addLast(current.getData());
            current = current.getNext();
        }

        lists.addLast(firstMiddle);
        lists.addLast(lastMiddle);

        return lists;
    }

    public void sort(Comparator<T> comparator){
        for (int i = 0; i < size-1; i++) {
            Node<T> current = head;
            for (int j = 0; j < size-1-i; j++) {
                Node<T> next = current.getNext();

                if (comparator.compare(current.getData(),next.getData())>0){
                    T temp = current.getData();
                    current.setData(next.getData());
                    next.setData(temp);
                }
                current = current.getNext();
            }
        }
    }

    private void unlink(Node<T> target){
        Node<T> prev = target.getPrev();
        Node<T> next = target.getNext();

        if (prev!= null){
            prev.setNext(next);
        } else {
            head = next;
        }

        if (next != null){
            next.setPrev(prev);
        }else {
            tail = prev;
        }
        size--;
    }

    public int getIndex(T data){
        Node<T> current = head;
        int index = 0;
        while (current != null){
            if (Objects.equals(current.getData(), data)) return index;
            current = current.getNext();
            index++;
        }
        return -1;
    }

    public Node<T> getNode(int index) {
        Node<T> current;

        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size-1; i > index; i--) {
                current = current.getPrev();
            }
        }

        return current;
    }

    // ====Métodos realizados para la preparación del parcial====

    public DoubleList<T> removeIf(Predicate<T> condition){
        DoubleList<T> removed = new DoubleList<>();
        Node<T> current = head;
        while (current!=null){
            if (condition.test(current.getData())){
                removed.addLast(current.getData());
                unlink(current);
            }
            current = current.getNext();
        }
        return removed;
    }

    public DoubleList<DoubleList<T>> divideBy(Predicate<T> condition){

        DoubleList<DoubleList<T>> halves = new DoubleList<DoubleList<T>>();

        DoubleList<T> firstMiddle = new DoubleList<T>();
        DoubleList<T> lastMiddle = new DoubleList<T>();

        Node<T> current = head;

        while (current!=null){
            if (condition.test(current.getData())){
                firstMiddle.addLast(current.getData());
                unlink(current);
            }else
                lastMiddle.addLast(current.getData());

            current = current.getNext();
        }
        halves.addLast(firstMiddle);
        halves.addLast(lastMiddle);

        return halves;
    }
}

package co.edu.uptc.doubleList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node<T>{

    private T data;
    private Node<T> next;
    private Node<T> prev;

    public Node(T data) {
        next = null;
        prev = null;
        this.data = data;
    }
}
package redblacktree;

import java.util.*;

public class RedBlackTree<K extends Comparable<? super K>, V> implements Iterable<Tuple<K,V>>{

  Node<K, V> root;

  public RedBlackTree() {
    this.root = null;
  }

  RedBlackTree(Node<K, V> root) {
    this.root = root;
  }

  public void put(K key, V value) {

    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);

    Node<K, V> parent = pair.getX();
    Node<K, V> current = pair.getY();

    if (current != null) { // found an existing key
      current.setValue(value);
      return;
    }

    if (parent == null) { // empty tree
      root = new Node<K, V>(key, value, Colour.BLACK);
      return;
    }
    /* create a new key */
    int comparison = key.compareTo(parent.getKey());
    Node<K, V> newNode = new Node<K, V>(key, value, Colour.RED);
    if (comparison < 0) {
      parent.setLeft(newNode);
    } else {
      assert comparison > 0;
      parent.setRight(newNode);
    }

    insertCaseOne(newNode);

  }

  private void insertCaseOne(Node<K, V> current) {
    if(current.getParent() == null){
      current.setBlack();
    }else{
      insertCaseTwo(current);
    }
  }

  private void insertCaseTwo(Node<K, V> current) {
    if(current.getParent().isRed()){
      insertCaseThree(current);
    }
  }

  private void insertCaseThree(Node<K, V> current) {
    if(current.getUncle() != null && current.getUncle().isRed()){
      current.getParent().setBlack();
      current.getUncle().setBlack();
      current.getGrandparent().setRed();
      insertCaseOne(current.getGrandparent());
    }else{
      insertCaseFour(current);
    }
  }

  private void insertCaseFour(Node<K, V> current) {
    Node<K,V> parent = current.getParent();
    if(parent.isLeftChild() && current.isRightChild()){
      parent.rotateLeft();
      insertCaseFive(parent);
    }else if (parent.isRightChild() && current.isLeftChild()){
      parent.rotateRight();
      insertCaseFive(parent);
    }else{
      insertCaseFive(current);
    }
  }

  private void insertCaseFive(Node<K, V> current) {
    current.getParent().setBlack();
    current.getGrandparent().setRed();
    Node<K,V> newGrandparent;
    if(current.isLeftChild()){
      newGrandparent = current.getGrandparent().rotateRight();
    }else{
      newGrandparent = current.getGrandparent().rotateLeft();
    }
    if(newGrandparent.getParent() == null){
      root = newGrandparent;
    }


  }

  private Tuple<Node<K, V>, Node<K, V>> findNode(K key) {
    Node<K, V> current = root;
    Node<K, V> parent = null;

    while (current != null) {
      parent = current;

      int comparison = key.compareTo(current.getKey());
      if (comparison < 0) {
        current = current.getLeft();
      } else if (comparison == 0) {
        break;
      } else {
        assert comparison > 0;
        current = current.getRight();
      }
    }
    return new Tuple<Node<K, V>, Node<K, V>>(parent, current);
  }

  public boolean contains(K key) {
    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);
    return pair.getY() != null;
  }

  public V get(K key) {
    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);
    Node<K, V> current = pair.getY();
    if (current == null) {
      throw new NoSuchElementException();
    }
    return current.getValue();
  }

  public void clear() {
    this.root = null;
  }

  public String toString() {
    return "RBT " + root + " ";
  }

  @Override
  public Iterator<Tuple<K, V>> iterator() {
    return new RBTIterator<>(root);
  }

  private static class RBTIterator<K extends Comparable<? super K>, V> implements Iterator<Tuple<K, V>> {
    private Node<K, V> current;
    boolean first = true;

    RBTIterator(Node<K, V> root) {
      this.current = root;
      while (current.getLeft() != null) {
        current = current.getLeft();
      }
    }

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public Tuple<K, V> next() {
      Node<K, V> prev = current;

      if (current.getRight() != null) {
        current = current.getRight();
        while (current.getLeft() != null) {
          current = current.getLeft();
        }
      } else {
        while (current != null) {
          if (current.isLeftChild()) {
            current = current.getParent();
            break;
          }else {
            current = current.getParent();
          }
        }
      }
      return new Tuple<>(prev.getKey(), prev.getValue());
    }
  }
}

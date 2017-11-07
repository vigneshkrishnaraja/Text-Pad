
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vigneshkrishnaraja
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    //defining red and black
    private Node root;
    //Node properties
    private class Node{
        private Key key;
        private Value value;
        private Node left, right;
        private boolean color;
        private int N;
        
        public Node(Key key, Value value, boolean color, int N){
            this.key = key;
            this.value = value;
            this.color = color;
            this.N = N;
        }
    }
    
    public RedBlackBST(){
    }
    //size of Node
    private int size(Node x){
        if(x!=null)
            return x.N;
        return 0;
    }
    
    public int size() {
        return size(root);
    }
    //checking if the tree is empty
    public boolean isEmpty() {
        return root == null;
    }
    //inserting values and keys
    public void insert(Key key, Value value) {
        if(key == null || value == null) throw new NullPointerException("Key or value is null.Please check the arguments");
        root = put(root,key,value);
        root.color = BLACK;
    }
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }
    
    private Node put(Node x, Key key,Value value){
        if(x==null) return new Node(key, value, RED, 1);
        int cmp = key.compareTo(x.key);
        if(cmp < 0) x.left = put(x.left,key, value);
        if(cmp > 0) x.right = put(x.right, key, value);
        else x.value = value;
        if (isRed(x.right) && !isRed(x.left))      x = rotateLeft(x);
        if (isRed(x.left)  &&  isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left)  &&  isRed(x.right))     flipColors(x);
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }
    
    private Node rotateRight(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        x.color = x.right.color;
        x.right.color = RED;
        x.N = y.N;
        y.N = size(y.left) + size(y.right) + 1;
        return x;
    }

    private Node rotateLeft(Node y) {
        Node x = y.right;
        y.right = x.left;
        x.left = y;
        x.color = x.left.color;
        x.left.color = RED;
        x.N = y.N;
        y.N = size(y.left) + size(y.right) + 1;
        return x;
    }

    private void flipColors(Node y) {
        y.color = !y.color;
        y.left.color = !y.left.color;
        y.right.color = !y.right.color;
    }
    
    public Value find(Key key) {
        if (key == null) throw new NullPointerException("Key is null");
        return find(root, key);
    }

    private Value find(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.value;
        }
        return null;
    }
    //checking if the key contains in the tree
    public boolean contains(Key key) {
        return find(key) != null;
    }
    //removing the key from the tree
    public boolean remove(Key key) {
        if (key == null) throw new NullPointerException("Key is null");
        if (!contains(key)) return false;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        return true;
    }
    private Node delete(Node y, Key key) { 
        // assert get(h, key) != null;

        if (key.compareTo(y.key) < 0)  {
            if (!isRed(y.left) && !isRed(y.left.left))
                y = moveRedLeft(y);//moving the red node left
            y.left = delete(y.left, key);
        }
        else {
            if (isRed(y.left))
                y = rotateRight(y);
            if (key.compareTo(y.key) == 0 && (y.right == null))
                return null;
            if (!isRed(y.right) && !isRed(y.right.left))
                y = moveRedRight(y);
            if (key.compareTo(y.key) == 0) {
                Node x = min(y.right);
                y.key = x.key;
                y.value = x.value;
                y.right = deleteMin(y.right);
            }
            else y.right = delete(y.right, key);
        }
        return balance(y);//rebalancing the tree
    }
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("No element present");
        return min(root).key;
    } 
    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) { 
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 
    
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("No element present");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node x) { 
        if (x.left == null)
            return null;

        if (!isRed(x.left) && !isRed(x.left.left))
            x = moveRedLeft(x);

        x.left = deleteMin(x.left);
        return balance(x);
    }
    

    //moving the red node left
    private Node moveRedLeft(Node x) {
        flipColors(x);
        if (isRed(x.right.left)) { 
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }
    //moving the red node right
    private Node moveRedRight(Node x) {
        flipColors(x);
        if (isRed(x.left.left)) { 
            x = rotateRight(x);
            flipColors(x);
        }
        return x;
    }
    //balancing the tree
    private Node balance(Node x) {

        if (isRed(x.right))                      x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right))     flipColors(x);

        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    //emptying the whole tree
    public void makeEmpty() {
        root = null;
    }
}

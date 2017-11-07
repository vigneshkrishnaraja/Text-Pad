/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vigneshkrishnaraja
 * @param <Key>
 */
public class HashTable <Key extends Comparable<Key>> implements Dictionary<Key>{
    private final int M = 88997;
    
    public HashTable() {
        
    }
    private int hash(Key key){ 
        return (key.hashCode() & 0x7fffffff);
    }
    
    @Override
    public int size() {
         return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void insert(Key key) {
        
    }

    @Override
    public boolean find(Key key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Key key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void makeEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}

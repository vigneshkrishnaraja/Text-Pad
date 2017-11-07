/* Dictionary.java */

/**
 *  An interface for dictionary ADTs.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public interface Dictionary<Key>
{
  /** 
   *  Returns the number of entries stored in the dictionary.  Keys in the 
   *  dictionary are distinct. 
   *  @return number of entries in the dictionary.
   **/

  public int size(); 

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty();

  /** 
   *  Create a new element including the input key,
   *  and insert the entry into the dictionary. Multiple entries with the 
   *  same key can NOT coexist in the dictionary. 
   *
   *  @param key the key by which the entry can be retrieved.
   *  @return void.
   **/

  public void insert(Key key);

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return true; otherwise return false.  
   *
   *  @param key the search key.
   *  @return the paired value, or null if no entry contains the specified key.
   **/

  public boolean find(Key key);

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return true; otherwise return false.
   *
   *  @param key the search key.
   *  @return true if key is found, or false if
   *          no entry contains the specified key.
   */

  public boolean remove(Key key);

  /**
   *  Remove all entries from the dictionary.
   */

  public void makeEmpty();

}

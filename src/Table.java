/* Table.java */

/**
 *  An interface for table ADTs.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public interface Table<Key, Value>
{

  /** 
   *  Returns the number of entries stored in the table.  Keys in the 
   *  table are distinct. 
   *  @return number of entries in the table.
   **/

  public int size(); 

  /** 
   *  Tests if the table is empty.
   *
   *  @return true if the table has no entries; false otherwise.
   **/

  public boolean isEmpty();

  /** 
   *  Create a new pair including the input key and associated value,
   *  and insert the entry into the table. Multiple entries with the 
   *  same key can NOT coexist in the table. If key is already in the
   *  table, reset its value.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value the associated value.
   *  @return void.
   **/

  public void insert(Key key, Value value);

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return the value; otherwise return null.  
   *
   *  @param key the search key.
   *  @return the paired value, or null if no entry contains the specified key.
   **/

  public Value find(Key key);

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
   *  Remove all entries from the table.
   */

  public void makeEmpty();

}

import java.util.LinkedList;

public class HashMap<Key, Value> {

    // Initializing the array to store the key and value pairs
    LinkedList<Entry<Key, Value>>[] hashMap;

    // This represents the number of entries in the HashMap
    int size;

    public HashMap() {
        // Initializing the new Array with a default size 2
        hashMap = new LinkedList[2];

        // Initialize size as 0
        size = 0;
    }

    // Put data method is used to insert the key and value to hasMap
    public void putData(Key key, Value value) {
        /*
        Before adding data it checks whether the size of the entries
        greater than or equals to the hashMap length
         */
        if (size >= hashMap.length) {
            resizeArray();
        }

        /* Calculate the index to store the entries by
         referring to the hashcode of the key value
         */
        int index = getIndex(key) % hashMap.length;

        // Check whether that index contains entries that are null
        if (hashMap[index] == null) {
            /* If it is null, it creates a new linkedList at that index and
            add the key and value to that linked list
             */
            hashMap[index] = new LinkedList<>();
            hashMap[index].add(new Entry<>(key, value));

            // incrementing the number of elements stored in array by 1

            size++;
        } else {
            /* If there are existing values in that index,
            check whether the inserting key already exists
            using linear search
             */
            for (Entry<Key, Value> entry : hashMap[index]) {
                if (entry.key.equals(key)) {
                    // If the key already exists, updating its value
                    entry.value = value;
                    return;
                }
            }
            // If the key doesn't exist, add a new entry to the LinkedList
            hashMap[index].add(new Entry<>(key, value));
            size++;
        }
    }

    // This method returns  the value of the given key
    public Value get(Key key) {

        // This calculates the index of the array using hashcode
        int index = getIndex(key) % hashMap.length;

        // Check whether any values are there in the given index
        if (hashMap[index] != null) {
            /* Then it checks for the given key in that index in hashMap.
            This is done, since hashmap can contain several key values in
            the same index.This use linear search method.
             */
            for (Entry<Key, Value> entry : hashMap[index]) {
                if (entry.key.equals(key)) {
                    // returns the value if the key is found
                    return entry.value;
                }
            }
        }

        // returns null if the key is not found
        return null;
    }

    // Method to remove an entry with the specified key from the HashMap
    public void remove(Key key) {
        // Calculate the index of the key in the array
        int index = getIndex(key) % hashMap.length;

        // Check if there are entries at the index
        if (hashMap[index] != null) {
            // Search for the entry with the matching key
            for (Entry<Key, Value> entry : hashMap[index]) {
                if (entry.key.equals(key)) {
                    // Remove the entry from the LinkedList and decrement the size
                    hashMap[index].remove(entry);
                    size--;
                    return;
                }
            }
        }
    }

    //This method checks whether user given key contains in the hashmap
    public boolean containsKey(Key key) {

        /* First, it calculates the index,
        using the hashcode of the given key
         */
        int index = getIndex(key) % hashMap.length;

        // Check whether there are entries in the given index
        if (hashMap[index] != null) {
            /*
            If that index has values,
            check whether that key exists using the linear search
             */
            for (Entry<Key, Value> entry : hashMap[index]) {
                if (entry.key.equals(key)) {
                    // If key is found, return true
                    return true;
                }
            }
        }

        // If key is not found return false
        return false;
    }

    /*
     This method returns the current size of the elements
     that are stored in the HashMap
     */
    public int size() {
        return size;
    }

   // This method resize the Array when it becomes full
    public void resizeArray() {
       // Create a new Array with a large size
        LinkedList<Entry<Key, Value>>[] oldHashMap = hashMap;
        hashMap = new LinkedList[size + 2];
        size = 0;

        // This process will rehash the oldHahMap
        for (LinkedList<Entry<Key, Value>> entries : oldHashMap) {
            if (entries != null) {
                for (Entry<Key, Value> entry : entries) {
                    // Insert each entry into the new array from the oldHahMap
                    putData(entry.key, entry.value);
                }
            }
        }
    }

    // This method calculates the index of a given key using the hashCode
    public int getIndex(Key key) {
        // Get the hash code of the key and return its absolute value
        return Math.abs(key.hashCode());
    }

    // This private inner class is used to represent the key and value entry
    private static class Entry<Key, Value> {
        Key key; // Generic type key
        Value value; // Generic type value

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }
}

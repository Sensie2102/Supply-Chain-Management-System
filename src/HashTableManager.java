import java.util.HashMap;
import java.util.Map;

public class HashTableManager {
    private static Map<Integer, Long> hashTable;

    static {
        if(HashedFileIndex.loadListArrayFromFile("index.txt") == null)
            hashTable = new HashMap<>();
        else
            hashTable = HashedFileIndex.loadListArrayFromFile("index.txt");
    }
    public static synchronized  Map<Integer,Long> getHashTable(){
        return hashTable;
    }
    public static synchronized void put(Integer key, Long value) {
        hashTable.put(key, value);
    }

    public static synchronized Long get(Integer key) {
        return hashTable.get(key);
    }

    public static synchronized void remove(String key) {
        hashTable.remove(key);
    }
}

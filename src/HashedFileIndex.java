import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashedFileIndex {
    public RandomAccessFile record_file;
    public RandomAccessFile index_file;
    int size = 1001;

    //public long recordPointer;

    HashedFileIndex(String record_file_path) throws IOException {
        record_file = new RandomAccessFile(record_file_path,"rw");
    }
    public void insert_record(String[] record,int hashcode) throws IOException{
        long fileLength = record_file.length();

        long recordIndex = fileLength;



        record_file.seek(fileLength);
        for(int j = 0;j<4;j++){
            record_file.writeBytes(record[j]);
            if(j==3){
                record_file.writeBytes("\n");
                continue;
            }
            record_file.writeBytes("|");

        }


        HashTableManager.put(hashcode,recordIndex);
        closeFile();
    }
    public void display() throws IOException {

        Display_Record dp = new Display_Record(record_file);

    }

    public String search(byte[] name) throws IOException {

        int hash = 0;

        for (byte b : name) {
            hash = b + (hash << 6) + (hash << 16) - hash;
        }
        Map<Integer,Long> loaded = loadListArrayFromFile("index.txt");
        if(loaded ==  null)
            return null;

            if(HashTableManager.get(hash) == null)
                return null;
            long recordPointer = HashTableManager.get(hash);

            record_file.seek(recordPointer);

            String line = record_file.readLine();

            return line;


    }


    public void closeFile() throws IOException{
        record_file.close();
    }
    public void saveFile(String filename, Map<Integer, Long> listArray){
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(listArray);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Map<Integer,Long> loadListArrayFromFile(String filename) {
        Map<Integer, Long> listArray = null;
        try (FileInputStream fileInputStream = new FileInputStream(filename);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            Object obj = objectInputStream.readObject();
            if (obj instanceof Map) {
                return (Map<Integer, Long>) obj;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return listArray;
    }

    public static Integer HashFun(String name){
        int hash = 0;
        byte[] bytes = name.getBytes();
        for (byte b : bytes) {
            hash = b + (hash << 6) + (hash << 16) - hash;
        }
        return hash;
    }
}

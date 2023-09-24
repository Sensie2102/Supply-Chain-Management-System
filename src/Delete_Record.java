import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.List;

public class Delete_Record extends Frame {

    HashMap<Integer,Long> markedRecords = new HashMap<Integer,Long>();
    HashedFileIndex hfi = new HashedFileIndex("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
    Record[] rec;
    HashedFileIndex HashAccess;
    Map<Integer,Long> map;
     JLabel name_enter;
     JTextField  name_search;
     JPanel input_panel;
     int nextRecordHash ;
     long nextRecordpointer;
     public Delete_Record() throws IOException{
         name_enter = new JLabel("Enter a name to Delete");
         input_panel = new JPanel(new FlowLayout());
         name_search = new JTextField(15);
         input_panel.add(name_enter);
         input_panel.add(name_search);
         JButton submit = new JButton("Enter");
         submit.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 try {
                     submit_action(e);
                 } catch (IOException ex) {
                     throw new RuntimeException(ex);
                 }
             }
         });
         input_panel.add(submit);
         addWindowListener(new WindowAdapter() {
             @Override
             public void windowClosing(WindowEvent e) {
                 System.exit(0);
             }
         });
         JButton home = new JButton("Back");
         home.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 try {
                     Home_Action(e);
                 } catch (IOException ex) {
                     throw new RuntimeException(ex);
                 }
             }
         });
         input_panel.add(home);
         add(input_panel);
         setBackground(new java.awt.Color(100, 153, 153));
         setSize(750,750);
         setLayout(new FlowLayout());
         setTitle("Delete");
         setVisible(true);
     }

     public void submit_action(ActionEvent e) throws IOException {
//         Record[] rec = new Record[];
         String name  = name_search.getText().toString();
         byte[] bytes = name.getBytes();
         int hash = 0;

         for (byte b : bytes) {
             hash = b + (hash << 6) + (hash << 16) - hash;
         }
         if(name == null){
             JOptionPane.showMessageDialog(this, "Not found");
         }else{
             map = HashTableManager.getHashTable();
             //check if HashTable is null
             if(map == null){
                 JOptionPane.showMessageDialog(this, "Please Insert Before deletion");
                 return;
             }
             int flag1 = -1;
             int numRecords = map.size();
             rec = new Record[numRecords];
             for(int i = 0;i<numRecords;i++){
                 rec[i] = new Record();
             }
             int rec_num = 0;
             for (Integer key : map.keySet()) {
                 Long value = map.get(key);
                 try {
                     hfi.record_file.seek(value);
                     if(hash == key){
                         flag1 = rec_num;
                     }

                     String line = hfi.record_file.readLine();
                     String[] comp = line.split("\\|");
                     rec[rec_num].index = key;
                     rec[rec_num].item_name = comp[0];
                     rec[rec_num].quantity = comp[1];
                     rec[rec_num].Active_orders = comp[2];
                     rec[rec_num].Cost = comp[3];
                     rec_num++;
                 } catch (IOException ex) {
                     throw new RuntimeException(ex);
                 }
             }


             FileWriter fileWriter = new FileWriter("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
             fileWriter.write("");

             for(int i = flag1;i<rec_num-1;i++){
                 rec[i] = rec[i+1];
             }
             rec_num--;
             map.remove(hash);
             remove_record(rec_num);
             JOptionPane.showMessageDialog(this, "Succesfull Deletion");
         }

     }

    public void remove_record(int rec_num) throws IOException {
        for (int i = 0;i<rec_num;i++){
            HashAccess  = new HashedFileIndex("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
            String[] record = new String[4];
            int key = rec[i].index;
            record[0] = rec[i].item_name;
            record[1] = rec[i].quantity;
            record[2] = rec[i].Active_orders;
            record[3] = rec[i].Cost;
            HashAccess.insert_record(record, key);
        }

    }


    public void Home_Action(ActionEvent e) throws IOException {

         this.dispose();
     }
}

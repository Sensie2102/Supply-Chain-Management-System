import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

public class Insert_Record extends Frame {

    JButton back;
    JButton submit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField name_field;
    private javax.swing.JTextField Quantity_field;
    private javax.swing.JTextField Cost_field;
    private javax.swing.JTextField Active_Order_field;
    HashedFileIndex HashAccess;

    String[] record = new String[7];

   Insert_Record() throws IOException{
       jPanel1 = new javax.swing.JPanel();
       jLabel1 = new javax.swing.JLabel();
       jLabel2 = new javax.swing.JLabel();
       jLabel3 = new javax.swing.JLabel();
       jLabel4 = new javax.swing.JLabel();
       jLabel5 = new javax.swing.JLabel();



       Quantity_field = new javax.swing.JTextField(15);
       name_field = new javax.swing.JTextField(15);
       Cost_field = new javax.swing.JTextField(15);
       Active_Order_field = new javax.swing.JTextField(15);


        GridBagConstraints constraints = new GridBagConstraints();

        jPanel1 = new JPanel();

        jPanel1.setBackground(new java.awt.Color(100, 153, 153));
        jPanel1.setLayout(new GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        constraints.gridx = 0;
        constraints.gridy = 0;
        jLabel1.setText("INSERT");
        jPanel1.add(jLabel1,constraints);


        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Name");
        constraints.gridx = 1;
        constraints.gridy = 0;
        jPanel1.add(jLabel2,constraints);



        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Quantity");
        constraints.gridx = 2;
        constraints.gridy = 0;
        jPanel1.add(jLabel3,constraints);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Active_Orders");
        constraints.gridx = 3;
        constraints.gridy = 0;
        jPanel1.add(jLabel4,constraints);


        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Cost");
        constraints.gridx = 4;
        constraints.gridy = 0;
        jPanel1.add(jLabel5,constraints);

        submit = new JButton("Submit");
        submit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Submit_Action(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

       back = new JButton("Back");
       back.setFont(new java.awt.Font("Tahoma", 1, 14));
       back.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               Back_Action(e);
           }
       });

       constraints.gridx = 2;
       constraints.gridy = 4;
        jPanel1.add(back,constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        jPanel1.add(name_field,constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        jPanel1.add(Quantity_field,constraints);

        constraints.gridx = 3;
        constraints.gridy = 2;
        jPanel1.add(Active_Order_field,constraints);

        constraints.gridx = 4;
        constraints.gridy = 2;
        jPanel1.add(Cost_field,constraints);



        constraints.gridx = 1;
        constraints.gridy = 4;
        jPanel1.add(submit,constraints);
//        Label l1 = new Label("Insert Data");
//        add(l1);
//        Label l2 = new Label("Item:");
//        add(l2);
//        add(item_name);



        //Closing Procedure
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        add(jPanel1);
        //Frame Initialization
        setSize(1000,1000);
        setVisible(true);
        setLayout(new FlowLayout());
        setTitle("Insert_Record");
        pack();

   }

   public void Back_Action(ActionEvent e){
       if(HashAccess == null){this.dispose();

       }else{
       HashAccess.saveFile("index.txt",HashTableManager.getHashTable());
       this.dispose();}
   }

   public void Submit_Action(java.awt.event.ActionEvent event) throws IOException {
       HashAccess  = new HashedFileIndex("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
       Map<Integer, Long> hashtable =  HashTableManager.getHashTable();
        String name = name_field.getText().toString();
        String Quantity = Quantity_field.getText().toString();
        String Active_order = Active_Order_field.getText().toString();
        String Cost = Cost_field.getText().toString();

        record[0] = name;
        record[1] = Quantity;
        record[2] = Active_order;
        record[3] = Cost;

        if(name!=null) {
            Integer HasHCode = HashFunction(name.getBytes());

                HashAccess.insert_record(record, HasHCode);
//                FS.Ht.put(HasHCode, name);
                JOptionPane.showMessageDialog(this, "Succesfull entry");
        }

   }

//    public Boolean checkSame(int HashCode){
//       if(FS.hashTable.containsKey(HashCode)){
//           return true;
//       }
//       else{
//
//           return false;
//       }
//    }
    public int HashFunction(byte[] SecondaryKey){
        int size = 1001;

            int hash = 0;

            for (byte b : SecondaryKey) {
                hash = b + (hash << 6) + (hash << 16) - hash;
            }

            return hash;

    }

}
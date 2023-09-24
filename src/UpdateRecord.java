import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class UpdateRecord extends Frame{
    HashedFileIndex hfi = new HashedFileIndex("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
    public JLabel name_enter;
    public JTextField  name_search;
    String name;
    JPanel search_panel;
    JPanel input_panel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private JLabel name_field;
    private javax.swing.JTextField Quantity_field;
    private javax.swing.JTextField Cost_field;
    private javax.swing.JTextField Active_Order_field;
    public UpdateRecord() throws IOException {

        name_enter = new JLabel("Enter a name to Update");
        search_panel = new JPanel(new FlowLayout());
        name_search = new JTextField(15);
        search_panel.add(name_enter);
        search_panel.add(name_search);
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
        search_panel.add(submit);
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
                Home_Action(e);
            }
        });
        search_panel.add(home);
        add(search_panel);
        setSize(750,750);
        setLayout(new FlowLayout());
        setTitle("Update");
        setVisible(true);
    }
    public void submit_action(ActionEvent event) throws IOException {
        name = name_search.getText().toString();
        String disp_line = hfi.search(name.getBytes());
        if(disp_line == null){
            JOptionPane.showMessageDialog(this, "Record not found");
        }
        else{
           setInput(name);
        }
    }

    public void setInput(String name){
        remove(search_panel);
        input_panel = new JPanel(new GridBagLayout());
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        Quantity_field = new javax.swing.JTextField(15);
        name_field = new JLabel(name);
        Cost_field = new javax.swing.JTextField(15);
        Active_Order_field = new javax.swing.JTextField(15);

        GridBagConstraints constraints = new GridBagConstraints();

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Name");
        constraints.gridx = 1;
        constraints.gridy = 0;
        input_panel.add(jLabel2,constraints);



        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Quantity");
        constraints.gridx = 2;
        constraints.gridy = 0;
        input_panel.add(jLabel3,constraints);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Active_Orders");
        constraints.gridx = 3;
        constraints.gridy = 0;
        input_panel.add(jLabel4,constraints);


        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Cost");
        constraints.gridx = 4;
        constraints.gridy = 0;
        input_panel.add(jLabel5,constraints);

        JButton input_submit = new JButton("Submit");
        input_submit.setFont(new java.awt.Font("Tahoma", 1, 14)); // input_panel
        input_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    input_action();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton back = new JButton("Back");
        back.setFont(new java.awt.Font("Tahoma", 1, 14));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home_Action(e);
            }
        });

        constraints.gridx = 2;
        constraints.gridy = 4;
        input_panel.add(back,constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        input_panel.add(name_field,constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        input_panel.add(Quantity_field,constraints);

        constraints.gridx = 3;
        constraints.gridy = 2;
        input_panel.add(Active_Order_field,constraints);

        constraints.gridx = 4;
        constraints.gridy = 2;
        input_panel.add(Cost_field,constraints);



        constraints.gridx = 1;
        constraints.gridy = 4;
        input_panel.add(input_submit,constraints);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        add(input_panel);

        setSize(1000,1000);
        setVisible(true);
        setLayout(new FlowLayout());
        setTitle("Update details");
        pack();
    }
    public void input_action() throws IOException {
        String quant = Quantity_field.getText().toString();
        String Act_ord = Active_Order_field.getText().toString();
        String new_cost = Cost_field.getText().toString();
        int recordIndex = hfi.HashFun(name);
        Long recordPosition = HashTableManager.get(recordIndex);
        Long nextRecordPosition = findNextPos(recordPosition);

        String[] rec = new String[4];
        hfi.record_file.seek(recordPosition);
        String line  = hfi.record_file.readLine();
        String[] comp = line.split("\\|");
        rec[0] = comp[0];
        rec[1] = comp[1];
        rec[2] = comp[2];
        rec[3] = comp[3];
        int recordLength = rec.length;
        String[] new_rec = new String[4];
        new_rec[0] = name;
        new_rec[1] = quant;
        new_rec[2] = Act_ord;
        new_rec[3] = new_cost;
        int updateRecLen = new_rec.length;

        int diff = updateRecLen - recordLength;
        moveSubsequentRecords( nextRecordPosition, diff);
        writeRecordData(recordPosition, new_rec);
        JOptionPane.showMessageDialog(this, "Record Updated");
    }
    public void moveSubsequentRecords(Long nextPos,int offset) throws IOException {
        if (offset > 0) {
            // Move subsequent records forward
            String[] rec = new String[4];
            long currentPos = hfi.record_file.length() - 1;
            long newPos = currentPos + offset;
            while (currentPos >= nextPos) {
                hfi.record_file.seek(currentPos);
                String data = hfi.record_file.readLine();
                String[] comp = data.split("\\|");
                rec[0] = comp[0];
                rec[1] = comp[1];
                rec[2] = comp[2];
                rec[3] = comp[3];
                hfi.record_file.seek(newPos);
                for(int j = 0;j<4;j++){
                    hfi.record_file.writeBytes(rec[j]);
                    if(j==3){
                        hfi.record_file.writeBytes("\n");
                        continue;
                    }
                    hfi.record_file.writeBytes("|");

                }
                currentPos--;
                newPos--;
            }
        } else if (offset < 0) {
            // Move subsequent records backward
            String[] rec = new String[4];
            long currentPos = nextPos;
            long newPos = currentPos + offset;
            while (currentPos < hfi.record_file.length()) {
                hfi.record_file.seek(currentPos);
                String data = hfi.record_file.readLine();
                hfi.record_file.seek(newPos);
                String[] comp = data.split("\\|");
                rec[0] = comp[0];
                rec[1] = comp[1];
                rec[2] = comp[2];
                rec[3] = comp[3];
                hfi.record_file.seek(newPos);
                for(int j = 0;j<4;j++){
                    hfi.record_file.writeBytes(rec[j]);
                    if(j==3){
                        hfi.record_file.writeBytes("\n");
                        continue;
                    }
                    hfi.record_file.writeBytes("|");

                }
                currentPos++;
                newPos++;
            }
        }
    }
    public  void writeRecordData(long recordPosition, String[] recordData ) throws IOException {
        hfi.record_file.seek(recordPosition);
        String[] rec = new String[4];

        for(int j = 0;j<4;j++){
            hfi.record_file.writeBytes(recordData[j]);
            if(j==3){
                hfi.record_file.writeBytes("\n");
                continue;
            }
            hfi.record_file.writeBytes("|");

        }
    }
    public Long findNextPos(Long recordPos) throws IOException {
        hfi.record_file.seek(recordPos);
        while (hfi.record_file.read() != '\n') {
            recordPos++;
        }
        return recordPos + 1;
    }
    public void Home_Action(ActionEvent e){
        this.dispose();
    }
}

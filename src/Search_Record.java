import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Search_Record extends Frame {
    HashedFileIndex hfi = new HashedFileIndex("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
    public JLabel name_enter;
    public JTextField  name_search;
    JPanel input_panel;
    JTable disp;
    JScrollPane sp;
    public Search_Record() throws IOException {
        name_enter = new JLabel("Enter a name to Search");
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
                Home_Action(e);
            }
        });
        input_panel.add(home);
        add(input_panel);
        setSize(750,750);
        setBackground(new java.awt.Color(100, 153, 153));
        setLayout(new FlowLayout());
        setTitle("Search");
        setVisible(true);
    }
    public void submit_action(ActionEvent event) throws IOException {
        if(sp!=null){
        remove(sp);}
        String[][] row = new String[1][4];
        String[] col = new String[]{"Name", "Quantity", "Active_Order", "Cost"};

        String name = name_search.getText().toString();
        String disp_line = hfi.search(name.getBytes());
        if(disp_line == null){
            JOptionPane.showMessageDialog(this, "Record not found");
        }else {

            String[] comp = disp_line.split("\\|");
            for (int i = 0; i < 4; i++) {
                row[0][i] = comp[i];
            }


            disp = new JTable(row, col);
            sp = new JScrollPane(disp);
            add(sp);
            setVisible(true);
        }
    }
    public void Home_Action(ActionEvent e){
        this.dispose();
    }
//    public static void main(String[] args) throws IOException {
//        new Search_Record().setVisible(true);
//    }


}

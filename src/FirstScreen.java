import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class FirstScreen extends Frame {


    int index = 0;
    String password = "Sensie123!";
    JButton insert  = new JButton("Insert");
    JButton delete  = new JButton("Delete")  ;
    JButton update  = new JButton("Update")  ;
    JButton display = new JButton("Display") ;
    JButton search  = new JButton("Search")  ;
    JButton back = new JButton("Back");
    JPanel user_jp1;
    Choice auth_list;
    JLabel list_label;
    JButton submit;
    JPanel auth_panel;
    JPanel editor_panel;
    JPanel adminn_panel;
    HashedFileIndex hfi;
    public static Dialog d;

    public FirstScreen(Boolean f) throws IOException{
        if(f == true)
            initparam();
        else{

        }
             hfi = new HashedFileIndex("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
    }
    public void initparam(){
        auth_panel = new JPanel(new FlowLayout());
        submit = new JButton("Ok");
        list_label = new JLabel("Select the Authorization you have:");
        auth_list = new Choice();
        auth_list.add("User");
        auth_list.add("Admin");
        auth_list.add("Editor");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Submit_Action(e);
            }
        });
        auth_panel.add(list_label);
        auth_panel.add(auth_list);
        auth_panel.add(submit);
        add(auth_panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                FileWriter fileWriter = null;
//                try {
//                    fileWriter = new FileWriter("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
//                    fileWriter.write("");
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//
//                File index_file = new File("H:\\FS MiniProject\\SupplyChainManagementSystem\\index.txt");
//                index_file.delete();
                System.exit(0);
            }
        });


        setSize(750,750);
        setVisible(true);
        setLayout(new FlowLayout());
        setTitle("Kingdom Corp");

    }

    public void Submit_Action(java.awt.event.ActionEvent e){
        index = auth_list.getSelectedIndex();
        if(index == 0){
            user_action();
        }
        else if(index == 1){
            admin_action();

        } else if (index == 2) {
            editor_action();
        }
    }
    public void admin_action(){
        JTextField pass = new JTextField(25);

        d = new Dialog(this,"Authorization");
        d.setLayout(new FlowLayout());
        d.add(new JLabel("Enter Password"));
        Button b = new Button("ok");
        d.add(pass);
        d.add(b);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(password.equals(pass.getText().toString())){
                    d.dispose();
                    set_admin();
                }else {
                    d.add(new TextField("Wrong!Enter again"));
                    d.setVisible(true);

                }
            }
        });
        d.setSize(500,500);
        d.setVisible(true);
    }
    public void set_admin(){
        remove(auth_panel);
        repaint();
        adminn_panel = new JPanel(new FlowLayout());
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Insert_Record ir = new Insert_Record();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    hfi.display();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                  Search_Record sr = new Search_Record();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Delete_Record dr = new Delete_Record();
                }catch (IOException ev){
                    throw new RuntimeException(ev);
                }
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UpdateRecord updateRecord = new UpdateRecord();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                repaint();
                dispose();
                initparam();
            }
        });
        adminn_panel.add(insert);
        adminn_panel.add(update);
        adminn_panel.add(delete);
        adminn_panel.add(search);
        adminn_panel.add(display);
        adminn_panel.add(back);
        add(adminn_panel);
        setVisible(true);
    }

    public void editor_action(){
        remove(auth_panel);
        repaint();
        editor_panel = new JPanel(new FlowLayout());
        editor_panel.add(new Label("Select:"));
        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Insert_Record ir = new Insert_Record();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HashedFileIndex hfi = new HashedFileIndex("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
                    hfi.display();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Delete_Record dr = new Delete_Record();
                }catch (IOException ev){
                    throw new RuntimeException(ev);
                }
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UpdateRecord updateRecord = new UpdateRecord();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                repaint();
                dispose();
                initparam();
            }
        });
        editor_panel.add(insert);
        editor_panel.add(delete);
        editor_panel.add(update);
        editor_panel.add(display);
        editor_panel.add(back);
        add(editor_panel);
        setVisible(true);
    }
    public void user_action(){
        remove(auth_panel);
        repaint();
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HashedFileIndex hfi = new HashedFileIndex("H:\\FS MiniProject\\SupplyChainManagementSystem\\src\\Record.txt");
                    hfi.display();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Search_Record sr = new Search_Record();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                repaint();
                dispose();
                initparam();
            }
        });
        user_jp1 = new JPanel(new FlowLayout());
        user_jp1.add(new Label("Select:"));
        user_jp1.add(display);
        user_jp1.add(search);
        user_jp1.add(back);
        add(user_jp1);
        setVisible(true);
    }


    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new FirstScreen(true).setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

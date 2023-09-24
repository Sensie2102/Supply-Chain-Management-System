import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

public class Display_Record extends Frame {

    Map<Integer,Long> map;
    public Display_Record(RandomAccessFile record_file) throws IOException {
        int col = 4;
        ArrayList column_name = new ArrayList<>();
        ArrayList row_data = new ArrayList<>();


        column_name.add("Name");
        column_name.add("Quantity");
        column_name.add("Active_Order");
        column_name.add("Cost");



        map = HashTableManager.getHashTable();
        if(map == null){
            JOptionPane.showMessageDialog(this, "Nothing to display");
            return;
        }
        String line;
        for(Integer key : map.keySet()) {

            Long value = map.get(key);
            record_file.seek(value);
            line = record_file.readLine();

            String[] comp = line.split("\\|");

            ArrayList row = new ArrayList(col);
            for(int i = 0;i<col;i++){
                row.add(comp[i]);

            }
            row_data.add(row);
        }


        Vector columnNamesVector = new Vector();

        Vector dataVector = new Vector();
        for (int i = 0; i < row_data.size(); i++)
        {
            ArrayList subArray = (ArrayList)row_data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++)
            {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }
        for ( int i = 0; i < column_name.size(); i++ )
            columnNamesVector.add(column_name.get(i));
        JTable table = new JTable(dataVector, columnNamesVector)
        {
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };
        JScrollPane scrollPane = new JScrollPane( table );
        add( scrollPane );

        JPanel buttonPanel = new JPanel();
        setBackground(new java.awt.Color(100, 153, 153));
        add( buttonPanel, BorderLayout.SOUTH );

        JButton Home=new JButton("Back");
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(Home);
        Home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home_Action(e);
            }
        });
        setSize(1000,1000);
        setVisible(true);
        setLayout(new FlowLayout());
        setTitle("Insert_Record");
        pack();
    }
    public void Home_Action(ActionEvent e){
        this.dispose();
    }

}

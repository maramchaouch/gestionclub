package ui;

import database.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class EventView extends JFrame {

    public EventView(int idClub) {
        setTitle("Club Events");
        setSize(500, 350);

        JTable table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        add(sp);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT activite, type, date_event FROM gestion_event WHERE idclub=?"
            );
            ps.setInt(1, idClub);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Activité", "Type", "Date"}, 0
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3)
                });
            }
            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}

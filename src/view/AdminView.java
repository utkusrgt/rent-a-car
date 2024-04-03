package view;

import business.BrandManager;
import entity.Brands;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scr_brand;
    private JTable tbl_brand;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private BrandManager brandManager;
    private JPopupMenu brandMenu;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.add(container);
        this.initializeGUI(400, 400);
        this.user = user;

        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText(this.user.getUsername());

        Object[] col_brand = {"Brand ID", "Brand Name"};
        ArrayList<Brands> brandsList = brandManager.findAll();
        this.tmdl_brand.setColumnIdentifiers(col_brand);
        for (Brands brands : brandsList) {
            Object[] obj = {brands.getId(), brands.getName()};
            this.tmdl_brand.addRow(obj);
        }

        this.tbl_brand.setModel(tmdl_brand);
        this.tbl_brand.getTableHeader().setReorderingAllowed(false);
        this.tbl_brand.setEnabled(false);
        this.tbl_brand.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_brand.rowAtPoint(e.getPoint());
                tbl_brand.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.brandMenu = new JPopupMenu();

        this.brandMenu.add("New").addActionListener(e -> {
            System.out.println("new");
        });
        this.brandMenu.add("Update").addActionListener(e -> {
            System.out.println("up");
        });

        this.brandMenu.add("Delete").addActionListener(e -> {
            System.out.println("Del");
        });


        this.tbl_brand.setComponentPopupMenu(brandMenu);
    }


}

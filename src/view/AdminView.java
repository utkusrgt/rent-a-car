package view;

import business.BrandManager;
import business.ModelManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane pnl;
    private JButton btn_logout;
    private JPanel pnl_brand;
    private JScrollPane scr_brand;
    private JTable tbl_brand;
    private JPanel pnl_mdel;
    private JScrollPane scroll_model;
    private JTable tbl_model;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brandMenu;
    private JPopupMenu modelMenu;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.add(container);
        this.initializeGUI(400, 400);
        this.user = user;

        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText(this.user.getUsername());

        loadBrandTable();
        loadBrandComponent();
        loadModelTable();
        tableRowSelect(this.tbl_model);
        this.modelMenu = new JPopupMenu();

        this.modelMenu.add("New").addActionListener(e -> {

        });
        this.modelMenu.add("Update").addActionListener(e -> {


        });

        this.modelMenu.add("Delete").addActionListener(e -> {


        });
        this.tbl_model.setComponentPopupMenu(modelMenu);






    }


    public void loadBrandComponent() {
        tableRowSelect(tbl_brand);

        this.brandMenu = new JPopupMenu();
        this.brandMenu.add("New").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                }
            });
        });
        this.brandMenu.add("Update").addActionListener(e -> {
            int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getByID(selectBrandId));

        });

        this.brandMenu.add("Delete").addActionListener(e -> {

            if(Helper.confirm("sure")){
                int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
                if(this.brandManager.delete(selectBrandId));
                Helper.showMsg("done");
                loadBrandTable();
            }else{
                Helper.showMsg("updateError");
            }

        });
        this.tbl_brand.setComponentPopupMenu(brandMenu);

    }

    public void loadBrandTable() {
        Object[] col_brand = {"Brand ID", "Brand Name"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);


    }

    public void loadModelTable(){
        Object[] col_model = {"Model ID" , "Brand", "Model", "Type", "Year", "Fuel", "Gear"};
        ArrayList<Object[]> modelList = this.modelManager.getForTable(col_model.length, this.modelManager.findAll());
        this.createTable(this.tmdl_model, this.tbl_model, col_model, modelList);

    }



}

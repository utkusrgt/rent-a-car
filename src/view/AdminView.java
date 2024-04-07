package view;

import business.BrandManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.Brands;
import entity.Model;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JButton btn_search;
    private JComboBox cmb_s_model_brand;
    private JComboBox lbl_s_type;
    private JComboBox cmb_s_model_fuel;
    private JComboBox cmb_s_model_gear;
    private JLabel lbl_search_brand;
    private JLabel lbl_search_type;
    private JLabel lbl_search_fuel;
    private JLabel lbl_search_gear;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private JPopupMenu brand_Menu;
    private JPopupMenu model_Menu;

    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.add(container);
        this.initializeGUI(1000, 400);
        this.user = user;

        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText(this.user.getUsername());

        loadBrandTable();
        loadBrandComponent();
        loadModelTable();
        loadModelComponent();
        loadModelFilter();
        loadModelFilterBrand();


    }

    private void loadModelComponent() {
        tableRowSelect(this.tbl_model);
        this.model_Menu = new JPopupMenu();

        this.model_Menu.add("New").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable();
                }
            });

        });
        this.model_Menu.add("Update").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getByID(selectModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable();
                }
            });
        });

        this.model_Menu.add("Delete").addActionListener(e -> {

            if(Helper.confirm("sure")){
                int selectModelID = this.getTableSelectedRow(tbl_model, 0);
                if(this.modelManager.delete(selectModelID));
                Helper.showMsg("done");
                loadModelTable();
            }else{
                Helper.showMsg("updateError");
            }
        });
        this.tbl_model.setComponentPopupMenu(model_Menu);
    }


    public void loadBrandComponent() {
        tableRowSelect(tbl_brand);

        this.brand_Menu = new JPopupMenu();
        this.brand_Menu.add("New").addActionListener(e -> {
            BrandView brandView = new BrandView(null);
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable();
                    loadModelFilterBrand();
                }
            });
        });
        this.brand_Menu.add("Update").addActionListener(e -> {
            int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
            BrandView brandView = new BrandView(this.brandManager.getByID(selectBrandId));
            brandView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadBrandTable();
                    loadModelTable();
                    loadModelFilterBrand();
                }
            });

        });

        this.brand_Menu.add("Delete").addActionListener(e -> {

            if(Helper.confirm("sure")){
                int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
                if(this.brandManager.delete(selectBrandId));
                Helper.showMsg("done");
                loadBrandTable();
                loadModelTable();
                loadModelFilterBrand();
            }else{
                Helper.showMsg("updateError");
            }

        });
        this.tbl_brand.setComponentPopupMenu(brand_Menu);

        this.btn_search.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(
                    selectedBrand.getKey(),
                    (Model.Fuel) cmb_s_model_fuel.getSelectedItem(),
                    (Model.Gear) cmb_s_model_gear.getSelectedItem(),
                    (Model.Type) lbl_s_type.getSelectedItem()
            );
            System.out.println(modelListBySearch );
        });
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

    public void loadModelFilter() {
        this.lbl_s_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.lbl_s_type.setSelectedItem(null);
        this.cmb_s_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_model_gear.setSelectedItem(null);
        this.cmb_s_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_model_fuel.setSelectedItem(null);


    }
    public void loadModelFilterBrand() {
        this.cmb_s_model_brand.removeAllItems();
        for (Brands obj : brandManager.findAll()) {
            this.cmb_s_model_brand.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_s_model_brand.setSelectedItem(null);
    }





}

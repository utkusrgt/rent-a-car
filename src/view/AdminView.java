package view;

import business.BrandManager;
import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.Brands;
import entity.Car;
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
    private JTable tbl_car;
    private JButton btn_search;
    private JComboBox cmb_s_model_brand;
    private JComboBox cmb_s_model_type;
    private JComboBox cmb_s_model_fuel;
    private JComboBox cmb_s_model_gear;
    private JLabel lbl_search_brand;
    private JLabel lbl_search_type;
    private JLabel lbl_search_fuel;
    private JLabel lbl_search_gear;
    private JButton btn_cncl_model;
    private JPanel pnl_car;
    private JScrollPane scrl_car;
    private User user;
    private DefaultTableModel tmdl_brand = new DefaultTableModel();
    private DefaultTableModel tmdl_model = new DefaultTableModel();
    private DefaultTableModel tmdl_car = new DefaultTableModel();
    private BrandManager brandManager;
    private ModelManager modelManager;
    private CarManager carManager;
    private JPopupMenu brand_Menu;
    private JPopupMenu model_Menu;
    private JPopupMenu car_menu;
    private Object[] col_model;


    public AdminView(User user) {
        this.brandManager = new BrandManager();
        this.modelManager = new ModelManager();
        this.carManager = new CarManager();
        this.add(container);
        this.initializeGUI(1000, 400);
        this.user = user;

        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText(this.user.getUsername());


        //Brand Tab
        loadBrandTable();
        loadBrandComponent();

        //Model Tab

        loadModelTable(null);
        loadModelComponent();
        loadModelFilter();

        //Car Tab
        loadCarTable();
        loadCarComponent();


    }

    public void loadCarComponent(){
        tableRowSelect(this.tbl_car);
        this.car_menu = new JPopupMenu();
        this.car_menu.add("New").addActionListener(e -> {
            CarView carView = new CarView(new Car());
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });
        });

        this.car_menu.add("Update").addActionListener(e -> {
            int selectCarId = this.getTableSelectedRow(tbl_car, 0);
            CarView carView = new CarView(this.carManager.getByID(selectCarId));
            carView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCarTable();
                }
            });



        });this.car_menu.add("Delete").addActionListener(e -> {

            if(Helper.confirm("sure")){
                int selectCarID = this.getTableSelectedRow(tbl_car, 0);
                if(this.carManager.delete(selectCarID));
                Helper.showMsg("done");
                loadModelTable(null);
            }else{
                Helper.showMsg("updateError");
            }
        });
        this.tbl_car.setComponentPopupMenu(car_menu);



    }










    private void loadModelComponent() {
        tableRowSelect(this.tbl_model);
        this.model_Menu = new JPopupMenu();

        this.model_Menu.add("New").addActionListener(e -> {
            ModelView modelView = new ModelView(new Model());
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);

                }
            });

        });
        this.model_Menu.add("Update").addActionListener(e -> {
            int selectModelId = this.getTableSelectedRow(tbl_model, 0);
            ModelView modelView = new ModelView(this.modelManager.getByID(selectModelId));
            modelView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadModelTable(null);
                    loadCarTable();
                }
            });
        });

        this.model_Menu.add("Delete").addActionListener(e -> {

            if(Helper.confirm("sure")){
                int selectModelID = this.getTableSelectedRow(tbl_model, 0);
                if(this.modelManager.delete(selectModelID));
                Helper.showMsg("done");
                loadModelTable(null);
                loadCarTable();
            }else{
                Helper.showMsg("updateError");
            }
        });
        this.tbl_model.setComponentPopupMenu(model_Menu);

        this.btn_search.addActionListener(e -> {
            ComboItem selectedBrand = (ComboItem) this.cmb_s_model_brand.getSelectedItem();
            int brandID = 0;
            if(selectedBrand != null){
                brandID = selectedBrand.getKey();
            }

            ArrayList<Model> modelListBySearch = this.modelManager.searchForTable(
                    brandID,
                    (Model.Fuel) cmb_s_model_fuel.getSelectedItem(),
                    (Model.Gear) cmb_s_model_gear.getSelectedItem(),
                    (Model.Type) cmb_s_model_type.getSelectedItem()
            );
            ArrayList<Object[]> modelRowListBySearch = this.modelManager.getForTable(this.col_model.length, modelListBySearch);
            loadModelTable(modelRowListBySearch);


        });

        this.btn_cncl_model.addActionListener(e -> {

            this.cmb_s_model_type.setSelectedItem(null);
            this.cmb_s_model_gear.setSelectedItem(null);
            this.cmb_s_model_fuel.setSelectedItem(null);
            this.cmb_s_model_brand.setSelectedItem(null);
            loadModelTable(null);

        });


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
                    loadModelTable(null);
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
                    loadModelTable(null);
                    loadModelFilterBrand();
                    loadCarTable();
                }
            });

        });

        this.brand_Menu.add("Delete").addActionListener(e -> {

            if(Helper.confirm("sure")){
                int selectBrandId = this.getTableSelectedRow(tbl_brand, 0);
                if(this.brandManager.delete(selectBrandId));
                Helper.showMsg("done");
                loadBrandTable();
                loadModelTable(null);
                loadModelFilterBrand();
                loadCarTable();
            }else{
                Helper.showMsg("updateError");
            }

        });
        this.tbl_brand.setComponentPopupMenu(brand_Menu);


    }

    public void loadBrandTable() {
        Object[] col_brand = {"Brand ID", "Brand Name"};
        ArrayList<Object[]> brandList = this.brandManager.getForTable(col_brand.length);
        this.createTable(this.tmdl_brand, this.tbl_brand, col_brand, brandList);


    }

    public void loadModelTable(ArrayList<Object[]> modelList){
        this.col_model = new Object[]{"Model ID" , "Brand", "Model", "Type", "Year", "Fuel", "Gear"};
        if(modelList == null){
            modelList = this.modelManager.getForTable(this.col_model.length, this.modelManager.findAll());
        }

        this.createTable(this.tmdl_model, this.tbl_model, col_model, modelList);

    }

    public void loadModelFilter() {
        this.cmb_s_model_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_s_model_type.setSelectedItem(null);
        this.cmb_s_model_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_s_model_gear.setSelectedItem(null);
        this.cmb_s_model_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));
        this.cmb_s_model_fuel.setSelectedItem(null);
        loadModelFilterBrand();


    }
    public void loadModelFilterBrand() {
        this.cmb_s_model_brand.removeAllItems();
        for (Brands obj : brandManager.findAll()) {
            this.cmb_s_model_brand.addItem(new ComboItem(obj.getId(), obj.getName()));
        }
        this.cmb_s_model_brand.setSelectedItem(null);
    }

    public void loadCarTable(){

        Object[] col_car = {"ID", "Brand", "Model", "Plate", "Color", "KM", "Year","Type" , "Fuel", "Gear"};
        ArrayList<Object[]> carList = this.carManager.getForTable(col_car.length, this.carManager.findAll());
        createTable(this.tmdl_car, this.tbl_car, col_car, carList);



    }





    }

package view;

import business.BrandManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.Brands;
import entity.Model;

import javax.swing.*;

public class ModelView extends Layout {
    private JPanel container;
    private JLabel lbl_header;
    private JLabel lbl_brand;
    private JComboBox<ComboItem> cmb_brand;
    private JLabel lbl_mdl;
    private JTextField fld_mdl_name;
    private JLabel lbl_year;
    private JTextField fld_mdl_year;
    private JLabel mdl_type;
    private JComboBox<Model.Type> cmb_type;
    private JLabel lbl_fuel;
    private JComboBox<Model.Fuel> cmb_mdl_fuel;
    private JLabel fld_gear;
    private JComboBox<Model.Gear> cmb_gear;
    private JButton btn_save;
    private Model model;
    private ModelManager modelManager;
    private BrandManager brandManager;

    public ModelView(Model model) {
        this.modelManager = new ModelManager();
        this.brandManager = new BrandManager();
        this.model = model;
        this.add(container);
        this.initializeGUI(300, 500);

        for(Brands brands : this.brandManager.findAll()){
            this.cmb_brand.addItem(new ComboItem(brands.getId(), brands.getName()));
        }
        this.cmb_type.setModel(new DefaultComboBoxModel<>(Model.Type.values()));
        this.cmb_gear.setModel(new DefaultComboBoxModel<>(Model.Gear.values()));
        this.cmb_mdl_fuel.setModel(new DefaultComboBoxModel<>(Model.Fuel.values()));

        if (this.model.getId() != 0){
            this.fld_mdl_year.setText(this.model.getYear());
            this.fld_mdl_name.setText(this.model.getName());
            this.cmb_mdl_fuel.getModel().setSelectedItem(this.model.getFuel());
            this.cmb_type.getModel().setSelectedItem(this.model.getType());
            this.cmb_gear.getModel().setSelectedItem(this.model.getGear());
            ComboItem defaultBrand = new ComboItem(this.model.getBrand().getId(), this.model.getBrand().getName());
            this.cmb_brand.getModel().setSelectedItem(defaultBrand);
        }




        this.btn_save.addActionListener(e ->{
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_mdl_name, this.fld_mdl_year})){
                Helper.showMsg("fill");

            }else{
                boolean result;
                ComboItem selectedBrand = (ComboItem) cmb_brand.getSelectedItem();
                this.model.setYear(fld_mdl_year.getText());
                this.model.setName((fld_mdl_name.getText()));
                this.model.setBrand_id(selectedBrand.getKey());
                this.model.setType((Model.Type) cmb_type.getSelectedItem());
                this.model.setGear((Model.Gear) cmb_gear.getSelectedItem());
                this.model.setFuel((Model.Fuel) cmb_mdl_fuel.getSelectedItem());
                if(this.model.getId() != 0){
                    result = this.modelManager.update(this.model);

                }else{
                    result = this.modelManager.save(this.model);
                }
                if(result){
                    Helper.showMsg("done");
                    dispose();
                }else{
                    Helper.showMsg("error");
                }

            }
        } );
    }

}

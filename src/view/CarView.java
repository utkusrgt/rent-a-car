package view;

import business.CarManager;
import business.ModelManager;
import core.ComboItem;
import core.Helper;
import entity.Car;
import entity.Model;

import javax.swing.*;

public class CarView extends Layout{
    private JPanel panel1;
    private JPanel container;
    private JLabel lbl_add_car;
    private JLabel lbl_model;
    private JComboBox cmb_model;
    private JComboBox cmb_color;
    private JComboBox cmb_plate;
    private JButton btn_car_save;
    private JLabel lbl_color;
    private JLabel lbl_KM;
    private JLabel lbl_plate;
    private JTextField fld_KM;
    private JTextField fld_plate;
    private Car car;
    private CarManager carManager;
    private ModelManager modelManager;

    public CarView(Car car) {
        this.car = car;
        this.carManager = new CarManager();
        this.modelManager = new ModelManager();
        this.add(container);
        this.initializeGUI(300, 500);


        this.cmb_color.setModel( new DefaultComboBoxModel<>(Car.Color.values()));
        for (Model model : this.modelManager.findAll()){
            this.cmb_model.addItem(model.getComboItem());
        }

        if(this.car.getId() != 0){
            ComboItem selectedItem = car.getModel().getComboItem();
            this.cmb_model.getModel().setSelectedItem(selectedItem);
            this.cmb_color.getModel().setSelectedItem(car.getColor());
            this.fld_plate.setText(car.getPlate());
            this.fld_KM.setText(Integer.toString(car.getKm()));
        }

        this.btn_car_save.addActionListener(e ->{
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_KM, this.fld_plate})){
                Helper.showMsg("fill");

            }else{
                boolean result;
                ComboItem selectedModel = (ComboItem) this.cmb_model.getSelectedItem();
                this.car.setModel_id(selectedModel.getKey());
                this.car.setColor((Car.Color) this.cmb_color.getSelectedItem());
                this.car.setPlate(this.fld_plate.getText());
                this.car.setKm(Integer.parseInt(this.fld_KM.getText()));
                if(this.car.getId() != 0){
                    result = this.carManager.update(this.car);

                }else{
                    result = this.carManager.save(this.car);
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




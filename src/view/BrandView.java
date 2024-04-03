package view;

import business.BrandManager;
import core.Helper;
import entity.Brands;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrandView extends Layout {
    private JPanel container;
    private JLabel lbl_brand;
    private JLabel lbl_brand_name;
    private JTextField flf_brand_name;
    private JButton btn_brand_save;
    private Brands brands;
    private BrandManager brandManager;

    public BrandView(Brands brands) {
        this.brandManager = new BrandManager();
        this.brands = brands;
        this.add(container);
        this.initializeGUI(300, 500);


        if (brands != null) {
            this.flf_brand_name.setText(brands.getName());
        }
        btn_brand_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.flf_brand_name)) {
                Helper.showMsg("fill");

            } else {
                boolean result;
                if (this.brands == null) {
                    Brands obj = new Brands(flf_brand_name.getText());
                    result = this.brandManager.save(obj);
                } else {
                    this.brands.setName(flf_brand_name.getText());
                    result = this.brandManager.update(this.brands);
                }

                if (result) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }

        });
    }
}

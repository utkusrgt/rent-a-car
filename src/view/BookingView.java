package view;

import business.BookManager;
import core.Helper;
import entity.Book;
import entity.Car;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookingView extends Layout {
    private JPanel container;
    private JLabel lbl_header;
    private JLabel lbl_car_info;
    private JLabel lbl_customer_name;
    private JTextField fld_customer_name;
    private JLabel lbl_idno;
    private JTextField fldl_book_idno;
    private JTextField fld_book_mp;
    private JTextField fld_book_email;
    private JTextField fld_book_strt_date;
    private JLabel lbl_customer_mp;
    private JLabel lbl_customer_email;
    private JLabel lbl_strt_date;
    private JTextField fld_book_fnsh_date;
    private JLabel lbl_fnsh_date;
    private JLabel pnl_price;
    private JTextField fld_book_price;
    private JLabel lbl_note;
    private JTextField fld_book_notes;
    private JButton btn_rent_save;
    private Car car;
    private BookManager bookManager;


    public BookingView(Car selectedCar, String strt_date, String fnsh_date) {
        this.add(container);
        this.car = selectedCar;
        this.bookManager = new BookManager();

        initializeGUI(400, 600);

        lbl_car_info.setText("Car : " +
                this.car.getPlate() + " / " +
                this.car.getModel().getBrand().getName() + " / " +
                this.car.getModel().getName());

        this.fld_book_strt_date.setText(strt_date);
        this.fld_book_fnsh_date.setText(fnsh_date);

        //test

        /*this.fld_customer_name.setText("Utku Sürgit");
        this.fldl_book_idno.setText("286543561");
        this.fld_book_mp.setText("5848618694");
        this.fld_book_email.setText("Test@test.com");
        this.fld_book_price.setText("2154");
        this.fld_book_notes.setText("Not");*/

        btn_rent_save.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_customer_name,
                    this.fldl_book_idno,
                    this.fld_book_mp,
                    this.fld_book_email,
                    this.fld_book_price,
                    this.fld_book_strt_date,
                    this.fld_book_fnsh_date
            };
            if(Helper.isFieldListEmpty(checkFieldList)){
                Helper.showMsg("fill");
            }else{
                Book book = new Book();
                book.setbCase("done");
                book.setCar_id(this.car.getId());
                book.setName(this.fld_customer_name.getText());
                book.setStrt_date(LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setFnsh_date(LocalDate.parse(fnsh_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setIdno(this.fldl_book_idno.getText());
                book.setMpno(this.fld_book_mp.getText());
                book.setMail(this.fld_book_email.getText());
                book.setNote(this.fld_book_notes.getText());
                book.setPrc(Integer.parseInt(this.fld_book_price.getText()));

                if(this.bookManager.save(book)){
                    Helper.showMsg("done");
                    dispose();
                }else{
                    Helper.showMsg("saveError");
                }


            }
        });
    }
}

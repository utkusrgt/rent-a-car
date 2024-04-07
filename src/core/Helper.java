package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static void setTheme(){
        String theme = "Nimbus";
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if(theme.equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;

            }
        }
    }

    public static void showMsg(String str){

        String msg;
        String title;

        switch (str){
            case "fill":
                msg = "Please fill in the blank fields";
                title = "Warning";
                break;
            case "done":
                msg = "Succesfull";
                title = "Warning";
                break;
            case "notFound":
                msg = "Login unsuccesfull";
                title = "Warning";
                break;
            case "saveError":
                msg = "Save already exists!";
                title = "Warning";
                break;
            case "updateError":
                msg = "Save not found!";
                title = "Warning";
                break;
            default:
                msg = str;
                title = "Result";
                break;
        }

        JOptionPane.showMessageDialog(null, msg , title, JOptionPane.INFORMATION_MESSAGE);

    }


    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fieldlist){
        for (JTextField field : fieldlist){
            if(isFieldEmpty(field))return true;
        }
        return false;
    }

    public static int getLocationPoint(String type, Dimension size){
        return switch (type){
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;

            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;

            default -> 0;

        };

    }

    public static boolean confirm(String str){
        String msg;
        if(str.equals("sure")){
            msg = "This will delete the item completely, please confirm";
        }else{
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null,msg, "Are you sure? ", JOptionPane.YES_NO_OPTION) == 0;
    }
}

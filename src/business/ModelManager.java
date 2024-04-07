package business;

import core.Helper;
import dao.ModelDao;
import entity.Brands;
import entity.Model;

import java.util.ArrayList;

public class ModelManager {

    private final ModelDao modelDao = new ModelDao();

    public Model getByID(int id) {
        return this.modelDao.getById(id);
    }

    public ArrayList<Model> findAll() {
        return this.modelDao.findAll();
    }
    public ArrayList<Object[]> getForTable(int size, ArrayList<Model> modelList) {
        ArrayList<Object[]> modelObjectList = new ArrayList<>();
        for (Model obj : modelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getBrand().getName();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getType();
            rowObject[i++] = obj.getYear();
            rowObject[i++] = obj.getFuel();
            rowObject[i++] = obj.getGear();
            modelObjectList.add(rowObject);

        }
        return modelObjectList;
    }

    public boolean save(Model model) {
        if (this.getByID(model.getId()) != null) {
            Helper.showMsg("saveError");
        }
        return this.modelDao.save(model);

    }


    public boolean update(Model model) {
        if (this.getByID(model.getId()) != null) {
            Helper.showMsg("updateError");
            return false;

        }
        return this.modelDao.update(model);
    }

    public boolean delete(int id){
        if(this.getByID(id) == null){
            Helper.showMsg("updateError");
            return false;
        }
        return this.modelDao.delete(id);


    }




}

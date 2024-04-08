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
            return false;
        }
        return this.modelDao.save(model);

    }


    public boolean update(Model model) {
        if (this.getByID(model.getId()) == null) {
            Helper.showMsg(model.getId() + "updateError");
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

    public ArrayList<Model> getByListBrandID(int brandID) {
        return this.modelDao.getByListBrandID(brandID);
    }

    public ArrayList<Model> searchForTable(int brandID, Model.Fuel fuel, Model.Gear gear, Model.Type type){
        String select = "SELECT * FROM public.model";
        ArrayList<String> whereList = new ArrayList<>();

        if(brandID != 0){
            whereList.add("model_brand_id = " + brandID);
        }

        if(fuel != null){
            whereList.add("model_fuel = '" + fuel.toString() + "'");
        }
        if(gear != null){
            whereList.add("model_gear = '" + gear.toString() + "'");
        }
        if(type != null){
            whereList.add("model_type = '" + type.toString() + "'");
        }

        String whereStr = String.join(" AND ", whereList);
        String query = select;
        if(whereStr.length() > 0 ){
            query += " WHERE " + whereStr;
        }


        return this.modelDao.selectByQuery(query);





    }





}

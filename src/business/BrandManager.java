package business;

import core.Helper;
import dao.BrandDao;
import entity.Brands;
import entity.Model;
//import jdk.internal.icu.text.NormalizerBase;
//mport sun.security.mscapi.CPublicKey;

import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;
    private final ModelManager modelManager;

    public BrandManager() {
        this.brandDao = new BrandDao();
        this.modelManager = new ModelManager();
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> brandRowLsit = new ArrayList<>();
        for (Brands brands : this.findAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = brands.getId();
            rowObject[i++] = brands.getName();
            brandRowLsit.add(rowObject);

        }
        return brandRowLsit;
    }


        public ArrayList<Brands> findAll() {
            return this.brandDao.findAll();
        }

    public boolean save(Brands brands) {
        if (brands.getId() != 0) {
            Helper.showMsg("saveError");
        }
        return this.brandDao.save(brands);

    }

    public Brands getByID(int id) {
        return this.brandDao.getById(id);
    }


    public boolean update(Brands brands) {
        if (this.getByID(brands.getId()) == null) {
            Helper.showMsg("updateError");

        }
        return this.brandDao.update(brands);
    }

    public boolean delete(int id){
        if(this.getByID(id) == null){
            Helper.showMsg("updateError");
            return false;
        }
        for(Model model : this.modelManager.getByListBrandID(id)){
            this.modelManager.delete(model.getId());
        }
        return this.brandDao.delete(id);


    }

}

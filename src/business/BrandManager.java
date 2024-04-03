package business;

import core.Helper;
import dao.BrandDao;
import entity.Brands;

import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;

    public BrandManager() {
        this.brandDao = new BrandDao();
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
}

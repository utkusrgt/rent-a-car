package business;

import dao.BrandDao;
import entity.Brands;

import java.util.ArrayList;

public class BrandManager {
    private final BrandDao brandDao;

    public BrandManager() {
        this.brandDao = new BrandDao();
    }


    public ArrayList<Brands> findAll(){
        return this.brandDao.findAll();
    }
}

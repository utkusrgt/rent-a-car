package dao;

import core.Db;
import entity.Brands;
import entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDao {
    private final Connection con;

    public BrandDao() {
        this.con = Db.getInstance();
    }

    public ArrayList<Brands> findAll(){
        ArrayList<Brands> brandList = new ArrayList<>();
        String sql = "SELECT * FROM public.brand";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while (rs.next()){
                brandList.add(this.match(rs));

            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return brandList;
    }
    public Brands match(ResultSet rs) throws SQLException {
        Brands obj = new Brands();
        obj.setId(rs.getInt("brand_id"));
        obj.setName(rs.getString("brand_name"));


        return  obj;

    }
}

package hanhit.mywalletapp.model;

import java.io.Serializable;

/**
 * Created by hanh.tran on 6/16/2016.
 */
public class Category implements Serializable{
    private int idCategory;
    private String nameCategory;
    private String nameIconCategory;

    public Category(int idCategory, String nameCategory, String nameIconCategory) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.nameIconCategory = nameIconCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public String getNameIconCategory() {
        return nameIconCategory;
    }

    public boolean isCategory(){
        return true;
    }

}

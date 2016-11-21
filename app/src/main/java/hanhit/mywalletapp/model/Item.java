package hanhit.mywalletapp.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by hanh.tran on 6/16/2016.
 */
public class Item implements Serializable {
    private int idItem;
    private int typeItem;
    private String nameItem;
    private String dateItem;
    private String hourItem;
    private String valueItem;
    private int idCategoryItem;

    public Item(int idItem, int typeItem, String nameItem, String dateItem, String hourItem, String valueItem, int idCategoryItem) {
        this.idItem = idItem;
        this.typeItem = typeItem;
        this.nameItem = nameItem;
        this.dateItem = dateItem;
        this.hourItem = hourItem;
        this.valueItem = valueItem;
        this.idCategoryItem = idCategoryItem;
    }

    public int getIdItem() {
        return idItem;
    }

    public int getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(int typeItem) {
        this.typeItem = typeItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getDateItem() {
        return dateItem;
    }

    public void setDateItem(String dateItem) {
        this.dateItem = dateItem;
    }

    public String getValueItem() {
        return valueItem;
    }

    public void setValueItem(String valueItem) {
        this.valueItem = valueItem;
    }

    public int getIdCategoryItem() {
        return idCategoryItem;
    }

    public void setIdCategoryItem(int idCategoryItem) {
        this.idCategoryItem = idCategoryItem;
    }

    public boolean isItem() {
        return true;
    }

    public String getHourItem() {
        return hourItem;
    }

    public void setHourItem(String hourItem) {
        this.hourItem = hourItem;
    }
}

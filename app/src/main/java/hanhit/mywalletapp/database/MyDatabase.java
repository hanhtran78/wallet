package hanhit.mywalletapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import hanhit.mywalletapp.MyHandle;
import hanhit.mywalletapp.model.Category;
import hanhit.mywalletapp.model.DateOfItem;
import hanhit.mywalletapp.model.Item;

/**
 * Created by hanh.tran on 6/17/2016.
 */
public class MyDatabase extends SQLiteOpenHelper {

    private MyHandle myHandle = new MyHandle();

    // Table Items
    public static final String TABLE_ITEMS = "items";
    public static final String COL_ID_ITEM = "idItem";
    public static final String COL_TYPE_ITEM = "typeItem";
    public static final String COL_NAME_ITEM = "nameItem";
    public static final String COL_DATE_ITEM = "dateItem";
    public static final String COL_HOUR_ITEM = "hourOfItem";
    public static final String COL_VALUE_ITEM = "valueItem";
    public static final String COL_CATEGORY_ID_ITEM = "categoryIdItem";

    // Create table
    private static final String CREATE_TABLE_ITEM = "create table " + TABLE_ITEMS + "("
            + COL_ID_ITEM + " integer primary key autoincrement, "
            + COL_TYPE_ITEM + " integer not null, "
            + COL_NAME_ITEM + " text, "
            + COL_DATE_ITEM + " text not null, "
            + COL_HOUR_ITEM + " text not null, "
            + COL_VALUE_ITEM + " text not null, "
            + COL_CATEGORY_ID_ITEM + " integer not null);";

    // Table Categories
    public static final String TABLE_CATEGORY = "categories";
    public static final String COL_ID_CATEGORY = "idCategory";
    public static final String COL_NAME_CATEGORY = "nameCategory";
    public static final String COL_NAME_IMAGE = "nameImage";

    //Create table Category
    private static final String CREATE_TABLE_CATEGORY = "create table " + TABLE_CATEGORY + "("
            + COL_ID_CATEGORY + " integer primary key autoincrement, "
            + COL_NAME_CATEGORY + " text not null, "
            + COL_NAME_IMAGE + " integer not null);";

    private static final String DATABASE_NAME = "my_wallet";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEM);
        db.execSQL(CREATE_TABLE_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(db);
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from categories where idCategory = " + id + "", null);
        res.moveToFirst();
        Category category = new Category(
                res.getInt(res.getColumnIndex(COL_ID_CATEGORY)),
                res.getString(res.getColumnIndex(COL_NAME_CATEGORY)),
                res.getString(res.getColumnIndex(COL_NAME_IMAGE))
        );
        return category;
    }

    public int numberItem() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_ITEMS);
        return numRows;
    }

    public boolean insertItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID_ITEM, item.getIdItem());
        cv.put(COL_TYPE_ITEM, item.getTypeItem());
        cv.put(COL_NAME_ITEM, item.getNameItem());
        cv.put(COL_DATE_ITEM, item.getDateItem());
        cv.put(COL_HOUR_ITEM, item.getHourItem());
        cv.put(COL_VALUE_ITEM, item.getValueItem());
        cv.put(COL_CATEGORY_ID_ITEM, item.getIdCategoryItem());

        if (db.insert(TABLE_ITEMS, null, cv) != 0)
            return true;
        return false;
    }

    public boolean insertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ID_CATEGORY, category.getIdCategory());
        cv.put(COL_NAME_CATEGORY, category.getNameCategory());
        cv.put(COL_NAME_IMAGE, category.getNameIconCategory());
        if ((db.insert(TABLE_CATEGORY, null, cv) != 0))
            return true;
        return false;
    }

    public ArrayList<String> getAllNameCategory() {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + COL_NAME_CATEGORY + " from " + TABLE_CATEGORY, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(
                    res.getString(res.getColumnIndex(COL_NAME_CATEGORY)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CATEGORY, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(new Category(
                    res.getInt(res.getColumnIndex(COL_ID_CATEGORY)),
                    res.getString(res.getColumnIndex(COL_NAME_CATEGORY)),
                    res.getString(res.getColumnIndex(COL_NAME_IMAGE)))
            );
            res.moveToNext();
        }
        return array_list;
    }

    public boolean updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TYPE_ITEM, item.getTypeItem());
        cv.put(COL_NAME_ITEM, item.getNameItem());
        cv.put(COL_DATE_ITEM, item.getDateItem());
        cv.put(COL_HOUR_ITEM, item.getHourItem());
        cv.put(COL_VALUE_ITEM, item.getValueItem());
        cv.put(COL_CATEGORY_ID_ITEM, item.getIdCategoryItem());
        db.update(TABLE_ITEMS, cv, COL_ID_ITEM + " = " + item.getIdItem(), null);
        return true;
    }

    public Integer deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEMS, COL_ID_ITEM + " = " + id, null);
    }

    public int getIdOfLastItem() {
        ArrayList<Item> items = getAllItem();
        if (items.size() == 0) {
            return 0;
        } else {
            return items.get(items.size() - 1).getIdItem();
        }
    }

    public ArrayList<Item> getAllItem() {
        ArrayList<Item> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from items", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(new Item(
                    res.getInt(res.getColumnIndex(COL_ID_ITEM)),
                    res.getInt(res.getColumnIndex(COL_TYPE_ITEM)),
                    res.getString(res.getColumnIndex(COL_NAME_ITEM)),
                    res.getString(res.getColumnIndex(COL_DATE_ITEM)),
                    res.getString(res.getColumnIndex(COL_HOUR_ITEM)),
                    res.getString(res.getColumnIndex(COL_VALUE_ITEM)),
                    res.getInt(res.getColumnIndex(COL_CATEGORY_ID_ITEM)))
            );
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Item> getAllItemByDate(String date) {
        ArrayList<Item> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from items", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            if (res.getString(res.getColumnIndex(COL_DATE_ITEM)).equals(date)) {
                array_list.add(new Item(
                        res.getInt(res.getColumnIndex(COL_ID_ITEM)),
                        res.getInt(res.getColumnIndex(COL_TYPE_ITEM)),
                        res.getString(res.getColumnIndex(COL_NAME_ITEM)),
                        res.getString(res.getColumnIndex(COL_DATE_ITEM)),
                        res.getString(res.getColumnIndex(COL_HOUR_ITEM)),
                        res.getString(res.getColumnIndex(COL_VALUE_ITEM)),
                        res.getInt(res.getColumnIndex(COL_CATEGORY_ID_ITEM)))
                );
            }
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Item> getAllItemByMonth(String month) {
        ArrayList<Item> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from items", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            if (myHandle.getMonth(res.getString(res.getColumnIndex(COL_DATE_ITEM))).equals(month)) {
                array_list.add(new Item(
                        res.getInt(res.getColumnIndex(COL_ID_ITEM)),
                        res.getInt(res.getColumnIndex(COL_TYPE_ITEM)),
                        res.getString(res.getColumnIndex(COL_NAME_ITEM)),
                        res.getString(res.getColumnIndex(COL_DATE_ITEM)),
                        res.getString(res.getColumnIndex(COL_HOUR_ITEM)),
                        res.getString(res.getColumnIndex(COL_VALUE_ITEM)),
                        res.getInt(res.getColumnIndex(COL_CATEGORY_ID_ITEM)))
                );
            }
            res.moveToNext();
        }
        return sortItemByDate(array_list);
    }

    public ArrayList<DateOfItem> getAllDateOfItemByMonth(String month) {
        ArrayList<Item> items = sortItemByDate(getAllItemByMonth(month));
        ArrayList<DateOfItem> dateOfItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            DateOfItem date = new DateOfItem(items.get(i).getDateItem());
            if (!checkDateIsExist(dateOfItems, date))
                dateOfItems.add(date);
        }

        return dateOfItems;
    }

    public boolean checkDateIsExist(ArrayList<DateOfItem> dateOfItems, DateOfItem date) {
        for (int i = 0; i < dateOfItems.size(); i++)
            if (dateOfItems.get(i).getDate().equals(date.getDate()))
                return true;
        return false;
    }

    public List<Object> getObjects(String month) {
        List<Object> objects = new ArrayList<>();
        ArrayList<DateOfItem> listDate = getAllDateOfItemByMonth(month);

        for (int i = 0; i < listDate.size(); i++) {
            DateOfItem date = listDate.get(i);
            objects.add(date);
            ArrayList<Item> listItem = getAllItemByDate(date.getDate());
            for (int j = 0; j < listItem.size(); j++) {
                objects.add(listItem.get(j));
            }
        }

        return objects;
    }

    public ArrayList<Item> getAllItemByMonthAndIdCategory(String month, int id) {
        ArrayList<Item> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from items", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            if (myHandle.getMonth(res.getString(res.getColumnIndex(COL_DATE_ITEM))).equals(month) &&
                    res.getInt(res.getColumnIndex(COL_CATEGORY_ID_ITEM)) == id) {
                array_list.add(new Item(
                        res.getInt(res.getColumnIndex(COL_ID_ITEM)),
                        res.getInt(res.getColumnIndex(COL_TYPE_ITEM)),
                        res.getString(res.getColumnIndex(COL_NAME_ITEM)),
                        res.getString(res.getColumnIndex(COL_DATE_ITEM)),
                        res.getString(res.getColumnIndex(COL_HOUR_ITEM)),
                        res.getString(res.getColumnIndex(COL_VALUE_ITEM)),
                        res.getInt(res.getColumnIndex(COL_CATEGORY_ID_ITEM)))
                );
            }
            res.moveToNext();
        }

        return sortItemByDate(array_list);
    }

    public BigInteger[] getValueAllByMonth(String month) {
        BigInteger sumIncome = BigInteger.valueOf(0);
        BigInteger sumExpense = BigInteger.valueOf(0);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select  * from items", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            if (myHandle.getMonth(res.getString(res.getColumnIndex(COL_DATE_ITEM))).equals(month)) {
                if (res.getInt(res.getColumnIndex(COL_TYPE_ITEM)) == 0) {
                    BigInteger income = BigInteger.valueOf(Integer.parseInt(res.getString(res.getColumnIndex(COL_VALUE_ITEM))));
                    sumIncome = sumIncome.add(income);
                } else {
                    BigInteger expense = BigInteger.valueOf(Integer.parseInt(res.getString(res.getColumnIndex(COL_VALUE_ITEM))));
                    sumExpense = sumExpense.add(expense);
                }
            }
            res.moveToNext();
        }
        BigInteger[] values = new BigInteger[]{sumIncome, sumExpense};
        return values;
    }

    public List getObjectByMonth(String month) {
        List objects = new ArrayList();
        ArrayList<Category> categoryList = getCategories();
        ArrayList<Item> itemList;

        for (int i = 0; i < categoryList.size(); i++) {
            itemList = getAllItemByMonthAndIdCategory(month, i);
            if (itemList.size() != 0) {
                objects.add(getCategory(i));
                for (int j = 0; j < itemList.size(); j++) {
                    objects.add(itemList.get(j));
                }
            }
        }

        return objects;
    }

    public BigInteger getValueByDate(String date) {
        BigInteger sumIncome = BigInteger.valueOf(0);
        BigInteger sumExpense = BigInteger.valueOf(0);

        ArrayList<Item> arrayList = getAllItemByDate(date);
        for (int i = 0; i < arrayList.size(); i++)
            if (arrayList.get(i).getTypeItem() == 0) {
                BigInteger income = BigInteger.valueOf(Integer.parseInt(arrayList.get(i).getValueItem()));
                sumIncome = sumIncome.add(income);
            } else {
                BigInteger expense = BigInteger.valueOf(Integer.parseInt(arrayList.get(i).getValueItem()));
                sumExpense = sumExpense.add(expense);
            }

        return sumIncome.subtract(sumExpense);
    }

    public ArrayList<Item> sortItemByDate(ArrayList<Item> items) {
        Collections.sort(items, new CustomComparator());
        return items;
    }
}

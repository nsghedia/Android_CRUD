package com.builddream.sqlapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "tCustomer";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CUSTOMER_NAME = "Customer_Name";
    public static final String COLUMN_CUSTOMER_AGE = "Customer_Age";
    public static final String COLUMN_CUSTOMER_IS_ACTIVE = "Customer_IsActive";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "Customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "Create Table " + CUSTOMER_TABLE + " (" + COLUMN_ID + " Integer primary key Autoincrement," + COLUMN_CUSTOMER_NAME + " Text," + COLUMN_CUSTOMER_AGE + " Integer," + COLUMN_CUSTOMER_IS_ACTIVE + " Bool)";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean addOne(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModel.getAge());
        cv.put(COLUMN_CUSTOMER_IS_ACTIVE, customerModel.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1)
            return false;
        else
            return true;
    }

    public List<CustomerModel> getCustomerList() {
        List<CustomerModel> customerModels = new ArrayList<>();

        String q = "Select * from " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(q, null);
        if (cursor.moveToFirst()) {
            do {
                int cId = cursor.getInt(0);
                String cName = cursor.getString(1);
                int cAge = cursor.getInt(2);
                boolean cIsActive = cursor.getInt(3) == 1 ? true : false;

                CustomerModel customerModel = new CustomerModel(cId, cName, cAge, cIsActive);
                customerModels.add(customerModel);

            } while (cursor.moveToNext());

        } else {

        }
        cursor.close();
        db.close();
        return customerModels;
    }

    public boolean deleteOne(CustomerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String q = "Delete from " + CUSTOMER_TABLE + " where " + COLUMN_ID + " = " + customerModel.getId();

        Cursor cursor = db.rawQuery(q, null);
        if (cursor.moveToFirst())
            return true;
        else
            return false;
    }
}

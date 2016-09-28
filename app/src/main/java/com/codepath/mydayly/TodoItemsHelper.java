package com.codepath.mydayly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lramaswamy on 4/18/2016.
 */
public class TodoItemsHelper extends SQLiteOpenHelper {
    private static int VERSION = 1;
    private static String DATABASE_NAME = "TodoItems.db";
    private static TodoItemsHelper sInstance;
    private static Context sContext;
    public static final String UUID = "uuid";
    public static final String CATEGORY = "category";
    public static final String ITEM_NAME = "itemName";
    public static final String PRIORITY = "priority";
    public static final String DUE_BY = "dueBy";
    public static final String TABLE_NAME = "TodoItems";

    private TodoItemsHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static synchronized TodoItemsHelper getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new TodoItemsHelper(context.getApplicationContext());
            sContext = context.getApplicationContext();
        }
        return sInstance;
    }

    public static synchronized TodoItemsHelper getInstance() {
        if(sInstance == null) {
            sInstance = new TodoItemsHelper(sContext);
        }
        return sInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_ITEMS_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                UUID + " INTEGER PRIMARY KEY, " + // Define a primary key
                CATEGORY + " TEXT, " + ITEM_NAME + " TEXT, " +
                PRIORITY + " TEXT, " + DUE_BY + " TEXT" +
                ")";

        db.execSQL(CREATE_TODO_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<TodoItems> getAllItems() {
        List<TodoItems> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0)
        {
            System.out.println("No Records in Database");
        }
        while(cursor.moveToNext()) {
            TodoItems itemNow = new TodoItems(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            items.add(itemNow);
        }
        cursor.close();
        db.close();
        return items;
    }

    public List<String> getAllItemNames() {
        List<String> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0)
        {
            System.out.println("No Records in Database");
        }
        while(cursor.moveToNext()) {
            items.add(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return items;
    }

    public void addItemtoDb(TodoItems item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY, item.getCategory());
        values.put(ITEM_NAME, item.getItem_name());
        values.put(PRIORITY, item.getPriority());
        values.put(DUE_BY, item.getDue_by());
        db.insert(TABLE_NAME, null, values);
        db.close();
        System.out.println("SAVED ITEMS TO DATABASE");
    }

    public void upgradeItem(TodoItems newItem, String oldName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY, newItem.getCategory());
        values.put(ITEM_NAME, newItem.getItem_name());
        values.put(PRIORITY, newItem.getPriority());
        values.put(DUE_BY, newItem.getDue_by());
        db.update(TABLE_NAME, values, ITEM_NAME+" = ?", new String[]{oldName});
        db.close();
        System.out.println("UPDATED ITEMS TO DATABASE");
    }

    public void removeItem(String removeRecord) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+ITEM_NAME+"='"+removeRecord+"'");
        db.close();

    }
}

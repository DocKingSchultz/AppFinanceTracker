package com.example.appfinance.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appfinance.model.BankReportInfo;
import com.example.appfinance.model.Category;
import com.example.appfinance.model.ReportCategoryLink;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;

    private final Context context;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "app_database";

    // Table Names
    private static final String TABLE_BANK_REPORT = "bank_report";
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_REPORT_CATEGORY_LINK = "report_category_link";

    // Common column names
    private static final String KEY_ID = "id";

    // BANK_REPORT Table - column names
    private static final String AMOUNT = "amount";
    private static final String DATE = "date";
    private static final String ACCOUNT_ID = "accountID";
    private static final String DESCRIPTION = "description";

    // CATEGORY Table - column names
    private static final String KEY_CATEGORY_NAME = "category_name";

    // REPORT_CATEGORY_LINK Table - column names
    private static final String KEY_REPORT_ID = "report_id";
    private static final String KEY_CATEGORY_ID = "category_id";

    // Table Create Statements
    // Bank report table create statement
    private static final String CREATE_TABLE_BANK_REPORT = "CREATE TABLE "
            + TABLE_BANK_REPORT + "(" + KEY_ID + " INTEGER PRIMARY KEY," + AMOUNT
            + " INTEGER," +  DATE + " DATE," + ACCOUNT_ID + " TEXT," + DESCRIPTION +" TEXT" + ")";

    // Category table create statement
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY_NAME + " TEXT" + ")";

    // Report-Category link table create statement
    private static final String CREATE_TABLE_REPORT_CATEGORY_LINK = "CREATE TABLE "
            + TABLE_REPORT_CATEGORY_LINK + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_REPORT_ID + " INTEGER," + KEY_CATEGORY_ID + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_BANK_REPORT);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_REPORT_CATEGORY_LINK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BANK_REPORT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT_CATEGORY_LINK);
        // create new tables
        onCreate(db);
    }

    ////////////////////////// CRUD operations for Bank Report
    public void addBankReport(BankReportInfo bankReport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, bankReport.getAmount());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(bankReport.getDate());
        values.put(DATE, dateString);

        values.put(ACCOUNT_ID, bankReport.getAccountID());
        values.put(DESCRIPTION, bankReport.getDescription());
        db.insert(TABLE_BANK_REPORT, null, values);
        db.close();
    }

    public BankReportInfo getBankReport(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BANK_REPORT, new String[] { KEY_ID, AMOUNT, DATE, ACCOUNT_ID, DESCRIPTION },
                KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        BankReportInfo bankReport = null;
        try {
            bankReport = new BankReportInfo("",context);
            bankReport.setID(id);
            bankReport.setAmount(cursor.getInt(1));
            bankReport.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(2)));
            bankReport.setAccountID(cursor.getString(3));
            bankReport.setDescription(cursor.getString(4));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        cursor.close();
        return bankReport;
    }

    public List<BankReportInfo> getAllBankReports() {
        List<BankReportInfo> bankReportList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_BANK_REPORT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BankReportInfo bankReport = new BankReportInfo("", context);
                bankReport.setID(cursor.getInt(0));
                bankReport.setAmount(cursor.getInt(1));
                try {
                    bankReport.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(2)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                bankReport.setAccountID(cursor.getString(3));
                bankReport.setDescription(cursor.getString(4));
                bankReportList.add(bankReport);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bankReportList;
    }

    public int updateBankReport(BankReportInfo bankReport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, bankReport.getAmount());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(bankReport.getDate());
        values.put(DATE, dateString);

        values.put(ACCOUNT_ID, bankReport.getAccountID());
        values.put(DESCRIPTION, bankReport.getDescription());
        return db.update(TABLE_BANK_REPORT, values, KEY_ID + " = ?", new String[] { String.valueOf(bankReport.getID()) });
    }

    public void deleteBankReport(BankReportInfo bankReport) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BANK_REPORT, KEY_ID + " = ?", new String[] { String.valueOf(bankReport.getID()) });
        db.close();
    }

    /////////////////////////// CRUD operations for Category
    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_NAME, category.getName());
        db.insert(TABLE_CATEGORY, null, values);
        db.close();
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORY, new String[] { KEY_ID, KEY_CATEGORY_NAME },
                KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Category category = new Category(
                cursor.getInt(0),
                cursor.getString(1)
        );
        cursor.close();
        return category;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setID(cursor.getInt(0));
                category.setName(cursor.getString(1));
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoryList;
    }

    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_NAME, category.getName());
        return db.update(TABLE_CATEGORY, values, KEY_ID + " = ?", new String[] { String.valueOf(category.getID()) });
    }

    public void deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, KEY_ID + " = ?", new String[] { String.valueOf(category.getID()) });
        db.close();
    }
    /////////////////////////// CRUD operations for Report-Category link
    public void addReportCategoryLink(ReportCategoryLink link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REPORT_ID, link.getReportID());
        values.put(KEY_CATEGORY_ID, link.getCategoryID());
        db.insert(TABLE_REPORT_CATEGORY_LINK, null, values);
        db.close();
    }

    public ReportCategoryLink getReportCategoryLink(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REPORT_CATEGORY_LINK,
                new String[]{KEY_ID, KEY_REPORT_ID, KEY_CATEGORY_ID},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        ReportCategoryLink link = null;
        if (cursor != null && cursor.moveToFirst()) {
            link = new ReportCategoryLink(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3)
            );
            cursor.close();
        }
        return link;
    }

    public List<ReportCategoryLink> getAllReportCategoryLinks() {
        List<ReportCategoryLink> links = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_REPORT_CATEGORY_LINK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ReportCategoryLink link = new ReportCategoryLink();
                link.setID(cursor.getInt(1));
                link.setReportID(cursor.getInt(2));
                link.setCategoryID(cursor.getInt(3));
                links.add(link);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return links;
    }

    public int updateReportCategoryLink(ReportCategoryLink link) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REPORT_ID, link.getReportID());
        values.put(KEY_CATEGORY_ID, link.getCategoryID());

        // Update the record in the Report-Category link table
        return db.update(TABLE_REPORT_CATEGORY_LINK, values, KEY_ID + " = ?", new String[] { String.valueOf(link.getID()) });
    }


    public void deleteReportCategoryLink(ReportCategoryLink link) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORT_CATEGORY_LINK, KEY_ID + " = ?", new String[] { String.valueOf(link.getID()) });
        db.close();
    }



}

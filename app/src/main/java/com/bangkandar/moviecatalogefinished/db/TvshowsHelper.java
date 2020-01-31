package com.bangkandar.moviecatalogefinished.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bangkandar.moviecatalogefinished.entity.TvShow;

import java.util.ArrayList;

import static com.bangkandar.moviecatalogefinished.db.Database.TABLE_TVSHOW;

public class TvshowsHelper {
    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static TvshowsHelper INSTANCE;
    private static SQLiteDatabase database;

    public TvshowsHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public static TvshowsHelper getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvshowsHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<TvShow> getAllTv() {
        ArrayList<TvShow> tvShows = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, Database.TvshowColumns._ID, null);
        cursor.moveToFirst();
        TvShow mItem;
        if (cursor.getCount() > 0) {
            do {
                mItem = new TvShow();
                mItem.setId(cursor.getInt(0));
                mItem.setJudul(String.valueOf(cursor.getString(1)));
                mItem.setGambar(String.valueOf(cursor.getString(2)));
                mItem.setDeskripsi(String.valueOf(cursor.getString(3)));
                mItem.setPopular(Double.valueOf(cursor.getString(4)));
                mItem.setRilis(String.valueOf(cursor.getString(5)));
                mItem.setRating(Double.valueOf(cursor.getString(6)));
                mItem.setBahasa(String.valueOf(cursor.getString(7)));
                tvShows.add(mItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return tvShows;
    }

    public Boolean getOne(String name) {
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + Database.TvshowColumns.JUDUL + " " + " LIKE " + "'" + name + "'";
        Cursor cursor = database.rawQuery(querySingleRecord, null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {
            return true;
        } else if (cursor.getCount() == 0) {
            return false;
        }
        return false;
    }

    public long insertTvshow(TvShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(Database.TvshowColumns.JUDUL, tvShow.getJudul());
        args.put(Database.TvshowColumns.GAMBAR, tvShow.getGambar());
        args.put(Database.TvshowColumns.DESKRIPSI, tvShow.getDeskripsi());
        args.put(Database.TvshowColumns.RILIS, tvShow.getRilis());
        args.put(Database.TvshowColumns.RATING, tvShow.getRating());
        args.put(Database.TvshowColumns.BAHASA, tvShow.getBahasa());
        args.put(Database.TvshowColumns.POPULAR, tvShow.getPopular());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTvshow(String title) {
        return database.delete(DATABASE_TABLE, Database.TvshowColumns.JUDUL + " = " + "'" + title + "'", null);
    }
}

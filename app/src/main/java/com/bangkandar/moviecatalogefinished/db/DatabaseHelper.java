package com.bangkandar.moviecatalogefinished.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmoviecatalogue";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            Database.MovieColumns.TABLE_MOVIE,
            Database.MovieColumns._ID,
            Database.MovieColumns.JUDUL,
            Database.MovieColumns.GAMBAR,
            Database.MovieColumns.DESKRIPSI,
            Database.MovieColumns.BAHASA,
            Database.MovieColumns.RILIS,
            Database.MovieColumns.RATING
    );

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            Database.TABLE_TVSHOW,
            Database.TvshowColumns._ID,
            Database.TvshowColumns.JUDUL,
            Database.TvshowColumns.GAMBAR,
            Database.TvshowColumns.DESKRIPSI,
            Database.TvshowColumns.POPULAR,
            Database.TvshowColumns.RILIS,
            Database.TvshowColumns.RATING,
            Database.TvshowColumns.BAHASA
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Database.MovieColumns.TABLE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Database.TABLE_TVSHOW);
        onCreate(sqLiteDatabase);
    }
}

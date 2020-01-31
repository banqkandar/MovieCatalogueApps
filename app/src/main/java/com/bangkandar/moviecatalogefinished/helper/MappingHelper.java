package com.bangkandar.moviecatalogefinished.helper;

import android.database.Cursor;

import com.bangkandar.moviecatalogefinished.db.Database;
import com.bangkandar.moviecatalogefinished.entity.Movie;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor moviesCursor) {
        ArrayList<Movie> moviesList = new ArrayList<>();

        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns._ID));
            String judul = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.JUDUL));
            String gambar = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.GAMBAR));
            String deskripsi = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.DESKRIPSI));
            String bahasa = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.BAHASA));
            String rilis = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.RILIS));
            Double rating = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.RATING));
            moviesList.add(new Movie(id, judul, gambar, deskripsi, bahasa, rilis, rating));
        }
        return moviesList;
    }

    public static Movie mapCursorToObject(Cursor moviesCursor) {
        moviesCursor.moveToFirst();
        int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns._ID));
        String judul = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.JUDUL));
        String gambar = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.GAMBAR));
        String deskripsi = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.DESKRIPSI));
        String bahasa = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.BAHASA));
        String rilis = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.RILIS));
        Double rating = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(Database.MovieColumns.RATING));

        return new Movie(id, judul, gambar, deskripsi, bahasa, rilis, rating);
    }
}

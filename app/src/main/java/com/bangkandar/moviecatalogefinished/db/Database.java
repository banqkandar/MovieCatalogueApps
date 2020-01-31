package com.bangkandar.moviecatalogefinished.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class Database {

    // Authority yang digunakan
    public static final String AUTHORITY = "com.bangkandar.moviecatalogefinished";
    private static final String SCHEME = "content";

    public static final class MovieColumns implements BaseColumns {
        // table name
        public static final String TABLE_MOVIE = "movie";

        public static final String JUDUL = "judul";
        public static final String GAMBAR = "gambar";
        public static final String DESKRIPSI = "deskripsi";
        public static final String BAHASA = "bahasa";
        public static final String RILIS = "rilis";
        public static final String RATING = "rating";

        // Base content yang digunakan untuk akses content provider
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    static String TABLE_TVSHOW = "tvshow";

    static final class TvshowColumns implements BaseColumns {
        static String JUDUL = "judul";
        static String GAMBAR = "gambar";
        static String DESKRIPSI = "deskripsi";
        static String POPULAR = "popular";
        static String RILIS = "rilis";
        static String RATING = "rating";
        static String BAHASA = "bahasa";
    }
}

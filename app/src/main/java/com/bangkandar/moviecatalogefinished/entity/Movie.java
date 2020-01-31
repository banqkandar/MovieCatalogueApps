package com.bangkandar.moviecatalogefinished.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {

    private String judul;
    private String gambar;
    private String deskripsi;
    private String bahasa;
    private String rilis;
    private Double rating;
    private int id;

    public Movie(int id, String judul, String gambar, String deskripsi, String bahasa, String rilis, double rating) {
        this.id = id;
        this.judul = judul;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.bahasa = bahasa;
        this.rilis = rilis;
        this.rating = rating;
    }

    public String getRilis() {
        return rilis;
    }

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie(JSONObject object) {
        try {
            this.judul = object.getString("title");
            this.deskripsi = object.getString("overview");
            this.rating = object.getDouble("vote_average");
            this.rilis = object.getString("release_date");
            this.bahasa = object.getString("original_language");
            this.gambar = object.getString("poster_path");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Movie() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.bahasa);
        dest.writeString(this.deskripsi);
        dest.writeDouble(this.rating);
        dest.writeString(this.rilis);
        dest.writeString(this.gambar);
        dest.writeInt(this.id);
    }

    protected Movie(Parcel in) {
        this.judul = in.readString();
        this.bahasa = in.readString();
        this.deskripsi = in.readString();
        this.rating = in.readDouble();
        this.rilis = in.readString();
        this.gambar = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
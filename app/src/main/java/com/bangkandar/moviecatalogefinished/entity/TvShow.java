package com.bangkandar.moviecatalogefinished.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShow implements Parcelable {

    private String judul;
    private String gambar;
    private String deskripsi;
    private Double popular;
    private String rilis;
    private Double rating;
    private String bahasa;
    private int id;

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
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

    public Double getPopular() {
        return popular;
    }

    public void setPopular(Double popular) {
        this.popular = popular;
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

    public TvShow() {
    }

    public TvShow(JSONObject object) {
        try {
            this.judul = object.getString("name");
            this.deskripsi = object.getString("overview");
            this.rating = object.getDouble("vote_average");
            this.rilis = object.getString("first_air_date");
            this.popular = object.getDouble("popularity");
            this.bahasa = object.getString("original_language");
            this.gambar = object.getString("poster_path");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.deskripsi);
        dest.writeDouble(this.popular);
        dest.writeDouble(this.rating);
        dest.writeString(this.rilis);
        dest.writeString(this.bahasa);
        dest.writeString(this.gambar);
        dest.writeInt(this.id);
    }

    protected TvShow(Parcel in) {
        this.judul = in.readString();
        this.deskripsi = in.readString();
        this.popular = in.readDouble();
        this.rating = in.readDouble();
        this.rilis = in.readString();
        this.bahasa = in.readString();
        this.gambar = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
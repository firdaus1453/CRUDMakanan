package me.firdaus1453.crudmakanan.model.detailmakanan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.firdaus1453.crudmakanan.model.makanan.MakananData;

/**
 * Created by firdaus1453 on 3/28/2019.
 */
public class DetailMakananResponse {
    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private MakananData makananData;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MakananData getMakananData() {
        return makananData;
    }

    public void setMakananData(MakananData makananData) {
        this.makananData = makananData;
    }
}

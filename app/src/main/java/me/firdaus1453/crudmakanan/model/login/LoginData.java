package me.firdaus1453.crudmakanan.model.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by firdaus1453 on 2/28/2019.
 */
public class LoginData implements Parcelable {

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("nama_user")
    private String namaUser;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("jenkel")
    private String jenkel;

    @SerializedName("no_telp")
    private String noTelp;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("level")
    private String level;

    public LoginData() {
    }

    protected LoginData(Parcel in) {
        id_user = in.readString();
        namaUser = in.readString();
        alamat = in.readString();
        jenkel = in.readString();
        noTelp = in.readString();
        username = in.readString();
        password = in.readString();
        level = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_user);
        dest.writeString(namaUser);
        dest.writeString(alamat);
        dest.writeString(jenkel);
        dest.writeString(noTelp);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(level);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenkel() {
        return jenkel;
    }

    public void setJenkel(String jenkel) {
        this.jenkel = jenkel;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

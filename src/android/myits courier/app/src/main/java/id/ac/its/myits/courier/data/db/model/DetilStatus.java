package id.ac.its.myits.courier.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetilStatus implements Parcelable {
    private String status;
    private String date;

    public DetilStatus(String status, String date) {
        this.status = status;
        this.date = date;
    }

    public DetilStatus() {
    }

    public static Creator<DetilStatus> getCREATOR() {
        return CREATOR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    protected DetilStatus(Parcel in) {
        status = in.readString();
        date = in.readString();
    }

    public static final Creator<DetilStatus> CREATOR = new Creator<DetilStatus>() {
        @Override
        public DetilStatus createFromParcel(Parcel in) {
            return new DetilStatus(in);
        }

        @Override
        public DetilStatus[] newArray(int size) {
            return new DetilStatus[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(date);
    }
}

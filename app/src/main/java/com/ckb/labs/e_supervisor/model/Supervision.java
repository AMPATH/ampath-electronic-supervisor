package com.ckb.labs.e_supervisor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Supervision  implements Parcelable {
    private Program program;
    private Double longitude;
    private Double latitude;
    private Status status;
    private User startedBy;
    private Date startTimestamp;
    private Date endTimestamp;
    private String purposeOfSupervision;

    protected Supervision(Parcel in) {
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        purposeOfSupervision = in.readString();
    }

    public static final Creator<Supervision> CREATOR = new Creator<Supervision>() {
        @Override
        public Supervision createFromParcel(Parcel in) {
            return new Supervision(in);
        }

        @Override
        public Supervision[] newArray(int size) {
            return new Supervision[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(longitude);
        }
        if (latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(latitude);
        }
        dest.writeString(purposeOfSupervision);
    }

    public enum Status {
        ACTIVE,
        DISCONTINUE,
        COMPLETED
    }
}


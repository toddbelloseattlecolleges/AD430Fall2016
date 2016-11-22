package northseattlecollege.ASLBuddy;

/**
 * Created by a Ghost on 11/5/2016.
 */

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Parcelable {

    private int userId;
    private String microsoftAPInfo;
    private String fullName;
    private int lastActiveTime;
    private boolean isInterpreter;
    private boolean okToChat;
    private boolean okToShowLocation;
    private double lastKnownLocationLat;
    private double lastKnownLocationLong;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMicrosoftAPInfo() {
        return microsoftAPInfo;
    }

    public void setMicrosoftAPInfo(String microsoftAPInfo) {
        this.microsoftAPInfo = microsoftAPInfo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(int lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public boolean isInterpreter() {
        return isInterpreter;
    }

    public void setInterpreter(boolean interpreter) {
        isInterpreter = interpreter;
    }

    public boolean isOkToChat() {
        return okToChat;
    }

    public void setOkToChat(boolean okToChat) {
        this.okToChat = okToChat;
    }

    public boolean isOkToShowLocation() {
        return okToShowLocation;
    }

    public void setOkToShowLocation(boolean okToShowLocation) {
        this.okToShowLocation = okToShowLocation;
    }

    public double getLastKnownLocationLat() {
        return lastKnownLocationLat;
    }

    public void setLastKnownLocationLat(double lastKnownLocationLat) {
        this.lastKnownLocationLat = lastKnownLocationLat;
    }

    public double getLastKnownLocationLong() {
        return lastKnownLocationLong;
    }

    public void setLastKnownLocationLong(double lastKnownLocationLong) {
        this.lastKnownLocationLong = lastKnownLocationLong;
    }

    public double getLastLocationUpdate() {
        return lastLocationUpdate;
    }

    public void setLastLocationUpdate(int lastLocationUpdate) {
        this.lastLocationUpdate = lastLocationUpdate;
    }

    private int lastLocationUpdate;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.microsoftAPInfo);
        dest.writeString(this.fullName);
        dest.writeInt(this.lastActiveTime);
        dest.writeByte(this.isInterpreter ? (byte) 1 : (byte) 0);
        dest.writeByte(this.okToChat ? (byte) 1 : (byte) 0);
        dest.writeByte(this.okToShowLocation ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.lastKnownLocationLat);
        dest.writeDouble(this.lastKnownLocationLong);
        dest.writeInt(this.lastLocationUpdate);
    }

    //reads parcel in the same order as the write
    protected User(Parcel in) {
        this.userId = in.readInt();
        this.microsoftAPInfo = in.readString();
        this.fullName = in.readString();
        this.lastActiveTime = in.readInt();
        this.isInterpreter = in.readByte() != 0;
        this.okToChat = in.readByte() != 0;
        this.okToShowLocation = in.readByte() != 0;
        this.lastKnownLocationLat = in.readDouble();
        this.lastKnownLocationLong = in.readDouble();
        this.lastLocationUpdate = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    //sending the intent
//    MyParcelable dataToSend = new MyParcelable();
//    Intent i = new Intent(this, NewActivity.class);
//    i.putExtra("myData", dataToSend); // using the (String name, Parcelable value) overload!
}

@JsonIgnoreProperties(ignoreUnknown = true)
class UserReport{

    private int reportId;
    private int creatingUserId;
    private int blockingUserId;
    private int creattionTimestamp;
    private boolean wasReported;
    private String reportingUserComment;
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getCreatingUserId() {
        return creatingUserId;
    }

    public void setCreatingUserId(int creatingUserId) {
        this.creatingUserId = creatingUserId;
    }

    public int getBlockingUserId() {
        return blockingUserId;
    }

    public void setBlockingUserId(int blockingUserId) {
        this.blockingUserId = blockingUserId;
    }

    public int getCreattionTimestamp() {
        return creattionTimestamp;
    }

    public void setCreattionTimestamp(int creattionTimestamp) {
        this.creattionTimestamp = creattionTimestamp;
    }

    public boolean isWasReported() {
        return wasReported;
    }

    public void setWasReported(boolean wasReported) {
        this.wasReported = wasReported;
    }

    public String getReportingUserComment() {
        return reportingUserComment;
    }

    public void setReportingUserComment(String reportingUserComment) {
        this.reportingUserComment = reportingUserComment;
    }

}
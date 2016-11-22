package northseattlecollege.ASLBuddy;

/**
 * Created by a Ghost on 11/5/2016.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private int userId;
    private String microsoftAPInfo;
    private String fullName;
    private int lastActiveTime;
    private boolean isInterpreter;
    private boolean okToChat;
    private boolean okToShowLocation;
    private int lastKnownLocationLat;
    private int lastKnownLocationLong;

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

    public int getLastKnownLocationLat() {
        return lastKnownLocationLat;
    }

    public void setLastKnownLocationLat(int lastKnownLocationLat) {
        this.lastKnownLocationLat = lastKnownLocationLat;
    }

    public int getLastKnownLocationLong() {
        return lastKnownLocationLong;
    }

    public void setLastKnownLocationLong(int lastKnownLocationLong) {
        this.lastKnownLocationLong = lastKnownLocationLong;
    }

    public int getLastLocationUpdate() {
        return lastLocationUpdate;
    }

    public void setLastLocationUpdate(int lastLocationUpdate) {
        this.lastLocationUpdate = lastLocationUpdate;
    }

    private int lastLocationUpdate;


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
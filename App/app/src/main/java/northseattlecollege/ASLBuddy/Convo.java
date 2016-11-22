package northseattlecollege.ASLBuddy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Created by a Ghost on 11/5/2016.
 * This is the conversation class. It stores variables related to conversations and these
 * values can be called with the built in methods to store in the database.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Convo {

    private int convoId;
    private int startTime;
    private int endTime;
    private int hohId;
    private int interpreterId;
    private int lastUpdatedHOH;
    private int lastUpdatedInterpreter;

    public Convo(){
    }

    public int getConvoId() {
        return convoId;
    }

    public void setConvoId(int convoId) {
        this.convoId = convoId;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getHohId() {
        return hohId;
    }

    public void setHohId(int hohId) {
        this.hohId = hohId;
    }

    public int getInterpreterId() {
        return interpreterId;
    }

    public void setInterpreterId(int interpreterId) {
        this.interpreterId = interpreterId;
    }

    public int getLastUpdatedHOH() {
        return lastUpdatedHOH;
    }

    public void setLastUpdatedHOH(int lastUpdatedHOH) {
        this.lastUpdatedHOH = lastUpdatedHOH;
    }

    public int getLastUpdatedInterpreter() {
        return lastUpdatedInterpreter;
    }

    public void setLastUpdatedInterpreter(int lastUpdatedInterpreter) {
        this.lastUpdatedInterpreter = lastUpdatedInterpreter;
    }


}

/**
 * This class defines the rating system for the HOH user to rate the Interpreter.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class ConvoRating{
    private int convoId;
    private int askSkillLevel;
    private int translateSpeed;
    private int Friendliness;

    public int getConvoId() {
        return convoId;
    }

    public void setConvoId(int convoId) {
        this.convoId = convoId;
    }

    public int getAskSkillLevel() {
        return askSkillLevel;
    }

    public void setAskSkillLevel(int askSkillLevel) {
        this.askSkillLevel = askSkillLevel;
    }

    public int getTranslateSpeed() {
        return translateSpeed;
    }

    public void setTranslateSpeed(int translateSpeed) {
        this.translateSpeed = translateSpeed;
    }

    public int getFriendliness() {
        return Friendliness;
    }

    public void setFriendliness(int friendliness) {
        Friendliness = friendliness;
    }



}
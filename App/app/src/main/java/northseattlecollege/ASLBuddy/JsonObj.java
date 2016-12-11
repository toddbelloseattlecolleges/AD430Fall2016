package northseattlecollege.ASLBuddy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class JsonObj {

    @JsonProperty("convo_id")
    private int convo_id;
    public int getConvo_id() {return convo_id;}
    public void setConvo_id(int usrIn){convo_id = usrIn;}

    @JsonProperty("start_time")
    private String start_time;
    public String getStart_time(){return start_time;}
    public void setStart_time(String usrIn){start_time = usrIn;}

    @JsonProperty("end_time")
    private String end_time;
    public String getEnd_time(){return end_time;}
    public void setEnd_time(String usrIn){end_time = usrIn;}

    @JsonProperty("hoh_user_id")
    private String hoh_user_id;
    public String getHoh_user_id(){return hoh_user_id;}
    public void setHoh_user_id(String usrIn){hoh_user_id = usrIn;}

    @JsonProperty("interpreter_user_id")
    private String interpreter_user_id;
    public String getInterpreter_user_id(){return interpreter_user_id;}
    public void setInterpreter_user_id(String usrIn){interpreter_user_id = usrIn;}

    @JsonProperty("last_updated_hoh")
    private String last_updated_hoh;
    public String getLast_updated_hoh(){return last_updated_hoh;}
    public void setLast_updated_hoh(String usrIn){last_updated_hoh = usrIn;}

    @JsonProperty("last_known_location_lat")
    private double last_known_location_lat;
    public double getLast_known_location_lat(){return last_known_location_lat;}
    public void setLast_known_location_lat(double usrLat){last_known_location_lat = usrLat;}

    @JsonProperty("last_known_location_long")
    private double last_known_location_long;
    public double getLast_known_location_long(){return last_known_location_long;}
    public void setLast_known_location_long(double usrLong){last_known_location_long = usrLong;}

    @JsonProperty("ok_to_chat")
    private boolean ok_to_chat;
    public boolean getOk_to_chat(){return ok_to_chat;}
    public void setOk_to_chat(boolean usrVid){ok_to_chat = usrVid; }

    @JsonProperty("ok_to_show_location")
    private boolean ok_to_show_location;
    public boolean getOk_to_show_location(){return ok_to_show_location;}
    public void setOk_to_show_location(boolean usrLoc){ ok_to_show_location = usrLoc; }

    @JsonProperty("skype_username")
    private String skype_username;
    public String getSkype_user_name(){return skype_username;}
    public void setSkype_user_name(String usrIn){skype_username = usrIn;}


    @JsonProperty("success")
    private String success;
    public String getSuccess(){return success;}
    public void setSuccess(String usrIn){success = usrIn;}

    @JsonProperty("message")
    private String message;
    public String getMessage(){return message;}
    public void setMessage(String usrIn){message = usrIn;}

}

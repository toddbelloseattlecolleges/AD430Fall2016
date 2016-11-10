package northseattlecollege.ASLBuddy;

public class JsonToObj {

//    double latitude;
//    double longitude;
    String site ;
//    String startTime;
//    String endTime;
//    String timeFrame;
    String queryType;
    String query;

    public JsonToObj(){
        site = "http://54.69.18.19/";
//        latitude = 0;
//        startTime = "272016-10-16T02:35:53.000%27%20";
//        endTime = "20%272016-10-16T02:37:10.000%27";
        queryType = "listConvo";
//        timeFrame = startTime + "and%"+ endTime;
        query = site + queryType;// + timeFrame;
    }

    public String getJson(){
        return null;
    }


}

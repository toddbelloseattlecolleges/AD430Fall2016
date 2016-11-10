package northseattlecollege.ASLBuddy;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

public class JsonQuery {

    public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();
        try {

            JsonObj obj = mapper.readValue(new URL("http://54.69.18.19/pingConvoHOH?hohUserId=1&ConvoId=9"), JsonObj.class);
            System.out.println(obj.getMessage());
            System.out.println(obj.getSuccess());
        }catch(Exception e){
            System.out.println(e);
        }

    }
}

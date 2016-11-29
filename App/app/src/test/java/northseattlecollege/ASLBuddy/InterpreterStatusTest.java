package northseattlecollege.ASLBuddy;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nathanflint on 11/28/16.
 */

public class InterpreterStatusTest {
    @Test
    public void GetVideoStatus() {
        InterpreterStatus interpreterStatus = new InterpreterStatus(1);
        boolean videoStatus = interpreterStatus.getVideoStatus();
        Assert.assertTrue(videoStatus);
    }
}

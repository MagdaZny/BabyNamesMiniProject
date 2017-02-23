import duke.FileResource;
import org.junit.Assert;
import org.junit.Test;

public class BabyBirthTest {

    @Test
    public void totalBirthTest(){
        FileResource file = new FileResource("testing/yob2012short.csv");
        Assert.assertEquals(73, BabyBirth.totalBirth(file));
    }

}

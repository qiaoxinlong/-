package FeudalLord.DbUtil;

import BaseUtil.TestBase;
import org.testng.annotations.Test;
import util.Log;

import java.sql.SQLException;
import java.util.List;

public class DbConnection extends TestBase {
    @Test
    public void dbTest() throws SQLException {
        List<?> queryResult = bnDbUtil.queryArrayHandler("SELECT * FROM `chain` limit 1");
        Log.logInfo(queryResult);
    }
}

package util;


/**
 * Created by sunp on 2019/2/27.
 * 数据库连接
 */
public class ClientBaseMataTest {
    /*
    领主推荐
     */
    protected DBUtil lzDbUtil;
//    班内接龙
    protected DBUtil bnDbUtil;
    public ClientBaseMataTest() {
        bnDbUtil = new DBUtil("jdbc:mysql://rm-8vb4dh18504wh0msebo.mysql.zhangbei.rds.aliyuncs.com:3306/matrix_test", "test_matrix", "lingzhu1303A");
        lzDbUtil = new DBUtil("jdbc:mysql://39.106.155.232:3306/lztest", "lingzhutest", "!@#lingzhu1303A..");
    }

}

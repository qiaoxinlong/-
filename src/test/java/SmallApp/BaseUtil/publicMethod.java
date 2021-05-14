package SmallApp.BaseUtil;
import org.testng.Assert;

/**
 * Created by dliu on 2017/11/10.
 */
public class publicMethod {
    public static RequestVo requestvo = new RequestVo();
    public static ResponseVo responsevo;

    public static String login(String domain, String envForTest) {
        String cookie = "";
        //企业登录地址
        String login_url = domain + "/FHH/EM0HXUL/Authorize/Login";
        //admin登录
        ResponseVo rv = HttpUtils.login(GetConfigUtil.getConfigPropertyByNameAndKey(envForTest, "adminAccount"), GetConfigUtil.getConfigPropertyByNameAndKey(envForTest, "adminPassword"), GetConfigUtil
                .getConfigPropertyByNameAndKey(envForTest, "EnterpriseAccount"), login_url);
        Assert.assertEquals(rv.getJson().getJSONObject("Value").getString("Result"), "7");
        String FSAuthX = HttpUtils.getCookieValue(rv.getCookies(), "FSAuthX");
        String FSAuthXC = HttpUtils.getCookieValue(rv.getCookies(), "FSAuthXC");
        cookie = rv.getCookiesToString();
        return cookie;
//        ResponseVo rv = HttpUtils.loginMobile(GetConfigUtil.getConfigPropertyByNameAndKey(envForTest, "adminAccount"), GetConfigUtil.getConfigPropertyByNameAndKey(envForTest, "adminPassword"), GetConfigUtil
//                .getConfigPropertyByNameAndKey(envForTest, "EnterpriseAccount"), login_url);
////        Assert.assertEquals(rv.getJson().getJSONObject("Value").getString("Result"), "7");
//        String FSAuthX = HttpUtils.getCookieValue(rv.getCookies(), "FSAuthX");
//        String FSAuthXC = HttpUtils.getCookieValue(rv.getCookies(), "FSAuthXC");
//        cookie = rv.getCookiesToString();
//        logger.info("AllCookie====" + cookie);
//        return cookie;
    }
}

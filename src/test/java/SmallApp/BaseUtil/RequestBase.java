package SmallApp.BaseUtil;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dliu on 2017/11/10.
 */
public class RequestBase {
    public static String envForTest;
    public static String domain;
    public static String testCaseName;
    public static String cookie = "";
    public static String tenantId = "";

    public static HashMap<String, String> headers = new HashMap<>();
    private static String path = "data";
    private static String dir = FileUtils.getCurrentWorkDir(path);
    private static ArrayList<File> inputFilesTemplateList = new ArrayList<File>();
    public static Map requestDatas = new HashMap<>();

    static {
        //初始化测试数据
        inputFilesTemplateList = FileUtils.getDirFiles(dir, ".json");
        for (int i = 0; i < inputFilesTemplateList.size(); i++) {
            String inputFileTemplateName = inputFilesTemplateList.get(i).toString();
            try {
                requestDatas.put(inputFileTemplateName.substring(inputFileTemplateName.lastIndexOf(FileUtils.FILE_SEPARATOR) + 1,
                        inputFileTemplateName.lastIndexOf(".")), JSONObject
                        .parseObject(FileUtils.read(inputFileTemplateName),
                                JSONObject.class));


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //    @Parameters({"env","type"})
    @Parameters({"env"})
    @BeforeSuite()
    public void init(String env) {
        //初始化环境变量
        envForTest = env;
        domain = GetConfigUtil.getConfigPropertyByNameAndKey(env, "domain");
        tenantId = GetConfigUtil.getConfigPropertyByNameAndKey(env, "tenantId");
        testCaseName = this.getClass().getSimpleName().split("Test")[0];

        headers.clear();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        //初始化登录
        cookie = publicMethod.login(domain, envForTest);
        headers.put("cookie", cookie);
    }

    //@author zhangman
    public static String getEmployeeId(String EmployeeName) {
        return GetConfigUtil.getConfigPropertyByNameAndKey(envForTest, EmployeeName);
    }

    public void threadWait() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

package SmallApp.TemplateTest;

import SmallApp.BaseUtil.RequestUrlBase;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;
import util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemplateAssertTest extends RequestUrlBase {
    @Test(description = "验证所有对象的场景是否正确")
    public void assertAllDescribeIsTrue() {
        List failList = new ArrayList();
        String[] NotExistDescribeList = {"AccountAddrObj", "CrmInfo", "CrmServiceManager", "Report", "DataBoard", "ReportPermissionMgr", "HighSeasObj", "LeadsPoolObj", "GoalValueObj"};
        JSONObject allMenuList = findAllMenuCustom(headers, domain);
        JSONArray describeList = allMenuList.getJSONObject("Value").getJSONArray("menus").getJSONObject(0).getJSONArray("items");
        for (int i = 0; i < describeList.size(); i++) {
            if (describeList.getJSONObject(i).containsKey("referenceApiname")) {
                String describeApiName = describeList.getJSONObject(i).get("referenceApiname").toString();
                if (!Arrays.asList(NotExistDescribeList).contains(describeApiName)) {
                    JSONObject findResult = findDescribeList(headers, domain, describeApiName);
                    //如果对象不存在,先存起来报错的对象
                    if (findResult.getJSONObject("Result").get("FailureCode").equals(320002404)) {
                        String failMessage = findResult.getJSONObject("Result").get("FailureMessage").toString();
                        int ApiNameLength = failMessage.length();
                        String notExistApiName = failMessage.substring(2, ApiNameLength - 3);
                        failList.add(notExistApiName);
                    } else {
                        JSONArray templateList = findResult.getJSONObject("Value").getJSONArray("templates");
                        for (int z = 0; z < templateList.size(); z++) {
                            String templateType = templateList.getJSONObject(z).get("type").toString();
                            String templateId = templateList.getJSONObject(z).get("_id").toString();
                            findDescribeByTemplate(headers, domain, describeApiName, templateId, templateType);
                        }
                    }
                }
            }
        }
        Log.logInfo("报错的对象" + failList);
    }
}

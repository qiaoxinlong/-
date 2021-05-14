package SmallApp.TemplateTest;

import SmallApp.BaseUtil.RequestUrlBase;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindDataDetailTest extends RequestUrlBase {
    @Test(description = "查询数据详情并查询数据的从对象列表")
    public void findDataDetailTest() {
        //排除的查询对象
        String[] NotExistDescribeList = {"CrmInfo", "LeadsPoolObj", "AccountCostObj", "HighSeasObj"};
        //排除不查询的对象
        String[] FlowDescribeList = {"Approval", "BPM", "CRMEmail", "AccountCostObj", "AttachObj"};
        //查询所有对象列表
        JSONObject allMenuList = findAllMenuCustom(headers, domain);
        JSONArray describeList = allMenuList.getJSONObject("Value").getJSONArray("menus").getJSONObject(0).getJSONArray("items");
        for (int j = 0; j < describeList.size(); j++) {
            String describeApiName = describeList.getJSONObject(j).get("referenceApiname").toString();
            if (!Arrays.asList(NotExistDescribeList).contains(describeApiName)) {
                JSONObject findResult = findFirstDataList(headers, domain, describeApiName);
                int findDataSize = findResult.getJSONObject("Value").getJSONArray("dataList").size();
                if (findDataSize > 0) {
                    JSONObject findData = findResult.getJSONObject("Value").getJSONArray("dataList").getJSONObject(0);
                    String dataId = findData.get("_id").toString();
                    JSONObject findDataResult = findDataDetail(headers, domain, describeApiName, dataId);
                    JSONArray findLayoutDescribe = findDataResult.getJSONObject("Value").getJSONObject("layout").getJSONArray("components");
                    //验证第一个
                    for (int i = 0; i < findLayoutDescribe.size(); i++) {
                        if (findLayoutDescribe.getJSONObject(i).containsKey("child_components")) {
                            JSONArray child_components = findLayoutDescribe.getJSONObject(i).getJSONArray("child_components");
                            for (int z = 0; z < child_components.size(); z++) {
                                //验证相关团队
                                if (child_components.getJSONObject(z).get("api_name").equals("relevant_team_component")) {
                                    JSONObject findTeamDetailResult = findTeamDetail(headers, domain, describeApiName, dataId);
                                    Assert.assertEquals(findTeamDetailResult.getJSONObject("Result").get("FailureCode"), 0);
                                }
                                //如果是关联对象或者主从对象
                                if (child_components.getJSONObject(z).containsKey("ref_object_api_name")) {
                                    String ref_object_api_name = child_components.getJSONObject(z).get("ref_object_api_name").toString();
                                    if (!Arrays.asList(FlowDescribeList).contains(ref_object_api_name)) {
                                        String related_list_name = child_components.getJSONObject(z).get("related_list_name").toString();
                                        JSONObject findReferenceResult = findRefrenceListDetail(headers, domain, describeApiName, ref_object_api_name, dataId, related_list_name);
                                        Log.logInfo("对象" + ref_object_api_name + "查询结果\n" + findReferenceResult);
                                        //验证查询不报错
                                        Assert.assertEquals(findReferenceResult.getJSONObject("Result").get("FailureCode"), 0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Test(description = "验证所有对象列表页是否正常")
    public void assertAllDescribeIsTrue() {
        List failList = new ArrayList();
        String[] NotExistDescribeList = {"CrmInfo", "CrmServiceManager", "Report", "DataBoard", "ReportPermissionMgr", "HighSeasObj", "LeadsPoolObj", "GoalValueObj", "AccountFinInfoObj", "CrmRival"};
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
                        Assert.assertEquals(findResult.getJSONObject("Result").get("FailureCode"), 0);
                    }
                }
            }
        }
        Log.logInfo("报错的对象" + failList);
    }
}

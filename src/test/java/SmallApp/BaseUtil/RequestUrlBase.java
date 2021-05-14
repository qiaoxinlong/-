package SmallApp.BaseUtil;

import com.alibaba.fastjson.JSONObject;
import util.Log;

import java.util.HashMap;

public class RequestUrlBase extends InitLoginBase {
    public static ResponseVo responsevo;

    //查询对象
    public static JSONObject findDescribeByApiName(HashMap<String, String> headers, String domain, String describeApiName) {
        String url = domain + "/FHH/EM1HNCRM/API/v1/object/describe/service/findDescribeByApiName";
        JSONObject requestJson = new JSONObject();
        requestJson.put("describe_apiname", describeApiName);
        requestJson.put("include_layout", false);
        requestJson.put("layout_type", "detail");
        return commonPostRequest(headers, url, requestJson);
    }

    //查询CRM所有对象
    public static JSONObject findAllMenuCustom(HashMap<String, String> headers, String domain) {
        String url = domain + "/FHH/EM1HNCRM/API/v1/object/crm_menu/service/all_menu";
        JSONObject requestJson = new JSONObject();
        return commonPostRequest(headers, url, requestJson);
    }

    //查询对象
    public static JSONObject findDescribeList(HashMap<String, String> headers, String domain, String describeApiName) {
        String url = domain + "/FHH/EM1HNCRM/API/v1/object/" + describeApiName + "/controller/ListHeader";
        JSONObject requestJson = new JSONObject();
        requestJson.put("include_layout", true);
        requestJson.put("apiname", describeApiName);
        requestJson.put("layout_type", "list");
        requestJson.put("layout_by_template", true);
        requestJson.put("thirdapp_record_type", null);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return commonPostRequest(headers, url, requestJson);

    }

    //查询对象
    public static JSONObject findFirstDataList(HashMap<String, String> headers, String domain, String describeApiName) {
        String url = domain + "/FHH/EM1HNCRM/API/v1/object/" + describeApiName + "/controller/List";
        JSONObject requestJson = new JSONObject();
        requestJson.put("object_describe_api_name", describeApiName);
        requestJson.put("search_template_type", "default");
        requestJson.put("ignore_scene_record_type", false);
        requestJson.put("search_query_info", "{\"limit\":1,\"offset\":0,\"filters\":[{\"field_name\":\"life_status\",\"field_values\":[\"normal\"],\"operator\":\"EQ\"}],\"orders\":[{\"fieldName\":\"last_modified_time\",\"isAsc\":false}]}");
        return commonPostRequest(headers, url, requestJson);
    }

    //查询对象
    public static JSONObject findRefrenceListDetail(HashMap<String, String> headers, String domain, String targetApiName, String sourceApiName, String dataId, String fieldRelatedListName) {
        String url = domain + "/FHH/EM1HNCRM/API/v1/object/" + sourceApiName + "/controller/RelatedList";
        JSONObject requestJson = new JSONObject();
        requestJson.put("associate_object_data_id", dataId);
        requestJson.put("associate_object_describe_api_name", targetApiName);
        requestJson.put("associated_object_describe_api_name", sourceApiName);
        requestJson.put("associated_object_field_related_list_name", fieldRelatedListName);
        requestJson.put("include_associated", true);
        requestJson.put("search_query_info", "{\"limit\":1,\"offset\":0}");
        requestJson.put("is_ordered", true);
        return commonPostRequest(headers, url, requestJson);
    }

    //按场景查询对象
    public static JSONObject findDescribeByTemplate(HashMap<String, String> headers, String domain, String describeApiName, String search_template_id, String search_template_type) {
        String url = domain + "/FHH/EM1HNCRM/API/v1/object/" + describeApiName + "/controller/ListHeader";
        JSONObject requestJson = new JSONObject();
        requestJson.put("object_describe_api_name", describeApiName);
        requestJson.put("search_template_id", search_template_id);
        requestJson.put("search_template_type", search_template_type);
        requestJson.put("ignore_scene_record_type", false);
        requestJson.put("search_query_info", "{\"limit\":1,\"offset\":0,\"filters\":[],\"orders\":[{\"fieldName\":\"last_modified_time\",\"isAsc\":false}]}");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return commonPostRequest(headers, url, requestJson);
    }

    //查询数据详情
    public static JSONObject findDataDetail(HashMap<String, String> headers, String domain, String describeApiName, String objectDataId) {
        String url = domain + "/FHH/EM1HNCRM/API/v1/object/" + describeApiName + "/controller/Detail";
        JSONObject requestJson = new JSONObject();
        requestJson.put("objectDescribeApiName", describeApiName);
        requestJson.put("objectDataId", objectDataId);
        return commonPostRequest(headers, url, requestJson);
    }

    /*
    获取相关团队
     */
    public static JSONObject findTeamDetail(HashMap<String, String> headers, String domain, String describeApiName, String dataID) {
        String url = domain + "/FHH/EM1HNCRM/API/v1/object/data_privilege/service/getTeamMember";
        JSONObject requestJson = new JSONObject();
        requestJson.put("objectDescribeApiName", describeApiName);
        requestJson.put("dataID", dataID);
        return commonPostRequest(headers, url, requestJson);
    }

    //请求
    private static JSONObject commonPostRequest(HashMap<String, String> headers, String url, JSONObject jsonObject) {
        publicMethod.requestvo.setRequestVo(headers, jsonObject.toString(), url);
        responsevo = HttpUtils.httpPostRaw(publicMethod.requestvo);
        Log.logInfo(url + "\n" + responsevo.getJson());
        return responsevo.getJson();
    }
}

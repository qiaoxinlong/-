package BaseUtil;

import client.ApiClient;
import com.alibaba.fastjson.JSONObject;
import util.ClientBaseMataTest;
import util.GetConfigUtil;
import util.Log;

import java.util.List;

public class TestBase1 extends ClientBaseMataTest {
    //国内版-手机号登录
    public JSONObject userLogin(String phone, int code, int inviteUid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/login";
        JSONObject dataJson = new JSONObject();
        dataJson.put("phone", phone);
        dataJson.put("code", code);
        dataJson.put("invite_uid", inviteUid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/login\n" + result);
        return JSONObject.parseObject(result);
    }

    //自动登录
    public JSONObject userAutoLogin() {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/autoLogin";
        String result = ApiClient.Post(url, null);
        Log.logInfo("lingzhu/user/autoLogin\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取其他人的用户信息
    public JSONObject userGetUid(String uid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/get/uid";
        JSONObject dataJson = new JSONObject();
        dataJson.put("uid", uid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/get/uid\n" + result);
        return JSONObject.parseObject(result);
    }

    //    获取用户详细信息
    public JSONObject userGetBrief() {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/get/brief";
        String dataArg = JSONObject.toJSONString(null);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/get/brief\n" + result);
        return JSONObject.parseObject(result);
    }

    //更新用户昵称
    public JSONObject userUpdateNickname(int retry, String nickname) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/update/nickname";
        JSONObject dataJson = new JSONObject();
        dataJson.put("retry", retry);
        dataJson.put("nickname", nickname);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/update/nickname\n" + result);
        return JSONObject.parseObject(result);
    }

    //阅读教材
    public JSONObject userUpdateTutorial() {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/update/tutorial";
        String dataArg = JSONObject.toJSONString(null);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/update/tutorial\n" + result);
        return JSONObject.parseObject(result);
    }

    //更新性别
    public JSONObject userUpdateSex(int sex) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/update/sex";
        JSONObject dataJson = new JSONObject();
        dataJson.put("sex", sex);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/update/sex\n" + result);
        return JSONObject.parseObject(result);
    }

    //    //更新头像 需要上传mp4格式
//    public JSONObject userUpdateSelfie(int sex) {
//        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/update/sex";
//        JSONObject dataJson = new JSONObject();
//        dataJson.put("sex", sex);
//        String dataArg = JSONObject.toJSONString(dataJson);
//        String result = ApiClient.Post(url, dataArg);
//        Log.logInfo("lingzhu/user/update/sex\n" + result);
//        return JSONObject.parseObject(result);
//    }
//创建OF地址
    public JSONObject userUpdateOfAddress(String uid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/update/ofAddress";
        JSONObject dataJson = new JSONObject();
        dataJson.put("uid", uid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/update/ofAddress\n" + result);
        return JSONObject.parseObject(result);
    }

    //通过云信ID获取用户信息
    public JSONObject userGetYunxunId(String yunchatId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/get/yunxun_id";
        JSONObject dataJson = new JSONObject();
        dataJson.put("yunchat_id", yunchatId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/get/yunxun_id\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取靠谱的人
    public JSONObject userListReliable(int page, int count) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/list/reliable";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/list/reliable\n" + result);
        return JSONObject.parseObject(result);
    }

    //添加/修改标签
    public JSONObject tagHandle(JSONObject handleTag) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/tag/handle";
        String dataArg = JSONObject.toJSONString(handleTag);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/tag/handle\n" + result);
        return JSONObject.parseObject(result);
    }

    //确认标签
    public JSONObject tagConfirm(String name, String phone, List tags) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/tag/confirm";
        JSONObject dataJson = new JSONObject();
        dataJson.put("name", name);
        dataJson.put("phone", phone);
        dataJson.put("tags", tags);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/tag/confirm\n" + result);
        return JSONObject.parseObject(result);
    }

    //删除标签
    public JSONObject tagDelete(String tid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/tag/delete";
        JSONObject dataJson = new JSONObject();
        dataJson.put("tid", tid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/tag/delete\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取用户标签列表
    public JSONObject tagListUid(String targetUid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/tag/list/uid";
        JSONObject dataJson = new JSONObject();
        dataJson.put("target_uid", targetUid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/tag/list/uid\n" + result);
        return JSONObject.parseObject(result);
    }

    //发送验证码
    public JSONObject systemSendMessage(String phone) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/system/sendMessage";
        JSONObject dataJson = new JSONObject();
        dataJson.put("phone", phone);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/system/sendMessage\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取系统升级
    public JSONObject getLastVersion(String version, String platform) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/system/get/lastesVersion";
        JSONObject dataJson = new JSONObject();
        dataJson.put("version", version);
        dataJson.put("platform", platform);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/system/get/lastesVersion\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取iOS系统配置信息
    public JSONObject getSystemConfig(String version, String platform) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/system/getSystemConfig";
        JSONObject dataJson = new JSONObject();
        dataJson.put("version", version);
        dataJson.put("platform", platform);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/system/getSystemConfig\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取Android系统配置
    public JSONObject getAndroidSystemConfig(String version, String platform) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/system/getAndroidSystemConfig";
        JSONObject dataJson = new JSONObject();
        dataJson.put("version", version);
        dataJson.put("platform", platform);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/system/getAndroidSystemConfig\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取闪屏广告
    public JSONObject getListAdvertises(String scale) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/system/ListAdvertises";
        JSONObject dataJson = new JSONObject();
        dataJson.put("scale", scale);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/system/ListAdvertises\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取当天的福豆聚合记录
    public JSONObject getAggregation() {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/fudou/aggregation/get";
        String dataArg = JSONObject.toJSONString(null);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/fudou/aggregation/get\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取福豆历史记录
    public JSONObject getAggregationList(int page, int count) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/fudou/aggregation/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/fudou/aggregation/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取福豆交易明细
    public JSONObject getFuDouList(int page, int count) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/fudou/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/fudou/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //健康检查
    public JSONObject checkHealth() {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/checkHealth";
        String result = ApiClient.Get(url);
        Log.logInfo("lingzhu/checkHealth\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取推荐列表
    public JSONObject recommendationList() {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/recommendation/list";
        String result = ApiClient.Post(url, null);
        Log.logInfo("lingzhu/recommendation/list\n" + result);
        return JSONObject.parseObject(result);
    }
    //创建邀请关系
    public JSONObject inviteCreate() {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/invite/create";
        String result = ApiClient.Post(url, null);
        Log.logInfo("lingzhu/invite/create\n" + result);
        return JSONObject.parseObject(result);
    }
    //绑定邀请关系
    public JSONObject inviteBind(String uuid, String phone, String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/invite/bind";
        JSONObject dataJson = new JSONObject();
        dataJson.put("uuid", uuid);
        dataJson.put("phone", phone);
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/invite/bind\n" + result);
        return JSONObject.parseObject(result);
    }
}

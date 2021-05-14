package BaseUtil;

import client.ApiClient;
import com.alibaba.fastjson.JSONObject;
import util.ClientBaseMataTest;
import util.GetConfigUtil;
import util.Log;

/**
 * @author sunp
 */
public class TestBase extends ClientBaseMataTest {

    // 获取我发布的帖子
    public JSONObject getMyFeedList(int pageSize) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/listPersonalMoments";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page_size", pageSize);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/listPersonalMoments\n" + result);
        return JSONObject.parseObject(result);
    }

    //    举报帖子
    public JSONObject feedReport(String requestId, String feedId, String content, String etag) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/report";
        JSONObject dataJson = new JSONObject();
        dataJson.put("request_id", requestId);
        dataJson.put("feed_id", feedId);
        dataJson.put("content", content);
        dataJson.put("etag", etag);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/report\n" + result);
        return JSONObject.parseObject(result);
    }

    //    删除我的帖子
    public JSONObject feedRemove(Long feedId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/remove";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/remove\n" + result);
        return JSONObject.parseObject(result);
    }

    //    删除评论
    public JSONObject feedDeleteComment(String feedId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/deleteComment";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/remove\n" + result);
        return JSONObject.parseObject(result);
    }

    //    判断社群是否可以发贴
    public JSONObject feedPermission(int cid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/permission";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/permission\n" + result);
        return JSONObject.parseObject(result);
    }

    //    加入社群
    public JSONObject communityJoin(int cid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/join";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/join\n" + result);
        return JSONObject.parseObject(result);
    }

    //    发布帖子
    public JSONObject feedCreate(JSONObject createFeed) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/create";
        String dataArg = JSONObject.toJSONString(createFeed);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/create\n" + result);
        return JSONObject.parseObject(result);
    }

    //    发布评论
    public JSONObject feedCreateComment(String feedId, String toUid, String content) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/createComment";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        dataJson.put("to_uid", toUid);
        dataJson.put("content", content);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/createComment\n" + result);
        return JSONObject.parseObject(result);
    }

    //    帖子分享回调
    public JSONObject feedShareNotify(String feedId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/shareNotify";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/shareNotify\n" + result);
        return JSONObject.parseObject(result);
    }

    //   帖子打赏
    public JSONObject feedReward(int feedId, int rewardAmount) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/reward";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        dataJson.put("reward_amount", rewardAmount);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/reward\n" + result);
        return JSONObject.parseObject(result);
    }

    //   悬赏提问
    public JSONObject feedListPersonalRewardQuestions(int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/listPersonalRewardQuestions";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/listPersonalRewardQuestions\n" + result);
        return JSONObject.parseObject(result);
    }

    //    悬赏采纳
    public JSONObject feedAcceptOfferReward(String feedId, String commentId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/acceptOfferReward";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        dataJson.put("comment_id", commentId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/acceptOfferReward\n" + result);
        return JSONObject.parseObject(result);
    }

    //我的悬赏回答
    public JSONObject feedListPersonalRewardAnswers(int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/listPersonalRewardAnswers";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/listPersonalRewardAnswers\n" + result);
        return JSONObject.parseObject(result);
    }

    //是否可以激活社群
    public JSONObject feedActivationPermission(int cid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/activationPermission";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/activationPermission\n" + result);
        return JSONObject.parseObject(result);
    }
    //更新社群
    public JSONObject communityUpdate(int cid, String key, String value) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/update";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        dataJson.put("key", key);
        dataJson.put("value", value);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/update\n" + result);
        return JSONObject.parseObject(result);
    }
    //    社群搜索-单独搜索动态
    public JSONObject feedSearchFeed(String keyword, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/searchFeed";
        JSONObject dataJson = new JSONObject();
        dataJson.put("keyword", keyword);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/searchFeed\n" + result);
        return JSONObject.parseObject(result);
    }

    //社群搜索-单独搜索社群
    public JSONObject communitySearchCommunity(String keyword, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/searchCommunity";
        JSONObject dataJson = new JSONObject();
        dataJson.put("keyword", keyword);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/searchCommunity\n" + result);
        return JSONObject.parseObject(result);
    }

    //社群搜索-搜索主页面请求接口
    public JSONObject communitySearch(String keyword, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/search";
        JSONObject dataJson = new JSONObject();
        dataJson.put("keyword", keyword);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/search\n" + result);
        return JSONObject.parseObject(result);
    }

    //离开社群
    public JSONObject communityLeave(int cid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/leave";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/leave\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取全部社群列表
    public JSONObject communityListGlobalCommunities(int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/listGlobalCommunities";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/listGlobalCommunities\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取帖子分享信息
    public JSONObject communityListGlobalCommunities(String feedId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/getShareInfo";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/getShareInfo\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取帖子列表
    public JSONObject feedListFeeds(String cid, int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/listFeeds";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/listFeeds\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取帖子打赏列表
    public JSONObject feedListRewards(String feedId, int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/listRewards";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/listRewards\n" + result);
        return JSONObject.parseObject(result);
    }

    //    获取悬赏列表
    public JSONObject feedListOffers(String cid, int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/listOffers";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/listOffers\n" + result);
        return JSONObject.parseObject(result);
    }

    // 获取用户社群列表
    public JSONObject communityListUserCommunities(int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/listUserCommunities";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/listUserCommunities\n" + result);
        return JSONObject.parseObject(result);
    }

    // 获取社群分红记录
    public JSONObject communityListCommunityFundBonus(String cid, int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/listCommunityFundBonus";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/listCommunityFundBonus\n" + result);
        return JSONObject.parseObject(result);
    }

    // 获取社群成员列表
    public JSONObject communitListMembers(String cid, int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/communit/listMembers";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/communit/listMembers\n" + result);
        return JSONObject.parseObject(result);
    }

    // 获取社群激活奖励
    public JSONObject communityGetCommunityActivationReward(String cid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/getCommunityActivationReward";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/getCommunityActivationReward\n" + result);
        return JSONObject.parseObject(result);
    }

    // 获取社群详情
    public JSONObject communityDetail(String cid, int pageSize, String pageToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/detail";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", pageToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/detail\n" + result);
        return JSONObject.parseObject(result);
    }

    // 获取帖子详情
    public JSONObject feedDetail(String feedId, int source) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/detail";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        dataJson.put("source", source);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/detail\n" + result);
        return JSONObject.parseObject(result);
    }

    // 获取社群邀请规则
    public JSONObject feedDetail(String cid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/community/getInviteRule";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/community/getInviteRule\n" + result);
        return JSONObject.parseObject(result);
    }

    // 获取评论列表
    public JSONObject feedDetail(String cid, int pageSize, String page_token) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/feed/listComments";
        JSONObject dataJson = new JSONObject();
        dataJson.put("cid", cid);
        dataJson.put("page_size", pageSize);
        dataJson.put("page_token", page_token);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/feed/listComments\n" + result);
        return JSONObject.parseObject(result);
    }

    // 删除状态
    public JSONObject newsfeeddelete(String nsid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/newsfeed/delete";
        JSONObject dataJson = new JSONObject();
        dataJson.put("nsid", nsid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/newsfeed/delete\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取常用状态(缺少参数)
    public JSONObject newsfeedList() {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/newsfeed/list/commonStates";
        JSONObject dataJson = new JSONObject();
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/newsfeed/list/commonStates\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取状态列表
    public JSONObject newsfeedList(String page, int count) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/newsfeed/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/newsfeed/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取用户发布的状态列表
    public JSONObject newsfeedListUid(String page, int count, String uid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/newsfeed/list/uid";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        dataJson.put("uid", uid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/newsfeed/list/uid\n" + result);
        return JSONObject.parseObject(result);
    }

    //H5获取商品信息
    public JSONObject productionDetail(String pid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/production/h5/detail";
        JSONObject dataJson = new JSONObject();
        dataJson.put("pid", pid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/production/h5/detail\n" + result);
        return JSONObject.parseObject(result);
    }

    //商品搜索
    public JSONObject productionSearch(int page, int count, String keyword) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/production/search";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        dataJson.put("keyword", keyword);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/production/search\n" + result);
        return JSONObject.parseObject(result);
    }

    //商品更新
    public JSONObject productionUpdate(JSONObject productUpdate) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/production/update";
        String dataArg = JSONObject.toJSONString(productUpdate);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/production/update\n" + result);
        return JSONObject.parseObject(result);
    }

    //更新商品库存
    public JSONObject updateCapacity(String pid, int capacity) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/production/update/capacity";
        JSONObject dataJson = new JSONObject();
        dataJson.put("pid", pid);
        dataJson.put("capacity", capacity);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/production/update/capacity\n" + result);
        return JSONObject.parseObject(result);
    }

    //    添加商品
    public JSONObject updateCapacity(JSONObject addCapacity) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/production/add";
        String dataArg = JSONObject.toJSONString(addCapacity);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/production/add\n" + result);
        return JSONObject.parseObject(result);
    }

    //    获取下架商品列表
    public JSONObject productionRemoveTid(int page, int count, int target_uid, int tid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/production/list/remove/tid";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        dataJson.put("target_uid", target_uid);
        dataJson.put("tid", tid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/production/list/remove/tid\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取商品列表
    public JSONObject productionListTid(int page, int count, int target_uid, int tid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/production/list/tid";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        dataJson.put("target_uid", target_uid);
        dataJson.put("tid", tid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/production/list/tid\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取商品详细信息
    public JSONObject productionDetail(int pid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/production/detail";
        JSONObject dataJson = new JSONObject();
        dataJson.put("pid", pid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/production/detail\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取推荐商品列表
    public JSONObject productionRecommendList(int page, int count) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/production/recommend/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/production/recommend/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //我的领地列表
    public JSONObject personalTerritoriesList(int page, int count, String uid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/territory/personalTerritories/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        dataJson.put("uid", uid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/territory/personalTerritories/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //举报动态-举报无效
    public JSONObject personalTerritoriesList(String feedId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/innerMessage/reportFeedInvalid";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/innerMessage/reportFeedInvalid\n" + result);
        return JSONObject.parseObject(result);
    }

    //举报动态-审核无误
    public JSONObject innerMessageReportFeedRemove(String feedId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/innerMessage/reportFeedRemove";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/innerMessage/reportFeedRemove\n" + result);
        return JSONObject.parseObject(result);
    }

    //举报动态-恢复内容
    public JSONObject innerMessageReportFeedRecover(String feedId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/innerMessage/reportFeedRecover";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/innerMessage/reportFeedRecover\n" + result);
        return JSONObject.parseObject(result);
    }

    //动态被举报-发布者申述
    public JSONObject innerMessageReportFeedRepresentations(String feedId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/innerMessage/reportFeedRepresentations";
        JSONObject dataJson = new JSONObject();
        dataJson.put("feed_id", feedId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/innerMessage/reportFeedRepresentations\n" + result);
        return JSONObject.parseObject(result);
    }

    //    补充
//购买广告位
    public JSONObject orderAdvertisementCreate(JSONObject orderAdvertisement) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/order/advertisement/create";
        String dataArg = JSONObject.toJSONString(orderAdvertisement);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/order/advertisement/create\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取广告记录
    public JSONObject advertisementList(int displayStatus, String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/advertisement/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("display_status", displayStatus);
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/advertisement/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //广告位设置(edit_info参数类型待确定，支付方式pay_type参数是否移除)
    public JSONObject advertisementCreate(JSONObject paySet) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/advertisement/create";
        String dataArg = JSONObject.toJSONString(paySet);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/advertisement/create\n" + result);
        return JSONObject.parseObject(result);
    }

    //关闭广告
    public JSONObject advertisementClose(String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/advertisement/close";
        JSONObject dataJson = new JSONObject();
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/advertisement/close\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取雷达列表(参数不全)
    public JSONObject radarListEntrance(String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/radar/listEntrance";
        JSONObject dataJson = new JSONObject();
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/radar/listEntrance\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取系统推荐信息（参数不全）
    public JSONObject recommendationList(String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/recommendation/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/recommendation/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取好友列表（参数不全）
    public JSONObject friendList(String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/friend/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/friend/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //购买领地券-苹果支付(参数不全)
    public JSONObject ticketApplepay(String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/ticket/applepay";
        JSONObject dataJson = new JSONObject();
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/ticket/applepay\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取福豆兑换配置参数
    public JSONObject ticketListFudouExchangeConfig(String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/ticket/listFudouExchangeConfig";
        JSONObject dataJson = new JSONObject();
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/ticket/listFudouExchangeConfig\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取苹果支付配置参数
    public JSONObject ticketListApplyPayConfig(String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/ticket/listApplyPayConfig";
        JSONObject dataJson = new JSONObject();
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/ticket/listApplyPayConfig\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取用户援助/求助信息
    public JSONObject aidListUserAids(int pageSize, String category, int page) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/aid/listUserAids";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page_size", pageSize);
        dataJson.put("category", category);
        dataJson.put("page", page);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/aid/listUserAids\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取赠送的领地券状态(post/get?)
    public JSONObject advertisementList(int pageSize, String category, int page) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/advertisement/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page_size", pageSize);
        dataJson.put("category", category);
        dataJson.put("page", page);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/advertisement/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取求助/援助详情
    public JSONObject aidDetail(String aId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/aid/detail";
        JSONObject dataJson = new JSONObject();
        dataJson.put("a_id", aId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/aid/detail\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取援助列表
    public JSONObject aidListAids(JSONObject getAidList) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/aid/listAids";
        String dataArg = JSONObject.toJSONString(getAidList);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/aid/listAids\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取援助/求助分享信息
    public JSONObject aidGetShareInfo(int aId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/aid/getShareInfo";
        JSONObject dataJson = new JSONObject();
        dataJson.put("a_id", aId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/aid/getShareInfo\n" + result);
        return JSONObject.parseObject(result);
    }

    //阅读教材(参数不全)
    public JSONObject userUpdateTutorial(int aId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/update/tutorial";
        JSONObject dataJson = new JSONObject();
        dataJson.put("a_id", aId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/update/tutorial\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取用户详细信息(参数不全)
    public JSONObject userGetBrief(int aId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/get/brief";
        JSONObject dataJson = new JSONObject();
        dataJson.put("a_id", aId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/get/brief\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取易宝转账失败的记录(参数不全)
    public JSONObject userGetFailedTransferInfo(int aId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/getFailedTransferInfo";
        JSONObject dataJson = new JSONObject();
        dataJson.put("a_id", aId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/getFailedTransferInfo\n" + result);
        return JSONObject.parseObject(result);
    }

    //自动登录(参数不全)
    public JSONObject userAutoLogin(int aId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/autoLogin";
        JSONObject dataJson = new JSONObject();
        dataJson.put("a_id", aId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/autoLogin\n" + result);
        return JSONObject.parseObject(result);
    }

    //创建邀请关系(参数不全)
    public JSONObject inviteCreate(int aId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/invite/create";
        JSONObject dataJson = new JSONObject();
        dataJson.put("a_id", aId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/invite/create\n" + result);
        return JSONObject.parseObject(result);
    }

    //站内信-采纳悬赏(参数不全、接口地址不全)
      /*public JSONObject inviteCreate(int a_id) {
          String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/invite/create";
          JSONObject dataJson = new JSONObject();
          dataJson.put("a_id", a_id);
          String dataArg = JSONObject.toJSONString(dataJson);
          String result = ApiClient.Post(url, dataArg);
          Log.logInfo("lingzhu/invite/create\n" + result);
          return JSONObject.parseObject(result);
      }
      //动态被举报-删除消息(参数不全、接口地址不全)
      public JSONObject inviteCreate(int a_id) {
          String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/invite/create";
          JSONObject dataJson = new JSONObject();
          dataJson.put("a_id", a_id);
          String dataArg = JSONObject.toJSONString(dataJson);
          String result = ApiClient.Post(url, dataArg);
          Log.logInfo("lingzhu/invite/create\n" + result);
          return JSONObject.parseObject(result);
      }
      //举报动态(参数不全、接口地址不全)
      public JSONObject inviteCreate(int a_id) {
          String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/invite/create";
          JSONObject dataJson = new JSONObject();
          dataJson.put("a_id", a_id);
          String dataArg = JSONObject.toJSONString(dataJson);
          String result = ApiClient.Post(url, dataArg);
          Log.logInfo("lingzhu/invite/create\n" + result);
          return JSONObject.parseObject(result);
      }*/
    //领地分享
    public JSONObject territoryInfoShare(int managerLevel, String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/territory/info/share";
        JSONObject dataJson = new JSONObject();
        dataJson.put("manager_level", managerLevel);
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/territory/info/share\n" + result);
        return JSONObject.parseObject(result);
    }

    //领地任务处理
    public JSONObject territoryTaskHandle(String territoryId, int ttid, int ttuid, int status) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/territory/task/handle";
        JSONObject dataJson = new JSONObject();
        dataJson.put("territory_id", territoryId);
        dataJson.put("ttid", ttid);
        dataJson.put("ttuid", ttuid);
        dataJson.put("status", status);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/territory/task/handle\n" + result);
        return JSONObject.parseObject(result);
    }

    //领地任务列表
    public JSONObject territoryTaskList(String territoryId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/territory/task/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("territory_id", territoryId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/territory/task/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取领地列表
    public JSONObject territoryListTerritories(JSONObject getTerritoryList) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/territory/listTerritories";
        String dataArg = JSONObject.toJSONString(getTerritoryList);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/territory/listTerritories\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取领地信息(坐标参数类型？？)
    public JSONObject territoryInfoV2(double lng, double lat) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/territory/infoV2";
        JSONObject dataJson = new JSONObject();
        dataJson.put("lng", lng);
        dataJson.put("lat", lat);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/territory/infoV2\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取店铺列表
    public JSONObject storeListStories(JSONObject getStoreList) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/store/listStories";
        String dataArg = JSONObject.toJSONString(getStoreList);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/store/listStories\n" + result);
        return JSONObject.parseObject(result);
    }

    //管理员列表
    public JSONObject territoryNewManagerList(int tilex, int tiley) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/territory/new/manager/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("tilex", tilex);
        dataJson.put("tiley", tiley);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/territory/new/manager/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //添加用户到群（经度纬度参数类型？？）
    public JSONObject territoryAddUserToTeam(double lng, double lat, String uid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/territory/addUserToTeam";
        JSONObject dataJson = new JSONObject();
        dataJson.put("lng", lng);
        dataJson.put("lat", lat);
        dataJson.put("uid", uid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/territory/addUserToTeam\n" + result);
        return JSONObject.parseObject(result);
    }

    //我的领地列表
    public JSONObject territoryPersonalTerritoriesList(int page, int count, String uid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/territory/personalTerritories/list";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        dataJson.put("uid", uid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/territory/personalTerritories/list\n" + result);
        return JSONObject.parseObject(result);
    }

    //海外版-获取邮箱验证码
    public JSONObject userSendEmailVerifyCode(String email) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/sendEmailVerifyCode";
        JSONObject dataJson = new JSONObject();
        dataJson.put("email", email);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/sendEmailVerifyCode\n" + result);
        return JSONObject.parseObject(result);
    }

    //购买领地券-支付宝支付（goldTicketNumber参数类型？？）
    public JSONObject ticketAlipay(int goldTicketNumber, int sliverTicketNumber, int copperTicketNumber) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/ticket/alipay";
        JSONObject dataJson = new JSONObject();
        dataJson.put("goldTicketNumber", goldTicketNumber);
        dataJson.put("sliverTicketNumber", sliverTicketNumber);
        dataJson.put("copperTicketNumber", copperTicketNumber);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/ticket/alipay\n" + result);
        return JSONObject.parseObject(result);
    }

    //购买领地券-微信支付-创建订单（goldTicketNumber参数类型？？）
    public JSONObject ticketWechatpay(int goldTicketNumber, int sliverTicketNumber, int copperTicketNumber) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/ticket/wechatpay";
        JSONObject dataJson = new JSONObject();
        dataJson.put("goldTicketNumber", goldTicketNumber);
        dataJson.put("sliverTicketNumber", sliverTicketNumber);
        dataJson.put("copperTicketNumber", copperTicketNumber);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/ticket/wechatpay\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取领地券价格(参数不全)
    public JSONObject ticketGetPrice(int goldTicketNumber, int sliverTicketNumber, int copperTicketNumber) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/ticket/get/price";
        JSONObject dataJson = new JSONObject();
        dataJson.put("goldTicketNumber", goldTicketNumber);
        dataJson.put("sliverTicketNumber", sliverTicketNumber);
        dataJson.put("copperTicketNumber", copperTicketNumber);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/ticket/get/price\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取赠送的领地券
    public JSONObject ticketWithdraw(String phone, String uuid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/ticket/withdraw";
        JSONObject dataJson = new JSONObject();
        dataJson.put("phone", phone);
        dataJson.put("uuid", uuid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/ticket/withdraw\n" + result);
        return JSONObject.parseObject(result);
    }

    //福豆兑换领地券
    public JSONObject ticketExchange(int count) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/ticket/exchange";
        JSONObject dataJson = new JSONObject();
        dataJson.put("count", count);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/ticket/exchange\n" + result);
        return JSONObject.parseObject(result);
    }

    //通过云信ID获取用户信息
    public JSONObject userGetYunxun_id(String yunchatId) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/get/yunxun_id";
        JSONObject dataJson = new JSONObject();
        dataJson.put("yunchat_id", yunchatId);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/get/yunxun_id\n" + result);
        return JSONObject.parseObject(result);
    }

    //赠送领地券-赠送给app内的用户（goldTicketNumber参数类型）
    public JSONObject userSendApp(int goldTicketNumber, double toUid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/send/app";
        JSONObject dataJson = new JSONObject();
        dataJson.put("goldTicketNumber", goldTicketNumber);
        dataJson.put("toUid", toUid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/send/app\n" + result);
        return JSONObject.parseObject(result);
    }

    //赠送领地券-分享到微信
    public JSONObject userSendWechat(int count) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/send/wechat";
        JSONObject dataJson = new JSONObject();
        dataJson.put("count", count);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/send/wechat\n" + result);
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

    //获取用户标签列表
    public JSONObject tagListUid(String targetuid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/tag/list/uid";
        JSONObject dataJson = new JSONObject();
        dataJson.put("target_uid", targetuid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/tag/list/uid\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取热门标签
    public JSONObject tagListHot(int page, int count) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/tag/list/hot";
        JSONObject dataJson = new JSONObject();
        dataJson.put("page", page);
        dataJson.put("count", count);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/tag/list/hot\n" + result);
        return JSONObject.parseObject(result);
    }

    //获取标签详情（标签ID tid参数类型？？）
    public JSONObject tagDetail(double tid) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/tag/detail";
        JSONObject dataJson = new JSONObject();
        dataJson.put("tid", tid);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/tag/detail\n" + result);
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

    //确认标签
    public JSONObject tagConfirm(String name, String phone, String tags) {
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

    //添加/修改标签
    public JSONObject tagHandle(JSONObject handleTag) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/tag/handle";
        String dataArg = JSONObject.toJSONString(handleTag);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/tag/handle\n" + result);
        return JSONObject.parseObject(result);
    }

    //更新资金密码(验证码code参数类型？？)
    public JSONObject userUpdatePassphrase(double code, String passphrase) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/update/passphrase";
        JSONObject dataJson = new JSONObject();
        dataJson.put("code", code);
        dataJson.put("passphrase", passphrase);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/update/passphrase\n" + result);
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

    //国内版-手机号登录(验证码code参数类型？？)
    public JSONObject userLogin(String phone, double code, String inviteUid) {
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

    //国内版-手机号一键登录
    public JSONObject userOneClickLogin(String accessToken) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/oneClickLogin";
        JSONObject dataJson = new JSONObject();
        dataJson.put("access_token", accessToken);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/oneClickLogin\n" + result);
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

    //获取系统升级
    public JSONObject getLastesVersion(String version, String platform) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/system/get/lastesVersion";
        JSONObject dataJson = new JSONObject();
        dataJson.put("version", version);
        dataJson.put("platform", platform);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/system/get/lastesVersion\n" + result);
        return JSONObject.parseObject(result);
    }

    //海外版-注册
    public JSONObject emailRegister(String email, String code, String password) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/emailRegister";
        JSONObject dataJson = new JSONObject();
        dataJson.put("email", email);
        dataJson.put("code", code);
        dataJson.put("password", password);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/emailRegister\n" + result);
        return JSONObject.parseObject(result);
    }

    //海外版-检查验证码
    public JSONObject checkEmailVerifyCode(String email, String code) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/checkEmailVerifyCode";
        JSONObject dataJson = new JSONObject();
        dataJson.put("email", email);
        dataJson.put("code", code);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/checkEmailVerifyCode\n" + result);
        return JSONObject.parseObject(result);
    }

    //海外版-检查验证码
    public JSONObject forgetPassword(String email, String code, String password) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/user/forgetPassword";
        JSONObject dataJson = new JSONObject();
        dataJson.put("email", email);
        dataJson.put("code", code);
        dataJson.put("password", password);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/user/forgetPassword\n" + result);
        return JSONObject.parseObject(result);
    }
    //通过链接/淘口令获取商品信息
    public JSONObject productFetchProductInfo(String link) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/product/fetchProductInfo";
        JSONObject dataJson = new JSONObject();
        dataJson.put("link", link);
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/product/fetchProductInfo\n" + result);
        return JSONObject.parseObject(result);
    }
    //确认推荐
    public JSONObject productConfirmRecommend(JSONObject dataJson) {
        String url = GetConfigUtil.getTestProperty("config", "RecommendUrl") + "lingzhu/product/confirmRecommend";
        String dataArg = JSONObject.toJSONString(dataJson);
        String result = ApiClient.Post(url, dataArg);
        Log.logInfo("lingzhu/product/confirmRecommend\n" + result);
        return JSONObject.parseObject(result);
    }

}

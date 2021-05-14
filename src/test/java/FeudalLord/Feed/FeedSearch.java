package FeudalLord.Feed;

import BaseUtil.TestBase;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.FileUtil;

import java.sql.SQLException;

public class FeedSearch extends TestBase {
    @Test(description = "获取我发布的帖子")
    public void getFeedList() {
//        前置条件
        JSONObject feedData = FileUtil.getFieldJsonData("data\\feedCreate.json");
        String time = System.currentTimeMillis() + "";
        feedData.put("content", time);
        JSONObject createResult = feedCreate(feedData);
        Assert.assertEquals(createResult.get("code"), 200);
        JSONObject findResult = getMyFeedList(1);
        Assert.assertEquals(findResult.getJSONObject("data").getJSONArray("feeds").getJSONObject(0).get("content"), time);
//    后置条件
//        feedRemove();
    }
    @Test(description = "已删除的帖子不会查询到")
    public void getRemoveFeedList() throws SQLException {
        JSONObject feedData = FileUtil.getFieldJsonData("data\\feedCreate.json");
        String time = System.currentTimeMillis() + "";
        feedData.put("content", time);
        JSONObject createResult = feedCreate(feedData);
        Assert.assertEquals(createResult.get("code"), 200);
//        查询我发布的帖子
        JSONObject findResult = getMyFeedList(1);
        Long id=(Long) findResult.getJSONObject("data").getJSONArray("feeds").getJSONObject(0).get("id");
//        删除帖子
        feedRemove(id);
        JSONObject findRemoveResult = getMyFeedList(1);
        Assert.assertNotEquals(findRemoveResult.getJSONObject("data").getJSONArray("feeds").getJSONObject(0).get("content"), time);

    }
}

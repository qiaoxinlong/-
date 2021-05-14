package FeudalLord.Feed;

import BaseUtil.TestBase;
import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.FileUtil;

public class FeedCreate extends TestBase {





    @Test(description = "正常发布一个帖子")
    public void createFeed() {
        JSONObject feedData = FileUtil.getFieldJsonData("data\\feedCreate.json");
        String time = System.currentTimeMillis() + "";
        feedData.put("content", time);
        JSONObject createResult = feedCreate(feedData);
        Assert.assertEquals(createResult.get("code"), 200);
        JSONObject findResult = getMyFeedList(1);
        Assert.assertEquals(findResult.getJSONObject("data").getJSONArray("feeds").getJSONObject(0).get("content"), time);
    }

    @Test(description = "社群ID不存在")
    public void cidNotExist() {
        JSONObject feedData = FileUtil.getFieldJsonData("data\\feedCreate.json");
        feedData.put("cid", -1);
        JSONObject createResult = feedCreate(feedData);
        Assert.assertEquals(createResult.get("code"), 400);
    }

    @Test(description = "内容为空，发帖失败")
    public void contentIsEmpty() {
        JSONObject feedData = FileUtil.getFieldJsonData("data\\feedCreate.json");
        feedData.put("content", "");
        JSONObject createResult = feedCreate(feedData);
        Assert.assertEquals(createResult.get("code"), 400);
    }
}

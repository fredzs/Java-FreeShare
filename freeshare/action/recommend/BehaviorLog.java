package com.free4lab.freeshare.action.recommend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.free4lab.utils.recommend.RecommendObject;


/***
 * 行为日志 .
 * json格式的字符串.描述用户在freeshare的一次行为.
 * A 对 B 做了某操作，并可能与Ｃ相关
 * 
<pre>
{
    A :{RecommendObject}
    BehaviorType
    B :{RecommendObject}
    C :{RecommendObject}
    Timestamp: 时间
}
</pre>
 * @author wangchao
 *
 */
public class BehaviorLog {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String BEHAVIOR_TYPE = "BehaviorType";
    public static String TIMESTAMP = "Timestamp";
    public static String A = "A";
    public static String B = "B";
    public static String C = "C";
    
    
    public static final String STARTTIME_STRING = "1970-01-01 00:00:00";
    public static final Long STARTTIME = -28800000L;//Const.sdf.parse("1970-01-01 00:00:00").getTime();
    
    //TODO 增加权值
    
    private static final RecommendObject EMPTY_OBJECT = new RecommendObject();
    private String behaviorType = Constant.BEHAVIOR_TYPE_UNKOWN;
    private RecommendObject a = EMPTY_OBJECT;
    private RecommendObject b = EMPTY_OBJECT;
    private RecommendObject c = EMPTY_OBJECT;
    
    private long timestamp = STARTTIME;
    private String timestampstr = null;
    
    public String toJSON() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put(A, a.getJSON());   
        obj.put(B, b.getJSON());
        obj.put(C, c.getJSON());
        
        obj.put(BEHAVIOR_TYPE, behaviorType)
        .put(TIMESTAMP, timestamp);
        return obj.toString();
    }
    
    static public BehaviorLog fromJSON(String json) throws JSONException{
        BehaviorLog bl = new BehaviorLog();
        
        JSONObject obj = new JSONObject(json);
        bl.behaviorType = obj.getString(BEHAVIOR_TYPE);
        bl.timestamp = obj.getLong(TIMESTAMP);
        bl.a = RecommendObject.fromJSON(obj.getJSONObject(A));
        bl.b = RecommendObject.fromJSON(obj.getJSONObject(B));
        bl.c = RecommendObject.fromJSON(obj.getJSONObject(C));
        return bl;
    }

    public RecommendObject getA() {
        return a;
    }

    public void setA(RecommendObject a) {
        this.a = a;
    }

    public RecommendObject getB() {
        return b;
    }

    public void setB(RecommendObject b) {
        this.b = b;
    }

    public RecommendObject getC() {
        return c;
    }

    public void setC(RecommendObject c) {
        this.c = c;
    }

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getTimestampString() {
        if(timestampstr == null){
            timestampstr = sdf.format(new Date(timestamp));
        }
        return timestampstr;
    }

    public void setTimestamp(String timestamp) throws ParseException {
        this.timestamp = sdf.parse(timestamp).getTime();
    }
    
    
    
    
}

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Will on 2017/5/31 12:00.
 */
public class JSONTest {
    public static void main(String[] args) {
        String str = "{\"FuzzyLineList\":null,\"LineList\":[{\"DownEndStationId\":22136,\"DownEndStationName\":\"昌盛花园公交首末站\",\"DownStartStationId\":646,\"DownStartStationName\":\"雀墓桥村\",\"Id\":74,\"LineDownEndTime\":\"21:30\",\"LineDownStartTime\":\"06:30\",\"LineType\":\"普通线路\",\"LineUpEndTime\":\"21:00\",\"LineUpStartTime\":\"06:10\",\"LinedownIntervalTime\":0,\"LinedownLength\":0,\"LineupIntervalTime\":0,\"LineupLength\":0,\"Name\":\"2路\",\"Price\":2,\"Remark\":null,\"ShortName\":\"2\",\"TicketType\":1,\"UpEndStationId\":645,\"UpEndStationName\":\"雀墓桥村\",\"UpStartStationId\":22136,\"UpStartStationName\":\"昌盛花园公交首末站\",\"IcCard\":\"0\",\"IsRing\":false,\"NormalPrice\":2,\"SeasonPrice\":2}]}";
         JSONObject jo = JSONObject.parseObject(str);
        StringBuilder sub = new StringBuilder("abcdd&");
        System.out.println(sub.substring(0,sub.length() - 1));
        jo.put("lineName",2);
        System.out.println(jo.toString());

    }
}

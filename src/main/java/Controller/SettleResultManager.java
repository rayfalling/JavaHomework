package Controller;

import Model.SettleResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static Controller.FileIO.ReadFile;

public class SettleResultManager {
    public List<SettleResult> getFromFile() {
        String str = ReadFile("data/结算信息.txt");
        if (str == null|| str.equals(""))
            return new ArrayList<>();
        else return JSON.parseArray(str, SettleResult.class);
    }

    public boolean writeToFile(ArrayList<SettleResult> settleResults) {
        if (settleResults != null) {
            return FileIO.WriteFile(JSONArray.toJSONString(settleResults), "data/结算信息.txt", true);
        }
        return false;
    }
}

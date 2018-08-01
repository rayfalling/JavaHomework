package Controller;

import Model.PreSettleResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static Controller.FileIO.ReadFile;

public class PreSettleResultManager {
    public List<PreSettleResult> getFromFile() {
        String str = ReadFile("data/预结算信息.txt");
        if (str == null || str.equals(""))
            return new ArrayList<>();
        else return JSON.parseArray(str, PreSettleResult.class);
    }

    public boolean writeToFile(ArrayList<PreSettleResult> preSettleResults) {
        if (preSettleResults != null) {
            return FileIO.WriteFile(JSONArray.toJSONString(preSettleResults), "data/预结算信息.txt", true);
        }
        return false;
    }
}

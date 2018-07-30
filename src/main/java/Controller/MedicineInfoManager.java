package Controller;

import Model.MedicineInfo;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static Controller.FileIO.ReadFile;

/**
 * 药品信息管理类
 * 将数据写入文件
 *
 * @author wanghaiwei
 * @version 1.0
 */

public class MedicineInfoManager {
    public static List<MedicineInfo> getFromFile() {
        String str = ReadFile("data/药品信息.txt");
        if (str == null)
            return new ArrayList<>();
        else return JSONArray.parseArray(str, MedicineInfo.class);
    }

    public static boolean writeToFile(ArrayList<MedicineInfo> medicineInfoArrayList) {
        if (medicineInfoArrayList != null) {
            return FileIO.WriteFile(JSONArray.toJSONString(medicineInfoArrayList), "data/药品信息.txt", true);
        }
        return false;
    }
}
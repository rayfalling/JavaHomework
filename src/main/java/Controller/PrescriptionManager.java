package Controller;

import Model.Prescription;
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
public class PrescriptionManager {
    public static List<Prescription> getFromFile() {
        String str = ReadFile("data/处方明细信息.txt");
        if (str == null)
            return new ArrayList<>();
        else return JSONArray.parseArray(str, Prescription.class);
    }

    public static boolean writeToFile(ArrayList<Prescription> prescriptionArrayList) {
        if (prescriptionArrayList != null) {
            return FileIO.WriteFile(JSONArray.toJSONString(prescriptionArrayList), "data/处方明细信息.txt", true);
        }
        return false;
    }
}

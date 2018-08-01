package Controller;

import Model.Hospital;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static Controller.FileIO.ReadFile;

public class HospitalManager {
    public List<Hospital> getFromFile() {
        String str = ReadFile("data/医院信息.txt");
        if (str == null|| str.equals(""))
            return new ArrayList<>();
        else return JSON.parseArray(str, Hospital.class);
    }

    public boolean writeToFile(ArrayList<Hospital> medicineInfoArrayList) {
        if (medicineInfoArrayList != null) {
            return FileIO.WriteFile(JSONArray.toJSONString(medicineInfoArrayList), "data/医院信息.txt", true);
        }
        return false;
    }
}

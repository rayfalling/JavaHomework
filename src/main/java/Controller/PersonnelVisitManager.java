package Controller;

import Model.PersonnelVisit;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static Controller.FileIO.ReadFile;
/**
 * 人员就诊信息管理类
 * 将数据写入文件
 *
 * @author wanghaiwei
 * @version 1.0
 */
public class PersonnelVisitManager {
    public List<PersonnelVisit> getFromFile() {
        String str = ReadFile("data/人员就诊信息.txt");
        if (str == null)
            return new ArrayList<>();
        else return JSONArray.parseArray(str, PersonnelVisit.class);
    }

    public boolean writeToFile(ArrayList<PersonnelVisit> personnelVisitArrayList) {
        if (personnelVisitArrayList != null) {
            return FileIO.WriteFile(JSONArray.toJSONString(personnelVisitArrayList), "data/人员就诊信息.txt", true);
        }
        return false;
    }
}

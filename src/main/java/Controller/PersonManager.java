package Controller;

import Model.Person;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static Controller.FileIO.ReadFile;

/**
 * 人员管理类
 * 将数据写入文件
 *
 * @author wanghaiwei
 * @version 1.0
 */
public class PersonManager {
    public List<Person> getFromFile() {
        String str = ReadFile("data/人员信息.txt");
        if (str == null|| str.equals(""))
            return new ArrayList<>();
        else return JSON.parseArray(str, Person.class);
    }

    public boolean writeToFile(ArrayList<Person> personArrayList) {
        if (personArrayList != null) {
            return FileIO.WriteFile(JSONArray.toJSONString(personArrayList), "data/人员信息.txt", true);
        }
        return false;
    }
}

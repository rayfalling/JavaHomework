package Controller;

import Model.Model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 工具类
 *
 * @author wanghaiwei
 * @version 0.2
 */
public class Tools {
    /**
     * 属性过滤&参数化查询
     * 传入查询字符形式"name:testuser"
     * 如果query为空，默认返回false
     *
     * @param query 接受的查询数组
     * @return 返回查询结果
     */
    public static boolean filter(Class c, String... query) {
        if (query.length <= 0) return false;
        boolean b[] = new boolean[query.length];
        Field[] field = c.getClass().getDeclaredFields();
        Field.setAccessible(field, true);
        for (Field aField : field) {
            String name = aField.getName().toLowerCase();
            for (int j = 0; j < query.length; j++) {
                try {
                    if (name.equals(query[j].substring(0, query[j].indexOf(":")))) {
                        Method m = c.getClass().getMethod("get" + name);
                        String value = m.invoke(c).toString();
                        if (value.contentEquals(query[j].substring(query[j].indexOf(":") + 1)))
                            b[j] = true;
                    }
                } catch (Exception e) {
                    b[j] = false;
                }
            }
        }
        Field.setAccessible(field, false);
        for (boolean tb : b) if (!tb) return false;
        return true;
    }

    private List<Model> modelArrayList = new ArrayList<>();

    /**
     * 实例化时的类型
     */
    private Class aClass;

    public Tools(Class aClass) {
        this.aClass = aClass;
    }

    /**
     * 添加操作
     * 接受参数可变长
     */
    public int Add(Model... M) {
        try {
            modelArrayList.addAll(Arrays.asList(M));
            return M.length;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 删除操作
     * 接受参数可变长
     */
    public boolean Remove(Model... M) {
        try {
            for (Model aM : M)
                if (modelArrayList.indexOf(aM) != -1)
                    modelArrayList.remove(aM);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 更新数据
     * 使用可变长参数列表
     */
    public boolean Update(Model... M) {
        try {
            for (Model aM : M)
                modelArrayList.forEach((model) -> {
                    if (model.getId().equals(aM.getId()))
                        modelArrayList.set(modelArrayList.indexOf(model), aM);
                });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 可变参数查询
     * 查询参数参见
     *
     * @see Tools#filter(Class, String...)
     */
    public ArrayList<Model> Find(String... query) {
        ArrayList<Model> temp = new ArrayList<>();
        modelArrayList.forEach((medicineInfo) -> {
            if (Tools.filter(aClass, query))
                temp.add(medicineInfo);
        });
        return temp;
    }
}

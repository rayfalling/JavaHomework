package Controller;

import Model.Model;
import Model.SettleResult;
import Model.ChargeItemLevel;
import Model.HospitalLevel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
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
    public Tools() {
    }

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

    public List<Model> getModelArrayList() {
        return modelArrayList;
    }

    public void setModelArrayList(List<Model> modelArrayList) {
        this.modelArrayList = modelArrayList;
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

    /**
     * 打印结算报表,使用poi库操作xls文件表格每一项
     * 出现了一些问题
     * 暂时不使用
     *
     * @param Id 用户索引
     */
    public void printSettleResultToExcel(String Id) throws IOException {
        SettleResult result = new SettleResultManager().getFromFile().stream().filter(settleResult -> settleResult.getId().equals(Id)).findFirst().get();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("结算清单");

        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleRowCell = titleRow.createCell(0);

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont fontStyle = workbook.createFont();
        fontStyle.setBold(true);
        fontStyle.setFontHeightInPoints((short) 18);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFont(fontStyle);
        titleRow.getCell(0).setCellStyle(cellStyle);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        //表头
        titleRowCell.setCellValue("报销结算清单");
        sheet.addMergedRegion(new CellRangeAddress(0, 3, 0, 10));
        HSSFRow settleDataRow = sheet.createRow(4);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 4));
        settleDataRow.createCell(5).setCellValue("结算日期");

        //录入结算日期
        settleDataRow.createCell(6).setCellValue(result.getSettleDate());
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 6, 10));

        HSSFRow baseRow = sheet.createRow(5);
        baseRow.createCell(0).setCellValue("姓名");
        baseRow.createCell(1).setCellValue(result.getName());
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 2));
        baseRow.createCell(3).setCellValue("个人编号");
        baseRow.createCell(4).setCellValue(result.getId());
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 4, 5));
        baseRow.createCell(6).setCellValue("人员类别");
        baseRow.createCell(7).setCellValue(result.getCategory());
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 7, 10));

        /*就诊医院和诊疗时段*/
        HSSFRow medicalRow = sheet.createRow(6);
        medicalRow.createCell(0).setCellValue("就诊医院");
        medicalRow.getCell(0).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(6, 6 + result.getInHospitalTime() - 1, 0, 0));
        medicalRow.createCell(6).setCellValue("就诊时段");
        medicalRow.getCell(6).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(6, 6 + result.getInHospitalTime() - 1, 6, 6));
        medicalRow.createCell(1).setCellValue(result.getMedicalTimeRange().get(0));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 5));
        medicalRow.createCell(7).setCellValue(result.getMedicalTimeRange().get(0));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 7, 10));
        for (int i = 1; i < result.getInHospitalTime(); i++) {
            HSSFRow row = sheet.createRow(6 + i);
            row.createCell(1).setCellValue(result.getMedicalTimeRange().get(i));
            sheet.addMergedRegion(new CellRangeAddress(6 + i, 6 + i, 1, 5));
            row.createCell(7).setCellValue(result.getMedicalTimeRange().get(i));
            sheet.addMergedRegion(new CellRangeAddress(6 + i, 6 + i, 7, 10));
        }

        int m = 5 + result.getInHospitalTime() + 1;

        sheet.createRow(m).createCell(0).setCellValue("结算明细：");
        sheet.addMergedRegion(new CellRangeAddress(m, m, 0, 10));
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
        hssfCellStyle.setFont(font);
        sheet.getRow(m).getCell(0).setCellStyle(hssfCellStyle);


        sheet.createRow(m + 1).createCell(0).setCellValue("起付标准：");
        sheet.addMergedRegion(new CellRangeAddress(m + 1, m + 1, 0, 2));
        sheet.createRow(m + 2).createCell(0).setCellValue("100元");
        sheet.getRow(m + 2).getCell(0).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(m + 2, m + 2, 0, 2));

        int e = m + 3 + result.getbClassItem().size() + result.getcClassItem().size() + 1;

        sheet.createRow(m + 3).createCell(0).setCellValue("自费项目：");
        sheet.addMergedRegion(new CellRangeAddress(m + 3, m + 3, 0, 2));
        if (result.getbClassItem().isEmpty()) {
            sheet.getRow(m + 3).getCell(0).setCellValue("自费项目：无");
        }
        for (int i = 0; i < result.getcClassItem().size(); i++) {
            sheet.createRow(m + 4 + i).createCell(0).setCellValue(result.getcClassItem().get(i));
            sheet.addMergedRegion(new CellRangeAddress(m + 4 + i, m + 4 + i, 0, 2));
            sheet.getRow(m + 4 + i).getCell(0).setCellStyle(style);
        }

        sheet.createRow(m + 3 + result.getcClassItem().size() + 1).createCell(0).setCellValue("乙类项目：");
        sheet.addMergedRegion(new CellRangeAddress(m + 3 + result.getcClassItem().size() + 1, m + 3 + result.getcClassItem().size() + 1, 0, 2));
        if (result.getbClassItem().isEmpty()) {
            sheet.getRow(m + 3 + result.getcClassItem().size() + 1).getCell(0).setCellValue("乙类项目：无");
        }
        for (int i = 0; i < result.getcClassItem().size(); i++) {
            sheet.createRow(m + 3 + result.getcClassItem().size() + 1 + i + 1).createCell(0).setCellValue(result.getbClassItem().get(i));
            sheet.addMergedRegion(new CellRangeAddress(m + 3 + result.getcClassItem().size() + 1 + i + 1, m + 3 + result.getcClassItem().size() + 1 + i + 1, 0, 2));
            sheet.getRow(m + 3 + result.getcClassItem().size() + 1 + i + 1).getCell(0).setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(m + 1, e, 3, 10));
        sheet.createRow(e + 1).createCell(0).setCellValue("个人自费费用：");
        sheet.addMergedRegion(new CellRangeAddress(e + 1, e + 1, 0, 2));
        sheet.createRow(e + 2).createCell(0).setCellValue("中心报销金额：");
        sheet.addMergedRegion(new CellRangeAddress(e + 2, e + 2, 0, 2));
        sheet.createRow(e + 3).createCell(0).setCellValue("年度花费总额：");
        sheet.getRow(e + 1).createCell(3).setCellValue(result.getSelfFundedAmount() + " 元");
        sheet.getRow(e + 2).createCell(3).setCellValue(result.getMedicalExpenses() + " 元");
        sheet.getRow(e + 3).createCell(3).setCellValue(result.getTotalExpenses() + "元");
        sheet.addMergedRegion(new CellRangeAddress(e + 1, e + 1, 3, 10));
        sheet.addMergedRegion(new CellRangeAddress(e + 2, e + 2, 3, 10));
        sheet.addMergedRegion(new CellRangeAddress(e + 3, e + 3, 3, 10));
        try {
            File file = new File(Class.class.getClass().getResource("/").toURI().getPath().replaceFirst("/", "") + "/报销结算打印单" + result.getId() + ".xls");
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 枚举类{@link HospitalLevel}转字符串
     * 无匹配默认返回""
     */
    public static String ConvertEnumHospitalLevelToString(HospitalLevel hospitalLevel) {
        switch (hospitalLevel) {
            case OneClass:
                return "一类";
            case SecondClass:
                return "二类";
            case ThirdClass:
                return "三类";
            case Community:
                return "社区";
        }
        return "";
    }

    /**
     * 字符串转枚举类{@link HospitalLevel}
     * 无匹配默认返回社区级别
     */
    public static HospitalLevel ConvertStringToEnumHospitalLevel(String hospitalLevel) {
        switch (hospitalLevel) {
            case "一类":
                return HospitalLevel.OneClass;
            case "二类":
                return HospitalLevel.SecondClass;
            case "三类":
                return HospitalLevel.ThirdClass;
            case "社区":
                return HospitalLevel.Community;
        }
        return HospitalLevel.Community;
    }

    /**
     * 枚举类{@link ChargeItemLevel}转字符串
     * 无匹配默认返回""
     */
    public static String ConvertEnumChargeItemLevelToString(ChargeItemLevel chargeItemLevel) {
        switch (chargeItemLevel) {
            case ClassA:
                return "甲类";
            case ClassB:
                return "乙类";
            case ClassC:
                return "丙类";
        }
        return "";
    }

    /**
     * 字符串转枚举类{@link HospitalLevel}
     * 无匹配默认返回社丙类
     */
    public static ChargeItemLevel ConvertStringToEnumChargeItemLevel(String hospitalLevel) {
        switch (hospitalLevel) {
            case "甲类":
                return ChargeItemLevel.ClassA;
            case "乙类":
                return ChargeItemLevel.ClassB;
            case "丙类":
                return ChargeItemLevel.ClassC;
        }
        return ChargeItemLevel.ClassC;
    }
}


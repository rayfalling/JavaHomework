package Model;

import java.util.ArrayList;

/**
 * 结算结果
 *
 * @author 海蔚
 * @version 0.1
 */
public class SettleResult extends Model {
    /**
     * 索引
     */
    private String Id;
    /**
     * 病人姓名
     */
    private String name;
    /**
     * 乙类药品名录
     */
    private ArrayList<String> bClassItem;
    /**
     * 医疗类别
     */
    private String category;
    /**
     * 结算日期
     */
    private String settleDate;
    /**
     * 就诊次数
     */
    private int inHospitalTime;
    /**
     * 就诊日期段
     */
    private ArrayList<String> medicalTimeRange;
    /**
     * 自费药品名录
     */
    private ArrayList<String> cClassItem;
    /**
     * 总计报销总额
     */
    private double totalExpenses;
    /**
     * 药品报销总额
     */
    private double medicalExpenses;
    /**
     * 诊疗报销总额
     */
    private double treatmentExpenses;
    /**
     * 医疗服务报销总额
     */
    private double serviceExpenses;
    /**
     * 自费金额
     */
    private double selfFundedAmount;
    /**
     * 起付标准
     */
    private double startStandardAmount;

    public SettleResult() {
    }

    public SettleResult(String id, String name, ArrayList<String> bClassItem, String category, String settleDate, int inHospitalTime, ArrayList<String> medicalTimeRange, ArrayList<String> cClassItem, double totalExpenses, double medicalExpenses, double treatmentExpenses, double serviceExpenses, double selfFundedAmount, double startStandardAmount) {
        Id = id;
        this.name = name;
        this.bClassItem = bClassItem;
        this.category = category;
        this.settleDate = settleDate;
        this.inHospitalTime = inHospitalTime;
        this.medicalTimeRange = medicalTimeRange;
        this.cClassItem = cClassItem;
        this.totalExpenses = totalExpenses;
        this.medicalExpenses = medicalExpenses;
        this.treatmentExpenses = treatmentExpenses;
        this.serviceExpenses = serviceExpenses;
        this.selfFundedAmount = selfFundedAmount;
        this.startStandardAmount = startStandardAmount;
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getbClassItem() {
        return bClassItem;
    }

    public void setbClassItem(ArrayList<String> bClassItem) {
        this.bClassItem = bClassItem;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public int getInHospitalTime() {
        return inHospitalTime;
    }

    public void setInHospitalTime(int inHospitalTime) {
        this.inHospitalTime = inHospitalTime;
    }

    public ArrayList<String> getMedicalTimeRange() {
        return medicalTimeRange;
    }

    public void setMedicalTimeRange(ArrayList<String> medicalTimeRange) {
        this.medicalTimeRange = medicalTimeRange;
    }

    public ArrayList<String> getcClassItem() {
        return cClassItem;
    }

    public void setcClassItem(ArrayList<String> cClassItem) {
        this.cClassItem = cClassItem;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getMedicalExpenses() {
        return medicalExpenses;
    }

    public void setMedicalExpenses(double medicalExpenses) {
        this.medicalExpenses = medicalExpenses;
    }

    public double getTreatmentExpenses() {
        return treatmentExpenses;
    }

    public void setTreatmentExpenses(double treatmentExpenses) {
        this.treatmentExpenses = treatmentExpenses;
    }

    public double getServiceExpenses() {
        return serviceExpenses;
    }

    public void setServiceExpenses(double serviceExpenses) {
        this.serviceExpenses = serviceExpenses;
    }

    public double getSelfFundedAmount() {
        return selfFundedAmount;
    }

    public void setSelfFundedAmount(double selfFundedAmount) {
        this.selfFundedAmount = selfFundedAmount;
    }

    public double getStartStandardAmount() {
        return startStandardAmount;
    }

    public void setStartStandardAmount(double startStandardAmount) {
        this.startStandardAmount = startStandardAmount;
    }
}

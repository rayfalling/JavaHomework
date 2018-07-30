package Model;

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

    public SettleResult(String id, double totalExpenses, double medicalExpenses, double treatmentExpenses, double serviceExpenses, double selfFundedAmount, double startStandardAmount) {
        Id = id;
        this.totalExpenses = totalExpenses;
        this.medicalExpenses = medicalExpenses;
        this.treatmentExpenses = treatmentExpenses;
        this.serviceExpenses = serviceExpenses;
        this.selfFundedAmount = selfFundedAmount;
        this.startStandardAmount = startStandardAmount;
    }

    public SettleResult() {
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public void setId(String id) {
        Id = id;
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

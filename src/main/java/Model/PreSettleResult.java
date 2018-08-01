package Model;

/**
 * 预结算信息
 *
 * @author 海蔚
 * @version 0.1
 */
public class PreSettleResult extends Model {
    /**
     * 索引
     * 关联用户Id
     */
    private String Id;
    /**
     * 门诊号
     */
    private String clinicNumber;
    /**
     * 费用总额
     */
    private double totalExpenses;
    /**
     * 报销金额
     */
    private double reimbursementAmount;
    /**
     * 自费金额
     */
    private double selfFundedAmount;
    /**
     * 起付标准
     */
    private double startStandardAmount;
    /**
     * 第一段自费
     */
    private double firstRangeAmount;
    /**
     * 第二段自费
     */
    private double secondRangeAmount;
    /**
     * 第三段自费
     */
    private double thirdRangeAmount;
    /**
     * 年度总计报销
     */
    private double yearTotalReimbursementAmount;

    public PreSettleResult(String id, String clinicNumber, double totalExpenses, double reimbursementAmount, double selfFundedAmount, double startStandardAmount, double firstRangeAmount, double secondRangeAmount, double thirdRangeAmount, double yearTotalReimbursementAmount) {
        Id = id;
        this.clinicNumber = clinicNumber;
        this.totalExpenses = totalExpenses;
        this.reimbursementAmount = reimbursementAmount;
        this.selfFundedAmount = selfFundedAmount;
        this.startStandardAmount = startStandardAmount;
        this.firstRangeAmount = firstRangeAmount;
        this.secondRangeAmount = secondRangeAmount;
        this.thirdRangeAmount = thirdRangeAmount;
        this.yearTotalReimbursementAmount = yearTotalReimbursementAmount;
    }

    public PreSettleResult() {

    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public void setId(String id) {
        Id = id;
    }

    public String getClinicNumber() {
        return clinicNumber;
    }

    public void setClinicNumber(String clinicNumber) {
        this.clinicNumber = clinicNumber;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getReimbursementAmount() {
        return reimbursementAmount;
    }

    public void setReimbursementAmount(double reimbursementAmount) {
        this.reimbursementAmount = reimbursementAmount;
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

    public double getFirstRangeAmount() {
        return firstRangeAmount;
    }

    public void setFirstRangeAmount(double firstRangeAmount) {
        this.firstRangeAmount = firstRangeAmount;
    }

    public double getSecondRangeAmount() {
        return secondRangeAmount;
    }

    public void setSecondRangeAmount(double secondRangeAmount) {
        this.secondRangeAmount = secondRangeAmount;
    }

    public double getThirdRangeAmount() {
        return thirdRangeAmount;
    }

    public void setThirdRangeAmount(double thirdRangeAmount) {
        this.thirdRangeAmount = thirdRangeAmount;
    }

    public double getYearTotalReimbursementAmount() {
        return yearTotalReimbursementAmount;
    }

    public void setYearTotalReimbursementAmount(double yearTotalReimbursementAmount) {
        this.yearTotalReimbursementAmount = yearTotalReimbursementAmount;
    }
}



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
     */
    private String Id;
    /**
     * 费用总额
     */
    private double totalExpensesl;
    /**
     * 报销金额
     */
    private double reimbursementAmountl;
    /**
     * 自费金额
     */
    private double selfFundedAmountl;
    /**
     * 起付标准
     */
    private double startStandardAmountl;
    /**
     * 第一段自费
     */
    private double firstRangeAmountl;
    /**
     * 第二段自费
     */
    private double secondRangeAmountl;
    /**
     * 第三段自费
     */
    private double thirdRangeAmount;
    /**
     * 乙类自费金额
     */
    private double bClassSelfFeeAmount;
    /**
     * 特检特治自费金额
     */
    private double specialSelfFeeAmount;

    public PreSettleResult(String id, double totalExpensesl, double reimbursementAmountl, double selfFundedAmountl, double startStandardAmountl, double firstRangeAmountl, double secondRangeAmountl, double thirdRangeAmount, double bClassSelfFeeAmount, double specialSelfFeeAmount) {
        Id = id;
        this.totalExpensesl = totalExpensesl;
        this.reimbursementAmountl = reimbursementAmountl;
        this.selfFundedAmountl = selfFundedAmountl;
        this.startStandardAmountl = startStandardAmountl;
        this.firstRangeAmountl = firstRangeAmountl;
        this.secondRangeAmountl = secondRangeAmountl;
        this.thirdRangeAmount = thirdRangeAmount;
        this.bClassSelfFeeAmount = bClassSelfFeeAmount;
        this.specialSelfFeeAmount = specialSelfFeeAmount;
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public void setId(String id) {
        Id = id;
    }

    public double getTotalExpensesl() {
        return totalExpensesl;
    }

    public void setTotalExpensesl(double totalExpensesl) {
        this.totalExpensesl = totalExpensesl;
    }

    public double getReimbursementAmountl() {
        return reimbursementAmountl;
    }

    public void setReimbursementAmountl(double reimbursementAmountl) {
        this.reimbursementAmountl = reimbursementAmountl;
    }

    public double getSelfFundedAmountl() {
        return selfFundedAmountl;
    }

    public void setSelfFundedAmountl(double selfFundedAmountl) {
        this.selfFundedAmountl = selfFundedAmountl;
    }

    public double getStartStandardAmountl() {
        return startStandardAmountl;
    }

    public void setStartStandardAmountl(double startStandardAmountl) {
        this.startStandardAmountl = startStandardAmountl;
    }

    public double getFirstRangeAmountl() {
        return firstRangeAmountl;
    }

    public void setFirstRangeAmountl(double firstRangeAmountl) {
        this.firstRangeAmountl = firstRangeAmountl;
    }

    public double getSecondRangeAmountl() {
        return secondRangeAmountl;
    }

    public void setSecondRangeAmountl(double secondRangeAmountl) {
        this.secondRangeAmountl = secondRangeAmountl;
    }

    public double getThirdRangeAmount() {
        return thirdRangeAmount;
    }

    public void setThirdRangeAmount(double thirdRangeAmount) {
        this.thirdRangeAmount = thirdRangeAmount;
    }

    public double getbClassSelfFeeAmount() {
        return bClassSelfFeeAmount;
    }

    public void setbClassSelfFeeAmount(double bClassSelfFeeAmount) {
        this.bClassSelfFeeAmount = bClassSelfFeeAmount;
    }

    public double getSpecialSelfFeeAmount() {
        return specialSelfFeeAmount;
    }

    public void setSpecialSelfFeeAmount(double specialSelfFeeAmount) {
        this.specialSelfFeeAmount = specialSelfFeeAmount;
    }
}

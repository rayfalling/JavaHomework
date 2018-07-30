package Model;

/**
 * 药品信息类
 *
 * @author 海蔚
 * @version 1.0
 */
public class MedicineInfo extends Model {
    /**
     * 药品编码
     * 索引
     */
    private String drugCode;
    /**
     * 药品名称
     */
    private String name;
    /**
     * 最高限价
     */
    private double priceLimit;
    /**
     * 药品剂量单位
     */
    private String drugDosageUnit;
    /**
     * 收费项目等级(甲类 乙类 丙类)
     */
    private ChargeItemLevel chargeItemLevel;
    /**
     * 医院等级(一级、二级、三级、社区)
     */
    private HospitalLevel hospitalLevel;

    public MedicineInfo(String drugCode, String name, double priceLimit, String drugDosageUnit, ChargeItemLevel chargeItemLevel, HospitalLevel hospitalLevel) {
        this.drugCode = drugCode;
        this.name = name;
        this.priceLimit = priceLimit;
        this.drugDosageUnit = drugDosageUnit;
        this.chargeItemLevel = chargeItemLevel;
        this.hospitalLevel = hospitalLevel;
    }

    public MedicineInfo() {
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(double priceLimit) {
        this.priceLimit = priceLimit;
    }

    public String getDrugDosageUnit() {
        return drugDosageUnit;
    }

    public void setDrugDosageUnit(String drugDosageUnit) {
        this.drugDosageUnit = drugDosageUnit;
    }

    public ChargeItemLevel getChargeItemLevel() {
        return chargeItemLevel;
    }

    public void setChargeItemLevel(ChargeItemLevel chargeItemLevel) {
        this.chargeItemLevel = chargeItemLevel;
    }

    public HospitalLevel getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(HospitalLevel hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    @Override
    public String getId() {
        return drugCode;
    }

    @Override
    public void setId(String drugCode) {
        this.drugCode = drugCode;
    }
}

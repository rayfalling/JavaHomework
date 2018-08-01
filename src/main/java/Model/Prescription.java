package Model;

/**
 * 处方信息类
 *
 * @author wanghaiwei
 * @version 1.0
 */
public class Prescription extends Model {
    /**
     * 索引
     */
    private String Id;
    /**
     * 门诊号
     */
    private String clinicNumber;
    /**
     * 药品编号
     */
    private String code;
    /**
     * 医院代码
     */
    private String hospitalCode;
    /**
     * 单价
     */
    private double itemPrice;
    /**
     * 数量
     */
    private double count;

    /**
     * 总金额
     */
    private double totalPrice;

    public Prescription(String id, String clinicNumber, String code, String hospitalCode, double itemPrice, double count, double totalPrice) {
        Id = id;
        this.clinicNumber = clinicNumber;
        this.code = code;
        this.hospitalCode = hospitalCode;
        this.itemPrice = itemPrice;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public Prescription() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
}

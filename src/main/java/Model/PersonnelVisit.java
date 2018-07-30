package Model;


import java.util.Date;

public class PersonnelVisit {
    /**
     * 人员ID
     */
    private String Id;
    /**
     * 门诊号
     */
    private String clinicNumber;
    /**
     * 医疗类别
     */
    private String category;
    /**
     * 入院日期
     */
    private Date admissionDate;
    /**
     * 出院日期
     */
    private Date leaveDate;
    /**
     * 医院代码
     */
    private String code;
    /**
     * 医院等级
     */
    private HospitalLevel hospitalLevel;
    /**
     * 出院原因
     */
    private String leaveReason;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getClinicNumber() {
        return clinicNumber;
    }

    public void setClinicNumber(String clinicNumber) {
        this.clinicNumber = clinicNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HospitalLevel getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(HospitalLevel hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public PersonnelVisit() {
    }

    public PersonnelVisit(String id, String clinicNumber, String category, Date admissionDate, String code, HospitalLevel hospitalLevel) {
        Id = id;
        this.clinicNumber = clinicNumber;
        this.category = category;
        this.admissionDate = admissionDate;
        this.code = code;
        this.hospitalLevel = hospitalLevel;
    }

}

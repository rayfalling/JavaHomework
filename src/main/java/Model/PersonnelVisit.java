package Model;


import java.time.LocalDate;

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
     * 11=在职职工
     * 21=退休人员
     * 40=享受最低保障的在职人员
     * 41=享受最低保障的退休人员
     */
    private String category;
    /**
     * 入院日期
     */
    private LocalDate admissionDate;
    /**
     * 出院日期
     */
    private LocalDate leaveDate;
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

    public PersonnelVisit(String id, String clinicNumber, String category, LocalDate admissionDate, LocalDate leaveDate, String code, HospitalLevel hospitalLevel, String leaveReason) {
        Id = id;
        this.clinicNumber = clinicNumber;
        this.category = category;
        this.admissionDate = admissionDate;
        this.leaveDate = leaveDate;
        this.code = code;
        this.hospitalLevel = hospitalLevel;
        this.leaveReason = leaveReason;
    }

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

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDate leaveDate) {
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

    public PersonnelVisit(String id, String clinicNumber, String category, LocalDate admissionDate, String code, HospitalLevel hospitalLevel) {
        Id = id;
        this.clinicNumber = clinicNumber;
        this.category = category;
        this.admissionDate = admissionDate;
        this.code = code;
        this.hospitalLevel = hospitalLevel;
    }

}

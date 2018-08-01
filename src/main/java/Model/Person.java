package Model;

import java.time.LocalDate;


public class Person extends Model {
    /**
     * 个人ID
     * 索引
     */
    private String Id;
    /**
     * 证件类型
     */
    private String typeOfCertificate;
    /**
     * 证件编号
     */
    private String certificateId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private sex sex;
    /**
     * 民族
     */
    private String nationality;
    /**
     * 出生日期
     */
    private LocalDate Birthday;

    public Person(String id, String typeOfCertificate, String certificateId, String name, sex sex) {
        Id = id;
        this.typeOfCertificate = typeOfCertificate;
        this.certificateId = certificateId;
        this.name = name;
        this.sex = sex;
    }

    public Person(String id, String typeOfCertificate, String certificateId, String name, sex sex, String nationality) {

        Id = id;
        this.typeOfCertificate = typeOfCertificate;
        this.certificateId = certificateId;
        this.name = name;
        this.sex = sex;
        this.nationality = nationality;
    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public void setId(String id) {
        Id = id;
    }

    public String getTypeOfCertificate() {
        return typeOfCertificate;
    }

    public void setTypeOfCertificate(String typeOfCertificate) {
        this.typeOfCertificate = typeOfCertificate;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public sex getSex() {
        return sex;
    }

    public void setSex(sex sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthday() {
        return Birthday;
    }

    public void setBirthday(LocalDate birthday) {
        Birthday = birthday;
    }

    public Person(String id, String typeOfCertificate, String certificateId, String name, boolean b) {
    }

    public Person(String id, String typeOfCertificate, String certificateId, String name, sex sex, String nationality, LocalDate birthday) {
        Id = id;
        this.typeOfCertificate = typeOfCertificate;
        this.certificateId = certificateId;
        this.name = name;
        this.sex = sex;
        this.nationality = nationality;
        Birthday = birthday;
    }

    public Person() {
    }
}



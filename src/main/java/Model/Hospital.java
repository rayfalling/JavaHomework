package Model;

/**
 * 信息类--医院
 *
 * @author 海蔚
 * @version 0.1
 */
public class Hospital extends Model {
    /**
     * 医院编号
     */
    private String code;
    /**
     * 医院名称
     */
    private String name;
    /**
     * 医院等级
     */
    private HospitalLevel level;

    public Hospital(String code, String name, HospitalLevel level) {
        this.code = code;
        this.name = name;
        this.level = level;
    }

    public Hospital() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HospitalLevel getLevel() {
        return level;
    }

    public void setLevel(HospitalLevel level) {
        this.level = level;
    }

    @Override
    public String getId() {
        return code;
    }

    @Override
    public void setId(String id) {
        code = id;
    }
}

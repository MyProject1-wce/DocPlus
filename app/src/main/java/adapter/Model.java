package adapter;

/**
 * Created by DELL on 29-10-2017.
 */

public class Model {
    //private int imageId;
    private int imageId;
    private String name;
    private String  special;
    private String  experience;
    private String  cadd;
    private String cname;
    private String userid;

    public Model(int imageId, String name, String special, String experience, String cadd, String cname, String userid) {
        this.imageId = imageId;
        this.name = name;
        this.special = special;
        this.experience = experience;
        this.cadd = cadd;
        this.cname = cname;
        this.userid = userid;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCadd() {
        return cadd;
    }

    public void setCadd(String cadd) {
        this.cadd = cadd;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

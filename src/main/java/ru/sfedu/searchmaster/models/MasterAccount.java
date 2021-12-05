package ru.sfedu.searchmaster.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.searchmaster.models.enums.EnumRating;

import java.util.Objects;

@Root(name = "MasterAccount")
public class MasterAccount extends Account {
    @Element
    @CsvBindByName
    private String workArea;
    @Element
    @CsvBindByName
    private String instagram;
    @Element
    @CsvBindByName
    private Integer workExperience;

    public MasterAccount() {
    }

    public MasterAccount(String workArea, String instagram, Integer workExperience) {
        this.workArea = workArea;
        this.instagram = instagram;
        this.workExperience = workExperience;
    }


    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public Integer getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MasterAccount)) return false;
        if (!super.equals(o)) return false;
        MasterAccount that = (MasterAccount) o;
        return getWorkArea().equals(that.getWorkArea()) && getInstagram().equals(that.getInstagram()) && getWorkExperience().equals(that.getWorkExperience());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWorkArea(), getInstagram(), getWorkExperience());
    }

    @Override
    public String toString() {
        return "MasterAccount{" +
                "workArea='" + workArea + '\'' +
                ", instagram='" + instagram + '\'' +
                ", workExperience=" + workExperience +
                '}';
    }
}

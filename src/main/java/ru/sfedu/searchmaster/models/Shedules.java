package ru.sfedu.searchmaster.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.searchmaster.utils.MasterAccountConverter;

import java.io.Serializable;
import java.util.Objects;

@Root(name = "Shedules")
public class Shedules implements Serializable {
    @Attribute
    @CsvBindByName
    private long id;
    @Element
    @CsvBindByName
    private String date;
    @Element
    @CsvBindByName
    private String time;
    @Element
    @CsvCustomBindByName(converter = MasterAccountConverter.class)
    private MasterAccount masterAccount;

    public Shedules() {
    }

    public Shedules(long id, String date, String time, MasterAccount masterAccount) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.masterAccount = masterAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MasterAccount getMasterAccount() {
        return masterAccount;
    }

    public void setMasterAccount(MasterAccount masterAccount) {
        this.masterAccount = masterAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shedules)) return false;
        Shedules shedules = (Shedules) o;
        return getId() == shedules.getId() && Objects.equals(getDate(), shedules.getDate()) && Objects.equals(getTime(), shedules.getTime()) && Objects.equals(getMasterAccount(), shedules.getMasterAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getTime(), getMasterAccount());
    }

    @Override
    public String toString() {
        return "Shedules{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", masterAccount=" + masterAccount +
                '}';
    }
}

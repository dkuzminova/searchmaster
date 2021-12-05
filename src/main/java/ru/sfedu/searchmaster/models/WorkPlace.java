package ru.sfedu.searchmaster.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.searchmaster.utils.MasterAccountListConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Root(name = "WorkPlace")
public class WorkPlace implements Serializable {
    @Attribute
    @CsvBindByName
    private long placeId;
    @Element
    @CsvCustomBindByName(converter = MasterAccountListConverter.class)
    private List<MasterAccount> masterAccount;
    @Element
    @CsvBindByName
    private String address;

    public WorkPlace() {
    }

    public WorkPlace(long placeId,String address, List<MasterAccount> masterAccount ) {
        this.placeId = placeId;
        this.masterAccount = masterAccount;
        this.address = address;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public List<MasterAccount> getMasterAccount() {
        return masterAccount;
    }

    public void setMasterAccount(List<MasterAccount> masterAccount) {
        this.masterAccount = masterAccount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkPlace)) return false;
        WorkPlace workPlace = (WorkPlace) o;
        return getPlaceId() == workPlace.getPlaceId() && Objects.equals(getMasterAccount(), workPlace.getMasterAccount()) && getAddress().equals(workPlace.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceId(), getMasterAccount(), getAddress());
    }

    @Override
    public String toString() {
        return "WorkPlace{" +
                "placeId=" + placeId +
                ", masterAccount=" + masterAccount +
                ", address='" + address + '\'' +
                '}';
    }
}

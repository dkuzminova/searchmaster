package ru.sfedu.searchmaster.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.searchmaster.models.enums.District;

import java.util.Objects;


@Root(name = "customeraccount")
public class CustomerAccount extends Account {
    @Element
    @CsvBindByName
    private District district;

    public CustomerAccount() {

    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAccount)) return false;
        if (!super.equals(o)) return false;
        CustomerAccount that = (CustomerAccount) o;
        return getDistrict() == that.getDistrict();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDistrict());
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
                "district=" + district +
                '}';
    }
}

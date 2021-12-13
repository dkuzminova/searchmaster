package ru.sfedu.searchmaster.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.searchmaster.models.enums.EnumRating;

import java.io.Serializable;
import java.util.Objects;


@Root(name = "Rating")
public class Rating implements Serializable {
    @Attribute
    @CsvBindByName
    private long id;
    @Element
    @CsvBindByName
    private long masterId;
    @Element
    @CsvBindByName
    private long customerId;
    @Element
    @CsvBindByName
    private EnumRating masterRating;
    @Element
    @CsvBindByName
    private EnumRating customerRating;

    public Rating() {
    }

    public Rating(long id, long masterId, long customerId, EnumRating masterRating, EnumRating customerRating) {
        this.id = id;
        this.masterId = masterId;
        this.customerId = customerId;
        this.masterRating = masterRating;
        this.customerRating = customerRating;
    }

    public Rating(boolean isMaster,long id, long masterId, EnumRating masterRating) {
        this.id = id;
        if(isMaster) {
            this.masterId = masterId;
            this.masterRating = masterRating;
        }else{
            this.customerId = masterId;
            this.customerRating = masterRating;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMasterId() {
        return masterId;
    }

    public void setMasterId(long masterId) {
        this.masterId = masterId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public EnumRating getMasterRating() {
        return masterRating;
    }

    public void setMasterRating(EnumRating masterRating) {
        this.masterRating = masterRating;
    }

    public EnumRating getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(EnumRating customerRating) {
        this.customerRating = customerRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating = (Rating) o;
        return getId() == rating.getId() && getMasterId() == rating.getMasterId() && getCustomerId() == rating.getCustomerId() && getMasterRating() == rating.getMasterRating() && getCustomerRating() == rating.getCustomerRating();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMasterId(), getCustomerId(), getMasterRating(), getCustomerRating());
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", masterId=" + masterId +
                ", customerId=" + customerId +
                ", masterRating=" + masterRating +
                ", customerRating=" + customerRating +
                '}';
    }
}

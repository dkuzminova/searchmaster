package ru.sfedu.searchmaster.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.searchmaster.utils.CustomerAccountConverter;
import ru.sfedu.searchmaster.utils.RatingAccountConverter;

import java.util.Objects;


@Root(name = "Shedule")
public class Shedule extends Shedules {
    @Element
    @CsvBindByName
    private String typeOfService;
    @Element
    @CsvCustomBindByName(converter = CustomerAccountConverter.class)
    private CustomerAccount customerAccount;
    @Element
    @CsvBindByName
    private boolean needRate;
    @Element
    @CsvCustomBindByName(converter = RatingAccountConverter.class)
    private Rating marking;

    public Shedule() {
    }

    public Shedule(String typeOfService, CustomerAccount customerAccount, boolean needRate, Rating marking) {
        this.typeOfService = typeOfService;
        this.customerAccount = customerAccount;
        this.needRate = needRate;
        this.marking = marking;
    }

    public Shedule(long id, String date, String time, MasterAccount masterAccount, String typeOfService, CustomerAccount customerAccount, boolean needRate, Rating marking) {
        super(id, date, time, masterAccount);
        this.typeOfService = typeOfService;
        this.customerAccount = customerAccount;
        this.needRate = needRate;
        this.marking = marking;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

    public Rating getMarking() {
        return marking;
    }

    public void setMarking(Rating marking) {
        this.marking = marking;
    }

    public boolean isNeedRate() {
        return needRate;
    }

    public void setNeedRate(boolean needRate) {
        this.needRate = needRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shedule)) return false;
        if (!super.equals(o)) return false;
        Shedule shedule = (Shedule) o;
        return isNeedRate() == shedule.isNeedRate() && getTypeOfService().equals(shedule.getTypeOfService()) && Objects.equals(getCustomerAccount(), shedule.getCustomerAccount()) && Objects.equals(getMarking(), shedule.getMarking());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTypeOfService(), getCustomerAccount(), getMarking(), isNeedRate());
    }

    @Override
    public String toString() {
        return "Shedule{" +
                "typeOfService='" + typeOfService + '\'' +
                ", customerAccount=" + customerAccount +
                ", marking=" + marking +
                ", needRate=" + needRate +
                '}';
    }
}

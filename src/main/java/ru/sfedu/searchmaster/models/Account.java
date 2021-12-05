package ru.sfedu.searchmaster.models;


import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import ru.sfedu.searchmaster.models.enums.EnumRating;

import java.io.Serializable;
import java.util.Objects;


@Root(name = "Account")
public class Account implements Serializable {
    @Attribute
    @CsvBindByName
    private long userId;
    @Element
    @CsvBindByName
    private String name;
    @Element
    @CsvBindByName
    private String surname;
    @Element
    @CsvBindByName
    private String birthday;
    @Element
    @CsvBindByName
    private String city;
    @Element
    @CsvBindByName
    private String phoneNumber;
    @Element
    @CsvBindByName
    private Boolean masterAccount;
    @Element
    @CsvBindByName
    private EnumRating rating;

    public Account() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getMasterAccount() {
        return masterAccount;
    }

    public void setMasterAccount(Boolean masterAccount) {
        this.masterAccount = masterAccount;
    }

    public EnumRating getRating() {
        return rating;
    }

    public void setRating(EnumRating rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getUserId() == account.getUserId() && getName().equals(account.getName()) && getSurname().equals(account.getSurname()) && getBirthday().equals(account.getBirthday()) && getCity().equals(account.getCity()) && getPhoneNumber().equals(account.getPhoneNumber()) && getMasterAccount().equals(account.getMasterAccount()) && getRating() == account.getRating();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName(), getSurname(), getBirthday(), getCity(), getPhoneNumber(), getMasterAccount(), getRating());
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", masterAccount=" + masterAccount +
                ", rating=" + rating +
                '}';
    }
}

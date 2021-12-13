package ru.sfedu.searchmaster.utils;

import ru.sfedu.searchmaster.api.DataProvider;
import ru.sfedu.searchmaster.api.DataProviderCsv;
import ru.sfedu.searchmaster.models.*;
import ru.sfedu.searchmaster.models.enums.District;
import ru.sfedu.searchmaster.models.enums.EnumRating;

import java.util.ArrayList;
import java.util.List;

public class CsvGenerator {
    public static DataProviderCsv instance = new DataProviderCsv();

    public static void addRecord(DataProvider dataProvider) {

        for (int i = 1; i <= 3; i++) {
            Account account = new Account();
            account.setUserId(i);
            account.setName(DataForTests.name[i - 1]);
            account.setSurname(DataForTests.surname[i - 1]);
            account.setBirthday(DataForTests.birhsday[i - 1]);
            account.setMasterAccount(DataForTests.isMaster[i - 1]);
            account.setCity(DataForTests.city[i - 1]);
            account.setPhoneNumber(DataForTests.phone[i - 1]);
            account.setRating(EnumRating.ZERO);
            dataProvider.insertAccount(account);
        }
        for (int i = 1; i <= 3; i++) {
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setUserId(i);
            customerAccount.setName(DataForTests.name[i - 1]);
            customerAccount.setSurname(DataForTests.surname[i - 1]);
            customerAccount.setBirthday(DataForTests.birhsday[i - 1]);
            customerAccount.setMasterAccount(DataForTests.isMaster[i - 1]);
            customerAccount.setCity(DataForTests.city[i - 1]);
            customerAccount.setPhoneNumber(DataForTests.phone[i - 1]);
            customerAccount.setRating(EnumRating.ZERO);
            customerAccount.setDistrict(District.CENTER);
            dataProvider.insertCustomerAccount(customerAccount);
        }
        for (int i = 1; i <= 3; i++) {
            MasterAccount masterAccount = new MasterAccount();
            masterAccount.setUserId(i);
            masterAccount.setName(DataForTests.name[i - 1]);
            masterAccount.setSurname(DataForTests.surname[i - 1]);
            masterAccount.setBirthday(DataForTests.birhsday[i - 1]);
            masterAccount.setMasterAccount(DataForTests.isMaster[i - 1]);
            masterAccount.setCity(DataForTests.city[i - 1]);
            masterAccount.setPhoneNumber(DataForTests.phone[i - 1]);
            masterAccount.setRating(EnumRating.ZERO);
            masterAccount.setWorkArea((i < 2) ? "Manikur" : "Pedicure");
            masterAccount.setInstagram("instagramm");
            masterAccount.setWorkExperience(i);
            dataProvider.insertMasterAccount(masterAccount);
        }
        for (int i = 1; i <= 3; i++) {
            Rating rating = new Rating(i, i, i, EnumRating.PERFECT, EnumRating.PERFECT);
            dataProvider.insertRating(rating);
        }
        for (int i = 1; i <= 3; i++) {
            Shedules shedules = new Shedules();
            shedules.setId(i);
            shedules.setDate(instance.between().toString());
            shedules.setTime( instance.getTime().toString());
            shedules.setMasterAccount(getMasterAccount(dataProvider));
            dataProvider.insertShedules(shedules);
        }
        for (int i = 1; i <= 3; i++) {
            Shedule shedule = new Shedule();
            shedule.setId(i);
            shedule.setDate(instance.between().toString());
            shedule.setTime( instance.getTime().toString());
            shedule.setMasterAccount(getMasterAccount(dataProvider));
            shedule.setTypeOfService("Manikur");
            shedule.setCustomerAccount(getCustomerAccount(dataProvider));
            shedule.setNeedRate(DataForTests.isMaster[i - 1]);
            shedule.setMarking(getRating(dataProvider));
            dataProvider.insertShedule(shedule);
        }
        for (int i = 1; i <= 3; i++) {
            WorkPlace workPlace = new WorkPlace(i,"Custom address",getMasterAccounts(dataProvider));
            dataProvider.insertWorkPlace(workPlace);
        }
    }

    public static List<MasterAccount> getMasterAccounts(DataProvider dataProvider) {
        List<MasterAccount> masterAccountList = new ArrayList<>();
        int max = 2;
        int min = 1;
        for (int i = 1; i <= 2; i++) {
            masterAccountList.add(dataProvider.searchMasterAccountById(((int) ((Math.random() * ((max - min) + 1)) + min))).get());
        }
        return masterAccountList;
    }

    public static MasterAccount getMasterAccount(DataProvider dataProvider) {
        int max = 2;
        int min = 1;
        return dataProvider.searchMasterAccountById(((int) ((Math.random() * ((max - min) + 1)) + min))).get();
    }

    public static CustomerAccount getCustomerAccount(DataProvider dataProvider) {
        int max = 2;
        int min = 1;
        return dataProvider.searchCustomerAccountById(((int) ((Math.random() * ((max - min) + 1)) + min))).get();
    }

    public static Rating getRating(DataProvider dataProvider) {
        int max = 2;
        int min = 1;
        return dataProvider.searchRatingById(((int) ((Math.random() * ((max - min) + 1)) + min))).get();
    }
}

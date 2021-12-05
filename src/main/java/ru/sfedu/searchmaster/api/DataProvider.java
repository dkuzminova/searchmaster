package ru.sfedu.searchmaster.api;


import ru.sfedu.searchmaster.Outcome;
import ru.sfedu.searchmaster.models.*;
import ru.sfedu.searchmaster.models.enums.EnumRating;

import java.util.List;
import java.util.Optional;

public interface DataProvider {

    Outcome<List<MasterAccount>> searchBeautyMasterByWorkArea(String workArea);

    Outcome<Boolean> sendRequestToMaster(long customerId, long masterId, String date, String time);

    Outcome<Boolean> respondToRequest(long masterId, long customerId, String date, String time, String typeOfService, Boolean needRate, Rating rating);

    Outcome<Boolean> setWorkSchedules(long masterId, String date, String time);

    Outcome<Boolean> rateMaster(long masterId, EnumRating masterMarking);

    Outcome<Boolean> rateCustomer(long customerId, EnumRating customerMarking);

    Outcome<Boolean> createWorkPlace(String address, boolean needToAdd, MasterAccount masterAccount);

    Outcome<Boolean> setMaster(WorkPlace workPlace, MasterAccount masterAccount);

    Outcome<Boolean> createSchedule(String typeOfService, long customerId, long masterId, String date, String time, Boolean needRate, Rating marking);

    Outcome<Account> insertAccount(Account account);
    Outcome<Void> updateAccount(Account account);
    Optional<Account> searchAccountById(long id);
    Outcome<Void> deleteAccount(long id);

    Outcome<CustomerAccount> insertCustomerAccount(CustomerAccount customerAccount);
    Outcome<Void> updateCustomerAccount(CustomerAccount customerAccount);
    Optional<CustomerAccount> searchCustomerAccountById(long id);
    Outcome<Void> deleteCustomerAccount(long id);

    Outcome<MasterAccount> insertMasterAccount(MasterAccount masterAccount);
    Outcome<Void> updateMasterAccount(MasterAccount masterAccount);
    Optional<MasterAccount> searchMasterAccountById(long id);
    Outcome<Void> deleteMasterAccount(long id);

    Outcome<Rating> insertRating(Rating rating);
    Outcome<Void> updateRating(Rating rating);
    Optional<Rating> searchRatingById(long id);
    Outcome<Void> deleteRating(long id);

    Outcome<Shedule> insertShedule(Shedule shedule);
    Outcome<Void> updateShedule(Shedule shedule);
    Optional<Shedule> searchSheduleById(long id);
    Outcome<Void> deleteShedule(long id);

    Outcome<Shedules> insertShedules(Shedules shedules);
    Outcome<Void> updateShedules(Shedules shedules);
    Optional<Shedules> searchShedulesById(long id);
    Outcome<Void> deleteShedules(long id);

    Outcome<WorkPlace> insertWorkPlace(WorkPlace workPlace);
    Outcome<Void> updateWorkPlace(WorkPlace workPlace);
    Optional<WorkPlace> searchWorkPlaceById(long id);
    Outcome<Void> deleteWorkPlace(long id);


}

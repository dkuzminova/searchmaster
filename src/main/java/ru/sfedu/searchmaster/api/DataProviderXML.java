//package ru.sfedu.searchmaster.api;
//
//
//import ru.sfedu.searchmaster.Outcome;
//import ru.sfedu.searchmaster.models.*;
//import ru.sfedu.searchmaster.models.enums.EnumRating;
//
//import java.util.List;
//import java.util.Optional;
//
//public class DataProviderXML implements DataProvider {
//
//
//    @Override
//    public Outcome<List<MasterAccount>> searchBeautyMasterByWorkArea(String workArea) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Boolean> sendRequestToMaster(long customerId, long masterId, String date, String time) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Boolean> respondToRequest(long masterId, long customerId, String date, String time, String typeOfService, Boolean needRate, Rating rating) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Boolean> setWorkSchedules(long masterId, String date, String time) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Boolean> rateMaster(long masterId, EnumRating masterMarking) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Boolean> rateCustomer(long customerId, EnumRating customerMarking) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Boolean> createWorkPlace(String address, boolean needToAdd, MasterAccount masterAccount) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Boolean> setMaster(WorkPlace workPlace, MasterAccount masterAccount) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Boolean> createSchedule(String typeOfService, long customerId, long masterId, String date, String time, Boolean needRate, Rating marking) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Account> insertAccount(Account account) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Void> updateAccount(Account account) {
//        return null;
//    }
//
//    @Override
//    public Optional<Account> searchAccountById(long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Outcome<Void> deleteAccount(long id) {
//        return null;
//    }
//
//    @Override
//    public Outcome<CustomerAccount> insertCustomerAccount(CustomerAccount customerAccount) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Void> updateCustomerAccount(CustomerAccount customerAccount) {
//        return null;
//    }
//
//    @Override
//    public Optional<CustomerAccount> searchCustomerAccountById(long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Outcome<Void> deleteCustomerAccount(long id) {
//        return null;
//    }
//
//    @Override
//    public Outcome<MasterAccount> insertMasterAccount(MasterAccount masterAccount) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Void> updateMasterAccount(MasterAccount masterAccount) {
//        return null;
//    }
//
//    @Override
//    public Optional<MasterAccount> searchMasterAccountById(long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Outcome<Void> deleteMasterAccount(long id) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Rating> insertRating(Rating rating) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Void> updateRating(Rating rating) {
//        return null;
//    }
//
//    @Override
//    public Optional<Rating> searchRatingById(long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Outcome<Void> deleteRating(long id) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Shedule> insertShedule(Shedule shedule) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Void> updateShedule(Shedule shedule) {
//        return null;
//    }
//
//    @Override
//    public Optional<Shedule> searchSheduleById(long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Outcome<Void> deleteShedule(long id) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Shedules> insertShedules(Shedules shedules) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Void> updateShedules(Shedules shedules) {
//        return null;
//    }
//
//    @Override
//    public Optional<Shedules> searchShedulesById(long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Outcome<Void> deleteShedules(long id) {
//        return null;
//    }
//
//    @Override
//    public Outcome<WorkPlace> insertWorkPlace(WorkPlace workPlace) {
//        return null;
//    }
//
//    @Override
//    public Outcome<Void> updateWorkPlace(WorkPlace workPlace) {
//        return null;
//    }
//
//    @Override
//    public Optional<WorkPlace> searchWorkPlaceById(long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public Outcome<Void> deleteWorkPlace(long id) {
//        return null;
//    }
//}
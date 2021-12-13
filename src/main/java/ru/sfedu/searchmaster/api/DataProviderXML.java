package ru.sfedu.searchmaster.api;


import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.searchmaster.Constants;
import ru.sfedu.searchmaster.Outcome;
import ru.sfedu.searchmaster.models.*;
import ru.sfedu.searchmaster.models.enums.EnumRating;
import ru.sfedu.searchmaster.models.enums.Outcomes;
import ru.sfedu.searchmaster.utils.XMLUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static ru.sfedu.searchmaster.models.enums.Outcomes.Error;
import static ru.sfedu.searchmaster.models.enums.Outcomes.*;
import static ru.sfedu.searchmaster.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.searchmaster.utils.HistoryUtil.saveToLog;

public class DataProviderXML implements DataProvider {

    private static final Logger log = LogManager.getLogger(DataProviderXML.class);

    @Override
    public Outcome<List<MasterAccount>> searchBeautyMasterByWorkArea(String workArea) {
        try {
            if (workArea.isEmpty()) {
                return new Outcome(Fail, false);
            }
            final String method = currentThread().getStackTrace()[1].getMethodName();
            List<MasterAccount> listRes = xmlToBean(Constants.MASTER_ACCOUNT_XML, method);
            List<MasterAccount> newListRes = listRes.stream()
                    .filter(el -> Objects.equals(el.getWorkArea(), workArea))
                    .collect(Collectors.toList());
            return new Outcome<>(Success, newListRes);
        } catch (Exception e) {
            return new Outcome<>(Error);
        }
    }

    @Override
    public Outcome<Boolean> sendRequestToMaster(long customerId, long masterId, String date, String time) {
        try {
            final String method = currentThread().getStackTrace()[1].getMethodName();
            if ((getMaxMasterAccountId() < masterId) || (getMaxCustomerAccountId() < customerId)) {
                return new Outcome<>(Fail, false);
            }
            List<Shedules> listRes = xmlToBean(Constants.SHEDULES_XML, method);
            Optional<Shedules> shedules = listRes.stream()
                    .filter(el -> (Objects.equals(el.getDate(), date)) && (Objects.equals(el.getTime(), time)))
                    .findFirst();
            if (shedules.isEmpty()) {
                return new Outcome<>(Success, true);
            } else {
                return new Outcome<>(Fail, false);
            }
        } catch (Exception e) {
            return new Outcome<>(Error);
        }
    }

    @Override
    public Outcome<Boolean> respondToRequest(long masterId, long customerId, String date, String time, String typeOfService, Boolean needRate, Rating rating) {
        try{
            if ((getMaxMasterAccountId() < masterId) || (getMaxCustomerAccountId() < customerId)) {
                return new Outcome<>(Fail, false);
            }
            MasterAccount masterAccount = searchMasterAccountById(masterId).get();
            CustomerAccount customerAccount = searchCustomerAccountById(customerId).get();
            Shedule shedule = new Shedule(getMaxSheduleId() + 1, date, time, masterAccount, typeOfService, customerAccount, needRate, rating);
            shedule.setId(getMaxSheduleId() + 1);
            insertShedule(shedule);
            return new Outcome<>(Success, true);
        } catch (Exception e) {
            return new Outcome<>(Error);
        }
    }

    @Override
    public Outcome<Boolean> setWorkSchedules(long masterId, String date, String time) {
        try{
            if ((getMaxMasterAccountId() < masterId)) {
                return new Outcome<>(Fail, false);
            }
            MasterAccount masterAccount = searchMasterAccountById(masterId).get();
            Shedules shedules = new Shedules(getMaxShedulesId() + 1, date, time, masterAccount);
            insertShedules(shedules);
            return new Outcome<>(Success, true);
        } catch (Exception e) {
            return new Outcome<>(Error);
        }
    }

    @Override
    public Outcome<Boolean> rateMaster(long masterId, EnumRating masterMarking) {
        try{
            if ((getMaxMasterAccountId() < masterId)) {
                return new Outcome<>(Fail, false);
            }
            Rating rating = new Rating(true, getMaxRatingId() + 1, masterId, masterMarking);
            rating.setCustomerRating(EnumRating.ZERO);
            rating.setCustomerId(0);
            rating.setId(getMaxRatingId() + 1);
            log.debug(rating);
            insertRating(rating);
            return new Outcome<>(Success, true);
        } catch (Exception e) {
            return new Outcome<>(Error);
        }
    }

    @Override
    public Outcome<Boolean> rateCustomer(long customerId, EnumRating customerMarking) {
        try{
            if ((getMaxMasterAccountId() < customerId)) {
                return new Outcome<>(Fail, false);
            }
            Rating rating = new Rating(false, getMaxRatingId() + 1, customerId, customerMarking);
            rating.setMasterRating(EnumRating.ZERO);
            rating.setMasterId(0);
            rating.setId(getMaxRatingId() + 1);
            insertRating(rating);
            return new Outcome<>(Success, true);
        } catch (Exception e) {
            return new Outcome<>(Error);
        }
    }

    @Override
    public Outcome<Boolean> createWorkPlace(String address, boolean needToAdd, MasterAccount masterAccount) {
        try{
            if (address.isEmpty() || ((getMaxMasterAccountId() < masterAccount.getUserId()))) {
                return new Outcome<>(Fail, false);
            }
            WorkPlace workPlace = new WorkPlace();
            workPlace.setAddress(address);
            workPlace.setPlaceId(getMaxWorkPlaceId() + 1);
            if (needToAdd) {
                setMaster(workPlace, masterAccount);
            } else {
                workPlace.setPlaceId(getMaxWorkPlaceId() + 1);
                insertWorkPlace(workPlace);
            }
            return new Outcome<>(Success, true);
        } catch (Exception e) {
            return new Outcome<>(Error);
        }
    }

    @Override
    public Outcome<Boolean> setMaster(WorkPlace workPlace, MasterAccount masterAccount) {
        try{
            if ((getMaxWorkPlaceId() < workPlace.getPlaceId()) || (getMaxMasterAccountId() < masterAccount.getUserId())) {
                return new Outcome<>(Fail, false);
            }
            workPlace.getMasterAccount().add(masterAccount);
            updateWorkPlace(workPlace);
            return new Outcome<>(Success, true);
        } catch (Exception e) {
            return new Outcome<>(Error);
        }
    }

    @Override
    public Outcome<Boolean> createSchedule(String typeOfService, long customerId, long masterId, String date, String time, Boolean needRate, Rating marking) {
        try{
            if ((getMaxMasterAccountId() < masterId) || (getMaxCustomerAccountId() < customerId)) {
                return new Outcome<>(Fail, false);
            }
            if (sendRequestToMaster(customerId, masterId, date, time).getData()) {
                rateMaster(masterId, marking.getMasterRating());
                rateCustomer(customerId, marking.getCustomerRating());
                return new Outcome<>(Success, respondToRequest(masterId, customerId, date, time, typeOfService, needRate, marking).getData());
            } else {
                return new Outcome<>(Fail);
            }
        } catch (Exception e) {
            return new Outcome<>(Fail);
        }
    }


    @Override
    public Outcome<Account> insertAccount(Account account) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Account> accounts = xmlToBean(Constants.ACCOUNT_XML, method);
        if (accounts.stream().anyMatch(o -> o.getUserId() == account.getUserId())) {
            return new Outcome<>(Fail, account);
        }
        accounts.add(account);
        if (beanToXml(accounts,Constants.ACCOUNT_XML, method) == Fail) {
            return new Outcome(Fail, accounts);
        }
        return new Outcome(Success, accounts);
    }

    @Override
    public Outcome<Void> updateAccount(Account account) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Account> objects = xmlToBean(Constants.ACCOUNT_XML, method);
        if (objects.stream().noneMatch(o -> o.getUserId() == account.getUserId())) {
            return new Outcome(Fail, account, format(Constants.ID_NOT_EXISTS, account.getUserId()));
        }
        objects.removeIf(o -> o.getUserId() == account.getUserId());
        objects.add(account);
        if (beanToXml(objects, Constants.ACCOUNT_XML, method) == Fail) {
            return new Outcome(Fail, account);
        }
        return new Outcome(Success, account);
    }

    @Override
    public Optional<Account> searchAccountById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Account> objects = xmlToBean(Constants.ACCOUNT_XML, method);
        return objects.stream().filter(o -> o.getUserId() == id).findFirst();
    }

    @Override
    public Outcome<Void> deleteAccount(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Account> objects = xmlToBean(Constants.ACCOUNT_XML, method);
        objects.removeIf(o -> o.getUserId() == id);
        beanToXml(objects, Constants.ACCOUNT_XML, method);
        return new Outcome<>(Success);
    }

    @Override
    public Outcome<CustomerAccount> insertCustomerAccount(CustomerAccount customerAccount) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomerAccount> customerAccounts = xmlToBean(Constants.CUSTOMER_ACCOUNT_XML, method);
        if (customerAccounts.stream().anyMatch(o -> o.getUserId() == customerAccount.getUserId())) {
            return new Outcome<>(Fail, customerAccount);
        }
        customerAccounts.add(customerAccount);
        if (beanToXml(customerAccounts, Constants.CUSTOMER_ACCOUNT_XML, method) == Fail) {
            return new Outcome<>(Fail, customerAccount);
        }
        return new Outcome<>(Success, customerAccount);
    }

    @Override
    public Outcome<Void> updateCustomerAccount(CustomerAccount customerAccount) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomerAccount> objects = xmlToBean( Constants.CUSTOMER_ACCOUNT_XML, method);
        if (objects.stream().noneMatch(o -> o.getUserId() == customerAccount.getUserId())) {
            return new Outcome(Fail, customerAccount, format(Constants.ID_NOT_EXISTS, customerAccount.getUserId()));
        }
        objects.removeIf(o -> o.getUserId() == customerAccount.getUserId());
        objects.add(customerAccount);
        if (beanToXml(objects, Constants.CUSTOMER_ACCOUNT_XML, method) == Fail) {
            return new Outcome(Fail, customerAccount);
        }
        return new Outcome(Success, customerAccount);
    }

    @Override
    public Optional<CustomerAccount> searchCustomerAccountById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomerAccount> objects = xmlToBean(Constants.CUSTOMER_ACCOUNT_XML, method);
        return objects.stream().filter(o -> o.getUserId() == id).findFirst();
    }

    @Override
    public Outcome<Void> deleteCustomerAccount(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomerAccount> objects = xmlToBean(Constants.CUSTOMER_ACCOUNT_XML, method);
        objects.removeIf(o -> o.getUserId() == id);
        beanToXml(objects, Constants.CUSTOMER_ACCOUNT_XML, method);
        return new Outcome<>(Success);
    }

    @Override
    public Outcome<MasterAccount> insertMasterAccount(MasterAccount masterAccount) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<MasterAccount> masterAccountList = xmlToBean(Constants.MASTER_ACCOUNT_XML, method);
        if (masterAccountList.stream().anyMatch(o -> o.getUserId() == masterAccount.getUserId())) {
            return new Outcome<>(Fail, masterAccount);
        }
        masterAccountList.add(masterAccount);
        if (beanToXml(masterAccountList, Constants.MASTER_ACCOUNT_XML, method) == Fail) {
            return new Outcome<>(Fail, masterAccount);
        }
        return new Outcome<>(Success, masterAccount);
    }

    @Override
    public Outcome<Void> updateMasterAccount(MasterAccount masterAccount) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<MasterAccount> objects = xmlToBean(Constants.MASTER_ACCOUNT_XML, method);
        if (objects.stream().noneMatch(o -> o.getUserId() == masterAccount.getUserId())) {
            return new Outcome(Fail, masterAccount, format(Constants.ID_NOT_EXISTS, masterAccount.getUserId()));
        }
        objects.removeIf(o -> o.getUserId() == masterAccount.getUserId());
        objects.add(masterAccount);
        if (beanToXml(objects, Constants.MASTER_ACCOUNT_XML, method) == Fail) {
            return new Outcome(Fail, masterAccount);
        }
        return new Outcome(Success, masterAccount);
    }

    @Override
    public Optional<MasterAccount> searchMasterAccountById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<MasterAccount> objects = xmlToBean(Constants.MASTER_ACCOUNT_XML, method);
        return objects.stream().filter(o -> o.getUserId() == id).findFirst();
    }

    @Override
    public Outcome<Void> deleteMasterAccount(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<MasterAccount> objects = xmlToBean(Constants.MASTER_ACCOUNT_XML, method);
        objects.removeIf(o -> o.getUserId() == id);
        beanToXml(objects, Constants.MASTER_ACCOUNT_XML, method);
        return new Outcome<>(Success);
    }

    @Override
    public Outcome<Rating> insertRating(Rating rating) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Rating> ratingList = xmlToBean(Constants.RATING_XML, method);
        if (ratingList.stream().anyMatch(o -> o.getId() == rating.getId())) {
            return new Outcome<>(Fail, rating);
        }
        ratingList.add(rating);
        if (beanToXml(ratingList, Constants.RATING_XML, method) == Fail) {
            return new Outcome<>(Fail, rating);
        }
        return new Outcome<>(Success, rating);
    }

    @Override
    public Outcome<Void> updateRating(Rating rating) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Rating> objects = xmlToBean(Constants.RATING_XML, method);
        if (objects.stream().noneMatch(o -> o.getId() == rating.getId())) {
            return new Outcome(Fail, rating, format(Constants.ID_NOT_EXISTS, rating.getId()));
        }
        objects.removeIf(o -> o.getId() == rating.getId());
        objects.add(rating);
        if (beanToXml(objects, Constants.RATING_XML, method) == Fail) {
            return new Outcome(Fail, rating);
        }
        return new Outcome(Success, rating);
    }

    @Override
    public Optional<Rating> searchRatingById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Rating> objects = xmlToBean(Constants.RATING_XML, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Outcome<Void> deleteRating(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Rating> objects = xmlToBean(Constants.RATING_XML, method);
        objects.removeIf(o -> o.getId() == id);
        beanToXml(objects, Constants.RATING_XML, method);
        return new Outcome<>(Success);
    }

    @Override
    public Outcome<Shedule> insertShedule(Shedule shedule) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedule> orderList = xmlToBean( Constants.SHEDULE_XML, method);
        if (orderList.stream().anyMatch(o -> o.getId() == shedule.getId())) {
            return new Outcome<>(Fail, shedule);
        }
        orderList.add(shedule);
        if (beanToXml(orderList, Constants.SHEDULE_XML, method) == Fail) {
            return new Outcome<>(Fail, shedule);
        }
        return new Outcome<>(Success, shedule);
    }

    @Override
    public Outcome<Void> updateShedule(Shedule shedule) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedule> objects = xmlToBean(Constants.SHEDULE_XML, method);
        if (objects.stream().noneMatch(o -> o.getId() == shedule.getId())) {
            return new Outcome(Fail, shedule, format(Constants.ID_NOT_EXISTS, shedule.getId()));
        }
        objects.removeIf(o -> o.getId() == shedule.getId());
        objects.add(shedule);
        if (beanToXml(objects, Constants.SHEDULE_XML, method) == Fail) {
            return new Outcome(Fail, shedule);
        }
        return new Outcome(Success, shedule);
    }

    @Override
    public Optional<Shedule> searchSheduleById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedule> objects = xmlToBean(Constants.SHEDULE_XML, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Outcome<Void> deleteShedule(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedule> objects = xmlToBean(Constants.SHEDULE_XML, method);
        objects.removeIf(o -> o.getId() == id);
        beanToXml(objects, Constants.SHEDULE_XML, method);
        return new Outcome<>(Success);
    }

    @Override
    public Outcome<Shedules> insertShedules(Shedules shedules) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedules> orderList = xmlToBean(Constants.SHEDULES_XML, method);
        if (orderList.stream().anyMatch(o -> o.getId() == shedules.getId())) {
            return new Outcome<>(Fail, shedules);
        }
        orderList.add(shedules);
        if (beanToXml(orderList, Constants.SHEDULES_XML, method) == Fail) {
            return new Outcome<>(Fail, shedules);
        }
        return new Outcome<>(Success, shedules);
    }

    @Override
    public Outcome<Void> updateShedules(Shedules shedules) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedules> objects = xmlToBean(Constants.SHEDULES_XML, method);
        if (objects.stream().noneMatch(o -> o.getId() == shedules.getId())) {
            return new Outcome(Fail, shedules, format(Constants.ID_NOT_EXISTS, shedules.getId()));
        }
        objects.removeIf(o -> o.getId() == shedules.getId());
        objects.add(shedules);
        if (beanToXml(objects, Constants.SHEDULES_XML, method) == Fail) {
            return new Outcome(Fail, shedules);
        }
        return new Outcome(Success, shedules);
    }

    @Override
    public Optional<Shedules> searchShedulesById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedules> objects = xmlToBean(Constants.SHEDULES_XML, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Outcome<Void> deleteShedules(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedules> objects = xmlToBean(Constants.SHEDULES_XML, method);
        objects.removeIf(o -> o.getId() == id);
        beanToXml(objects, Constants.SHEDULES_XML, method);
        return new Outcome<>(Success);
    }

    @Override
    public Outcome<WorkPlace> insertWorkPlace(WorkPlace workPlace) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<WorkPlace> workPlaces = xmlToBean(Constants.WORKPLACE_XML, method);
        if (workPlaces.stream().anyMatch(o -> o.getPlaceId() == workPlace.getPlaceId())) {
            return new Outcome<>(Fail, workPlace);
        }
        workPlaces.add(workPlace);
        if (beanToXml(workPlaces, Constants.WORKPLACE_XML, method) == Fail) {
            return new Outcome<>(Fail, workPlace);
        }
        return new Outcome<>(Success, workPlace);
    }

    @Override
    public Outcome<Void> updateWorkPlace(WorkPlace workPlace) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<WorkPlace> objects = xmlToBean(Constants.WORKPLACE_XML, method);
        if (objects.stream().noneMatch(o -> o.getPlaceId() == workPlace.getPlaceId())) {
            return new Outcome(Fail, workPlace, format(Constants.ID_NOT_EXISTS, workPlace.getPlaceId()));
        }
        objects.removeIf(o -> o.getPlaceId() == workPlace.getPlaceId());
        objects.add(workPlace);
        if (beanToXml(objects, Constants.WORKPLACE_XML, method) == Fail) {
            return new Outcome(Fail, workPlace);
        }
        return new Outcome(Success, workPlace);
    }

    @Override
    public Optional<WorkPlace> searchWorkPlaceById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<WorkPlace> objects = xmlToBean(Constants.WORKPLACE_XML, method);
        return objects.stream().filter(o -> o.getPlaceId() == id).findFirst();
    }

    @Override
    public Outcome<Void> deleteWorkPlace(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<WorkPlace> objects = xmlToBean(Constants.WORKPLACE_XML, method);
        objects.removeIf(o -> o.getPlaceId() == id);
        beanToXml(objects, Constants.WORKPLACE_XML, method);
        return new Outcome<>(Success);
    }

    private <T> Outcomes beanToXml(List<T> ts, String key, String method) {
        Outcomes outcomes;
        try {
            FileWriter fileWriter = new FileWriter(getConfigurationEntry(key));
            Serializer serializer = new Persister();
            XMLUtil<T> container = new XMLUtil<T>(ts);
            serializer.write(container, fileWriter);
            fileWriter.close();
            outcomes = Success;
        } catch (Exception exception) {
            log.error(exception);
            outcomes = Fail;
        }
        saveToLog(createHistoryContent(method, ts, outcomes));
        return outcomes;
    }

    private <T> List<T> xmlToBean(String key, String method) {
        try {
            FileReader reader = new FileReader(getConfigurationEntry(key));
            Serializer serializer = new Persister();
            XMLUtil<T> container = serializer.read(XMLUtil.class, reader);
            final List<T> querySet = container.getList();
            saveToLog(createHistoryContent(method, querySet, Success));
            reader.close();
            return querySet;
        } catch (Exception e) {
            log.error(e);
        }
        saveToLog(createHistoryContent(method, null, Fail));
        return new ArrayList<>();
    }

    private HistoryContent createHistoryContent(String method, Object object, Outcomes outcomes) {
        return new HistoryContent(this.getClass().getSimpleName(), new Date(), Constants.DEFAULT_ACTOR, method, object, outcomes);
    }

    public static Date between() {
        long aDay = TimeUnit.DAYS.toMillis(1);
        long now = new Date().getTime();
        Date hundredYearsAgo = new Date(now - aDay * 365 * 100);
        Date tenDaysAgo = new Date(now - aDay * 10);
        long startMillis = hundredYearsAgo.getTime();
        long endMillis = tenDaysAgo.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);
        return DateUtils.truncate(new Date(randomMillisSinceEpoch), Calendar.DAY_OF_MONTH);
    }

    public static Time getTime() {
        final Random random = new Random();
        final int millisInDay = 24 * 60 * 60 * 1000;
        return new Time((long) random.nextInt(millisInDay));
    }

    public long getMaxMasterAccountId() {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<MasterAccount> listRes = xmlToBean( Constants.MASTER_ACCOUNT_XML, method);
        return listRes.get(listRes.size() - 1).getUserId();
    }

    public long getMaxCustomerAccountId() {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CustomerAccount> listRes = xmlToBean(Constants.CUSTOMER_ACCOUNT_XML, method);
        return listRes.get(listRes.size() - 1).getUserId();
    }

    public long getMaxWorkPlaceId() {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<WorkPlace> listRes = xmlToBean(Constants.WORKPLACE_XML, method);
        return listRes.get(listRes.size() - 1).getPlaceId();
    }

    public long getMaxRatingId() {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Rating> listRes = xmlToBean(Constants.RATING_XML, method);
        return listRes.get(listRes.size() - 1).getId();
    }

    public long getMaxShedulesId() {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedules> listRes = xmlToBean(Constants.SHEDULES_XML, method);
        return listRes.get(listRes.size() - 1).getId();
    }

    public long getMaxSheduleId() {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Shedule> listRes = xmlToBean(Constants.SHEDULE_XML, method);
        return listRes.get(listRes.size() - 1).getId();
    }
}
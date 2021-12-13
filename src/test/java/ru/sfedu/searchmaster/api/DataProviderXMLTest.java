package ru.sfedu.searchmaster.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sfedu.searchmaster.models.MasterAccount;
import ru.sfedu.searchmaster.models.Rating;
import ru.sfedu.searchmaster.models.WorkPlace;
import ru.sfedu.searchmaster.models.enums.EnumRating;
import ru.sfedu.searchmaster.utils.CsvGenerator;

import java.util.List;

class DataProviderXMLTest {

    public static DataProvider instance = new DataProviderXML();
    public static DataProviderXML dataProviderXML = new DataProviderXML();

    @BeforeAll
    static void setCSVEnv() {
        CsvGenerator.addRecord(dataProviderXML);
    }

    @Test
    void searchBeautyMasterByWorkAreaSuccess() {
        List<MasterAccount> masterAccountList = instance.searchBeautyMasterByWorkArea("Pedicure").getData();
        MasterAccount masterAccount = masterAccountList.get(0);
        Assertions.assertEquals(2, masterAccount.getUserId());
    }

    @Test
    void searchBeautyMasterByWorkAreaFail() {
        List<MasterAccount> masterAccountList = instance.searchBeautyMasterByWorkArea("Pedicure").getData();
        MasterAccount masterAccount = masterAccountList.get(0);
        Assertions.assertNotEquals(1, masterAccount.getUserId());
    }

    @Test
    void sendRequestToMasterSuccess() {
        Assertions.assertEquals(true, instance.sendRequestToMaster(2, 1, "Mon Mar 23 12:43:32 GMT+03:00 2020", "22:43:32").getData());
    }

    @Test
    void sendRequestToMasterFail() {
        Assertions.assertNotEquals(true, instance.sendRequestToMaster(11, 8, "Mon Mar 23 12:43:32 GMT+03:00 2020", "22:43:32").getData());
    }

    @Test
    void respondToRequestSuccess() {
        Assertions.assertEquals(true, instance.respondToRequest(2, 1, "Mon Mar 23 12:43:32 GMT+03:00 2020", "22:43:32", "Manikure", false, new Rating()).getData());
    }

    @Test
    void respondToRequestFail() {
        Assertions.assertEquals(false, instance.respondToRequest(11, 12, "Mon Mar 23 12:43:32 GMT+03:00 2020", "22:43:32", "Manikure", false, new Rating()).getData());
    }

    @Test
    void setWorkSchedulesSuccess() {
        Assertions.assertEquals(true, instance.setWorkSchedules(2, "Mon Mar 23", "20:08:56").getData());
    }

    @Test
    void setWorkSchedulesFail() {
        Assertions.assertEquals(false, instance.setWorkSchedules(12, "Mon Mar 23", "20:08:56").getData());
    }

    @Test
    void rateMasterSuccess() {
        Assertions.assertEquals(true, instance.rateMaster(3, EnumRating.NORMAL).getData());
    }

    @Test
    void rateMasterFail() {
        Assertions.assertEquals(false, instance.rateMaster(11, EnumRating.NORMAL).getData());
    }

    @Test
    void rateCustomerSuccess() {
        Assertions.assertEquals(true, instance.rateCustomer(2, EnumRating.NORMAL).getData());
    }

    @Test
    void rateCustomerFail() {
        Assertions.assertEquals(false, instance.rateCustomer(11, EnumRating.NORMAL).getData());
    }

    @Test
    void createWorkspaceSuccess() {
        Assertions.assertEquals(true, instance.createWorkPlace("Milchacova 10", true, dataProviderXML.searchMasterAccountById(1).get()).getData());
    }

    @Test
    void createWorkspaceFail() {
        Assertions.assertEquals(false, instance.createWorkPlace("", false, new MasterAccount()).getData());
    }

    @Test
    void setMasterSuccess() {
        Assertions.assertNull(instance.setMaster(new WorkPlace(), new MasterAccount()).getData());
    }

    @Test
    void setMasterFail() {
        WorkPlace workPlace = dataProviderXML.searchWorkPlaceById(3).get();
        MasterAccount masterAccount = dataProviderXML.searchMasterAccountById(3).get();
        Assertions.assertNotEquals(false, instance.setMaster(workPlace, masterAccount).getData());
    }

    @Test
    void createScheduleSuccess() {
        Rating rating = dataProviderXML.searchRatingById(3).get();
        Assertions.assertEquals(true, instance.createSchedule("Manikure", 2, 2, "29.10.2021", "16:52:07", false, rating).getData());
    }

    @Test
    void createScheduleFail() {
        Rating rating = dataProviderXML.searchRatingById(3).get();
        Assertions.assertEquals(false, instance.createSchedule("Manikure", 12, 20, "29.10.2021", "16:52:07", false, rating).getData());

    }
}
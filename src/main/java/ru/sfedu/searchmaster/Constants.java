package ru.sfedu.searchmaster;


public class Constants {
    public static final String ENV_PROP_KEY = "environment";
    public static final String ENV_PROP_VALUE = "src/main/resources/environment.properties";
    public static final String DEFAULT_ACTOR = "system";

    public static final String MONGO_CONNECT = "mongodb://localhost:27017";
    public static final String MONGO_DATABASE = "searchmaster";
    public static final String MONGO_COLLECTION = "history";

    public static final String ACCOUNT_CSV = "ru.sfedu.searchmaster.csv.account";
    public static final String CUSTOMER_ACCOUNT_CSV = "ru.sfedu.searchmaster.csv.customer_account";
    public static final String MASTER_ACCOUNT_CSV = "ru.sfedu.searchmaster.csv.master_account";
    public static final String RATING_CSV = "ru.sfedu.searchmaster.csv.rating";
    public static final String SHEDULE_CSV = "ru.sfedu.searchmaster.csv.shedule";
    public static final String SHEDULES_CSV = "ru.sfedu.searchmaster.csv.shedules";
    public static final String WORKPLACE_CSV = "ru.sfedu.searchmaster.csv.workplace";

    public static final String ID_NOT_EXISTS = "Object[%d] is not exists";
}

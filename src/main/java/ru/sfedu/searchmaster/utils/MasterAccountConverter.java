package ru.sfedu.searchmaster.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.searchmaster.models.MasterAccount;

public class MasterAccountConverter extends AbstractBeanField<MasterAccount, Integer> {
    private static final Logger log = LogManager.getLogger(MasterAccountConverter.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String indexString = s.substring(1, s.length() - 1);
        MasterAccount masterAccount = new MasterAccount();
        if (!indexString.isEmpty()) {
            masterAccount.setUserId(Long.parseLong(indexString));
        }
        return masterAccount;
    }

    public String convertToWrite(Object value) {
        MasterAccount masterAccount = (MasterAccount) value;
        StringBuilder builder = new StringBuilder("[");
        builder.append(masterAccount.getUserId());
        builder.append(",");
        builder.delete(builder.length() - 1, builder.length());
        builder.append("]");
        return builder.toString();
    }
}

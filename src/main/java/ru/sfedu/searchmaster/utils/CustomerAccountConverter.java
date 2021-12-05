package ru.sfedu.searchmaster.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.searchmaster.models.CustomerAccount;

public class CustomerAccountConverter extends AbstractBeanField<CustomerAccount, Integer> {
    private static final Logger log = LogManager.getLogger(CustomerAccountConverter.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String indexString = s.substring(1, s.length() - 1);
        CustomerAccount customerAccount = new CustomerAccount();
        if (!indexString.isEmpty()) {
            customerAccount.setUserId(Long.parseLong(indexString));
        }
        return customerAccount;
    }

    public String convertToWrite(Object value) {
        CustomerAccount customerAccount = (CustomerAccount) value;
        StringBuilder builder = new StringBuilder("[");
        builder.append(customerAccount.getUserId());
        builder.append(",");
        builder.delete(builder.length() - 1, builder.length());
        builder.append("]");
        return builder.toString();
    }
}

package ru.sfedu.searchmaster.utils;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.searchmaster.models.MasterAccount;

import java.util.ArrayList;
import java.util.List;

public class MasterAccountListConverter extends AbstractBeanField<MasterAccount, Integer> {

    @Override
    protected Object convert(String s) {
        String indexString;
        indexString = s.substring(1, s.length() - 1);
        String[] unparsedIndexList = indexString.split(",");
        List<MasterAccount> masterAccounts = new ArrayList<>();
        for (String strIndex : unparsedIndexList) {
            if (!strIndex.isEmpty()) {
                MasterAccount masterAccount = new MasterAccount();
                masterAccount.setUserId(Long.parseLong(strIndex));
                masterAccounts.add(masterAccount);
            }
        }
        return masterAccounts;
    }

    public String convertToWrite(Object value) {
        List<MasterAccount> masterAccountList = (List<MasterAccount>) value;
        StringBuilder builder = new StringBuilder("[");
        if (masterAccountList.size() > 0) {
            for (MasterAccount masterAccount : masterAccountList) {
                builder.append(masterAccount.getUserId());
                builder.append(",");
            }

            builder.delete(builder.length() - 1, builder.length());
        }
        builder.append("]");
        return builder.toString();
    }
}

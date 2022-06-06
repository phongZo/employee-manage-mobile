package com.example.source.strategy;

import com.example.source.model.Account;

import java.util.List;

public class SortAscending implements SortAlgorithm{
    @Override
    public List<Account> sort(List<Account> accountList) {
        for (int i = 0; i < accountList.size(); i++) {
            for (int j = i + 1; j < accountList.size(); j++) {

                Account acc1 = accountList.get(j);
                Account acc2 = accountList.get(i);
                if (acc1.getId() < acc2.getId()) {
                    accountList.set(i,acc1);
                    accountList.set(j,acc2);
                }
            }
        }
        return accountList;
    }
}

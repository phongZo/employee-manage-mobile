package com.example.source.strategy;

import com.example.source.model.Account;

import java.util.List;

public class SortContext {
    private SortAlgorithm sortAlgorithm;

    public SortContext(SortAlgorithm sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }

    public List<Account> sorting (List<Account> accountList){
        return sortAlgorithm.sort(accountList);
    }

    public void setSortAlgorithm(SortAlgorithm sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }
}

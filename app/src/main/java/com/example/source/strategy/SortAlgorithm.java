package com.example.source.strategy;

import com.example.source.model.Account;

import java.util.List;

public interface SortAlgorithm {
    public List<Account> sort(List<Account> accountList);
}

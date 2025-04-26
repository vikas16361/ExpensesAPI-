package com.vikas.TrackerAPI.service;

import java.util.List;

import com.vikas.TrackerAPI.entity.Expense;

public interface ExpenseService {
	List<Expense> getAllExpenses();

	Expense getExpenseById(Long id);
	void deleteExpenseById(Long id);
	Expense addExpenseDetails(Expense expense);
	Expense updateExpenses(Long id,Expense expense);
}

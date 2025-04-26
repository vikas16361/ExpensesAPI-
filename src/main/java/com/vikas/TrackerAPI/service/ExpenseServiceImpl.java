package com.vikas.TrackerAPI.service;

import com.vikas.TrackerAPI.Exception.ExpenseNotFoundException;
import com.vikas.TrackerAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vikas.TrackerAPI.entity.Expense;
import com.vikas.TrackerAPI.repository.ExpenseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserServiceImpl userService;


	@Override
	public List<Expense> getAllExpenses() {
		return expenseRepository.findAll();
	}


	@Override
	public Expense getExpenseById(Long id) {
		Optional<Expense> byId =  expenseRepository.findById(id);
		if(byId.isPresent()){
			return byId.get();
		}
		else {
			throw new ExpenseNotFoundException("Expense not found"+id);
		}

	}

	@Override
	public void deleteExpenseById(Long id) {
		expenseRepository.deleteById(id);
	}

	@Override
	public Expense addExpenseDetails(Expense expense) {
		expense.setUser(userService.getLoggedin());
		Expense save = expenseRepository.save(expense);
		return save;
	}

	@Override
	public Expense updateExpenses(Long id, Expense expense) {
		Expense expenseingdetail = getExpenseById(id);
		expenseingdetail.setExpenseName(expense.getExpenseName() !=null ? expense.getExpenseName() : expenseingdetail.getExpenseName());
		expenseingdetail.setExpenseAmount(expense.getExpenseAmount()!=null ? expense.getExpenseAmount():expenseingdetail.getExpenseAmount());
		expenseingdetail.setCatagory(expense.getCatagory() != null ? expense.getCatagory() : expenseingdetail.getCatagory());
		expenseingdetail.setDescription(expense.getDescription() != null ? expense.getDescription() : expenseingdetail.getDescription());
		expenseingdetail.setDate(expense.getDate()!= null? expense.getDate() : expenseingdetail.getDate());
		expenseRepository.save(expenseingdetail);
		return expenseingdetail;
	}

}

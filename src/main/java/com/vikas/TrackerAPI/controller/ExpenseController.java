package com.vikas.TrackerAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.vikas.TrackerAPI.entity.Expense;
import com.vikas.TrackerAPI.service.ExpenseService;

@RestController
public class ExpenseController {
	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/expenses")
	public Expense addExpense( @RequestBody Expense expense) {
		System.out.println("print expense details ::" + expense);
		return expenseService.addExpenseDetails(expense);
	}
	
	@GetMapping("/expenses")
	public List<Expense> gettAllExpense() {
		return expenseService.getAllExpenses();
	}
	
	@GetMapping("/expenses/{id}")
	public Expense getExpenseByid(@PathVariable("id") Long id) {
		return expenseService.getExpenseById(id);
	}

	@DeleteMapping("/expenses")
	public void deleteExpenseByid(@RequestParam("id") Long id) {
		 expenseService.deleteExpenseById(id);
	}

	@PutMapping("/expenses/{id}")
	public Expense updateExpense(@RequestBody Expense expense, @PathVariable Long id) {
		return  expenseService.updateExpenses(id, expense);
	}
}

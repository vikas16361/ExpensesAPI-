package com.vikas.TrackerAPI.entity;

import java.math.BigDecimal;
//import java.util.Date;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.aspectj.bridge.Message;
import org.hibernate.annotations.GeneratorType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

//@Data
//@AllArgsConstructor//
//@NoArgsConstructor
@Entity
@Table(name = "tb1_expenses")
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "expense_name")
	@NotNull

	private String expenseName;
	private String description;
	@Column(name = "expense_amount")
	private BigDecimal expenseAmount;
	private String catagory;
	private Date date;
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "user_id",nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Expense() {
		super();
	}

	public Expense(Long id, String expenseName, String description, BigDecimal expenseAmount, String catagory,
			Date date) {
		super();
		this.id = id;
		this.expenseName = expenseName;
		this.description = description;
		this.expenseAmount = expenseAmount;
		this.catagory = catagory;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(BigDecimal expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", expenseName=" + expenseName + ", description=" + description
				+ ", expenseAmount=" + expenseAmount + ", catagory=" + catagory + ", date=" + date + "]";
	}
	

}

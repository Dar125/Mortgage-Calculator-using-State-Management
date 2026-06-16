// Muhammad Dar
// CECS-453
// Lab Assignment 4
// Mortgage Calculator
// 16 June, 2026
package com.example.mortgagecalculator

import java.text.DecimalFormat
import kotlin.math.pow

class Mortgage {

    // Default mortgage values
    private var amount = 100000.0
    private var years = 30
    private var rate = 0.035

    // Set the loan amount
    fun setAmount(newAmount: Double) {
        if (newAmount >= 0) {
            amount = newAmount
        }
    }

    // Set the loan term in years
    fun setYears(newYears: Int) {
        if (newYears >= 0) {
            years = newYears
        }
    }

    // Set the interest rate
    fun setRate(newRate: Double) {
        if (newRate >= 0) {
            rate = newRate
        }
    }

    // Return the current amount
    fun getAmount(): Double {
        return amount
    }

    // Return the current number of years
    fun getYears(): Int {
        return years
    }

    // Return the current interest rate
    fun getRate(): Double {
        return rate
    }

    // Calculate the monthly mortgage payment
    fun monthlyPayment(): Double {
        val monthlyRate = rate / 12.0
        val temp = (1.0 / (1.0 + monthlyRate)).pow(years * 12)
        return amount * monthlyRate / (1 - temp)
    }

    // Calculate the total payment over the loan term
    fun totalPayment(): Double {
        return monthlyPayment() * years * 12
    }
    // Format the monthly payment as currency
    fun formattedMonthlyPayment(): String {
        val money = DecimalFormat("$#,##0.00")
        return money.format(monthlyPayment())
    }

    // Format the total payment as currency
    fun formattedTotalPayment(): String {
        val money = DecimalFormat("$#,##0.00")
        return money.format(totalPayment())
    }

    // Format the loan amount as currency
    fun formattedAmount(): String {
        val money = DecimalFormat("$#,##0.00")
        return money.format(amount)
    }
}
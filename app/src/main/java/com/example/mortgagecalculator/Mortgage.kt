package com.example.mortgagecalculator

import java.text.DecimalFormat
import kotlin.math.pow

class Mortgage {

    private var amount = 100000.0
    private var years = 30
    private var rate = 0.035

    fun setAmount(newAmount: Double) {
        if (newAmount >= 0) {
            amount = newAmount
        }
    }

    fun setYears(newYears: Int) {
        if (newYears >= 0) {
            years = newYears
        }
    }

    fun setRate(newRate: Double) {
        if (newRate >= 0) {
            rate = newRate
        }
    }

    fun getAmount(): Double {
        return amount
    }

    fun getYears(): Int {
        return years
    }

    fun getRate(): Double {
        return rate
    }

    fun monthlyPayment(): Double {
        val monthlyRate = rate / 12.0
        val temp = (1.0 / (1.0 + monthlyRate)).pow(years * 12)
        return amount * monthlyRate / (1 - temp)
    }

    fun totalPayment(): Double {
        return monthlyPayment() * years * 12
    }

    fun formattedMonthlyPayment(): String {
        val money = DecimalFormat("$#,##0.00")
        return money.format(monthlyPayment())
    }

    fun formattedTotalPayment(): String {
        val money = DecimalFormat("$#,##0.00")
        return money.format(totalPayment())
    }

    fun formattedAmount(): String {
        val money = DecimalFormat("$#,##0.00")
        return money.format(amount)
    }
}
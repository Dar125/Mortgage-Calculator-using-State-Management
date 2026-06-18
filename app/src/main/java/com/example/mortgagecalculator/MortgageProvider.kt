// Muhammad Dar
// CECS-453
// Mobile Application Development
// Lab Assignment #5
// Mortgage Calculator Using State Management
// June 18, 2026
package com.example.mortgagecalculator

// Provider used to share mortgage data between activities
object MortgageProvider {

    // Default mortgage values
    var amount = 100000.0
    var years = 30
    var rate = 0.035

    // Update the mortgage information
    fun setMortgage(newAmount: Double, newYears: Int, newRate: Double) {
        amount = newAmount
        years = newYears
        rate = newRate
    }

    // Create and return a Mortgage object
    fun getMortgage(): Mortgage {
        val mortgage = Mortgage()

        mortgage.setAmount(amount)
        mortgage.setYears(years)
        mortgage.setRate(rate)

        return mortgage
    }
}
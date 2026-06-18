// Muhammad Dar
// CECS-453
// Mobile Application Development
// Lab Assignment #5
// Mortgage Calculator Using State Management
// June 18, 2026

package com.example.mortgagecalculator

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // TextViews used to display mortgage information
    private lateinit var amountTextView: TextView
    private lateinit var yearsTextView: TextView
    private lateinit var rateTextView: TextView
    private lateinit var monthlyPaymentTextView: TextView
    private lateinit var totalPaymentTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect TextViews from the layout
        amountTextView = findViewById(R.id.amountTextView)
        yearsTextView = findViewById(R.id.yearsTextView)
        rateTextView = findViewById(R.id.rateTextView)
        monthlyPaymentTextView = findViewById(R.id.monthlyPaymentTextView)
        totalPaymentTextView = findViewById(R.id.totalPaymentTextView)

        // Connect the checkbox and button
        val termsCheckBox = findViewById<CheckBox>(R.id.termsCheckBox)
        val modifyButton = findViewById<Button>(R.id.modifyButton)

        // Load saved mortgage information from SharedPreferences
        loadData()

        // Display an AlertDialog when the checkbox is clicked
        termsCheckBox.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Terms and Conditions")
                .setMessage("Do you agree to the terms and conditions?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show()
        }

        // Open ModifyActivity when the button is pressed
        modifyButton.setOnClickListener {
            val intent = Intent(this, ModifyActivity::class.java)
            startActivity(intent)
        }
    }

    // Refresh the screen whenever the activity becomes visible
    override fun onResume() {
        super.onResume()
        updateScreen()
    }

    // Load saved values from SharedPreferences
    private fun loadData() {
        val sharedPref = getSharedPreferences("mortgage_data", MODE_PRIVATE)

        val amount = sharedPref.getFloat("amount", 100000.0f).toDouble()
        val years = sharedPref.getInt("years", 30)
        val rate = sharedPref.getFloat("rate", 0.035f).toDouble()

        // Store the values in the Provider
        MortgageProvider.setMortgage(amount, years, rate)
    }

    // Update all mortgage information displayed on the screen
    private fun updateScreen() {

        // Get the mortgage information from the Provider
        val mortgage = MortgageProvider.getMortgage()

        amountTextView.text = mortgage.formattedAmount()
        yearsTextView.text = mortgage.getYears().toString()

        // Display the interest rate as a percentage
        rateTextView.text = String.format("%.2f%%", mortgage.getRate() * 100)

        // Display monthly payment
        monthlyPaymentTextView.text =
            "Monthly Payment ${mortgage.formattedMonthlyPayment()}"

        // Display total payment
        totalPaymentTextView.text =
            "Total Payment ${mortgage.formattedTotalPayment()}"
    }
}
// Muhammad Dar
// CECS-453
// Lab Assignment 4
// Mortgage Calculator
// 16 June, 2026
package com.example.mortgagecalculator

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Default mortgage values
    private var amount = 100000.0
    private var years = 30
    private var rate = 0.035

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect TextViews from the layout
        val amountTextView = findViewById<TextView>(R.id.amountTextView)
        val yearsTextView = findViewById<TextView>(R.id.yearsTextView)
        val rateTextView = findViewById<TextView>(R.id.rateTextView)
        val monthlyPaymentTextView = findViewById<TextView>(R.id.monthlyPaymentTextView)
        val totalPaymentTextView = findViewById<TextView>(R.id.totalPaymentTextView)

        // Connect the checkbox and button
        val termsCheckBox = findViewById<CheckBox>(R.id.termsCheckBox)
        val modifyButton = findViewById<Button>(R.id.modifyButton)

        // Create a mortgage object with default values
        val mortgage = Mortgage()

        // Display mortgage information on the screen
        updateScreen(
            mortgage,
            amountTextView,
            yearsTextView,
            rateTextView,
            monthlyPaymentTextView,
            totalPaymentTextView
        )

        // Display an AlertDialog when the checkbox is clicked
        termsCheckBox.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Terms and Conditions")
                .setMessage("Do you agree to the terms and conditions?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show()
        }

        // Open the ModifyActivity when the button is pressed
        modifyButton.setOnClickListener {
            val intent = Intent(this, ModifyActivity::class.java)

            intent.putExtra("amount", amount)
            intent.putExtra("years", years)
            intent.putExtra("rate", rate)

            startActivityForResult(intent, 1)
        }
    }

    // Receive updated values from ModifyActivity
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            // Get updated values
            amount = data.getDoubleExtra("amount", 100000.0)
            years = data.getIntExtra("years", 30)
            rate = data.getDoubleExtra("rate", 0.035)

            // Update the mortgage object
            val mortgage = Mortgage()
            mortgage.setAmount(amount)
            mortgage.setYears(years)
            mortgage.setRate(rate)

            // Get references to the TextViews again
            val amountTextView = findViewById<TextView>(R.id.amountTextView)
            val yearsTextView = findViewById<TextView>(R.id.yearsTextView)
            val rateTextView = findViewById<TextView>(R.id.rateTextView)
            val monthlyPaymentTextView = findViewById<TextView>(R.id.monthlyPaymentTextView)
            val totalPaymentTextView = findViewById<TextView>(R.id.totalPaymentTextView)

            // Refresh the screen with new values
            updateScreen(
                mortgage,
                amountTextView,
                yearsTextView,
                rateTextView,
                monthlyPaymentTextView,
                totalPaymentTextView
            )
        }
    }

    // Update all displayed mortgage information
    private fun updateScreen(
        mortgage: Mortgage,
        amountTextView: TextView,
        yearsTextView: TextView,
        rateTextView: TextView,
        monthlyPaymentTextView: TextView,
        totalPaymentTextView: TextView
    ) {

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
// Muhammad Dar
// CECS-453
// Lab Assignment 4
// Mortgage Calculator
// 16 June, 2026
package com.example.mortgagecalculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class ModifyActivity : AppCompatActivity() {

    // Stores the selected interest rate
    private var selectedRate = 0.035

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        // Connect UI components to Kotlin variables
        val yearsRadioGroup = findViewById<RadioGroup>(R.id.yearsRadioGroup)
        val radio10 = findViewById<RadioButton>(R.id.radio10)
        val radio15 = findViewById<RadioButton>(R.id.radio15)
        val radio30 = findViewById<RadioButton>(R.id.radio30)
        val amountEditText = findViewById<EditText>(R.id.amountEditText)
        val rateListView = findViewById<ListView>(R.id.rateListView)
        val doneButton = findViewById<Button>(R.id.doneButton)

        // Get current mortgage values from MainActivity
        val oldAmount = intent.getDoubleExtra("amount", 100000.0)
        val oldYears = intent.getIntExtra("years", 30)
        val oldRate = intent.getDoubleExtra("rate", 0.035)

        // Display current values on the screen
        amountEditText.setText(oldAmount.toString())
        selectedRate = oldRate

        // Select the correct radio button
        if (oldYears == 10) {
            radio10.isChecked = true
        } else if (oldYears == 15) {
            radio15.isChecked = true
        } else {
            radio30.isChecked = true
        }

        // Create the list of interest rates from 2.0% to 15.0%
        val rates = ArrayList<String>()
        var rate = 2.0

        while (rate <= 15.0) {
            rates.add(rate.toString() + "%")
            rate += 0.25
        }

        // Display interest rates in the ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rates)
        rateListView.adapter = adapter

        // Save the selected interest rate
        rateListView.setOnItemClickListener { _, _, position, _ ->
            selectedRate = (2.0 + (position * 0.25)) / 100.0
        }

        // Return updated information to MainActivity
        doneButton.setOnClickListener {
            val resultIntent = Intent()

            // Default loan term
            var years = 30

            // Determine which year option is selected
            if (yearsRadioGroup.checkedRadioButtonId == R.id.radio10) {
                years = 10
            } else if (yearsRadioGroup.checkedRadioButtonId == R.id.radio15) {
                years = 15
            }

            // Read amount entered by the user
            var amount = 100000.0

            if (amountEditText.text.toString() != "") {
                amount = amountEditText.text.toString().toDouble()
            }

            // Send updated values back to MainActivity
            resultIntent.putExtra("amount", amount)
            resultIntent.putExtra("years", years)
            resultIntent.putExtra("rate", selectedRate)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
// Muhammad Dar
// CECS-453
// Mobile Application Development
// Lab Assignment #5
// Mortgage Calculator Using State Management
// June 18, 2026
package com.example.mortgagecalculator

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

        // Display saved mortgage values from the Provider
        amountEditText.setText(MortgageProvider.amount.toString())
        selectedRate = MortgageProvider.rate

        // Select the correct radio button
        if (MortgageProvider.years == 10) {
            radio10.isChecked = true
        } else if (MortgageProvider.years == 15) {
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

        // Scroll to the previously saved interest rate
        val position = ((MortgageProvider.rate * 100 - 2.0) / 0.25).toInt()

        rateListView.post {
            rateListView.setSelection(position)
        }

        // Save the selected interest rate
        rateListView.setOnItemClickListener { _, _, position, _ ->
            selectedRate = (2.0 + (position * 0.25)) / 100.0
        }

        // Save the updated mortgage information
        doneButton.setOnClickListener {

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

            // Update the Provider with the new values
            MortgageProvider.setMortgage(amount, years, selectedRate)

            // Save values using SharedPreferences
            saveData(amount, years, selectedRate)

            finish()
        }
    }

    // Save mortgage information in SharedPreferences
    private fun saveData(amount: Double, years: Int, rate: Double) {
        val sharedPref = getSharedPreferences("mortgage_data", MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putFloat("amount", amount.toFloat())
        editor.putInt("years", years)
        editor.putFloat("rate", rate.toFloat())

        editor.apply()
    }
}
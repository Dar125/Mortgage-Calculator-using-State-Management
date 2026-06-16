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

    private var selectedRate = 0.035

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        val yearsRadioGroup = findViewById<RadioGroup>(R.id.yearsRadioGroup)
        val radio10 = findViewById<RadioButton>(R.id.radio10)
        val radio15 = findViewById<RadioButton>(R.id.radio15)
        val radio30 = findViewById<RadioButton>(R.id.radio30)
        val amountEditText = findViewById<EditText>(R.id.amountEditText)
        val rateListView = findViewById<ListView>(R.id.rateListView)
        val doneButton = findViewById<Button>(R.id.doneButton)

        val oldAmount = intent.getDoubleExtra("amount", 100000.0)
        val oldYears = intent.getIntExtra("years", 30)
        val oldRate = intent.getDoubleExtra("rate", 0.035)

        amountEditText.setText(oldAmount.toString())
        selectedRate = oldRate

        if (oldYears == 10) {
            radio10.isChecked = true
        } else if (oldYears == 15) {
            radio15.isChecked = true
        } else {
            radio30.isChecked = true
        }

        val rates = ArrayList<String>()
        var rate = 2.0

        while (rate <= 15.0) {
            rates.add(rate.toString() + "%")
            rate += 0.25
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rates)
        rateListView.adapter = adapter

        rateListView.setOnItemClickListener { _, _, position, _ ->
            selectedRate = (2.0 + (position * 0.25)) / 100.0
        }

        doneButton.setOnClickListener {
            val resultIntent = Intent()

            var years = 30

            if (yearsRadioGroup.checkedRadioButtonId == R.id.radio10) {
                years = 10
            } else if (yearsRadioGroup.checkedRadioButtonId == R.id.radio15) {
                years = 15
            }

            var amount = 100000.0

            if (amountEditText.text.toString() != "") {
                amount = amountEditText.text.toString().toDouble()
            }

            resultIntent.putExtra("amount", amount)
            resultIntent.putExtra("years", years)
            resultIntent.putExtra("rate", selectedRate)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
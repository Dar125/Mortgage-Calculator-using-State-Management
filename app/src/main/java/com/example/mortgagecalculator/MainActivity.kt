package com.example.mortgagecalculator

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var amount = 100000.0
    private var years = 30
    private var rate = 0.035

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val amountTextView = findViewById<TextView>(R.id.amountTextView)
        val yearsTextView = findViewById<TextView>(R.id.yearsTextView)
        val rateTextView = findViewById<TextView>(R.id.rateTextView)
        val monthlyPaymentTextView = findViewById<TextView>(R.id.monthlyPaymentTextView)
        val totalPaymentTextView = findViewById<TextView>(R.id.totalPaymentTextView)

        val termsCheckBox = findViewById<CheckBox>(R.id.termsCheckBox)
        val modifyButton = findViewById<Button>(R.id.modifyButton)

        val mortgage = Mortgage()

        updateScreen(
            mortgage,
            amountTextView,
            yearsTextView,
            rateTextView,
            monthlyPaymentTextView,
            totalPaymentTextView
        )

        termsCheckBox.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Terms and Conditions")
                .setMessage("Do you agree to the terms and conditions?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show()
        }

        modifyButton.setOnClickListener {
            val intent = Intent(this, ModifyActivity::class.java)

            intent.putExtra("amount", amount)
            intent.putExtra("years", years)
            intent.putExtra("rate", rate)

            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            amount = data.getDoubleExtra("amount", 100000.0)
            years = data.getIntExtra("years", 30)
            rate = data.getDoubleExtra("rate", 0.035)

            val mortgage = Mortgage()
            mortgage.setAmount(amount)
            mortgage.setYears(years)
            mortgage.setRate(rate)

            val amountTextView = findViewById<TextView>(R.id.amountTextView)
            val yearsTextView = findViewById<TextView>(R.id.yearsTextView)
            val rateTextView = findViewById<TextView>(R.id.rateTextView)
            val monthlyPaymentTextView = findViewById<TextView>(R.id.monthlyPaymentTextView)
            val totalPaymentTextView = findViewById<TextView>(R.id.totalPaymentTextView)

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
        rateTextView.text = String.format("%.2f%%", mortgage.getRate() * 100)

        monthlyPaymentTextView.text =
            "Monthly Payment ${mortgage.formattedMonthlyPayment()}"

        totalPaymentTextView.text =
            "Total Payment ${mortgage.formattedTotalPayment()}"
    }
}
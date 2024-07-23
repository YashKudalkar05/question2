package com.example.question2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.question2.R

class MainActivity : AppCompatActivity() {

    private lateinit var editTextAmount: EditText
    private lateinit var spinnerFromCurrency: Spinner
    private lateinit var spinnerToCurrency: Spinner
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView

    private var fromCurrency: String = "USD"
    private var toCurrency: String = "USD"

    private val currencies = arrayOf("USD", "EUR", "GBP", "INR", "JPY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextAmount = findViewById(R.id.editTextAmount)
        spinnerFromCurrency = findViewById(R.id.spinnerFromCurrency)
        spinnerToCurrency = findViewById(R.id.spinnerToCurrency)
        buttonConvert = findViewById(R.id.buttonConvert)
        textViewResult = findViewById(R.id.textViewResult)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerFromCurrency.adapter = adapter
        spinnerToCurrency.adapter = adapter

        spinnerFromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                fromCurrency = currencies[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                fromCurrency = currencies[0]
            }
        }

        spinnerToCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                toCurrency = currencies[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                toCurrency = currencies[0]
            }
        }

        buttonConvert.setOnClickListener {
            val amountStr = editTextAmount.text.toString()
            if (amountStr.isNotEmpty()) {
                val amount = amountStr.toDouble()
                val convertedAmount = convertCurrency(amount, fromCurrency, toCurrency)
                textViewResult.text = String.format("Converted Amount: %.2f %s", convertedAmount, toCurrency)
            } else {
                Toast.makeText(this@MainActivity, "Please enter an amount", Toast.LENGTH_SHORT).show()
            }
        }

        handleIncomingIntent()
    }

    private fun handleIncomingIntent() {
        val result = intent.getStringExtra("RESULT")
        if (result != null) {
            editTextAmount.setText(result)
        }
    }

    private fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Double {
        // This is just a dummy conversion rate for demonstration purposes
        var rate = 1.0
        if (fromCurrency == "USD" && toCurrency == "EUR") {
            rate = 0.85
        } else if (fromCurrency == "USD" && toCurrency == "GBP") {
            rate = 0.75
        } else if (fromCurrency == "USD" && toCurrency == "INR") {
            rate = 74.5
        } else if (fromCurrency == "USD" && toCurrency == "JPY") {
            rate = 110.0
        }
        // Add more conversion logic as needed
        return amount * rate
    }
}

package com.example.percentcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.percentcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var shared : SharedPreferences


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calcButtonId.setOnClickListener {
            if(binding.priceId.text.toString().toDoubleOrNull() != null && binding.percentageId.text.toString().toDoubleOrNull() != null){
                val price = binding.priceId.text.toString().toDoubleOrNull()!!
                val percent = binding.percentageId.text.toString().toDoubleOrNull()!!
                val outcome = price - (price*percent*.01)
                binding.outcomeId.text = "You need to pay: $"+String.format("%.1f", outcome).toDouble().toString()
            }else if(binding.priceId.text.toString().toDoubleOrNull() != null && binding.percentageId.text.toString().toDoubleOrNull() == null){
                binding.outcomeId.text = "Please Enter Percent Off"
            }else if(binding.priceId.text.toString().toDoubleOrNull() == null && binding.percentageId.text.toString().toDoubleOrNull() != null){
                binding.outcomeId.text = "Please Enter Price of Item"
            }else{
                binding.outcomeId.text = "Please Fill With Numbers"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        shared = getSharedPreferences("simpleDB" , Context.MODE_PRIVATE)
        val outcome = shared.getString("outcome", "Could not load").toString()
        val price = shared.getString("price", "Could not load").toString()
        val percent = shared.getString("percent", "Could not load").toString()
        if(outcome == "Could not load"){
            binding.priceId.setText("0")
            binding.percentageId.setText("0")
            binding.outcomeId.text = "0"
        }else{
            binding.priceId.setText(price)
            binding.percentageId.setText(percent)
            binding.outcomeId.text = outcome
        }
    }

    override fun onPause() {
        super.onPause()
        shared = getSharedPreferences("simpleDB" , Context.MODE_PRIVATE)
        val edit = shared.edit()
        edit.putString("outcome", binding.outcomeId.text.toString())
        edit.putString("price", binding.priceId.text.toString())
        edit.putString("percent", binding.percentageId.text.toString())
        edit.apply()
    }
}
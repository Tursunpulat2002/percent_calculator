package com.example.percentcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.example.percentcalculator.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var shared : SharedPreferences


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Shamsi", "On Create")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calcButtonId.setOnClickListener {
            var price = binding.priceId.text.toString().toDoubleOrNull()
            var percent = binding.percentageId.text.toString().toDoubleOrNull()
            if(price != null && percent != null){
                var outcome = price - (price*percent*.01)
                binding.outcomeId.text = String.format("%.1f", outcome).toDouble().toString()
            }else{
                binding.outcomeId.text = "Enter numbers"
            }
        }


    }

    override fun onStart() {
        super.onStart()
        Log.e("Shamsi", "On Start")
    }

    override fun onResume() {
        super.onResume()
        Log.e("Shamsi", "On Resume")
        shared = getSharedPreferences("simpleDB" , Context.MODE_PRIVATE)
        val outcome = shared.getString("outcome", "Could not load").toString()
        val price = shared.getString("price", "Could not load").toString()
        val percent = shared.getString("percent", "Could not load").toString()
        if(outcome == "Could not load"){
            binding.priceId.setText("0")
            binding.percentageId.setText("0")
            binding.outcomeId.setText("0")
        }else{
            binding.priceId.setText(price)
            binding.percentageId.setText(percent)
            binding.outcomeId.setText(outcome)
        }

    }

    override fun onRestart() {
        super.onRestart()
        Log.e("Shamsi", "On Restart")
    }

    override fun onPause() {
        super.onPause()
        Log.e("Shamsi", "On Pause")
        shared = getSharedPreferences("simpleDB" , Context.MODE_PRIVATE)
        val edit = shared.edit()
        edit.putString("outcome", binding.outcomeId.text.toString())
        edit.putString("price", binding.priceId.text.toString())
        edit.putString("percent", binding.percentageId.text.toString())
        edit.apply()
        Log.e("Shamsi", binding.outcomeId.text.toString())
    }

    override fun onStop() {
        super.onStop()
        Log.e("Shamsi", "On Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Shamsi", "On Destroy")
    }
}
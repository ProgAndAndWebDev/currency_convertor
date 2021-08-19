package com.example.currencyconvertor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.currencyconvertor.R
import com.example.currencyconvertor.databinding.ActivityHomeBinding
import com.example.currencyconvertor.viewModels.HomeViewModel
import kotlinx.coroutines.InternalCoroutinesApi

class HomeActivity : AppCompatActivity(){

    @InternalCoroutinesApi
    private val viewModel:HomeViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
            .also { binding ->
                binding.viewModel = viewModel
                binding.lifecycleOwner = this
            }
        viewModel.conn.observe(this@HomeActivity) {}
    }
}

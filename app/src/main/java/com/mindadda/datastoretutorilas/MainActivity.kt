package com.mindadda.datastoretutorilas

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.mindadda.datastoretutorilas.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var userDetails: UserDetails

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        userDetails = UserDetails(context = this)



        lifecycle.coroutineScope.launchWhenCreated {
            userDetails.getUserAge().collect {
                binding.age.text = it.toString()
            }

        }

        lifecycle.coroutineScope.launchWhenCreated {
            userDetails.getUserName().collect {
                binding.name.text = it.toString()
            }

        }

        binding.saveData.setOnClickListener {
            CoroutineScope(IO).launch {
                userDetails.storeUser(
                        binding.enterName.text.toString().trim(),
                        binding.enterAge.text.toString().trim().toInt()
                )
            }


        }


    }
}
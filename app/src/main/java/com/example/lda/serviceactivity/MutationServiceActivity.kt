package com.example.lda.serviceactivity

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.lda.R
import com.example.lda.formfragment.mutation.MutationReasonFragment

class MutationServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_mutation_service2)

        val backButton=findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val mutationReasonFragment = MutationReasonFragment()

        setCurrentFragment(mutationReasonFragment)

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
}
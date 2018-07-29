package co.anbora.onlinequizapp

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import co.anbora.onlinequizapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        binding.navigation.setOnNavigationItemSelectedListener { item ->

            var selectedFragment: Fragment = CategoryFragment.newInstance()

            when(item.itemId) {
                R.id.action_category -> {
                    selectedFragment = CategoryFragment.newInstance()
                }
                R.id.action_ranking -> {
                    selectedFragment = RankingFragment.newInstance()
                }
            }
            setFragment(selectedFragment)
            return@setOnNavigationItemSelectedListener true
        }

        setFragment(CategoryFragment.newInstance())

    }

    private fun setFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }
}

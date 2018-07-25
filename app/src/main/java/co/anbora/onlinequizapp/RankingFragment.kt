package co.anbora.onlinequizapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.anbora.onlinequizapp.databinding.FragmentCategoryBinding
import co.anbora.onlinequizapp.databinding.FragmentRankingBinding

class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding

    companion object {
        fun newInstance() = RankingFragment().apply {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentRankingBinding.inflate(inflater, container, false)

        return binding.root
    }

}

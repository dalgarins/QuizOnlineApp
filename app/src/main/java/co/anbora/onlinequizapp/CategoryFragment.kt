package co.anbora.onlinequizapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.anbora.onlinequizapp.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var layoutManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance(): CategoryFragment {
            val categoryFragment: CategoryFragment = CategoryFragment()
            return categoryFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }
}

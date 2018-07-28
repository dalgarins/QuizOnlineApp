package co.anbora.onlinequizapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.anbora.onlinequizapp.actions.ItemClickListener
import co.anbora.onlinequizapp.databinding.CategoryLayoutBinding
import co.anbora.onlinequizapp.databinding.FragmentCategoryBinding
import co.anbora.onlinequizapp.domain.model.Category
import co.anbora.onlinequizapp.viewholder.CategoryViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.firebase.ui.database.FirebaseRecyclerOptions



class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: FirebaseRecyclerAdapter<Category, CategoryViewHolder>

    private lateinit var database: FirebaseDatabase
    private lateinit var categories: DatabaseReference

    companion object {
        fun newInstance(): CategoryFragment {
            val categoryFragment: CategoryFragment = CategoryFragment()
            return categoryFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance()
        categories = database.getReference("Category")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        layoutManager = LinearLayoutManager(context)

        binding.listCategory.let {
            it.setHasFixedSize(true)
            it.layoutManager = layoutManager
        }

        loadCategories()

        return binding.root
    }

    private fun loadCategories() {

        val query = FirebaseDatabase.getInstance()
                .reference
                .child("Category")
                .limitToLast(50)

        val options = FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, Category::class.java)
                .setLifecycleOwner(this)
                .build()

        adapter = object: FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

                val binding: CategoryLayoutBinding = CategoryLayoutBinding.inflate(LayoutInflater.from(parent.context),
                        parent, false)
                return CategoryViewHolder(binding)
            }

            override fun onBindViewHolder(holder: CategoryViewHolder, position: Int, model: Category) {

                holder.bind(model)
                holder.setItemClickListener(object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {

                        Toast.makeText(activity,
                                String.format("%s|%s", adapter.getRef(position).key, model.name),
                                Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        adapter.notifyDataSetChanged()
        binding.listCategory.adapter = adapter
    }
}

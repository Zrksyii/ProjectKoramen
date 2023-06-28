package com.example.projectkoramen.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectkoramen.R
import com.example.projectkoramen.adapter.RecAdapter
import com.example.projectkoramen.data.Pizza

class HomeFragment : Fragment() {

    private lateinit var imageId: Array<Int>
    private lateinit var names: Array<String>
    private lateinit var ingredients: Array<String>
    private lateinit var recView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Pizza>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageId = arrayOf(
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6,
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
            R.drawable.p5,
            R.drawable.p6
        )

        names = arrayOf(
            "pizza1",
            "pizza2",
            "pizza3",
            "pizza4",
            "pizza5",
            "pizza6",
            "pizza7",
            "pizza8",
            "pizza9",
            "pizza10",
            "pizza11",
            "pizza12",
            "pizza13",
            "pizza14",
            "pizza15",
            "pizza16",
            "pizza17",
            "pizza18",
            "pizza19",
            "pizza20",
            "pizza21",
            "pizza22",
            "pizza23",
            "pizza24"
        )

        ingredients = arrayOf(
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano",
            "Tomato sos, cheese, oregano"
        )

        recView = requireView().findViewById(R.id.recView)
        recView.layoutManager = GridLayoutManager(requireContext(), 3)
        recView.setHasFixedSize(true)

        itemArrayList = arrayListOf()

        getData()

        recView.adapter = RecAdapter(itemArrayList)
    }
    private fun getData() {

        for (i in imageId.indices) {
            val pizza = Pizza(imageId[i], names[i], ingredients[i])
            itemArrayList.add(pizza)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}

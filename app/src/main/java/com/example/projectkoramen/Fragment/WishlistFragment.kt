package com.example.projectkoramen.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectkoramen.Helper.EXTRA_POSITION
import com.example.projectkoramen.Helper.EXTRA_QUOTE
import com.example.projectkoramen.Helper.RESULT_ADD
import com.example.projectkoramen.Helper.RESULT_DELETE
import com.example.projectkoramen.Helper.RESULT_UPDATE
import com.example.projectkoramen.Helper.mapCursorToArrayList
import com.example.projectkoramen.QuoteAddUpdateActivity
import com.example.projectkoramen.adapter.QuoteAdapter
import com.example.projectkoramen.data.Quote
import com.example.projectkoramen.databinding.FragmentWishlistBinding
import com.example.projectkoramen.db.QuoteHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {
    private var _binding: FragmentWishlistBinding? = null
    private val binding get() = _binding!!

    private lateinit var quoteHelper: QuoteHelper
    private lateinit var adapter: QuoteAdapter

    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null) {
                when (it.resultCode) {
                    RESULT_ADD -> {
                        val quote = it.data?.getParcelableExtra<Quote>(EXTRA_QUOTE) as Quote
                        adapter.addItem(quote)
                        binding.rvQuotes.smoothScrollToPosition(adapter.itemCount - 1)
                        showSnackBarMessage("Satu item berhasil ditambahkan")
                    }

                    RESULT_UPDATE -> {
                        val quote = it.data?.getParcelableExtra<Quote>(EXTRA_QUOTE) as Quote
                        val position = it.data?.getIntExtra(EXTRA_POSITION, 0) as Int
                        adapter.updateItem(position, quote)
                        binding.rvQuotes.smoothScrollToPosition(position)
                        showSnackBarMessage("Satu item berhasil diubah")
                    }

                    RESULT_DELETE -> {
                        val position = it.data?.getIntExtra(EXTRA_POSITION, 0) as Int
                        adapter.removeItem(position)
                        showSnackBarMessage("Satu item berhasil dihapus")
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Quotes"
        binding.apply {
            rvQuotes.layoutManager = LinearLayoutManager(requireContext())
            rvQuotes.setHasFixedSize(true)
            adapter = QuoteAdapter(object : QuoteAdapter.OnItemClickCallback {
                override fun onItemClicked(selectedNote: Quote?, position: Int?) {
                    val intent = Intent(requireContext(), QuoteAddUpdateActivity::class.java)
                    intent.putExtra(EXTRA_QUOTE, selectedNote)
                    intent.putExtra(EXTRA_POSITION, position)
                    resultLauncher.launch(intent)
                }
            })
            rvQuotes.adapter = adapter
            quoteHelper = QuoteHelper.getInstance(requireContext().applicationContext)
            quoteHelper.open()
            if (savedInstanceState == null) {
                loadQuotes()
            } else {
                val list = savedInstanceState.getParcelableArrayList<Quote>(EXTRA_STATE)
                if (list != null) {
                    adapter.listQuotes = list
                }
            }
            fabAdd.setOnClickListener {
                val intent = Intent(requireContext(), QuoteAddUpdateActivity::class.java)
                resultLauncher.launch(intent)
            }
        }
    }

    private fun loadQuotes() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.progressbar.visibility = View.VISIBLE
            quoteHelper = QuoteHelper.getInstance(requireContext().applicationContext)
            quoteHelper.open()
            val deferredQuotes = async(Dispatchers.IO) {
                val cursor = quoteHelper.queryAll()
                mapCursorToArrayList(cursor)
            }

            binding.progressbar.visibility = View.INVISIBLE
            val quotes = deferredQuotes.await()
            if (quotes.size > 0) adapter.listQuotes = quotes
            else {
                adapter.listQuotes = ArrayList()
                showSnackBarMessage("Tidak ada data saat ini")
            }
            quoteHelper.close()
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.rvQuotes, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listQuotes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_STATE = "EXTRA_STATE"
    }
}
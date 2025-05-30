package com.example.foodorder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.ui.adapter.SepetAdapter
import com.example.foodorder.viewmodel.SepetViewModel

class SepetFragment : Fragment() {

    private lateinit var viewModel: SepetViewModel
    private lateinit var adapter: SepetAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var textToplamFiyat: TextView
    private lateinit var textBosSepet: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sepet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SepetViewModel::class.java]

        setupRecyclerView(view)
        initViews(view)
        observeViewModel()

        viewModel.sepettekiYemekleriGetir()
    }

    private fun initViews(view: View) {
        textToplamFiyat = view.findViewById(R.id.textToplamFiyat)
        textBosSepet = view.findViewById(R.id.textBosSepet)
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewSepet)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = SepetAdapter(emptyList()) { sepetYemek ->
            viewModel.sepettenSil(sepetYemek.sepet_yemek_id.toInt())
        }
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.sepetYemekleri.observe(viewLifecycleOwner) { yemekler ->
            if (yemekler.isEmpty()) {
                recyclerView.visibility = View.GONE
                textBosSepet.visibility = View.VISIBLE
                textToplamFiyat.text = "Toplam: 0 ₺"
            } else {
                recyclerView.visibility = View.VISIBLE
                textBosSepet.visibility = View.GONE
                adapter.updateList(yemekler)
            }
        }

        viewModel.toplamFiyat.observe(viewLifecycleOwner) { toplam ->
            textToplamFiyat.text = "Toplam: $toplam ₺"
        }

        viewModel.silindi.observe(viewLifecycleOwner) { silindi ->
            if (silindi) {
                Toast.makeText(context, "Ürün sepetten silindi", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.sepettekiYemekleriGetir()
    }
}
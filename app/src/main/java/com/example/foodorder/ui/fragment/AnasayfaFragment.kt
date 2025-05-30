package com.example.foodorder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.data.model.Yemek
import com.example.foodorder.ui.adapter.YemekAdapter
import com.example.foodorder.viewmodel.AnasayfaViewModel

class AnasayfaFragment : Fragment() {

    private lateinit var viewModel: AnasayfaViewModel
    private lateinit var adapter: YemekAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anasayfa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AnasayfaViewModel::class.java]

        setupRecyclerView(view)
        observeViewModel()

        viewModel.yemekleriGetir()
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewYemekler)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        adapter = YemekAdapter(emptyList()) { yemek ->
            yemekDetayinaGit(yemek)
        }
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.yemekListesi.observe(viewLifecycleOwner) { yemekler ->
            adapter.updateList(yemekler)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun yemekDetayinaGit(yemek: Yemek) {
        val bundle = Bundle().apply {
            putString("yemek_id", yemek.yemek_id)
            putString("yemek_adi", yemek.yemek_adi)
            putString("yemek_resim_adi", yemek.yemek_resim_adi)
            putString("yemek_fiyat", yemek.yemek_fiyat)
        }
        findNavController().navigate(R.id.action_anasayfa_to_detay, bundle)
    }
}
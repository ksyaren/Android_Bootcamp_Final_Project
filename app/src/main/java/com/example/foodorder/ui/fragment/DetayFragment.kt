package com.example.foodorder.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.utils.Constants
import com.example.foodorder.viewmodel.DetayViewModel

class DetayFragment : Fragment() {

    private lateinit var viewModel: DetayViewModel
    private lateinit var imageYemek: ImageView
    private lateinit var textYemekAdi: TextView
    private lateinit var textYemekFiyat: TextView
    private lateinit var buttonMinus: Button
    private lateinit var buttonPlus: Button
    private lateinit var textAdet: TextView
    private lateinit var buttonSepeteEkle: Button
    private lateinit var textToplamFiyat: TextView

    private var yemekAdi = ""
    private var yemekResimAdi = ""
    private var yemekFiyat = 0
    private var adet = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DetayViewModel::class.java]

        initViews(view)
        readArguments()
        setupUI()
        observeViewModel()
    }

    private fun initViews(view: View) {
        imageYemek = view.findViewById(R.id.imageYemek)
        textYemekAdi = view.findViewById(R.id.textYemekAdi)
        textYemekFiyat = view.findViewById(R.id.textYemekFiyat)
        buttonMinus = view.findViewById(R.id.buttonMinus)
        buttonPlus = view.findViewById(R.id.buttonPlus)
        textAdet = view.findViewById(R.id.textAdet)
        buttonSepeteEkle = view.findViewById(R.id.buttonSepeteEkle)
        textToplamFiyat = view.findViewById(R.id.textToplamFiyat)
    }

    private fun readArguments() {
        arguments?.let { bundle ->
            yemekAdi = bundle.getString("yemek_adi", "")
            yemekResimAdi = bundle.getString("yemek_resim_adi", "")
            yemekFiyat = bundle.getString("yemek_fiyat", "0").toInt()
        }
    }

    private fun setupUI() {
        textYemekAdi.text = yemekAdi
        textYemekFiyat.text = "$yemekFiyat ₺"
        textAdet.text = adet.toString()
        updateToplamFiyat()

        Glide.with(this)
            .load("${Constants.IMAGE_BASE_URL}$yemekResimAdi")
            .into(imageYemek)

        buttonMinus.setOnClickListener {
            if (adet > 1) {
                adet--
                textAdet.text = adet.toString()
                updateToplamFiyat()
            }
        }

        buttonPlus.setOnClickListener {
            adet++
            textAdet.text = adet.toString()
            updateToplamFiyat()
        }

        buttonSepeteEkle.setOnClickListener {
            viewModel.sepeteEkle(yemekAdi, yemekResimAdi, yemekFiyat, adet)
        }
    }

    private fun observeViewModel() {
        viewModel.sepeteEklendi.observe(viewLifecycleOwner) { eklendi ->
            if (eklendi) {
                Toast.makeText(context, "Sepete eklendi!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateToplamFiyat() {
        val toplam = yemekFiyat * adet
        textToplamFiyat.text = "Toplam: $toplam ₺"
    }
}
package com.example.firebasepraktikum.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.firebasepraktikum.ModelData.DetailSiswa
import com.example.firebasepraktikum.ModelData.UIStateSiswa
import com.example.firebasepraktikum.ModelData.toDataSiswa
import com.example.firebasepraktikum.Repository.RepositorySiswa

class EntryViewModel(private val repositorySiswa: RepositorySiswa): ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa ): Boolean
    {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput
                (detailSiswa))
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun addSiswa() {
        if (validasiInput()) {
            repositorySiswa.postDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
        }
    }
}
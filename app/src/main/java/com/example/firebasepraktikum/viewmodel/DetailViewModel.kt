package com.example.firebasepraktikum.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasepraktikum.ModelData.Siswa
import com.example.firebasepraktikum.Repository.RepositorySiswa
import com.example.firebasepraktikum.view.Route.DestinasiDetail
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface StatusUIDetail {
    data class Success(val satusiswa: Siswa?) : StatusUIDetail
    object Error : StatusUIDetail
    object loading : StatusUIDetail
}
class DetailViewModel(savedStateHandle: SavedStateHandle, private val repositorySiswa: RepositorySiswa):
    ViewModel() {

    private val idSiswa: Long =
        savedStateHandle.get<String>(DestinasiDetail.itemIdArg)?.toLong()
            ?: error("idSiswa tidak ditemukan di SavedStateHandle")

    var statusUIDetail:StatusUIDetail by mutableStateOf(StatusUIDetail.loading)
        private set

    init {
        getSatuSiswa()
    }
    fun getSatuSiswa(){
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.loading
            statusUIDetail = try {
                StatusUIDetail.Success(satusiswa = repositorySiswa.getSatuSiswa(idSiswa) )
            }
            catch (e: IOException){
                StatusUIDetail.Error
            }
            catch (e: Exception){
                StatusUIDetail.Error
            }
        }
    }
    fun getSatuSiswa(){
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.loading
            statusUIDetail = try {
                StatusUIDetail.Success(satusiswa = repositorySiswa.getSatuSiswa(idSiswa) )
            }
            catch (e: IOException){
                StatusUIDetail.Error
            }
            catch (e: Exception){
                StatusUIDetail.Error
            }
        }
    }
    suspend fun hapusSatuSiswa() {
        try {
            repositorySiswa.hapusSatuSiswa(idSiswa)
            println("Sukses Hapus Data: $idSiswa")
        } catch (e: Exception) {
            println("Gagal Hapus Data: ${e.message}")
        }
    }
}
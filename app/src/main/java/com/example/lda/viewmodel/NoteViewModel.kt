package com.example.lda.viewmodel

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NoteViewModel:ViewModel() {

    val noteText=MutableLiveData<String>()

    fun addNote(note:String){
        noteText.value=note
    }

    fun clearNote(){
        noteText.value=""

    }

}
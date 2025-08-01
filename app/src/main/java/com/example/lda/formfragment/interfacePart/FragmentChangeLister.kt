package com.example.lda.formfragment.interfacePart

import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

interface FragmentChangeLister {
    fun replaceWith(fragment: Fragment,nextCardView: String)
}
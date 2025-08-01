package com.example.lda.formfragment.others

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.lda.R
import com.example.lda.utils.createCardGrid

class OthersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_others)

        val rootLayout = findViewById<LinearLayout>(R.id.containerLayout)
        val typeface = ResourcesCompat.getFont(this, R.font.poppins_regular)

        val cardDataList = listOf(
            Pair(R.drawable.construction, resources.getString(R.string.construction)),
            Pair(R.drawable.noc_water, resources.getString(R.string.noc_for_water)),
            Pair(R.drawable.restoretion_plote, resources.getString(R.string.restoretion_plote)),
            Pair(R.drawable.merger_plot, resources.getString(R.string.merge_plot)),
            Pair(R.drawable.time_extention, resources.getString(R.string.time_extention)),
            Pair(R.drawable.change_land_use, resources.getString(R.string.change_land_use)),
            Pair(R.drawable.no_dues_certificate, resources.getString(R.string.no_due_certificate)),
            Pair(R.drawable.re_allotment_letter, resources.getString(R.string.re_allotment_letter)),
            Pair(R.drawable.strip_land, resources.getString(R.string.strip_land)),
            Pair(R.drawable.approval_building_plan, resources.getString(R.string.approval_of_building_plan)),
        )

        createCardGrid(rootLayout, cardDataList, typeface = typeface)

    }

}
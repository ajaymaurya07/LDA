package com.example.lda


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import com.example.lda.utils.createCardGrid


class ServicesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root=inflater.inflate(R.layout.fragment_service, container, false)

        val rootLayout = root.findViewById<LinearLayout>(R.id.containerLayout)
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)


        val cardDataList = listOf(
            Pair(R.drawable.mutation_img, resources.getString(R.string.mutation_reason)),
            Pair(R.drawable.freehold_certificate, resources.getString(R.string.lease_exemption_certificate_freehold)),
            Pair(R.drawable.lease_collection_yearly, resources.getString(R.string.lease_collection_yearly)),
            Pair(R.drawable.payment_against_challan, resources.getString(R.string.payment_against_demand_note_challan)),
            Pair(R.drawable.property_reconstitution, resources.getString(R.string.property_subdivision_and_reconstitution)),
            Pair(R.drawable.permision_martgage, resources.getString(R.string.permission_to_martgage)),

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
            Pair(R.drawable.parking, resources.getString(R.string.parking)),
        )

        requireContext().createCardGrid(rootLayout, cardDataList, typeface = typeface)
        return root
    }


}
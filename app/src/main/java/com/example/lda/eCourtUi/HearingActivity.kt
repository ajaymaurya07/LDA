package com.example.lda.eCourtUi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lda.R
import com.example.lda.constent.Constent
import com.example.lda.databinding.ActivityHearingBinding
import com.example.lda.eCourtUi.utils.CaseDetailsHelper
import com.example.lda.eCourtUi.utils.ProgressBar
import com.example.lda.eCourtUi.utils.SharedPrefHelper
import com.example.lda.eCourtUi.utils.SystemBarsHelper.applySafeAreaInsets
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.viewmodel.BasicDetailsViewModel
import com.example.lda.viewmodel.CaseStatusViewModel
import com.example.lda.viewmodel.CategoryDetailsViewModel
import com.example.lda.viewmodel.CourtDetailsViewModel
import com.example.lda.viewmodel.HearingDetailsViewModel
import com.example.lda.viewmodel.HearingHistoryViewModel
import com.example.lda.viewmodel.IADetailsViewModel
import com.example.lda.viewmodel.InterimOrderViewModel
import com.example.lda.viewmodel.LegalDetailsViewModel
import com.example.lda.viewmodel.LoginViewModel
import com.example.lda.viewmodel.NoteViewModel
import com.example.lda.viewmodel.PetitionerAdvocateViewModel
import com.example.lda.viewmodel.RespondentAndAdvocateViewmodel
import com.example.lda.viewmodel.SubOrdinateCaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class HearingActivity : AppCompatActivity() {
    lateinit var binding: ActivityHearingBinding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var basicDetailsViewModel: BasicDetailsViewModel
    private lateinit var hearingDetailsViewModel: HearingDetailsViewModel
    private lateinit var petitionerAdvocateViewModel: PetitionerAdvocateViewModel
    private lateinit var respondentAndAdvocateViewmodel: RespondentAndAdvocateViewmodel
    private lateinit var hearingHistoryViewModel: HearingHistoryViewModel
    private lateinit var interimOrderViewModel: InterimOrderViewModel
    private lateinit var courtDetailsViewModel: CourtDetailsViewModel
    private lateinit var legalDetailsViewModel: LegalDetailsViewModel
    private lateinit var caseStatusViewModel: CaseStatusViewModel
    private lateinit var categoryDetailsViewModel: CategoryDetailsViewModel
    private lateinit var iADetailsViewModel: IADetailsViewModel
    private lateinit var subOrdinateCaseViewModel: SubOrdinateCaseViewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var progressDialog: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_hearing)
        viewModel= ViewModelProvider(this)[LoginViewModel::class.java]
        progressDialog = ProgressBar(this)

        // shared preference find data
        val userId = SharedPrefHelper.getUserId(this) ?: ""
        val userType = SharedPrefHelper.getUserType(this) ?: ""
        val department = SharedPrefHelper.getDepartment(this) ?: ""

        // item click from adapter to get cnr number
        val cnrNumber = intent.getStringExtra("cnrNumber") ?: ""

        binding.toolBar.title = "CNR Number: $cnrNumber"

        // call case details api related cnr number
        viewModel.getCaseDetailsByCnr(userId,Constent.VERSION,userType,department,cnrNumber)
        caseDetailsObserver()



        applySafeAreaInsets(
            rootView = binding.root,
            toolbar = binding.topAppBar,
            statusBarColor = getColor(R.color.primary),
            lightStatusBar = false,
            )

        binding.toolBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // add note
//        addNote()
        basicDetails()
        hearingDetails()
        courtDetails()
        legalDetails()
        caseStatus()
        categoryDetails()
        iADetails()
        hearingHistory()
        interimOrder()
        petitionerNameAndAdvocate()
        respondentNameAndAdvocate()
        subOrdinateCase()
    }


    private fun caseDetailsObserver(){
        viewModel.caseDetailsByCnrListData.observe(this) { response ->
            if (response.statusCode == "200") {
                val caseDetails = response.result
                // Step 1: Create map via mapper
                val basicDetailsMap= caseDetails?.let { CaseDetailsHelper.mapBasicDetails(it) }
                val hearingDetailsMap= caseDetails?.let { CaseDetailsHelper.mapHearingDetails(it) }
                val courtDetailsMap= caseDetails?.let { CaseDetailsHelper.mapCourtDetails(it) }
                val legalDetailsMap= caseDetails?.let { CaseDetailsHelper.mapLegalDetails(it) }
                val caseStatusMap= caseDetails?.let { CaseDetailsHelper.mapCaseStatus(it) }
                val categoryDetailsMap= caseDetails?.let { CaseDetailsHelper.mapCategoryDetails(it) }


                val iaList = caseDetails?.iaDetails
                val headerRow = listOf("IA Number", "Party", "Date of Filing", "Next Date", "IA Status","Classification")

                val dataRows: List<List<String>> = iaList
                    ?.mapNotNull { item ->
                        item?.let { CaseDetailsHelper.mapIaDetails(it) }
                    } ?: emptyList()

                val finalTable: List<List<String>> = listOf(headerRow) + dataRows

                CaseDetailsHelper.populateTable(this, binding.iaDetailsTable, finalTable)


                val hearingHistory = caseDetails?.historyOfCaseHearing
                val headerRowHearingHistory = listOf("Cause List Type", "Judge", "Business Of Date", "Hearing Date", "Purpose")

                val dataRowsHearingHistory: List<List<String>> = hearingHistory?.mapNotNull {
                    it?.let { item->
                        CaseDetailsHelper.mapHearingHistory(item)
                    } } ?: emptyList()

                val finalTableHearingHistory: List<List<String>> = listOf(headerRowHearingHistory) + dataRowsHearingHistory
                CaseDetailsHelper.populateTable(this,binding.hearingHistoryTable,finalTableHearingHistory)


                val interimOrder = caseDetails?.interimOrder
                val headerRowInterimOrder = listOf("Order Number", "Order Date", "Order Details")

                val dataRowInterimOrder :List<List<String>> = interimOrder?.mapNotNull {
                    it?.let { item->
                        CaseDetailsHelper.mapInterimOrder(item)
                    }
                } ?: emptyList()

                val finalTableInterimOrder:List<List<String>>  = listOf(headerRowInterimOrder) + dataRowInterimOrder
                CaseDetailsHelper.populateInterimOrderTable(this,binding.interimOrderTable,finalTableInterimOrder)



                val petitionerAndAdvocateMap= caseDetails?.let { CaseDetailsHelper.mapPetitionerAndAdvocate(it) }
                val respondentAndAdvocateMap= caseDetails?.let { CaseDetailsHelper.mapRespondentAndAdvocate(it) }
                val subOrdinateCaseMap= caseDetails?.let { CaseDetailsHelper.mapSubOrdinateCourt(it) }

                // Step 2: Populate UI via helper
                if (basicDetailsMap != null) {
                    CaseDetailsHelper.populateBasicDetailsTable(this, binding.basicDetailsTable, basicDetailsMap)
                }
                if (hearingDetailsMap!=null){
                    CaseDetailsHelper.populateBasicDetailsTable(this,binding.hearingDetailsTable,hearingDetailsMap)
                }
                if (courtDetailsMap!= null){
                    CaseDetailsHelper.populateBasicDetailsTable(this,binding.courtDetailsTable,courtDetailsMap)
                }
                if (legalDetailsMap!= null){
                    CaseDetailsHelper.populateBasicDetailsTable(this,binding.legalDetailsTable,legalDetailsMap)
                }
                if (caseStatusMap!= null){
                    CaseDetailsHelper.populateBasicDetailsTable(this,binding.caseStatusTable,caseStatusMap)
                }
                if (categoryDetailsMap!= null){
                    CaseDetailsHelper.populateBasicDetailsTable(this,binding.categoryDetailsTable,categoryDetailsMap)
                }
                if (petitionerAndAdvocateMap!= null){
                    CaseDetailsHelper.populateBasicDetailsTable(this,binding.petitionerAndAdvocateTable,petitionerAndAdvocateMap)
                }
                if (respondentAndAdvocateMap!= null){
                    CaseDetailsHelper.populateBasicDetailsTable(this,binding.respondentAndAdvocateTable,respondentAndAdvocateMap)
                }
                if (subOrdinateCaseMap!= null){
                    CaseDetailsHelper.populateBasicDetailsTable(this,binding.subOrdinateCourtTable,subOrdinateCaseMap)
                }

            } else{
                response.statusMessage?.let {
                    AlertDialogHelper.showAlertDialog(
                        this,
                        "Alert Message",
                        it,
                        "Ok", { dialog, which ->
                            dialog.dismiss()
                        },
                        "Cancel", { dialog, which ->
                            dialog.dismiss()
                        }
                    )
                }
            }
        }
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading){
                progressDialog.startLoadingDialog("Loading Data......")
            }else{
                progressDialog.dismissDialog()
            }
        }
    }


    private fun basicDetails() {
        basicDetailsViewModel = ViewModelProvider(this)[BasicDetailsViewModel::class.java]
        basicDetailsViewModel.toggleData(true)

        basicDetailsViewModel.isOpened.observe(this) {
            if (it) {
                binding.basicDetailsTable.visibility = View.VISIBLE
                binding.basicDetailsLabelLl.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.basicDetailsTable.visibility = View.GONE
                binding.basicDetailsLabelLl.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }

        }


        binding.basicDetailsLabelLl.setOnClickListener {
            if (basicDetailsViewModel.isOpened.value == true) {
                basicDetailsViewModel.toggleData(false)
            } else {
                basicDetailsViewModel.toggleData(true)
            }
        }


    }

    private fun hearingDetails() {
        hearingDetailsViewModel = ViewModelProvider(this)[HearingDetailsViewModel::class.java]
        hearingDetailsViewModel.toggleData(false)

        hearingDetailsViewModel.isOpened.observe(this) {
            if (it) {
                binding.hearingDetailsTable.visibility = View.VISIBLE
                binding.hearingDetailsLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.hearingDetailsTable.visibility = View.GONE
                binding.hearingDetailsLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        }

        binding.hearingDetailsLabelll.setOnClickListener {
            if (hearingDetailsViewModel.isOpened.value == true) {
                hearingDetailsViewModel.toggleData(false)
            } else {
                hearingDetailsViewModel.toggleData(true)
            }
        }

    }

    private fun courtDetails() {
        courtDetailsViewModel=ViewModelProvider(this)[CourtDetailsViewModel::class.java]
        courtDetailsViewModel.toggleData(false)

        courtDetailsViewModel.isOpened.observe(this, Observer {
            if (it) {
                binding.courtDetailsTable.visibility = View.VISIBLE
                binding.courtDetailsLabelLl.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.courtDetailsTable.visibility = View.GONE
                binding.courtDetailsLabelLl.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })

        binding.courtDetailsLabelLl.setOnClickListener {
            if (courtDetailsViewModel.isOpened.value == true) {
                courtDetailsViewModel.toggleData(false)
            } else {
                courtDetailsViewModel.toggleData(true)
            }
        }


    }

    private fun legalDetails(){
        legalDetailsViewModel= ViewModelProvider(this)[LegalDetailsViewModel::class.java]
        legalDetailsViewModel.toggleData(false)

        legalDetailsViewModel.isOpened.observe(this, Observer {
            if (it) {
                binding.legalDetailsTable.visibility = View.VISIBLE
                binding.legalDetailsLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.legalDetailsTable.visibility = View.GONE
                binding.legalDetailsLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })
        binding.legalDetailsLabelll.setOnClickListener {
            if (legalDetailsViewModel.isOpened.value == true) {
                legalDetailsViewModel.toggleData(false)
            } else {
                legalDetailsViewModel.toggleData(true)
            }
        }

    }

    private fun caseStatus(){
        caseStatusViewModel=ViewModelProvider(this)[CaseStatusViewModel::class.java]
        caseStatusViewModel.toggleData(false)

        caseStatusViewModel.isOpened.observe(this, Observer {
            if (it) {
                binding.caseStatusTable.visibility = View.VISIBLE
                binding.caseStatusLabelLl.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.caseStatusTable.visibility = View.GONE
                binding.caseStatusLabelLl.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })
        binding.caseStatusLabelLl.setOnClickListener {
            if (caseStatusViewModel.isOpened.value == true) {
                caseStatusViewModel.toggleData(false)
            } else {
                caseStatusViewModel.toggleData(true)
            }
        }

    }

    private fun categoryDetails(){
        categoryDetailsViewModel=ViewModelProvider(this)[CategoryDetailsViewModel::class.java]
        categoryDetailsViewModel.toggleData(false)

        categoryDetailsViewModel.isOpened.observe(this, Observer {
            if (it) {
                binding.categoryDetailsTable.visibility = View.VISIBLE
                binding.categoryDetailsTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.categoryDetailsTable.visibility = View.GONE
                binding.categoryDetailsTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })
        binding.categoryDetailsTableLabelll.setOnClickListener {
            if (categoryDetailsViewModel.isOpened.value == true) {
                categoryDetailsViewModel.toggleData(false)
            } else {
                categoryDetailsViewModel.toggleData(true)
            }
        }
    }

    private fun iADetails(){
        iADetailsViewModel=ViewModelProvider(this)[IADetailsViewModel::class.java]
        iADetailsViewModel.toggleData(false)

        iADetailsViewModel.isOpened.observe(this, Observer {
            if (it) {
                binding.iaDetailsTable.visibility = View.VISIBLE
                binding.iaDetailsTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.iaDetailsTable.visibility = View.GONE
                binding.iaDetailsTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })
        binding.iaDetailsTableLabelll.setOnClickListener {
            if (iADetailsViewModel.isOpened.value == true) {
                iADetailsViewModel.toggleData(false)
            } else {
                iADetailsViewModel.toggleData(true)
            }
        }
    }

    private fun hearingHistory(){
        hearingHistoryViewModel=ViewModelProvider(this)[HearingHistoryViewModel::class.java]
        hearingHistoryViewModel.toggleData(false)

        hearingHistoryViewModel.isOpened.observe(this, Observer {
            if (it) {
                binding.hearingHistoryTable.visibility = View.VISIBLE
                binding.hearingHistoryTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.hearingHistoryTable.visibility = View.GONE
                binding.hearingHistoryTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })
        binding.hearingHistoryTableLabelll.setOnClickListener {
            if (hearingHistoryViewModel.isOpened.value == true) {
                hearingHistoryViewModel.toggleData(false)
            } else {
                hearingHistoryViewModel.toggleData(true)
            }
        }
    }

    private fun interimOrder(){
        interimOrderViewModel=ViewModelProvider(this)[InterimOrderViewModel::class.java]
        interimOrderViewModel.toggleData(false)

        interimOrderViewModel.isOpened.observe(this, Observer {
            if (it) {
                binding.interimOrderTable.visibility = View.VISIBLE
                binding.interimOrderTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.interimOrderTable.visibility = View.GONE
                binding.interimOrderTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })
        binding.interimOrderTableLabelll.setOnClickListener {
            if (interimOrderViewModel.isOpened.value == true) {
                interimOrderViewModel.toggleData(false)
            } else {
                interimOrderViewModel.toggleData(true)
            }
        }
    }

    private fun petitionerNameAndAdvocate(){
        petitionerAdvocateViewModel=ViewModelProvider(this)[PetitionerAdvocateViewModel::class.java]
        petitionerAdvocateViewModel.toggleData(false)

        petitionerAdvocateViewModel.isOpened.observe(this, Observer {
            if (it) {
                binding.petitionerAndAdvocateTable.visibility = View.VISIBLE
                binding.petitionerAndAdvocateTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.petitionerAndAdvocateTable.visibility = View.GONE
                binding.petitionerAndAdvocateTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })
        binding.petitionerAndAdvocateTableLabelll.setOnClickListener {
            if (petitionerAdvocateViewModel.isOpened.value == true) {
                petitionerAdvocateViewModel.toggleData(false)
            } else {
                petitionerAdvocateViewModel.toggleData(true)
            }
        }
    }

    private fun respondentNameAndAdvocate(){
        respondentAndAdvocateViewmodel=ViewModelProvider(this)[RespondentAndAdvocateViewmodel::class.java]
        respondentAndAdvocateViewmodel.toggleData(false)

        respondentAndAdvocateViewmodel.isOpened.observe(this, Observer {
            if (it) {
                binding.respondentAndAdvocateTable.visibility = View.VISIBLE
                binding.respondentAndAdvocateTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.respondentAndAdvocateTable.visibility = View.GONE
                binding.respondentAndAdvocateTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })
        binding.respondentAndAdvocateTableLabelll.setOnClickListener {
            if (respondentAndAdvocateViewmodel.isOpened.value == true) {
                respondentAndAdvocateViewmodel.toggleData(false)
            } else {
                respondentAndAdvocateViewmodel.toggleData(true)
            }
        }
    }

    private fun subOrdinateCase(){
        subOrdinateCaseViewModel=ViewModelProvider(this)[SubOrdinateCaseViewModel::class.java]
        subOrdinateCaseViewModel.toggleData(false)

        subOrdinateCaseViewModel.isOpened.observe(this, Observer {
            if (it) {
                binding.subOrdinateCourtTable.visibility = View.VISIBLE
                binding.subOrdinateCourtTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.primary
                    )
                )
            } else {
                binding.subOrdinateCourtTable.visibility = View.GONE
                binding.subOrdinateCourtTableLabelll.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.secondary
                    )
                )
            }
        })
        binding.subOrdinateCourtTableLabelll.setOnClickListener {
            if (subOrdinateCaseViewModel.isOpened.value == true) {
                subOrdinateCaseViewModel.toggleData(false)
            } else {
                subOrdinateCaseViewModel.toggleData(true)
            }
        }
    }





    private fun addNote() {
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        noteViewModel.addNote("")

        noteViewModel.noteText.observe(this) { text ->
            binding.noteText.text = text

            if (!text.isNullOrEmpty()) {
                binding.noteTextLl.visibility = View.VISIBLE
                binding.addNoteLabelLl.visibility = View.GONE
            } else {
                binding.noteTextLl.visibility = View.GONE
                binding.addNoteLabelLl.visibility = View.VISIBLE
            }
        }
        binding.btnEdit.setOnClickListener {
            editDialog()
        }
        binding.btnDelete.setOnClickListener {
            noteViewModel.clearNote()
        }
        binding.addNoteLabelLl.setOnClickListener {
            editDialog()
        }
    }
    private fun editDialog() {
        val dialogView = layoutInflater.inflate(R.layout.add_note_dialog, null)

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogView)
        // Keyboard handling
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val edittext = dialog.findViewById<EditText>(R.id.edittext)
        val close = dialog.findViewById<ImageView>(R.id.close)
        val add = dialog.findViewById<ImageView>(R.id.add)

        edittext?.setText(noteViewModel.noteText.value.toString())

        close?.setOnClickListener {
            dialog.dismiss()
        }
        add?.setOnClickListener {
            noteViewModel.addNote(edittext?.text.toString())
            dialog.dismiss()
        }

        dialog.show()
    }

}
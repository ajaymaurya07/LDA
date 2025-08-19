package com.example.lda.formfragment.mutation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ProgressBar
import com.example.lda.R
import com.example.lda.formfragment.interfacePart.FragmentChangeLister
import com.example.lda.server.GeminiRequest
import com.example.lda.server.GeminiResponse
import com.example.lda.server.GeminiService
import com.example.lda.utils.AlertDialogHelper
import com.example.lda.utils.DatePickerUtils
import com.example.lda.utils.phoneEmailValidater.InputValidator
import com.example.lda.utils.setupDropdown
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.log


class PropertyDetailsMutation : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_property_details_mutation, container, false)

        // Access the AutoCompleteTextView
        val dateEditText: EditText = view.findViewById(R.id.final_registary_date)
        val allotteePrefix: AutoCompleteTextView = view.findViewById(R.id.allottee_prefix)
        val landUses: AutoCompleteTextView = view.findViewById(R.id.land_use)
        val schemeName: AutoCompleteTextView = view.findViewById(R.id.scheme_name)
        val SaveNextBtn:MaterialButton = view.findViewById(R.id.save_next_btn)

        // required fields
        val allotteName=view.findViewById<EditText>(R.id.allottee_name)
        val fatherSpouseName=view.findViewById<EditText>(R.id.father_spouse_name)
        val phoneNumber=view.findViewById<EditText>(R.id.phone_number)
        val propertyNo=view.findViewById<EditText>(R.id.property_no)
        val areaOfPropertySQm=view.findViewById<EditText>(R.id.area_of_property)
        val coveredAreaSQM=view.findViewById<EditText>(R.id.covered_area_sqm)
        val floor=view.findViewById<EditText>(R.id.floor)
        val currentPropertyValue=view.findViewById<EditText>(R.id.current_property_value)
        val outStandingAmount=view.findViewById<EditText>(R.id.outstanding_amount)
        val noOfIntermediateSales=view.findViewById<EditText>(R.id.no_of_intermediate_sales)
        val emaiId=view.findViewById<EditText>(R.id.email_id)

        // no required fields
        val daPropertyCode=view.findViewById<EditText>(R.id.da_property_code)
        val postelAddress=view.findViewById<EditText>(R.id.postal_address)
        val block=view.findViewById<AutoCompleteTextView>(R.id.sector_block)

        // validation
        var isPhoneValid = true
        var isEmailValid = true
        var isAlloteeNameValid = true
        var isFatherSpouseNameValid = true
        var isPostalAddressValid = true



        phoneNumber.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                if (input.isNotEmpty()) {
                    if (!InputValidator.isValidPhone(input)) {
                        isPhoneValid=false
                        phoneNumber.error = "Invalid Phone Number"
                    } else {
                        isPhoneValid=true
                        phoneNumber.error = null
                    }
                }
            }

        })

        emaiId.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val input = emaiId.text.toString().trim()
                if (input.isNotEmpty()) {
                    validateEmailWithGemini(input) { isValid ->
                        if (isValid) {
                            emaiId.error = null
                            isEmailValid=true
                        } else {
                            isEmailValid=false
                            emaiId.error = "Invalid Email Id!"
                        }
                    }
                }
            }
        }

        allotteName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val input = allotteName.text.toString().trim()

                if (input.isNotEmpty()) {

                    validateNameWithGemini(input) { isValid ->
                        Log.d("TAG", "onCreateView: $input $isValid")
                        if (isValid) {
                            allotteName.error = null
                            isAlloteeNameValid=true
                        } else {

                            isAlloteeNameValid=false
                            allotteName.error = "Not a valid human name!"
                        }

                    }
                }
            }
        }
        fatherSpouseName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val input = fatherSpouseName.text.toString().trim()
                if (input.isNotEmpty()) {
                    validateNameWithGemini(input) { isValid ->
                        if (isValid) {
                            fatherSpouseName.error = null
                            isFatherSpouseNameValid=true
                        } else {
                            isFatherSpouseNameValid=false
                            Log.d("TAG", "onCreateView: $isFatherSpouseNameValid")
                            fatherSpouseName.error = "Not a valid human name!"
                        }
                    }
                }
            }
        }

        postelAddress.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val address = postelAddress.text.toString().trim()
                if (address.isNotEmpty()) {
                    validateAddressWithGemini(address) { isValid ->
                        if (!isValid) {
                            isPostalAddressValid=false
                            postelAddress.error = "Invalid Address!"
                        } else {
                            postelAddress.error = null
                            isPostalAddressValid=true
                        }
                    }
                }
            }
        }




        // Set options in dropdown
        val allotteePrefixList = listOf("Mr.", "Miss.", "Smt.","Shri.","Lt.")
        val blockList = listOf("block A", "Block B", "Block C","Block D")
        val landUseList = listOf("Residential", "Commercial", "Office", "Community", "Transport", "Plot", "Kiosk", "House", "Shop", "Other Open Area", "Amusements And Parks", "Industries", "Bazaar Street", "Viniyamatikaran", "Mishrit(Mixed)", "Agriculture area", "Baadh Prabhavit Kshetra", "Jalashay", "Chak road & chak naali", "School (Saikshik)", "Kuda Nistaran kendra", "No Construction Zone", "Nagariya nirmit kshetra", "Services", "Aarakshit ksehtra", "Kendriya Kriyaye", "Sansthagat", "Samudayik kendra")
        val schemeNameList = listOf(
            "swarn jaynti nagar vistaar",
            "pala road kashi ram nagar yojna",
            "Swarn jaynti nagar yojna",
            "Lucknow Road Vikas Nagar Yojna",
            "shanti niketan avasiya yojna",
            "avantika Phase-2",
            "Avantika Phase-1",
            "Brij Vihar Yojna",
            "Transport Nagar Phase-1"
        )



        setupDropdown(requireContext(), allotteePrefix, allotteePrefixList)
        setupDropdown(requireContext(), landUses, landUseList)
        setupDropdown(requireContext(), schemeName, schemeNameList)
        setupDropdown(requireContext(), block, blockList)



        DatePickerUtils.attachDatePicker(requireContext(), dateEditText)


        SaveNextBtn.setOnClickListener {

            val phoneText = phoneNumber.text.toString()
            val emailText = emaiId.text.toString()

//            val isValidPhoneNumber = InputValidator.isValidPhone(phoneText)
//            val isValidMailId = InputValidator.isValidEmail(emailText)


            val check = requiredFieldCheck(
                allotteName.text.toString(),
                fatherSpouseName.text.toString(),
                phoneText,
                propertyNo.text.toString(),
                areaOfPropertySQm.text.toString(),
                coveredAreaSQM.text.toString(),
                floor.text.toString(),
                currentPropertyValue.text.toString(),
                outStandingAmount.text.toString(),
                noOfIntermediateSales.text.toString(),
                allotteePrefix.text.toString(),
                landUses.text.toString(),
                schemeName.text.toString(),
                emailText
            )

            fun showErrorDialog(message: String) {
                AlertDialogHelper.showAlertDialog(
                    requireContext(),
                    "Alert Message",
                    message,
                    "Ok", { dialog, _ -> dialog.dismiss() },
                    "Cancel", { dialog, _ -> dialog.dismiss() }
                )
            }

            if (allotteName.text.toString().isNotEmpty() && !isAlloteeNameValid){
                showErrorDialog("Allottee Name Invalid!")
                return@setOnClickListener
            }
            if (fatherSpouseName.text.toString().isNotEmpty() && !isFatherSpouseNameValid){
                showErrorDialog("Father/Spouse Name Invalid!")
                return@setOnClickListener
            }
            if (phoneText.isNotEmpty() && !isPhoneValid) {
                showErrorDialog("Phone Number Invalid!")
                return@setOnClickListener
            }
            if (emailText.isNotEmpty() && !isEmailValid) {
                showErrorDialog("Email Id Invalid!")
                return@setOnClickListener
            }

            if (postelAddress.text.toString().isNotEmpty() && !isPostalAddressValid){
                showErrorDialog("Postal Address Invalid!")
                return@setOnClickListener
            }

            if (check) {
                showErrorDialog("Plz Add All Mandatory(*) Fields Data!")
                return@setOnClickListener
            }
            (activity as? FragmentChangeLister)
                ?.replaceWith(SupportingDocument(), "supportingDocumentCardView")
        }

        return view
    }



    fun requiredFieldCheck(allotteeName:String,fatherSpouseName:String,phoneNumber:String,
                           propertyNo:String,areaOfPropertySQm:String,coveredAreaSQM:String,
                           floor:String,currentPropertyValue:String,outStandingAmount:String,
                           noOfIntermediateSales:String,allotteePrefix:String,landUses:String,
                           schemeName:String,emaiId:String):Boolean{

        if(allotteeName.isEmpty() || fatherSpouseName.isEmpty() || phoneNumber.isEmpty() ||
            propertyNo.isEmpty() || areaOfPropertySQm.isEmpty() || coveredAreaSQM.isEmpty() ||
            floor.isEmpty() || currentPropertyValue.isEmpty() || outStandingAmount.isEmpty() ||
            noOfIntermediateSales.isEmpty() || allotteePrefix.isEmpty() || landUses.isEmpty() ||
            emaiId.isEmpty() || schemeName.isEmpty()){
            return true
        }
        return false

    }

    fun validateNameWithGemini(name: String, onResult: (Boolean) -> Unit) {
        val prompt = """
        Check if "$name" is a real human personal name (like Indian or English names).
        Respond with only one word: YES if valid, NO if invalid.
    """.trimIndent()

        val body = GeminiRequest(
            contents = listOf(
                mapOf("parts" to listOf(mapOf("text" to prompt)))
            )
        )

        val call = GeminiService.api.validateName(body)
        call.enqueue(object : retrofit2.Callback<GeminiResponse> {
            override fun onResponse(
                call: retrofit2.Call<GeminiResponse>,
                response: retrofit2.Response<GeminiResponse>
            ) {
                if (response.isSuccessful) {
                    val text = response.body()
                        ?.candidates?.firstOrNull()
                        ?.get("content").toString()

                    val isValid = text.contains("YES", ignoreCase = true)
                    onResult(isValid)
                } else {
                    onResult(false)
                }
            }

            override fun onFailure(call: retrofit2.Call<GeminiResponse>, t: Throwable) {
                onResult(false)
            }
        })
    }

    fun validateEmailWithGemini(email: String, onResult: (Boolean) -> Unit) {
        val prompt = "Check if '$email' is a valid and realistic email address. " +
                "Return YES if valid, NO otherwise."

        val body = GeminiRequest(
            contents = listOf(
                mapOf("parts" to listOf(mapOf("text" to prompt)))
            )
        )

        val call = GeminiService.api.validateName(body)  // same endpoint
        call.enqueue(object : retrofit2.Callback<GeminiResponse> {
            override fun onResponse(
                call: retrofit2.Call<GeminiResponse>,
                response: retrofit2.Response<GeminiResponse>
            ) {
                if (response.isSuccessful) {
                    val text = response.body()
                        ?.candidates?.firstOrNull()
                        ?.get("content").toString()

                    val isValid = text.contains("YES", ignoreCase = true)
                    onResult(isValid)
                } else {
                    onResult(false)
                }
            }

            override fun onFailure(call: retrofit2.Call<GeminiResponse>, t: Throwable) {
                onResult(false)
            }
        })
    }

    fun validateAddressWithGemini(address: String, onResult: (Boolean) -> Unit) {
        val prompt = "Check if '$address' is a valid human postal/residential address in India. " +
                "It should look like a real sector/block/house/street address, not random text. " +
                "Return YES if valid, NO otherwise."

        val body = GeminiRequest(
            contents = listOf(
                mapOf("parts" to listOf(mapOf("text" to prompt)))
            )
        )

        val call = GeminiService.api.validateName(body) // same endpoint
        call.enqueue(object : retrofit2.Callback<GeminiResponse> {
            override fun onResponse(
                call: retrofit2.Call<GeminiResponse>,
                response: retrofit2.Response<GeminiResponse>
            ) {
                if (response.isSuccessful) {
                    val text = response.body()
                        ?.candidates?.firstOrNull()
                        ?.get("content").toString()

                    val isValid = text.contains("YES", ignoreCase = true)
                    onResult(isValid)
                } else {
                    onResult(false)
                }
            }

            override fun onFailure(call: retrofit2.Call<GeminiResponse>, t: Throwable) {
                onResult(false)
            }
        })
    }


}


//        fatherSpouseName.addTextChangedListener(object :TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                val input = s.toString()
//                if (input.isNotEmpty()) {
//                    if (!InputValidator.isValidName(input)) {
//                        fatherSpouseName.error = "Invalid Name."
//                    } else {
//                        fatherSpouseName.error = null
//                    }
//                }
//            }
//
//        })
//        postelAddress.addTextChangedListener(object :TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                val input = s.toString()
//                if (input.isNotEmpty()) {
//                    if (!InputValidator.isValidPostalAddress(input)) {
//                        postelAddress.error = "Invalid Address."
//                    } else {
//                        postelAddress.error = null
//                    }
//                }
//            }
//
//        })
//        emaiId.addTextChangedListener(object :TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                val input = s.toString()
//                if (input.isNotEmpty()) {
//                    if (!InputValidator.isValidEmail(input)) {
//                        emaiId.error = "Invalid Email."
//                    } else {
//                        emaiId.error = null
//                    }
//                }
//            }
//
//        })



package com.techcognics.procuremasster.presentation.supplierprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcognics.procuremasster.data.remote.Category
import com.techcognics.procuremasster.data.remote.SubCategory
import com.techcognics.procuremasster.data.remote.Industry
import com.techcognics.procuremasster.data.remote.SubIndustry
import com.techcognics.procuremasster.data.remote.ItemProces
import com.techcognics.procuremasster.data.remote.Country
import com.techcognics.procuremasster.data.remote.State
import com.techcognics.procuremasster.data.remote.City
import com.techcognics.procuremasster.data.remote.CurrencyDetailsItem
import com.techcognics.procuremasster.data.repository.SupplierRepository
import com.techcognics.procuremasster.presentation.supplier.SupplierUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.firstOrNull

@HiltViewModel
class SupplierProfileViewModel @Inject constructor(
    private val repository: SupplierRepository
) : ViewModel() {

    // ---------- main profile ui state ----------
    private val _uiState = MutableStateFlow(SupplierUiState(isLoading = true))
    val uiState: StateFlow<SupplierUiState> = _uiState

    // ---------- dropdown lists ----------
    private val _industryList = MutableStateFlow<List<Industry>>(emptyList())
    val industryList: StateFlow<List<Industry>> = _industryList

    private val _subIndustryList = MutableStateFlow<List<SubIndustry>>(emptyList())
    val subIndustryList: StateFlow<List<SubIndustry>> = _subIndustryList

    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList

    private val _subCategoryList = MutableStateFlow<List<SubCategory>>(emptyList())
    val subCategoryList: StateFlow<List<SubCategory>> = _subCategoryList

    private val _itemProcessList = MutableStateFlow<List<ItemProces>>(emptyList())
    val itemProcessList: StateFlow<List<ItemProces>> = _itemProcessList

    private val _countryList = MutableStateFlow<List<Country>>(emptyList())
    val countryList: StateFlow<List<Country>> = _countryList

    private val _stateList = MutableStateFlow<List<State>>(emptyList())
    val stateList: StateFlow<List<State>> = _stateList

    private val _cityList = MutableStateFlow<List<City>>(emptyList())
    val cityList: StateFlow<List<City>> = _cityList

    private val _currencyList = MutableStateFlow<List<CurrencyDetailsItem>>(emptyList())
    val currencyList: StateFlow<List<CurrencyDetailsItem>> = _currencyList

    fun loadAll(userId: Long) {
        viewModelScope.launch {
            loadProfile(userId)
            loadDropdowns()
        }
    }

    private suspend fun loadProfile(userId: Long) {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val response = repository.getSupplierByUserId(userId)

            _uiState.value = SupplierUiState(
                isLoading = false,
                companyName = response.companyName.orEmpty(),
                userName = response.user?.login.orEmpty(),
                contactPerson = response.contactPerson.orEmpty(),
                email = response.user?.email.orEmpty(),
                mobileNumber = response.user?.mobileNumber.orEmpty(),
                phoneNumber = response.user?.phoneNumber.orEmpty(),
                industry = response.industry?.industryName.orEmpty(),
                subIndustry = response.subIndustry?.subIndustryName.orEmpty(),
                currency = response.currency.orEmpty(),
                website = response.website.orEmpty(),
                gstNumber = response.gstinNumber.orEmpty(),
                country = response.country?.name.orEmpty(),
                state = response.state?.name.orEmpty(),
                city = response.city?.name.orEmpty(),
                pinCode = response.pinCode.orEmpty(),
                companyAddress = response.companyAddress.orEmpty(),
                category = response.category?.categoryName.orEmpty(),
                subCategory = response.subCategory?.subCategoryName.orEmpty(),
                itemProcess = response.itemProcess
                    ?.joinToString(", ") { it.itemProcessName }
                    .orEmpty()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = e.message ?: "Something went wrong"
            )
        }
    }

    private suspend fun loadDropdowns() {
        try {
            _industryList.value = repository.getIndustryList()
            _subIndustryList.value = repository.getSubIndustryList()
            _categoryList.value = repository.getCategoryList()
            _subCategoryList.value = repository.getSubCategoryList()
            _itemProcessList.value = repository.getItemProcessList()
            _currencyList.value = repository.getCurrencyList()

            val countries = repository.getCountryList()
            _countryList.value = countries

            // 👇 Country.id is Int (adjust if name is different)
            val defaultCountryId: Int = countries.firstOrNull()?.id ?: 1
            _stateList.value = repository.getStateList(defaultCountryId)

            // 👇 State.id is Int (adjust if name is different)
            val defaultStateId: Int? = _stateList.value.firstOrNull()?.id
            if (defaultStateId != null) {
                _cityList.value = repository.getCityList(defaultStateId)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun onCountrySelected(countryName: String) {
        val country = _countryList.value.firstOrNull { it.name == countryName } ?: return
        viewModelScope.launch {
            // country.id is Int (change if your field is different)
            _stateList.value = repository.getStateList(country.id)
            _cityList.value = emptyList()
        }
    }

    fun onStateSelected(stateName: String) {
        val state = _stateList.value.firstOrNull { it.name == stateName } ?: return
        viewModelScope.launch {
            // state.id is Int (change if your field is different)
            _cityList.value = repository.getCityList(state.id)
        }
    }


    fun onCompanyAddressChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            companyAddress = newValue,
            companyAddressError = newValue.isBlank()
        )
    }





}






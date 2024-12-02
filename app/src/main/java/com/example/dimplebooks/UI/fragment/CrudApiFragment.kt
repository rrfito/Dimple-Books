//package com.example.dimplebooks.UI.fragment
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.activityViewModels
//import com.example.dimplebooks.UI.adapters.entertaimentBookAdapter
//import com.example.dimplebooks.UI.utils.ViewModelFactory
//import com.example.dimplebooks.UI.viewModel.productViewModel
//import com.example.dimplebooks.data.model.ProductPostRequest
//import com.example.dimplebooks.data.network.RetrofitInstance
//import com.example.dimplebooks.data.repository.ProductRepository
//import com.example.dimplebooks.databinding.FragmentCrudapiBinding
//import com.example.dimplebooks.utils.Resource
//import com.google.android.material.snackbar.Snackbar
//
//class CrudApiFragment : Fragment() {
//
//    private var _binding: FragmentCrudapiBinding? = null
//    private val binding get() = _binding!!
//
//    private val productViewModel: productViewModel by activityViewModels  {
//        ViewModelFactory(productViewModel::class.java) {
//            val repository = ProductRepository(RetrofitInstance.getCRUDApi)
//            productViewModel(repository)
//        }
//    }
//    private lateinit var adapter : entertaimentBookAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentCrudapiBinding.inflate(inflater, container, false)
//
//        adapter = entertaimentBookAdapter(mutableListOf())
//        binding.productList.adapter = adapter
//        binding
//        return binding.root
//    }
//
//    private fun getProduct(){
//        productViewModel.getProducts(requireContext())
//        productViewModel.data.observe(viewLifecycleOwner) { resource ->
//            when (resource) {
//                is Resource.Empty -> {
//                    Log.d("Data Product", "Data Kosong. (${resource.message})")
//                    binding.emptyProduct.root.visibility = View.VISIBLE
//                    binding.loadingProduct.root.visibility = View.GONE
//                    binding.errorProduct.root.visibility = View.GONE
//                    binding.productList.visibility = View.GONE
//
//                    binding.emptyProduct.emptyMessage.text = resource.message
//                }
//                is Resource.Error -> {
//                    Log.e("Data User", resource.message.toString())
//                    binding.emptyProduct.root.visibility = View.GONE
//                    binding.loadingProduct.root.visibility = View.GONE
//                    binding.errorProduct.root.visibility = View.VISIBLE
//                    binding.productList.visibility = View.GONE
//
//                    binding.errorProduct.errorMessage.text = resource.message
//
//                    binding.errorProduct.retryButton.setOnClickListener{
//                        productViewModel.getProducts(requireContext(),true)
//                    }
//                }
//                is Resource.Loading -> {
//                    Log.d("Data User", "Mohon Tunggu...")
//                    binding.emptyProduct.root.visibility = View.GONE
//                    binding.loadingProduct.root.visibility = View.VISIBLE
//                    binding.errorProduct.root.visibility = View.GONE
//                    binding.productList.visibility = View.GONE
//                }
//                is Resource.Success -> {
//                    Log.d("Data User", "Data berhasil didapatkan")
//                    binding.emptyProduct.root.visibility = View.GONE
//                    binding.loadingProduct.root.visibility = View.GONE
//                    binding.errorProduct.root.visibility = View.GONE
//                    binding.productList.visibility = View.VISIBLE
//
//                    val productItem = resource.data!!.items.mapIndexed { index, data ->
//                        NewsHorizontalModel(
//                            "https://images.unsplash.com/photo-1530224264768-7ff8c1789d79?w=1024",
//                            data.name
//                        )
//                    }
//                    adapter.updateData(productItem)
//                }
//            }
//        }
//    }
//    private fun createProduct() {
//        val name = "Buku"
//        val desc = "Ini Deskripsi Buku"
//        val price = 250000
//
//        val products = listOf(
//            ProductPostRequest(name = name, desription = desc, price = price)
//        )
//        productViewModel.createProduct(requireContext(), products)
//        productViewModel.createStatus.observe(viewLifecycleOwner) { resource ->
//            when (resource) {
//                is Resource.Loading -> {
//
//                }
//
//                is Resource.Success -> {
//
//                    Snackbar.make(
//                        binding.root,
//                        "Product created successfully!",
//                        Snackbar.LENGTH_SHORT
//                    ).show()
//                }
//
//                is Resource.Error -> {
//                    Snackbar.make(
//                        binding.root,
//                        resource.message ?: "Failed to create product.",
//                        Snackbar.LENGTH_LONG
//                    ).show()
//                }
//
//                else -> {}
//            }
//        }
//    }
//
//}

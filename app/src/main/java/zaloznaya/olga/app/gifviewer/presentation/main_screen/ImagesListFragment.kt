package zaloznaya.olga.app.gifviewer.presentation.main_screen

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.databinding.FragmentImagesListBinding
import zaloznaya.olga.app.gifviewer.presentation.adapters.ImagesListAdapter
import zaloznaya.olga.app.gifviewer.presentation.adapters.PaginationScrollListener
import zaloznaya.olga.app.gifviewer.utils.TAG

class ImagesListFragment : Fragment(R.layout.fragment_images_list) {

    private val viewModel by viewModel<ImagesListViewModel>()
    private val adapter = ImagesListAdapter()
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    // Google sign in
    private val gso by lazy { GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("617066506773-t93hnd81n81tk2ck4j7kj5ss702n7jec.apps.googleusercontent.com")
        .requestEmail()
        .build()
    }
    private val gsc by lazy {
        GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImagesListBinding
        .inflate(inflater, container, false)
        .apply {
            lifecycleOwner = this@ImagesListFragment
            lifecycleScope.launch {
                initRecyclerView(rvImages)
                initSearchView(searchView)
                btTestLogin.setOnClickListener {
                    val signInIntent: Intent = gsc.signInIntent
                    startActivityForResult(signInIntent, 1001)
                }
            }
            viewmodel = viewModel
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setViewModel(viewModel)
        viewModel.getImages().observe(viewLifecycleOwner) { list ->
//            Log.d(TAG, "LIST from API:")
//            list.forEach {
//                Log.d(TAG, "image = ${it.id}")
//            }
            isLoading = false
            adapter.setImagesList(list)
        }
    }

    private fun initRecyclerView(rv: RecyclerView) {
        val gridLayoutManager =
            if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(requireContext(), 3)
            } else {
                GridLayoutManager(requireContext(), 5)
            }

        rv.adapter = adapter
        rv.layoutManager = gridLayoutManager
        rv.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                viewModel.loadNextPage()
            }
        })
    }

    private fun initSearchView(sv: SearchView) {
        sv.setOnClickListener {
            sv.isIconified = false
            sv.requestFocusFromTouch()
        }
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.newSearchImages(query)
                Log.d(TAG, "SEARCH: $query")
                sv.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()) viewModel.reloadTrendingImages()
                return true
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("AUTH","AuthWithGoogle: ${account.idToken}")
                Log.d("AUTH","email: ${account.email}")
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e("AUTH","Google sign in failed - ${e.message}")
                Toast.makeText(requireContext(), "Google sign in failed - ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
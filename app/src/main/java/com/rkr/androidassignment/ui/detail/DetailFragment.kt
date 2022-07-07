package com.rkr.androidassignment.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.rkr.androidassignment.databinding.FragmentDetailBinding
import com.rkr.androidassignment.di.AppComponentProvider
import javax.inject.Inject

class DetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as? AppComponentProvider)?.getAppComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentDetailBinding = FragmentDetailBinding.inflate(inflater)
        fragmentDetailBinding.viewModel = viewModel
        fragmentDetailBinding.lifecycleOwner = this
        return fragmentDetailBinding.root
    }


}

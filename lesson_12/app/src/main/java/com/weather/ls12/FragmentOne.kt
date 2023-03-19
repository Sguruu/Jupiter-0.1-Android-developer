package com.weather.ls12

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentOne : Fragment(R.layout.fragment_one) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("MyTest", "FragmentOne onAttach ${hashCode()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MyTest", "FragmentOne onCreate ${hashCode()}")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyTest", "FragmentOne onCreateView ${hashCode()}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MyTest", "FragmentOne onViewCreated ${hashCode()}")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d("MyTest", "FragmentOne onViewStateRestored ${hashCode()}")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d("MyTest", "FragmentOne onStart ${hashCode()}")
        super.onStart()
    }

    override fun onResume() {
        Log.d("MyTest", "FragmentOne onResume ${hashCode()}")
        super.onResume()
    }

    override fun onPause() {
        Log.d("MyTest", "FragmentOne onPause ${hashCode()}")
        super.onPause()
    }

    override fun onStop() {
        Log.d("MyTest", "FragmentOne onStop ${hashCode()}")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("MyTest", "FragmentOne onDestroyView ${hashCode()}")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("MyTest", "FragmentOne onDestroy ${hashCode()}")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("MyTest", "FragmentOne onDetach ${hashCode()}")
        super.onDetach()
    }
}

package com.weather.ls_13

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.weather.ls_13.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    companion object {

        private const val KEY_TEX = "KEY_TEX"
        private const val KEY_COLOR = "KEY_COLOR"
        private const val KEY_IMAGE = "KEY_IMAGE"

        /**
         * Создание экземпляра класса фрагмента
         * @StringRes @ColorRes  @DrawableRes аннотации чтобы пользователь не смог бередать другой
         * int а передал именно ресурсный int
         */
        fun newInstance(
            @StringRes textRes: Int,
            @ColorRes bgColorRes: Int,
            @DrawableRes drawableRes: Int
        ): OnboardingFragment {
            val args = Bundle()
            args.putInt(KEY_TEX, textRes)
            args.putInt(KEY_COLOR, bgColorRes)
            args.putInt(KEY_IMAGE, drawableRes)

            val fragment = OnboardingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(
            "MyTest",
            "OnboardingFragment onCreateView = ${
            resources.getString(
                requireArguments().getInt(KEY_TEX)
            )
            }"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        val view = binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // установим цвет фона
        requireView().setBackgroundResource(requireArguments().getInt(KEY_COLOR))
        binding.textView.text = requireArguments().getString(KEY_TEX)
        binding.imageView.setBackgroundResource(requireArguments().getInt(KEY_IMAGE))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(
            "MyTest",
            "OnboardingFragment onDestroy = ${
            resources.getString(
                requireArguments().getInt(KEY_TEX)
            )
            }"
        )
    }
}

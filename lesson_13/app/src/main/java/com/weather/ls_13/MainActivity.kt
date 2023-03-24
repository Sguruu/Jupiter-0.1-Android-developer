package com.weather.ls_13

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weather.ls_13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val screensList: List<OnboardingScreenData> = listOf(
        OnboardingScreenData(
            textRes = R.string.name_1,
            bgColorRes = R.color.color1,
            drawableRes = R.drawable.avatar_1
        ),
        OnboardingScreenData(
            textRes = R.string.name_2,
            bgColorRes = R.color.color2,
            drawableRes = R.drawable.avatar_2
        ),
        OnboardingScreenData(
            textRes = R.string.name_3,
            bgColorRes = R.color.color3,
            drawableRes = R.drawable.avatar_3
        ),
        OnboardingScreenData(
            textRes = R.string.name_4,
            bgColorRes = R.color.color4,
            drawableRes = R.drawable.avatar_4
        ),
        OnboardingScreenData(
            textRes = R.string.name_5,
            bgColorRes = R.color.color5,
            drawableRes = R.drawable.avatar_5
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // создание адаптера
        val adapter = Adapter(screensList, this)
        // присвоедние адаптера к viewpager
        binding.viewPager.adapter = adapter
        // позволяет настраивать предзагрузку фрагментов
        binding.viewPager.offscreenPageLimit = 3
    }
}

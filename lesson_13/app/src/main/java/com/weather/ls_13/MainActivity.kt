package com.weather.ls_13

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
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

        binding.buttonTabLayout.setOnClickListener {
            val intent = Intent(this, TabsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonDialog.setOnClickListener {
            val intent = Intent(this, DialogActivity::class.java)
            startActivity(intent)
        }

        // создание адаптера
        val adapter = Adapter(screensList, this)
        // присвоедние адаптера к viewpager
        binding.viewPager.adapter = adapter
        // позволяет настраивать предзагрузку фрагментов
        binding.viewPager.offscreenPageLimit = 3

        /*
        setCurrentItem(item, smoothScroll) — метод для программного перелистывания страниц:
	    item — индекс страницы, которую необходимо отобразить;
	    smoothScroll — флаг, показывающий, нужно ли перелистнуть
	    страницу плавно с анимацией или без анимации;
         */
        binding.viewPager.setCurrentItem(2, false)
        // currentItem — свойство для получения текущей страницы;
        binding.viewPager.currentItem

        // orientation — свойство для настройки направления скролла (по умолчанию — горизонтальное)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        /*
        registerOnPageChangeCallback — колбэк для отслеживания того, что пользователь перелистнул
        страницу у ViewPager.
         */
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            /**
             * @param position позиция текущего перелистывания экрана
             */
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                showToast("текущая позиция : $position")
            }
        })

        /*
        Здесь:
        pageTransformer — параметр, который показывает, на какой стадии скролла находится страница;
        transformPage(@NotNull View page, float position) — метод, в котором
        происходит основная логика; принимает на вход страницу и позицию её скролла:
        position 0 — страница полностью видна пользователю;
        position −1 — соседняя страница слева от видимой пользователю страницы
        (если мы рассматриваем горизонтальный скролл), пользователь не видит эту страницу;
        position 1 — соседняя страница справа от видимой пользователю страницы.
         */

        binding.viewPager.setPageTransformer(object : ViewPager2.PageTransformer {
            /**
             * Напишим логику, что если наши фрагменты невидимы пользователю они буду исчезать
             */
            override fun transformPage(page: View, position: Float) {
                when {
                    position < -1 || position > 1 -> {
                        // alfa значение отвечающая за видимость view где 0 - полность не видим
                        // 1 - видим
                        page.alpha = 0f
                    }

                    position <= 0 -> {
                        page.alpha = 1 + position
                    }

                    position <= 1 -> {
                        page.alpha = 1 - position
                    }
                }
            }
        })
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}

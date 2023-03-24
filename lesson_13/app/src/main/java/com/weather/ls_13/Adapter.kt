package com.weather.ls_13

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Базовая реадизация адаптера которая работает с ViewPager,
 * В конструктор необходимо передать либо родительский фрагмент либо родительскую активность
 */
// FragmentActivity - Базовая активность которая работает с фрагментами
class Adapter(
    private val screens: List<OnboardingScreenData>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    /**
     * Сообщает сколько элементов может быть, по этому число viewPager отобразит нужное количество
     * страниц
     */
    override fun getItemCount(): Int {
        Log.d("MyTest","Adapter getItemCount")
        // отобразит такое количество страниц равный количество эоементов в конструкторе
        return screens.size
    }

    /**
     * Создает фрагмент для viewPager по надобности
     * @param position индекс элемента который необходимо отобразить
     */
    override fun createFragment(position: Int): Fragment {
        Log.d("MyTest","Adapter createFragment position = $position")
        val textRes = screens[position].textRes
        val bgColorRes = screens[position].bgColorRes
        val drawableRes = screens[position].drawableRes

        return OnboardingFragment.newInstance(textRes, bgColorRes, drawableRes)
    }
}

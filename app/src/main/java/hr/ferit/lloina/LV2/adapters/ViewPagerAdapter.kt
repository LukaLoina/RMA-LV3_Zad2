package hr.ferit.lloina.LV2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hr.ferit.lloina.LV2.fragments.InspiringPersonsListFragment

class ViewPagerAdapter(val fragmentActivity: FragmentActivity, val screens: List<() -> Fragment>) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int = screens.size

    override fun createFragment(position: Int): Fragment {
        return screens[position]()
    }
}
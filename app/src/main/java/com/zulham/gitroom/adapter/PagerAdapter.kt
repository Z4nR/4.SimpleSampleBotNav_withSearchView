package com.zulham.gitroom.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zulham.gitroom.R
import com.zulham.gitroom.ui.follower.FollowerFragment
import com.zulham.gitroom.ui.following.FollowingFragment
import com.zulham.gitroom.ui.repository.RepoFragment

class PagerAdapter(private val mContext: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var username: String? = null

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.follower, R.string.following, R.string.repository)

    override fun getCount(): Int = TAB_TITLES.size

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()
        bundle.putString("username", username)

        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
            2 -> fragment = RepoFragment()
        }

        fragment?.arguments = bundle
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

}
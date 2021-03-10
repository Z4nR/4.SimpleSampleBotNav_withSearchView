package com.zulham.gitroom.data

import com.zulham.gitroom.R
import com.zulham.gitroom.data.model.ModelUser

object dummyData {

    private  val userName = arrayOf(
            "JakeWharton",
            "amitshekhariitbhu",
            "romainguy",
            "chrisbanes",
            "tipsy",
            "ravi8x",
            "jasoet",
            "budioktaviyan",
            "hendisantika",
            "sidiqpermana"
    )

    private  val name = arrayOf(
            "Jake Wharton",
            "Amit Shekhar",
            "Romain Guy",
            "Chris Banes",
            "David",
            "Ravi Tamada",
            "Deny Prasetyo",
            "Budi Oktaviyan",
            "Hendi Santika",
            "Sidiq Permana"
    )

    private  val userCompany = arrayOf(
            "Google, Inc.",
            "MindOrksOpenSource",
            "Google",
            "Google \n working on @android",
            "Working Group Two",
            "AndroidHive | Droid5",
            "Gojek Engineering",
            "KotlinID",
            "JVMDeveloperID \n @KotlinID \n @IDDevOps",
            "Nusantara Beta Studio"
    )

    private  val location = arrayOf(
            "Pittsburgh, PA, USA",
            "New Delhi, India",
            "California",
            "Sydney, Australia",
            "Trondheim, Norway",
            "India",
            "Kotagede \n Yogyakarta, Indonesia",
            "Jakarta, Indonesia",
            "Bojongsoang \n Bandung Jawa Barat",
            "Jakarta Indonesia"
    )

    private  val userRepository = arrayOf(
            "102",
            "37",
            "9",
            "30",
            "56",
            "28",
            "44",
            "110",
            "1064",
            "65"
    )

    private  val follower = arrayOf(
            "56995",
            "5153",
            "7972",
            "14725",
            "788",
            "18628",
            "277",
            "178",
            "428",
            "465"
    )

    private  val following = arrayOf(
            "12",
            "2",
            "0",
            "1",
            "0",
            "3",
            "39",
            "23",
            "61",
            "10"
    )

    private val userImages = intArrayOf(
            R.drawable.user1,
            R.drawable.user2,
            R.drawable.user3,
            R.drawable.user4,
            R.drawable.user5,
            R.drawable.user6,
            R.drawable.user7,
            R.drawable.user8,
            R.drawable.user9,
            R.drawable.user10
    )
    val list: ArrayList<ModelUser>
        get() {
            val list: ArrayList<ModelUser> = arrayListOf()
            for (position in userName.indices) {
                list.add(ModelUser(
                        userName[position],
                        name[position],
                        userCompany[position],
                        follower[position],
                        following[position],
                        location[position],
                        userRepository[position],
                        userImages[position]))
            }

            return list
        }

}
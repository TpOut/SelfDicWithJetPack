package com.example.selfdicwithjetpack

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.selfdicwithjetpack.general.PrivacyDialog
import com.example.selfdicwithjetpack.login.LoginFrag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyTestSuite {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    // 安全提示 android.permission.INSTALL_GRANT_RUNTIME_PERMISSIONS
    // 需要开启 允许USB 安装（安全
    @Test
    fun testEvent() {
//        val scenario = activityScenarioRule.scenario
        val scenario = ActivityScenario.launch(MainActivity::class.java)

//        scenario.moveToState(Lifecycle.State.CREATED)
//        scenario.moveToState(Lifecycle.State.RESUMED)

        var originalActivityState = scenario.state
        Log.d("屠龙宝刀", "originalActivityState $originalActivityState")
        onView(withId(R.id.btn)).perform(click())

        scenario.onActivity { activity ->
//            startActivity(Intent(activity, DetailAct::class.java))
        }
        originalActivityState = scenario.state
        Log.d("屠龙宝刀", "originalActivityState $originalActivityState")

    }

    @Test
    fun testFragment(){

//        val dessertRepository = mock(DessertsRepository::class.java)
//        launchFragment<DessertsFragment>(factory = MyFragmentFactory(dessertRepository)) // 直接运行，不依赖activity（创建一个空的

//        launchFragment<PrivacyDialog>() // dialog 也不依赖

        val fragmentArgs = bundleOf("sddsf" to 0)
        val scenario = launchFragmentInContainer<LoginFrag>(fragmentArgs) // 依赖一个activity
//        scenario.moveToState()
        // 后续和activity 的类似
    }
}
    
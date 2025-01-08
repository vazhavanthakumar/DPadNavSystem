package com.ev.dpadnavsystem.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ev.dpadnavsystem.R
import com.ev.dpadnavsystem.databinding.ActivityMainBinding
import com.ev.dpadnavsystem.helper.DPSpeechRecognizer
import com.ev.dpadnavsystem.utils.MenuTypes
import com.ev.dpadnavsystem.utils.OnSwipeTouchListener
import com.ev.dpadnavsystem.utils.PermissionRequestCode
import com.ev.dpadnavsystem.utils.SpeechCallBack
import com.ev.dpadnavsystem.utils.collectFlowOnLifeCycle
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var speechRecognizer: DPSpeechRecognizer

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        checkPermission()
        initListeners()
        subscribeObservers()
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.containerNavGraph) as NavHostFragment
        navController = navHostFragment.navController
        val swipeAnimation = AnimationUtils.loadAnimation(this, R.anim.swipe_right)
        binding.ivArrowRight.startAnimation(swipeAnimation)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        binding.ivArrowRight.setOnClickListener { navigateToSubmenu(MenuTypes.TYPE_LOCATION) }
        binding.ivLocation.setOnClickListener { navigateToSubmenu(MenuTypes.TYPE_LOCATION) }
        binding.ivSettings.setOnClickListener { navigateToSubmenu(MenuTypes.TYPE_CONTROL) }
        binding.ivBluetooth.setOnClickListener { navigateToSubmenu(MenuTypes.TYPE_BT) }
        navController.addOnDestinationChangedListener(onDestinationChangeListener)
        binding.ivVoiceControl.setOnClickListener { speechRecognizer.startRecognizer() }
        binding.rootView.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                navigateToSubmenu(MenuTypes.TYPE_LOCATION)
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                onBackPressedDispatcher.onBackPressed()
            }
        });
    }

    private fun navigateToSubmenu(id: Int) {
        homeViewModel.updateSelectedSubMenuID(id)
        if (navController.currentDestination == null ||
            navController.currentDestination?.id != R.id.navSubmenu
        ) {
            navController.navigate(R.id.action_navRider_to_navSubmenu,
                Bundle().apply {
                    putInt(MenuTypes.TYPE_MENU, id)
                })
        }
    }

    private val onDestinationChangeListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            toggleSubMenu(MenuTypes.TYPE_LOCATION)
            if (destination.id == R.id.navRider) {
                binding.isSubMenuOpen = false
            } else {
                binding.isSubMenuOpen = true
            }
        }

    private fun subscribeObservers() {
        collectFlowOnLifeCycle(homeViewModel.subMenuSelectionState) {
            toggleSubMenu(it)
        }
        collectFlowOnLifeCycle(speechRecognizer.speechCallBack) {
            Log.e("TAG", "subscribeObservers: ")
            when (it) {
                SpeechCallBack.TYPE_OPEN_MENU -> navigateToSubmenu(MenuTypes.TYPE_LOCATION)
                SpeechCallBack.TYPE_CLOSE_MENU -> onBackPressedDispatcher.onBackPressed()
                SpeechCallBack.NOTHING -> {}
            }
        }
    }

    private fun toggleSubMenu(type: Int) {
        when (type) {
            MenuTypes.TYPE_LOCATION -> {
                binding.isLocation = navController.currentDestination?.id != R.id.navRider
                binding.isBt = false
                binding.isControls = false
            }

            MenuTypes.TYPE_BT -> {
                binding.isLocation = false
                binding.isBt = true
                binding.isControls = false
            }

            MenuTypes.TYPE_CONTROL -> {
                binding.isLocation = false
                binding.isBt = false
                binding.isControls = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(onDestinationChangeListener)
    }

    override fun onStop() {
        super.onStop()
        speechRecognizer.destroy()
    }

    private fun checkPermission() {
        // Check if the app has permission to record audio
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {

            // If permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                PermissionRequestCode.MIC_PERMISSION
            )
        } else {
            // Permission already granted, start your voice control service
            Log.e("TAG", "checkPermission: granted ")
            speechRecognizer.startRecognizer()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PermissionRequestCode.MIC_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start the voice control service
                Log.e("TAG", "onRequestPermissionsResult: permission granted")
                speechRecognizer.startRecognizer()
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Permission denied to use microphone", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}


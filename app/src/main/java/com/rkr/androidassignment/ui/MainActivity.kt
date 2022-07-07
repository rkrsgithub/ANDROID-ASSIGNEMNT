package com.rkr.androidassignment.ui

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.rkr.androidassignment.R
import com.rkr.androidassignment.di.AppComponentProvider
import java.net.InetAddress
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }
    private var nsdManager: NsdManager? = null

    // Instantiate a new DiscoveryListener
    private val discoveryListener = object : NsdManager.DiscoveryListener {

        // Called as soon as service discovery begins.
        override fun onDiscoveryStarted(regType: String) {
            Log.d(TAG, "Service discovery started - $regType")
            viewModel.saveDevice(NsdServiceInfo().apply {
                host = InetAddress.getLocalHost()
                serviceName = "Dummy"
            })
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            // A service was found! Do something with it.
            Log.d(TAG, "Service discovery success$service")
            viewModel.saveDevice(service)
        }

        override fun onServiceLost(service: NsdServiceInfo) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e(TAG, "service lost: $service")
        }

        override fun onDiscoveryStopped(serviceType: String) {
            Log.i(TAG, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager?.stopServiceDiscovery(this)
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(TAG, "Discovery failed: Error code:$errorCode")
            nsdManager?.stopServiceDiscovery(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as AppComponentProvider).getAppComponent().inject(this)
        setContentView(R.layout.activity_main)
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            signIn()
        } else {
            viewModel.setLoggedInState(true)
        }
    }

    private fun discoverServices() {
        nsdManager = (getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
            discoverServices("_mdns._tcp", NsdManager.PROTOCOL_DNS_SD, discoveryListener)
        }
    }

    override fun onPause() {
        tearDown()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        discoverServices()
    }

    override fun onDestroy() {
        tearDown()
        super.onDestroy()
    }

    // NsdHelper's tearDown method
    private fun tearDown() {
        nsdManager?.apply {
            try {
                stopServiceDiscovery(discoveryListener)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun signIn() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

// Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            viewModel.setLoggedInState(true)
            Toast.makeText(this, "Welcome ${user?.displayName.orEmpty()}", Toast.LENGTH_SHORT)
                .show()
        } else {
            viewModel.setLoggedInState(false)
            Toast.makeText(this, "Sign in failed, please try again later", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            actionLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun actionLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                viewModel.setLoggedInState(false)
                finish()
            }
    }
}

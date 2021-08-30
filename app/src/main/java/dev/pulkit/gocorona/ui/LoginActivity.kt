package dev.pulkit.gocorona.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dev.pulkit.gocorona.R
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    lateinit var backendOTP:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btVerifyOtpLoginActivity.setOnClickListener {
            Intent(this,MainActivity::class.java).apply {
                startActivity(this)
            }
        }
        btVerifyOtpLoginActivity.isEnabled = false
        pbActivityLogin.visibility = View.GONE
        btSendOtpLoginActivity.setOnClickListener { sendOtp() }
        btVerifyOtpLoginActivity.setOnClickListener { verifyOtp() }
    }
    private fun sendOtp() {
        val phoneNumber = etMobileNumberLoginActivity.text.toString()
        if(phoneNumber.length != 10 || phoneNumber[0]=='0'){
            Toast.makeText(this, "Please enter a valid number!", Toast.LENGTH_SHORT).show()
            return
        }
        pbActivityLogin.visibility = View.VISIBLE
        llActivityLogin.isEnabled = false
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$phoneNumber",60,
            TimeUnit.SECONDS,this,object: PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onCodeSent(backendOtp: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                    btSendOtpLoginActivity.isEnabled = false
                    btVerifyOtpLoginActivity.isEnabled = true
                    pbActivityLogin.visibility = View.GONE
                    backendOTP = backendOtp
                    Log.d("pulkit","on code sent $backendOtp")
                }

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    pbActivityLogin.visibility = View.GONE
                    llActivityLogin.isEnabled = true
                    Log.d("pulkit","verification completed")
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    pbActivityLogin.visibility = View.GONE
                    llActivityLogin.isEnabled = true
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    Log.d("pulkit","verification failed")
                }
            })
    }

    private fun verifyOtp() {
        val enteredOtp = etEnteredOtpLoginActivity.text.toString()

        val phoneAuthCredential = PhoneAuthProvider.getCredential(backendOTP,enteredOtp)
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener { task ->
                pbActivityLogin.visibility = View.GONE
                llActivityLogin.isEnabled = true
                val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
                with(sharedPref.edit()){
                    putBoolean(getString(R.string.areyoudoctor),cbAreYouDoctor.isChecked)
                    apply()
                }
                if(task.isSuccessful){
                    Intent(this,MainActivity::class.java).let {
                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(it)
                        finishAffinity()
                    }
                }else{
                    Toast.makeText(this, "Incorrect OTP!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
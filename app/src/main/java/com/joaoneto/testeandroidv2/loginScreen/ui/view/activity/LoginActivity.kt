package com.joaoneto.testeandroidv2.loginScreen.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.joaoneto.testeandroidv2.R
import com.joaoneto.testeandroidv2.loginScreen.ui.viewModel.LoginViewModel
import com.joaoneto.testeandroidv2.util.system.PreferencesHelper
import com.joaoneto.testeandroidv2.util.system.SnackbarHelper
import com.joaoneto.testeandroidv2.mainScreen.view.activity.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private val preferencesHelper: PreferencesHelper by lazy {
        PreferencesHelper(this)
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        checkPreferences()
        checkFields()
    }

    private fun checkFields() {

        loginButton.setOnClickListener {
            val user = inputEmail.text.toString()
            val pass = inputPass.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty()) {

                viewModel.signIn(user, pass).observe(this, Observer {
                    if (it == null) {
                        SnackbarHelper.message(
                            loginConstraint,
                            "Erro ao acessar conta, verifique suas credenciais e tente novamente"
                        )
                    } else {

                        preferencesHelper.setUsername(user)
                        preferencesHelper.setPassword(pass)

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        val bundle = Bundle()
                        bundle.putSerializable("userAccountData", it.userAccount)
                        intent.putExtras(bundle)
                        startActivity(intent)
                        finish()
                    }

                })

            } else {
                SnackbarHelper.message(loginConstraint, "Campos obrigatórios vazios")
            }
        }

    }

    private fun checkPreferences() {
        if (preferencesHelper.getUsername().isNotEmpty()) {
            inputEmail.text =
                Editable.Factory.getInstance().newEditable(preferencesHelper.getUsername())

        } else {
            inputEmail.text = Editable.Factory.getInstance().newEditable("")
        }
        if (preferencesHelper.getPassword().isNotEmpty()) {
            inputPass.text =
                Editable.Factory.getInstance().newEditable(preferencesHelper.getPassword())
        } else {
            inputPass.text =
                Editable.Factory.getInstance().newEditable("")
        }
    }

}

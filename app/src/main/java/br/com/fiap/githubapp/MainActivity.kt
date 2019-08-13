package br.com.fiap.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import br.com.fiap.githubapp.api.GitHubService
import br.com.fiap.githubapp.model.Usuario
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.android.synthetic.main.include_loading.*
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        btnPesquisar.setOnClickListener {
            mainViewModel.pesquisar(edtUserLogin.text.toString())
        }

        registerObsever()
    }

    private fun registerObsever() {
        mainViewModel.usuarioResponse.observe(this,
            Observer { setUsuario(it) })

        mainViewModel.loading.observe(this,
            Observer {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            })

        mainViewModel.messageError.observe(this, Observer {
            if (it != "") {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun setUsuario(usuario: Usuario?) {
        txtNomeUsuario.text = usuario?.nome

        Picasso.get()
            .load(usuario?.avatarURL)
            .into(imvLogin)
    }

    private fun showLoading() {
        containerLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        containerLoading.visibility = View.GONE
    }


}

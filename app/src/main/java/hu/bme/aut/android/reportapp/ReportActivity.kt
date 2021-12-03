package hu.bme.aut.android.reportapp

import android.app.ProgressDialog
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.reportapp.adapter.ReportAdapter
import hu.bme.aut.android.reportapp.data.Report
import hu.bme.aut.android.reportapp.databinding.ActivityReportBinding
import hu.bme.aut.android.reportapp.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private var progressDialog: ProgressDialog? = null
    private lateinit var adapter: ReportAdapter
    private var username: String = ""
    private var password: String = ""
    private var reports: ArrayList<Report> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val credentials: Bundle? = intent.extras
        username = credentials!!.getString("username")!!
        password = credentials.getString("password")!!
        initRecyclerView()
        getAllReports()

        binding.fabNewitem.setOnClickListener{
            sendNewReportItem()
        }
    }

    private fun initRecyclerView() {
        adapter = ReportAdapter()
        binding.rvReportRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            reverseLayout = false
            stackFromEnd = false
        }
        binding.rvReportRecyclerView.adapter = adapter
    }

    private fun showProgressDialog() {
        if (progressDialog != null) {
            return
        }

        progressDialog = ProgressDialog(this).apply {
            setCancelable(false)
            setMessage(resources.getString(R.string.loading))
            show()
        }
    }

    private fun hideProgressDialog() {
        progressDialog?.let { dialog ->
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
        progressDialog = null
    }

    private fun getAllReports() {
        showProgressDialog()
        NetworkManager.getAllReports().enqueue(object : Callback<ArrayList<Report>>{
            override fun onResponse(
                call: Call<ArrayList<Report>>,
                response: Response<ArrayList<Report>>
            ) {
                if (response.isSuccessful) {
                    reports = response.body()!!
                    adapter.removeAllCollectionPoint()
                    Log.d("*Click* Noice size: ", reports.size.toString())
                    for(i in 0 until reports.size){
                        adapter.addCollectionPoint(reports[i])
                        Log.d("*Click* Noice id: ", reports[i].id.toString())
                    }
                    hideProgressDialog()
                } else {
                    Toast.makeText(this@ReportActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<Report>>, t: Throwable) {
                hideProgressDialog()
                t.printStackTrace()
                Toast.makeText(this@ReportActivity, "Network request error occured, check LOG", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun sendNewReportItem() {
        val report = Report(
            null,
            "Demo report2",
            "Demo station name2",
            "Demo type2",
            47.469299,
            19.027645,
            "Demo user name2"
        )
        NetworkManager.postNewReport(report).enqueue(object : Callback<Report>{
            override fun onResponse(call: Call<Report>, response: Response<Report>) {
                if (response.isSuccessful) {
                    Log.d("*Click* Noice size: ", reports.size.toString())
                    getAllReports()
                } else {
                    Toast.makeText(this@ReportActivity, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Report>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@ReportActivity, "Network request error occured, check LOG", Toast.LENGTH_LONG).show()
            }
        })
    }
}
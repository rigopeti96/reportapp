package hu.bme.aut.android.reportapp.data

data class Report(
    val id: Int?,
    val reportType: String,
    val stationName: String,
    val transportType: String,
    val latitude: Double,
    val longitude: Double,
    //var reportDate: LocalDateTime,
    //var reportDateUntil: LocalDateTime,
    val reporterName: String
)
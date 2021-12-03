package hu.bme.aut.android.reportapp.data

data class User (
    private val name: String,
    private val password: String,
    private val enabled: Boolean,
    private val roles: List<String?>?
)
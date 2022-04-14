package sk.parohy.codecon.api.model

data class God(
    val name: String = "",
    val title: String = "",
    val desc: String = ""
) {
    override fun toString(): String {
        return "Name: $name - $title"
    }
}
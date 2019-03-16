import org.json.JSONObject

class Grid() {
    fun updateGrid(gridInformation: String) {
        val gridJson = JSONObject(gridInformation)
        println(gridJson)
        // todo evaluate the grid data
    }
}


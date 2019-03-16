class Game(
    var instanceName: String?,
    var activePlayers: String?
) {
    fun updateGameInstance(
        gameString: String
    ) {
        //InputForm: [{"activePlayers":1,"name":"1"}]
        this.activePlayers = gameString.split(":", ",")[1]
        this.instanceName = gameString.split(":", ",")[3].removeSurrounding("\"").replace("}", "").replace("]", "")
            .replace("\"", "")
    }

    fun updatePlayers(playersString: String) {
        println(playersString)
        try {
            // todo evaluate the players data
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateTicker(tickerString: String) {
        println(tickerString)
        try {
            // todo may evaluate the ticker data
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

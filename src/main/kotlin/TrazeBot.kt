import org.json.JSONObject

object TrazeBot {
    var botSecretUserToken: String? = null
    var botPlayerId: Int? = null
    var instanceName: String? = null
    var activePlayers: String? = null

    fun updateGameInstance(gameString: String) {
        //InputForm: [{"activePlayers":1,"name":"1"}]
        println("1 instance")
        activePlayers = gameString.split(":", ",")[1]
//            println("activePlayers: $activePlayers")
        instanceName = gameString.split(":", ",")[3].removeSurrounding("\"").replace("}", "").replace("]", "")
            .replace("\"", "")
//            println("instanceName: $instanceName")
    }

    fun updateGrid(gridInformation: String) {
        val gridJson = JSONObject(gridInformation)
        println("2 grid")
//        println(gridJson)
    }

    fun updatePlayers(playersString: String) {
        println("3 players")
//        println(playersString)
        try {
//            players = objectMapper.readValue(playersString, Array<Player>::class.java)
//            if (!playerAlive())
//                BrokerClient.join()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateTicker(tickerString: String) {
        println("4 ticker")
//        println(tickerString)
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun initPlayer(playerJsonString: String) {
        val player = JSONObject(playerJsonString)
        botPlayerId = player.get("id") as Int
        botSecretUserToken = player.get("secretUserToken").toString()
        println("Spieler " + player.get("name") + " erfolgreich registriert!")
        println("botPlayerId: $botPlayerId botSecretUserToken $botSecretUserToken")
    }
}

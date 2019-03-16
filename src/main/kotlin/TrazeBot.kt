import org.json.JSONObject

class TrazeBot(
    var botSecretUserToken: String?,
    var botPlayerId: Int?
) {

    fun initBot(playerJsonString: String) {
        val bot = JSONObject(playerJsonString)
        this.botPlayerId = bot.get("id") as Int
        this.botSecretUserToken = bot.get("secretUserToken").toString()
        println("Bot " + bot.get("name") + " erfolgreich registriert!")
    }
}

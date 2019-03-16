import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject

class BrokerClient(
    var mqttClientId: String,
    val botName: String,
    var mqttClient: MqttClient
) {

    fun initGame(myBroker: BrokerClient, myGame: Game, myTraceBot: TrazeBot, myGrid: Grid) {
        try {
            //Connection to the Server
            connectToServer()

            //Callback for the Informationsaving in Objects
            val theCallback = SimpleMqttCallBack(myBroker, myGame, myTraceBot, myGrid)
            this.mqttClient.setCallback(theCallback)

            //Subscribe the game
            mqttClient.subscribe("traze/games")

            myBroker.joinOnGrid(myGame)

            mqttClient.subscribe("traze/${myGame.instanceName}/grid")
            mqttClient.subscribe("traze/${myGame.instanceName}/players")
            mqttClient.subscribe("traze/${myGame.instanceName}/ticker")
            mqttClient.subscribe("traze/${myGame.instanceName}/player/${this.mqttClientId}")

            //SubscribeAndJoin-> Subscribe for all topics and Join the Game afterwards
            //BrokerClient.Subscribe is called from Game.updateGameInstance, after the instanceName is known
            botLogic(myBroker, myGame, myTraceBot)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun connectToServer() {
        try {
            val options = MqttConnectOptions()
            options.isCleanSession = true
            mqttClient.connect(options)
            if (mqttClient.isConnected) {
                println("Client und Server sind verbunden")
            } else {
                println("KEINE Verbindung")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun joinOnGrid(myGame: Game) {
        try {
//          Input {"name": "myIngameNick", "mqttClientName": "myClientName"}
            val joiningPlayer =
                JSONObject("{\"name\": \"" + this.botName + "\", \"mqttClientName\": \"" + this.mqttClientId + "\"}")

            val message = MqttMessage()
            message.payload = joiningPlayer.toString().toByteArray()
//            println("publish join: ${joiningPlayer.toString()}")
            mqttClient.publish("traze/${myGame.instanceName}/join", message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

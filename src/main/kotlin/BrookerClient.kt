import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject
import java.util.*
import java.util.concurrent.locks.Lock

object BrokerClient {
    val mqttClientId = UUID.randomUUID().toString()
    val botName = "kotlin-bot-" + Math.round((Math.random() * 50) + 1)
    val brokerUri = "tcp://traze.iteratec.de:1883"
    var mqttClient: MqttClient = MqttClient(BrokerClient.brokerUri, BrokerClient.mqttClientId)


    fun initGame() {
        try {
            println("botName: $botName ClientID: $mqttClientId")

            //Connection to the Server
            connectToServer()

            //Callback for the Informationsaving in Objects
            val theCallback = SimpleMqttCallBack()
            mqttClient.setCallback(theCallback)

            //Subscribe for all topics
            subscribe()
            // Join after Subcription, then you get the playertoken
            joinOnGrid()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun connectToServer() {
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

    fun subscribe() {
        try {
            mqttClient.subscribe("traze/games")
            Thread.sleep(7_000)
            //todo wait&signal?
            mqttClient.subscribe("traze/${TrazeBot.instanceName}/grid")
            mqttClient.subscribe("traze/${TrazeBot.instanceName}/players")
            mqttClient.subscribe("traze/${TrazeBot.instanceName}/ticker")
            mqttClient.subscribe("traze/${TrazeBot.instanceName}/player/${BrokerClient.mqttClientId}")
            println("5 sucribe ende")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun joinOnGrid() {
        try {
//              Input {"name": "myIngameNick", "mqttClientName": "myClientName"}
            val joiningPlayer =
                JSONObject("{\"name\": \"" + BrokerClient.botName + "\", \"mqttClientName\": \"" + BrokerClient.mqttClientId + "\"}")

            val message = MqttMessage()
            message.payload = joiningPlayer.toString().toByteArray()
            println("6 join")
            println("publishing: ${joiningPlayer.toString()}")
            mqttClient.publish("traze/${TrazeBot.instanceName}/join", message)
            Thread.sleep(5_000)
            //todo wait&signal?
            println("ende join")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    fun subcribeToPlayer() {
//        try {
//            println("7 subcribeplayer")
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//    }
}

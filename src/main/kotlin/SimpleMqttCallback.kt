import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class SimpleMqttCallBack(
    var myBroker: BrokerClient,
    var myGame: Game,
    var myTraceBot: TrazeBot,
    var myGrid: Grid

) : MqttCallback {
    override fun connectionLost(throwable: Throwable) {
        println("Connection to MQTT broker lost!")
        System.exit(0)
    }

    @Throws(Exception::class)
    override fun messageArrived(topic: String, newMqttMessage: MqttMessage) {
        when (topic) {
            "traze/games" -> myGame.updateGameInstance(String(newMqttMessage.payload))
            "traze/${myGame.instanceName}/grid" -> myGrid.updateGrid(String(newMqttMessage.payload))
            "traze/${myGame.instanceName}/player/${myBroker.mqttClientId}" -> myTraceBot.initBot(String(newMqttMessage.payload))
            "traze/${myGame.instanceName}/players" -> myGame.updatePlayers(String(newMqttMessage.payload))
            "traze/${myGame.instanceName}/ticker" -> myGame.updateTicker(String(newMqttMessage.payload))
        }
    }

    override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {
        // not used
    }
}
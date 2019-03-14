import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class SimpleMqttCallBack : MqttCallback {

    override fun connectionLost(throwable: Throwable) {
        println("Connection to MQTT broker lost!")
        System.exit(0)
    }

    @Throws(Exception::class)
    override fun messageArrived(topic: String, newMqttMessage: MqttMessage) {
        if (topic == "traze/games") {
            TrazeBot.updateGameInstance(String(newMqttMessage.payload))
        } else if (topic == "traze/${TrazeBot.instanceName}/grid") {
            TrazeBot.updateGrid(String(newMqttMessage.payload))
        } else if (topic == "traze/${TrazeBot.instanceName}/player/${BrokerClient.mqttClientId}") {
            TrazeBot.initPlayer(String(newMqttMessage.payload))
        } else if (topic == "traze/${TrazeBot.instanceName}/players") {
            TrazeBot.updatePlayers(String(newMqttMessage.payload))
        } else if (topic == "traze/${TrazeBot.instanceName}/ticker") {
            TrazeBot.updateTicker(String(newMqttMessage.payload))
        }
    }

    override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {
        // not used in this example
    }
}
import org.eclipse.paho.client.mqttv3.IMqttMessageListener
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.util.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback




fun main(args: Array<String>) {
    println("Start")

    val botName = "kotlin-bot-" + Math.round((Math.random() * 50) + 1)
    val mqttClientId = UUID.randomUUID().toString()
    val brokerUri = "tcp://traze.iteratec.de:1883"
//    val password = "pass12"

    println("botName: " + botName + " ClientID: " + mqttClientId)

    //Connection to Server
    val mqttClient  = MqttClient(brokerUri, mqttClientId)

    val options = MqttConnectOptions()

    options.isCleanSession = true
//        options.setUserName(botName)
//        options.setPassword(password.toCharArray())

    mqttClient.connect(options)

    println("Client Connection Status to server: " + mqttClient.isConnected)

    // Select a game instance
    val GAMES_TOPIC = "traze/games"

    mqttClient.setCallback(object : MqttCallback {
        override fun connectionLost(throwable: Throwable) {
            println("Connection to MQTT broker lost!")
        }

        @Throws(Exception::class)
        override fun messageArrived(t: String, msg2: MqttMessage) {
            println("Payload: \n\t" + String(msg2.getPayload()))
        }

        override fun deliveryComplete(t: IMqttDeliveryToken) {}
    })
    mqttClient.subscribe(GAMES_TOPIC)

    // Optional for suscribe
//    mqttClient.subscribe(GAMES_TOPIC,
//        { topic, msg ->
//            val payload = msg.getPayload()
//            println("Game Instance2: " + String(payload))
//        }
//    )

    // 6Later Subscibe to grid

    // 7Optional Suscribe to players
    // 8Optional Suscribe to ticker

    // 2Suscribe  player

    // 3publish join game

    botLogic(mqttClient)

    // Leaving Game is not wanted
//    mqttClient.disconnect()
}

fun botLogic(mqttClient: MqttClient) {
    // Method for your bot Logic
    // 4publish a message - steering

    val payload = String.format("N").toByteArray()
    val msg = MqttMessage(payload)
    msg.setQos(0)
    msg.setRetained(true)
    // not finished
    mqttClient.publish("traze/{instanceName}/{playerId}/steer", msg)

    //while? keepalive?
}
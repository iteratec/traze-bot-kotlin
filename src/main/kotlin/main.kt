import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import java.util.*
import kotlin.math.roundToInt

fun main(args: Array<String>) {
    println("Start")

    val botName = "kotlin-bot-" + Math.round((Math.random() * 50) + 1)
    val mqttClientId = UUID.randomUUID().toString()
    val brokerUri = "tcp://traze.iteratec.de:1883"
//    val password = "pass12"

    println("botName: " + botName + " ClientID: " + mqttClientId)

    //Connection to Server
    val mqttClient: MqttClient = MqttClient(brokerUri, mqttClientId)

    val options = MqttConnectOptions()

    options.isCleanSession = true
//        options.setUserName(botName)
//        options.setPassword(password.toCharArray())

    mqttClient.connect(options)

    println("connected: {}" + mqttClient.isConnected)

    // Start game

    botLogic()


    mqttClient.disconnect()

    println("Ende")
}

fun botLogic() {
    //publish a message

    // 8 seconds
    //while (true)
        //keepalive
        //move right
        // seconds -1
        //wait x seconds

    //suscribe to topic (see the grid)
}

//getConnection(botName, mqttClientId, "tcp://traze.iteratec.de:1883")
//
//fun getConnection(botName: String, mqttClientId: String, brokerUri: String) {
//    println("fun getConnection:")
//    println("botName: " + botName + " ClientID: " + mqttClientId)
//
//    val mqttClient: MqttClient
//}


import org.eclipse.paho.client.mqttv3.*
import org.json.JSONObject

fun main(args: Array<String>) {
    println("Start")
    //Setup Connection
    BrokerClient.initGame()

    println("8 ende init")
    //automatic "keepalive" Through Event from grid
    botLogic()
}

fun botLogic() {
//  Function for your bot Logic
//  exmaple for publish a message - steering

    sendSteering("N")
}

fun sendSteering(direction: String) {

    //  Input {"course":"N", "playerToken": "de37c1bc-d0e6-4c66-aaa3-911511f43d54"}
    val sterringBikeJson =
        JSONObject("{\"course\": \"" + direction + "\", \"playerToken\": \"" + TrazeBot.botSecretUserToken + "\"}")

    val msg = MqttMessage()
    msg.payload = sterringBikeJson.toString().toByteArray()
    println("9 steering")
    println("publishing: ${sterringBikeJson.toString()}")
    BrokerClient.mqttClient.publish("traze/${TrazeBot.instanceName}/${TrazeBot.botPlayerId}/steer", msg)
}
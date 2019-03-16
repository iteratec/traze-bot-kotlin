import org.eclipse.paho.client.mqttv3.*
import org.json.JSONObject
import java.util.*

fun main(args: Array<String>) {
    println ("Start der Anwendung")
    val mqttClientId = UUID.randomUUID().toString()
    val botName = "kotlin-bot-" + Math.round((Math.random() * 50) + 1)
    val brokerUri = "tcp://traze.iteratec.de:1883"
    val mqttClient = MqttClient(brokerUri, mqttClientId)
    val myBroker = BrokerClient(mqttClientId, botName, mqttClient)

    val myGame = Game(null, null)
    val myTraceBot = TrazeBot(null, null)
    val myGrid = Grid()

    myBroker.initGame(myBroker, myGame, myTraceBot, myGrid)
}

fun botLogic(myBroker: BrokerClient, myGame: Game, myTraceBot: TrazeBot) {
    Thread.sleep(3_000) //Optimization? better wait until objects are set up

    // todo implement your bot logic
    //  exmaple for publish a message - steering
    sendSteering("N", myBroker, myGame, myTraceBot)
}

fun sendSteering(direction: String, myBroker: BrokerClient, myGame: Game, myTraceBot: TrazeBot) {

    //  Input {"course":"N", "playerToken": "de37c1bc-d0e6-4c66-aaa3-911511f43d54"}
    val sterringBikeJson =
        JSONObject("{\"course\": \"" + direction + "\", \"playerToken\": \"" + myTraceBot.botSecretUserToken + "\"}")

    val msg = MqttMessage()
    msg.payload = sterringBikeJson.toString().toByteArray()
//    println("publish steering: ${sterringBikeJson.toString()}")
    myBroker.mqttClient.publish("traze/${myGame.instanceName}/${myTraceBot.botPlayerId}/steer", msg)
    println("Richtung versendet \"Ende\"")
}
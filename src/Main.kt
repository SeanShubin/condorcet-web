import fake.ApiFake
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    val api = ApiFake()
    val reactor = Reactor
    val environment = Environment(document, window)
    val components = Components
    val eventLoop = EventLoop(reactor, api, environment, components)
    eventLoop.handleEvent(Events.Initialize)
}

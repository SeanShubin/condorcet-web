import kotlin.browser.document
import kotlin.browser.window

fun main() {
    val reactor = CondorcetReactor()
    val environment = Environment(document, window)
    val components = CondorcetComponents
    val eventLoop = EventLoop(reactor, environment, components)
    eventLoop.handleEvent(CondorcetEvents.Initialize)
}

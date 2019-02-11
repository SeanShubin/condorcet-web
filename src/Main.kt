import event.CondorcetEvents
import generic.Environment
import generic.EventLoop
import reactor.CondorcetReactor
import kotlin.browser.document
import kotlin.browser.window

fun main() {
    val h1 = document.createElement("h1")
    h1.textContent = "Hello, world!"
    val body = document.body!!
    body.appendChild(h1)

    val reactor = CondorcetReactor()
    val environment = Environment(document, window)
    val eventLoop = EventLoop(reactor, environment)
    eventLoop.handleEvent(CondorcetEvents.Initialize)
}

package serverless

trait Event

case class HttpEvent(path: String,
                     method: String,
                     uriLambdaAlias: String = "${stageVariables.env}",
                     cors: Boolean = false,
                     `private`: Boolean = false,
                     authorizer: Option[Authorizer] = None,
                     request: Request = Request()) extends Event {
  lazy val response: Response = Response(cors)
}

case class StreamEvent(arn: String,
                       batchSize: Int = 100,
                       startingPosition: String,
                       enabled: Boolean = false) extends Event

case class Events(events: Event*) {
  def httpEventsForeach[U](f: HttpEvent => U): Unit =
    events.filter(_.isInstanceOf[HttpEvent]).foreach(e => f(e.asInstanceOf[HttpEvent]))
}


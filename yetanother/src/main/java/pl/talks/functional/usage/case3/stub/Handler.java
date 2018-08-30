package pl.talks.functional.usage.case3.stub;

public interface Handler {

  void handleMessage(ResourceA ticketDeltaMessage);

  Class<ResourceA> getHandlerClass();
}

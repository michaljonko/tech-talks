package pl.org.jdd.legacy.stub;

public interface Handler {

  void handleMessage(Engineer ticketDeltaMessage);

  Class<Engineer> getHandlerClass();
}

package pl.talks.functional.usage.case3;

public interface Handler {
    void handleMessage(ResourceA ticketDeltaMessage);

    Class<ResourceA> getHandlerClass();
}

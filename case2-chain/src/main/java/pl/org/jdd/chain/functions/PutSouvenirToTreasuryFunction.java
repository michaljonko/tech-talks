package pl.org.jdd.chain.functions;

import io.vavr.Function1;
import lombok.extern.java.Log;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.SouvenirRequestMessage;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.legacy.stub.diamond.DiamondMessageConverter;


@Log
public class PutSouvenirToTreasuryFunction implements Function1<Diamond, Location> {
    private final DiamondMessageConverter converter;
    private final Treasury treasury;

    public PutSouvenirToTreasuryFunction(DiamondMessageConverter converter, Treasury treasury) {
        this.converter = converter;
        this.treasury = treasury;
    }

    @Override
    public Location apply(Diamond souvenir) {
        SouvenirRequestMessage souvenirPackage = converter.convert(souvenir);
        Location packLocation = treasury.put(souvenirPackage);
        return packLocation;
    }
}

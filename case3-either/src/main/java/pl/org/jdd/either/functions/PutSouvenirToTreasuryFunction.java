package pl.org.jdd.either.functions;

import io.vavr.Function1;
import io.vavr.control.Either;
import lombok.extern.java.Log;
import pl.org.jdd.legacy.stub.Location;
import pl.org.jdd.legacy.stub.PackageSouvenir;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.diamond.Diamond;
import pl.org.jdd.legacy.stub.diamond.DiamondPacker;


@Log
public class PutSouvenirToTreasuryFunction implements Function1<Diamond, Either<Throwable, Location>> {

    private final DiamondPacker packer;
    private final Treasury treasury;

    public PutSouvenirToTreasuryFunction(DiamondPacker packer, Treasury treasury) {
        this.packer = packer;
        this.treasury = treasury;
    }

    @Override
    public Either<Throwable, Location> apply(Diamond souvenir) {
        PackageSouvenir diamondPackage = packer.pack(souvenir);
        return Either.right(treasury.put(diamondPackage));
    }
}

package pl.org.jdd.tryof

import io.micrometer.core.instrument.Metrics
import io.vavr.control.Either
import pl.org.jdd.legacy.stub.Location
import pl.org.jdd.legacy.stub.Treasury
import pl.org.jdd.legacy.stub.jewellery.Jewellery
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator
import pl.org.jdd.tryof.failure.NotValuableItem
import spock.lang.Specification
import spock.lang.Unroll

class JewelleryHandlerSpec extends Specification {

    @Unroll
    def "for #jewellery handler returns #expectedEither"() {
        setup:
        def validator = new JewelleryValidator()
        def packer = new JewelleryPacker()
        def treasury = new Treasury()
        def meterRegistry = Metrics.globalRegistry

        when:
        def handler = new JewelleryHandler(validator, packer, treasury, meterRegistry)

        then:
        def either = handler.handleSouvenir(jewellery)

        expect:
        either == expectedEither

        where:
        jewellery                 || expectedEither
        new Jewellery("topaz")    || Either.left(new NotValuableItem(jewellery))
        new Jewellery("gold")     || Either.left(new NotValuableItem(jewellery))
        new Jewellery("diamonds") || Either.right(new Location("Black hole"))

    }
}

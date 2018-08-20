package pl.coffeepower.yet.another.functional

import io.vavr.control.Either
import spock.lang.Specification
import spock.lang.Unroll

class TryItWithResourcesSpec extends Specification {

    @Unroll
    def "Using plain Java philosophy for text='#inputText' and word='#word' occurrence should be #occurrence"() {
        expect:
        TryItWithResources.wordFrequencyInFirstTenLinesOfReader(new StringReader(inputText), word) == occurrence

        where:
        inputText                                 | word   || occurrence
        "This is Spock\n.\t:test,\n"              | "test" || 1
        ""                                        | "x"    || 0
        "Simple\n\t\n\tword,\nabc:\n\t\tword\n\n" | "word" || 2
        "\n\r\t\t\n\n\r\t\t...word;;;\n"          | "word" || 1
        "\n\n\n\n\n###word#\n\n\n\n\n\nword"      | "word" || 1
    }

    @Unroll
    def "Using functional philosophy for text='#inputText' and word='#word' occurrence should be #occurrence"() {
        expect:
        TryItWithResources.wordFrequencyInFirstTenLinesOfReaderWithTry(new StringReader(inputText), word) == occurrence

        where:
        inputText                                 | word   || occurrence
        "This is Spock\n.\t:test,\n"              | "test" || 1
        ""                                        | "x"    || 0
        "Simple\n\t\n\tword,\nabc:\n\t\tword\n\n" | "word" || 2
        "\n\r\t\t\n\n\r\t\t...word;;;\n"          | "word" || 1
        "\n\n\n\n\n###word#\n\n\n\n\n\nword"      | "word" || 1
    }

    @Unroll
    def "Using functional philosophy for text='#inputText' and word='#word' answer should be #either"() {
        expect:
        TryItWithResources.wordFrequencyInFirstTenLinesOfReaderWithTryAndEither(new StringReader(inputText), word) == either

        where:
        inputText                                 | word   || either
        "This is Spock\n.\t:test,\n"              | "test" || Either.right(1)
        ""                                        | "x"    || Either.left(new TryItWithResources.NoSuchWordInText())
        "Simple\n\t\n\tword,\nabc:\n\t\tword\n\n" | "word" || Either.right(2)
        "\n\r\t\t\n\n\r\t\t...word;;;\n"          | "word" || Either.right(1)
        "\n\n\n\n\n###word#\n\n\n\n\n\nword"      | "word" || Either.right(1)
    }
}

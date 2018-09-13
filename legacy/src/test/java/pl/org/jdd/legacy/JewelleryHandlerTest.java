package pl.org.jdd.legacy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.org.jdd.legacy.stub.SouvenirPackage;
import pl.org.jdd.legacy.stub.Treasury;
import pl.org.jdd.legacy.stub.jewellery.Jewellery;
import pl.org.jdd.legacy.stub.jewellery.JewelleryPacker;
import pl.org.jdd.legacy.stub.jewellery.JewelleryValidator;


@RunWith(MockitoJUnitRunner.class)
public class JewelleryHandlerTest {

  @Mock
  private JewelleryPacker packerMock;
  @Mock
  private Treasury treasuryMock;
  @Mock
  private JewelleryValidator validatorMock;
  @Mock
  private MeterRegistry meterRegistryMock;
  @Mock
  private Counter counterMock;

  private JewelleryHandler subject;

  @Before
  public void init() {
    subject = new JewelleryHandler(validatorMock, packerMock, treasuryMock, meterRegistryMock);
  }

  @Test
  public void invalidCounterIsIncrementWhenNotValid() {
    Jewellery jewellery = new Jewellery("gold");
    when(validatorMock.isValid(any())).thenReturn(Boolean.FALSE);
    when(meterRegistryMock.counter("jewellery.invalid.counter")).thenReturn(counterMock);

    subject.handleSouvenir(jewellery);

    Mockito.verify(counterMock).increment();
  }

  @Test
  public void jewelleryCounterIsIncrementWhenIsValid() {
    Jewellery jewellery = new Jewellery("gold");
    when(validatorMock.isValid(any())).thenReturn(Boolean.TRUE);
    when(meterRegistryMock.counter("jewellery.counter")).thenReturn(counterMock);

    subject.handleSouvenir(jewellery);

    Mockito.verify(counterMock).increment();
  }

  @Test
  public void souvenirPackageWasPutToTreasure() {
    Jewellery jewellery = new Jewellery("gold");
    SouvenirPackage souvenirPackage = new SouvenirPackage();
    when(validatorMock.isValid(any())).thenReturn(Boolean.TRUE);
    when(meterRegistryMock.counter("jewellery.counter")).thenReturn(counterMock);
    when(packerMock.pack(jewellery)).thenReturn(souvenirPackage);
    subject.handleSouvenir(jewellery);

    Mockito.verify(treasuryMock).put(souvenirPackage);
  }
}
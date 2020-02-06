package sensor.distance;

import com.pi4j.io.gpio.*;

import java.util.logging.Logger;

/***
 * Class for using the HC-SR04 Distance-Sensor
 * Functionality:
 * 1) Start measurement with trigger (min 10 Microseconds high pulse)
 * 2) the subsonic module sends ca 250 Microseconds later a 40-kHz Burst Signal of eight pulse (
 *            duration 200 microseconds)
 * 3) Echo-Output is suddenly set to High and the module waits for the echo of the signal
 * 4) if the echo is received -> Echo-Output is set to Low
 * 5) 20ms later you can start a new measurement
 * 6) measurement failure -> echo output stays for 38 seconds on High
 *
 * calculate Distance: distance = (SpeedOfSound * time) / 2   [m, s]
 * -> for 20 degree: distance =
 */

public class HcSr04DistanceSensor implements DistanceSensor {

    private static final Logger log = Logger.getLogger( HcSr04DistanceSensor.class.getName() );

    private static final String NAME = "HC-SR04";
    private static final int AMOUNT_OF_MEASUREMENTS = 1;

    GpioPinDigitalOutput sensorTriggerPin;
    GpioPinDigitalInput sensorEchoPin;

    public HcSr04DistanceSensor(GpioController gpio,
                                Pin sensorTriggerPin,
                                Pin sensorEchoPin) {
        this.sensorTriggerPin = gpio.provisionDigitalOutputPin(sensorTriggerPin);
        this.sensorEchoPin = gpio.provisionDigitalInputPin(sensorEchoPin, PinPullResistance.PULL_DOWN);
    }

    @Override
    public double getDistanceCm() {
        double distanceSum = 0.0;
        for (int i = 0; i < AMOUNT_OF_MEASUREMENTS; i++) {
            distanceSum += sense();
        }
        double distanceAvg = distanceSum / AMOUNT_OF_MEASUREMENTS;
        log.info("Distance-avg: " + distanceAvg);
        return distanceAvg;
    }

    private double sense(){
        sensorTriggerPin.high();
        busyWait(10000);
        sensorTriggerPin.low();
        waitEchoPinGetsHigh();
        long startTimeNano = System.nanoTime();
        waitEchoPinGetsLow();
        long endTimeNano = System.nanoTime();
        long durationTimeNano = endTimeNano - startTimeNano;
        double distanceCm = ((((durationTimeNano)/1000)) / 58.3);
        log.info("Distance: " + distanceCm);
        try{
            Thread.sleep(1);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        return distanceCm;
    }

    private void waitEchoPinGetsHigh() {
        while(sensorEchoPin.isLow()){}
    }

    private void waitEchoPinGetsLow() {
        while(sensorEchoPin.isHigh()){}
    }

    private void busyWait(long waitNanoSeconds) {
        long nanoStart = System.nanoTime();
        while(System.nanoTime()-nanoStart < waitNanoSeconds) {}
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String toString() {
        return "Subsonic Distance Sensor " + getName();
    }
}

package motor_controller.l298n;

import com.pi4j.io.gpio.*;
import motor_controller.motor.DcMotor;

import java.util.logging.Logger;

import static motor_controller.l298n.L298NDualMotorDriver.NO_SPEED;

public class L298NDcMotor implements DcMotor {
    private static final Logger log = Logger.getLogger( L298NDcMotor.class.getName() );

    private final GpioPinPwmOutput speedPin;
    private final GpioPinDigitalOutput forwardPin;
    private final GpioPinDigitalOutput backwardPin;
    private final int minSpeed;
    private final int maxSpeed;
    private final String name;
    private int currentSpeed;

    public L298NDcMotor(GpioController gpio,
                        Pin speedPinMotorLeft,
                        Pin forwardPinMotorLeft,
                        Pin backwardPinMotorLeft,
                        int minSpeed,
                        int maxSpeed,
                        String name,
                        boolean setShutdownOptions) {

        this.speedPin = gpio.provisionPwmOutputPin(speedPinMotorLeft, "SPEED_" + name, NO_SPEED);
        this.forwardPin = gpio.provisionDigitalOutputPin(forwardPinMotorLeft, "FORWARD_" + name, PinState.LOW);
        this.backwardPin = gpio.provisionDigitalOutputPin(backwardPinMotorLeft, "BACKWARD_" + name, PinState.LOW);
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.name = name;

        if(setShutdownOptions){
            this.speedPin.setShutdownOptions(true);
            this.forwardPin.setShutdownOptions(true);
            this.backwardPin.setShutdownOptions(true);
        }
    }

    public void forward(int speed) {
        if(speed == NO_SPEED) {
            stop();
        }
        else {
            forwardPin.high();
            backwardPin.low();
            setSpeed(speed);
        }
    }

    public void backward(int speed) {
        if(speed == NO_SPEED) {
            stop();
        }
        else {
            forwardPin.low();
            backwardPin.high();
            setSpeed(speed);
        }
    }

    public void stop() {
        forwardPin.low();
        backwardPin.low();
        setSpeed(NO_SPEED);
        log.info("Stopping " + name);
    }

    private void setSpeed(int speed) {
        if(currentSpeed != NO_SPEED) {
            speedPin.setPwm(NO_SPEED);
        }
        int adjustedSpeed = adjustSpeed(speed);
        currentSpeed = adjustedSpeed;
        speedPin.setPwm(adjustedSpeed);
    }

    private int adjustSpeed(int speed) {
        int result = Math.abs(speed);
        if(speed == NO_SPEED) {
            result = NO_SPEED;
        }
        else if(speed < minSpeed) {
            result = minSpeed;
        }
        else if(speed > maxSpeed) {
            result = maxSpeed;
        }
//        log.info("adjust speed: in:" + speed + " out: " + result);
        return result;
    }

    @Override
    public String toString() {
        return "Motor-Driver: " + name;
    }
}

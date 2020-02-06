package motor_controller.l298n;

import com.pi4j.io.gpio.*;
import motor_controller.motor.DcMotor;
import motor_controller.DualMotorDriver;

public final class L298NDualMotorDriver implements DualMotorDriver {
    protected static final int NO_SPEED = 0;

    private DcMotor motorLeft;
    private  DcMotor motorRight;
    private final String name = "L298N";

    public L298NDualMotorDriver(GpioController gpio,
                                Pin speedPinMotorLeft,
                                Pin forwardPinMotorLeft,
                                Pin backwardPinMotorLeft,
                                Pin speedPinMotorRight,
                                Pin forwardPinMotorRight,
                                Pin backwardPinMotorRight,
                                int motorLeftMinSpeed,
                                int motorLeftMaxSpeed,
                                int motorRightMinSpeed,
                                int motorRightMaxSpeed) {

        this.motorLeft = new L298NDcMotor(gpio,
                speedPinMotorLeft,
                forwardPinMotorLeft,
                backwardPinMotorLeft,
                motorLeftMinSpeed,
                motorLeftMaxSpeed,
                "Motor-Left",
                true
        );
        this.motorRight = new L298NDcMotor(gpio,
                speedPinMotorRight,
                forwardPinMotorRight,
                backwardPinMotorRight,
                motorRightMinSpeed,
                motorRightMaxSpeed,
                "Motor-Right",
                true
        );
    }

    @Override
    public void forward(int speed) {
        motorLeft.forward(speed);
        motorRight.forward(speed);
    }

    @Override
    public void backward(int speed) {
        motorLeft.backward(speed);
        motorRight.backward(speed);
    }

    @Override
    public void stop() {
        motorLeft.stop();
        motorRight.stop();
    }

    @Override
    public void stopLeft() {
        motorLeft.stop();
    }

    @Override
    public void stopRight() {
        motorRight.stop();
    }

    @Override
    public void forwardLeft(int speed) {
        motorLeft.forward(speed);
    }

    @Override
    public void forwardRight(int speed) {
        motorRight.forward(speed);
    }

    @Override
    public void backwardLeft(int speed) {
        motorLeft.backward(speed);
    }

    @Override
    public void backwardRight(int speed) {
        motorRight.backward(speed);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name +
                ": motorLeft: " + motorLeft +
                "\tmotorRight: " + motorRight;
    }
}

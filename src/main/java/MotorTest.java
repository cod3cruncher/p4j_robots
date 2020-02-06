import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiPin;
import motor_controller.DualMotorDriver;
import motor_controller.l298n.L298NDualMotorDriver;
import sensor.distance.DistanceSensor;
import sensor.distance.HcSr04DistanceSensor;

public class MotorTest {

    public static void main(String[] args) throws Exception {
//        GpioController gpio = GpioFactory.getInstance();
//
//        DualMotorDriver motorDriver = new L298NDualMotorDriver(gpio,
//                RaspiPin.GPIO_23,
//                RaspiPin.GPIO_24,
//                RaspiPin.GPIO_25,
//                RaspiPin.GPIO_26,
//                RaspiPin.GPIO_27,
//                RaspiPin.GPIO_28,
//                200,
//                600,
//                200,
//                600
//        );

//        testMotors();

        testUltra();

//        System.out.println("Test");


    }

    public static void testMotors() {

        GpioController gpio = GpioFactory.getInstance();

        DualMotorDriver motorDriver = new L298NDualMotorDriver(gpio,
                RaspiPin.GPIO_23,
                RaspiPin.GPIO_24,
                RaspiPin.GPIO_25,
                RaspiPin.GPIO_26,
                RaspiPin.GPIO_27,
                RaspiPin.GPIO_28,
                200,
                600,
                200,
                600
        );

        int secDelay = 3;

        wait(secDelay);
        print("-------- Motor Test ---------");
        motorDriver.stop();
        wait(secDelay);

//        print("Left Motor forward min speed");
//        motorDriver.forwardLeft(200);
//        wait(secDelay);
//        motorDriver.stopLeft();
//        print("Left Motor forward max speed");
//        motorDriver.forwardLeft(600);
//        wait(secDelay);
//        motorDriver.stopLeft();
//
//
//        print("Left Motor backward min speed");
//        motorDriver.backwardLeft(200);
//        wait(secDelay);
//        motorDriver.stopLeft();
//        print("Left Motor backward max speed");
//        motorDriver.backwardLeft(600);
//        wait(secDelay);
//        motorDriver.stopLeft();

//        wait(secDelay);
//        print("Right Motor forward min speed");
//        motorDriver.forwardRight(200);
//        wait(secDelay);
//        print("Right Motor forward max speed");
//        motorDriver.forwardRight(600);
//        wait(secDelay);
//        print("Right Motor backward min speed");
//        motorDriver.backwardRight(200);
//        wait(2);
//        print("Right Motor backward max speed");
//        motorDriver.forwardRight(600);
//
        wait(secDelay);
        print("Stopping motors");
        motorDriver.stop();
        wait(secDelay);
        print("Both forward");
        motorDriver.forward(400);
        wait(secDelay);
        print("Stopping motors");
        motorDriver.stop();
        wait(secDelay);
        print("Both backward");
        motorDriver.backward(400);
        wait(secDelay);
        print("Changing direction 2 times");
        motorDriver.forward(400);
        wait(secDelay);
        motorDriver.backward(400);
        wait(secDelay);
        motorDriver.forward(400);
        wait(secDelay);
        motorDriver.stop();
        print("Left forward - right backward");
        motorDriver.forwardLeft(400);
        motorDriver.backwardRight(400);
        wait(secDelay);
        print("Changing");
        motorDriver.backwardLeft(400);
        motorDriver.forwardRight(400);
        wait(secDelay);
        print("end");
        motorDriver.stop();

    }

    private static void wait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void testUltra() throws InterruptedException{
        GpioController gpio = GpioFactory.getInstance();

        DistanceSensor distanceSensor = new HcSr04DistanceSensor(gpio,
                RaspiPin.GPIO_01,
                RaspiPin.GPIO_04);

        while(true) {
            System.out.println("Distance: " + distanceSensor.getDistanceCm());
            Thread.sleep(1000);
        }
    }

    private static void print(String msg) {
        System.out.println(msg);
    }


}


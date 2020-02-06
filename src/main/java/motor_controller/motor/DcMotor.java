package motor_controller.motor;

public interface DcMotor {

    void forward(int speed);
    void backward(int speed);
    void stop();
}

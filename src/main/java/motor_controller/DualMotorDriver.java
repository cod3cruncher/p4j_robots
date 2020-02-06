package motor_controller;

public interface DualMotorDriver {

    void forward(int speed);
    void backward(int speed);
    void stop();
    void forwardLeft(int speed);
    void forwardRight(int speed);
    void backwardLeft(int speed);
    void backwardRight(int speed);
    void stopLeft();
    void stopRight();
    String getName();



}

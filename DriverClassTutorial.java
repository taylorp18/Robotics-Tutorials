package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Phoebe Taylor on 9/29/2017.
 * This class is a tutorial for running a linear drive train. It's fairly simple, and I'll write
 * it with four motors, a Continuous Rotation servo, and a 180 degree servo.
 * You don't need to comment your code this much, but definitely put the grey'd out comments throughout
 * your code so other people will be able to understand what you're writing... even if it's obvious!
 * Generally, the comments will be the grey ones except for this header comment...
 */

/* The following statement tells the program what to call itself on the phones when you are selecting which
 * program to run. The group name isn't really important right now, just put a default one. For the
 * name, make it simple but explanatory, it is the name that will show up on the phone so make sure
 * it is descriptive enough to differentiate it from another code
 */
@TeleOp (name = "group_name: DriverClassTutorial", group = "group_name")

/* make sure you extend LinearOpMode, if you don't you won't be able to use the default methods that
FIRST gives you
 */
public class DriverClassTutorial extends LinearOpMode {

    /** make an object of your hardware class called robot so you can use the methods and motors in
    that class, i.e., frontRight, backLeft, etc.
     */
    HardwareClassTutorial robot = new HardwareClassTutorial();

    //not sure why you need this... just put it
    @Override
    //this function will run over and over when you hit init on the phone
    public void runOpMode()
    {
        /** make variables that will be assigned to the value of the y axes of the joysticks. For this
        kind of drivetrain (linear), you will use the y value of the left joystick for the left two
        wheels and the y value of the right joystick to control the right two motors
         */
        double left_y;
        double right_y;

        //run the init function of the hardware class
        robot.init(hardwareMap);

        //tell the robot not to run this code unless the start button is pressed
        waitForStart();

        //this code runs when start has been pressed and opmode is activated
        while(opModeIsActive()) {
            /** it is good practice to make a 'buffer zone' in the code so that the robot won't move
            if you barely tap the joystick... That's what the else parts of the following if-else
             statements do. Inside, we are setting the variables for left and right y axis variables
             to the position of the joystick.
             */
            if (gamepad1.left_stick_y > 0.1 || gamepad1.left_stick_y < -0.1) {
                /* remember to make the joystick value negative, because the gamepad for some reason
                makes "up" on the y axis negative and "down" on the y axis negative */
                left_y = -gamepad1.left_stick_y;
            }
            else {
                left_y = 0;
            }
            if (gamepad1.right_stick_y > 0.1 || gamepad1.right_stick_y < -0.1) {
                right_y = -gamepad1.right_stick_y;
            }
            else {
                right_y = 0;
            }

            /** set the 180 degree servo to a 0.9 position when right bumper is hit, and 0.1 position
             * when left bumper is hit. When nothing is pressed, it will default to 0.5. Positions
             * for 180 servos are in radians and go from 1 to 0. Try not to make them go all the way
             * to either 0 or 1, because it could strain the gears. This is essentially a waving motion
             */
            if (gamepad1.right_bumper) {
                robot.oneEightyServo.setPosition(0.9);
            }
            else if (gamepad1.left_bumper) {
                robot.oneEightyServo.setPosition(0.1);
            }
            else {
                robot.oneEightyServo.setPosition(0.5);
            }

            /** set the continuous rotation servo to power 1 when you press the a button */
            if (gamepad1.a) {
                robot.conRotationServo.setPower(1.0);
            }
            else {
                robot.conRotationServo.setPower(0.0);
            }

            /** set all the motors to the correct power that you just declared above */
            robot.frontLeft.setPower(left_y);
            robot.backLeft.setPower(left_y);
            robot.frontRight.setPower(right_y);
            robot.backRight.setPower(right_y);




        }

    }

}

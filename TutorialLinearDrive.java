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
 *
 * NEVER ENABLE INSTANT RUN ON ANDRIOD STUDIO. EVER. DON'T DO IT. MS. DISANTO SAID TO SAY THAT. SO DON'T TRY IT.
 */

/* The following statement tells the program what to call itself on the phones when you are selecting which
 * program to run. The group name isn't really important right now, just put a default one. For the
 * name, make it simple but explanatory, it is the name that will show up on the phone so make sure
 * it is descriptive enough to differentiate it from another code
 */
@TeleOp (name = "DriverClassTutorial", group = "group_name")

/* make sure you extend LinearOpMode, if you don't you won't be able to use the default methods that
FIRST gives you
 */


public class TutorialLinearDrive extends LinearOpMode {

    /** make an object of your hardware class called robot so you can use the methods and motors in
     that class, i.e., frontRight, backLeft, etc.
     */
    TutorialHardware robot = new TutorialHardware();

    //the next two variables will serve as the running value for the incrementation of the grabbers open/closed
    public double servo_value = 0.01; //open (is 0)

    //the following two variables will be the close value limits for the bottom two grabbers
    public double close_bottom_left = 0.47; //was .48 //closed is 1
    public double close_bottom_right = 0.46; //was .48 //closed is 0

    //the next two variables will serve as the running value for the incrementation of the grabbers open/closed
    public double top_left_grab = 0.01; //open (is 0)
    public double top_right_grab = 0.80; //open (is 1)
    //the following two variables will be the open value limits for the top two grabbers
    public double close_top_left = 0.47; // closed is 1 //was 0.45
    public double close_top_right = 0.46; //closed is 0 //was 0.48


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
             * for 180 servos have a range from 1 to 0. Try not to make them go all the way
             * to either 0 or 1, because it could strain the gears. This is essentially a waving motion
             */

             /* FOR DEBUGGING THIS: my suggestion is the make the white boards your best friend. Don't
             * even try to debug servo positions without a working robot to test on. Just sort of guess
             * and put in some positions that seem like they are reasonable, and then do trial and error
             * while testing. Change in small increments (you'd be surprised how different 0.1 is from 0.18)
             * Helpful tip for that, when doing something like a grabber, write down in comments everywhere
             * whether open is 1 or 0, and whether closed is 1 or 0, visualizing on a white board if that
             * helps you (I know that's the same information, but honestly I am such a visual learner that
             * unless I wrote that kind of thing down very explicitly, I got so confused)
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

            /** This next method is another way of moving a 180 servo. Instead of going position-position,
             * I started using an incrementation method last year that worked a lot better for our grabbers.
             * Essentially, you set two outer limits (most open you want it to go, and most closed you
             * want it to go) and then increment a variable very slowly so that it changes position
             * slowly as you hold down a button.
             */

            /* TIPS FOR DEBUGGING THIS: By good coding convention, try not to edit numberic values in
            the code itself (as in, this far down in the code). You want a variable that represents
            the max or min (or whatever you're coding) to be far up at the top of the code so it's
            easier to access and less likely you'll accidentally do something to mess it all up (which
            happens a lot, trust me. You have one tired day and aren't really paying attention and suddenly
            nothing works)
             */
            //close
            while (gamepad1.dpad_left && servo_value < close_bottom_left){
                //this second term in the while statement line is necessary so that you don't allow
                // it to close or open past the limits you give it. The problem will come when you
                // DON'T have this and it allows the count to be way outside the 0-1 limit of servos
                // (don't let it tell the servo to open to -8)
                robot.oneEightyServo.setPosition(servo_value);
                //increment closed
                servo_value += 0.006;
            }
            //open
            while (gamepad1.dpad_right && servo_value > 0.05) {
                //bottom pair of grabbers
                robot.oneEightyServo.setPosition(servo_value);
                //increment open
                servo_value -= 0.006;
            }
            //close
            while (gamepad1.dpad_down && servo_value < close_top_left){
                robot.oneEightyServo.setPosition(servo_value);
                //increment closed
                servo_value += 0.006;
            }

            /** set the continuous rotation servo to power 1 when you press the a button */
            if (gamepad1.a) {
                robot.conRotationServo.setPower(1.0);
            }
            else {
                robot.conRotationServo.setPower(0.0);
            }

            /** set all the motors to the correct power that you just declared above (where you process
             * the joystick signal) */
            robot.frontLeft.setPower(left_y);
            robot.backLeft.setPower(left_y);
            robot.frontRight.setPower(right_y);
            robot.backRight.setPower(right_y);




        }

    }

}

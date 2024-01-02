package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "Drone Test")
public class DroneTest extends LinearOpMode {
    DcMotor droneLauncher;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        droneLauncher = hardwareMap.dcMotor.get("Drone_Motor");

        droneLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        droneLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        droneLauncher.setTargetPosition(-250);
        droneLauncher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        droneLauncher.setPower(0.05);

        runtime.reset();
        while (opModeIsActive() &&
                (runtime.seconds() < 5) &&
                ((droneLauncher.isBusy()))) {

            // Display it for the driver.
            telemetry.addData("Goal", "Running to :%7d", droneLauncher.getTargetPosition());
            telemetry.addData("Actual distance moved", "Running at %7d",
                    droneLauncher.getCurrentPosition());
            telemetry.addData("Speed", droneLauncher.getPower());
            telemetry.update();
        }

            droneLauncher.setPower(0);
            droneLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}

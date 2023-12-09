package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "Color Sensor")
public class colorsensortest extends LinearOpMode {

    ColorSensor colorSensor;
    @Override
    public void runOpMode(){
        colorSensor = hardwareMap.colorSensor.get("ColorSensor");

        waitForStart();
        while(opModeIsActive()){
            if(colorSensor.blue() >= colorSensor.red()+2 && colorSensor.blue() >= colorSensor.green()+2){
                telemetry.addData("Detected Item", "Blue MayTag");
            }
            if(colorSensor.red() >= colorSensor.blue()+2  && colorSensor.red() >= colorSensor.green()+2){
                telemetry.addData("Detected Item", "Red MayTag");
            }
            telemetry.addData("Color at the moment (RGB)", "&7d %7d %7d", colorSensor.red(),colorSensor.green(), colorSensor.blue());
            telemetry.update();
        }
    }
}

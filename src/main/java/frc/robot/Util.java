// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class Util {
    public static double deadband(double value, double band) {
        if(Math.abs(value) < band)
            return 0;
        return value;
    }
}

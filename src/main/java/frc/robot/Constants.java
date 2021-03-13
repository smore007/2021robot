/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    
    public static int kDTBackLeftTalon = -1;
    public static int kDTFrontLeftTalon = -1;
    public static int kDTBackRightTalon = -1;
    public static int kDTFrontRightTalon = -1;
    
    public static int kIndexerTalon = -1;
    public static int kGatewayTalon = -1;
    public static int kShooterTalonMaster = -1;
    public static int kShooterTalonSlave = -1;

    public static int kShooterPistonForward = -1;
    public static int kShooterPistonReverse = -1;

    public static double kDistanceToRaise = 5;

    public static double kAcceptableShooterError = 50;
    public static double xuru(double distance, boolean raised) {
        return 0;
    }
}

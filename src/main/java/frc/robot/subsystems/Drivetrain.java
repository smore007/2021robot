/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  
  WPI_TalonSRX m_leftMaster = new WPI_TalonSRX(Constants.kDTBackLeftTalon);
  WPI_TalonSRX m_leftSlave = new WPI_TalonSRX(Constants.kDTFrontLeftTalon);
  WPI_TalonSRX m_rightMaster = new WPI_TalonSRX(Constants.kDTBackRightTalon);
  WPI_TalonSRX m_rightSlave = new WPI_TalonSRX(Constants.kDTFrontRightTalon);
  
  DifferentialDrive m_drive = new DifferentialDrive(m_leftMaster, m_rightMaster);

  /**
   * Creates a new Drivetrain.
   */
  public Drivetrain() {

  }

  public void arcadeDrive(double speed, double turn) {
    m_drive.arcadeDrive(speed, turn);
  }
}

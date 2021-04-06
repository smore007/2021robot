/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Gateway extends SubsystemBase {
  
  public WPI_TalonSRX m_wheel = new WPI_TalonSRX(Constants.kGatewayTalon);
  
  /**
   * Creates a new Gateway.
   */
  public Gateway() {
    SmartDashboard.putData(m_wheel);
  }

  public void spin(double percent) {
    m_wheel.set(ControlMode.PercentOutput, percent);
  }
}

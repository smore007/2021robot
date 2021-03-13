// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

  NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");;
  
  NetworkTableEntry m_tv = m_table.getEntry("tv");
  NetworkTableEntry m_tx = m_table.getEntry("tx");
  NetworkTableEntry m_ty = m_table.getEntry("ty");
  NetworkTableEntry m_ledMode =  m_table.getEntry("ledMode");
  NetworkTableEntry m_camMode = m_table.getEntry("camMode");

  /** Creates a new Limelight. */
  public Limelight() {
    
  }

  public double getDistance() {
    // TODO
    return -1;
  }
}

package entities;

/**
 * Created by mac on 25.07.2017.
 */
public enum EntityType {
    DI, // DI, DI14
    DO, // DOS
    AI, // AIK
    AO, // AO, AO6

    // DI based
    SensorSwitch,

    // DOS based
    Relay,
    Valve,
    Motor,

    // AI based
    Sensor,

    // AO based
    PWM,

    // Complex types
    Cylinder,
    MotorPWM,
}

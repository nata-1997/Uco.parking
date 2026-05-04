package co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.outport;

import application.outport.Outport;
import co.edu.uco.ucoparking.initializer.features.student.registernewstudent.application.usecase.RegisterNewStudentDomain;

/**
 * Puerto de persistencia del caso de uso: persiste un estudiante nuevo (dominio ya validado).
 */
public interface RegisterNewStudentOutPort extends Outport<RegisterNewStudentDomain, Void> {
}


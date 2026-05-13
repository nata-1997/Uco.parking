package co.edu.uco.ucoparking.application.inputport;


//capa facade//
public interface InputPort <T,R>{
    R execute(T data);
}

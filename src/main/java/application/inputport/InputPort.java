package application.inputport;


//capa facade//
public interface InputPort <T,R>{
    R execute(T data);
}

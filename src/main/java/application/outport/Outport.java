package application.outport;

public interface Outport <T,R>{
    R execute(T data);
}


package infrastructure.persistence.crossscutting.Helper;

public class ObjectHelper {

    private ObjectHelper() {
    }

    public static <O> boolean isNull(final O object){
        return object == null;
    }

    public static  <O> O getDefaultIfNull(final O object, final O defaultValue){
        return (isNull(object) ? defaultValue : object);
    }

}

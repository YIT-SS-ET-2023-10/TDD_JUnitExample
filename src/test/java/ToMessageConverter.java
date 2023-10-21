import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class ToMessageConverter extends SimpleArgumentConverter {
    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        checkSource(source);
        checkTarget(targetType);

        String sourceString = (String)source;
        return new Message(sourceString);
    }

    private void checkTarget(Class<?> targetType){
        System.out.println(targetType.getName());
        if (!targetType.equals(Message.class))
            throw new ArgumentConversionException("Can only convert to Message");

    }

    private void checkSource(Object source){
        if (source == null){
            throw new ArgumentConversionException("Cannot convert null source object");
        }

        if (!source.getClass().equals(String.class)){
            throw new ArgumentConversionException("Cannot convert source object because it's not a string");
        }

        String sourceString = ((String)source).trim();
        if(sourceString.isEmpty())
            throw new ArgumentConversionException("Cannot convert empty source object");

    }

}


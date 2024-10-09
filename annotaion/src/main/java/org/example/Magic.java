package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//애노테이션을 어디까지 유지할 것인지
//바이트 코드에는 필요없음
@Target(ElementType.TYPE) // Interface, Class, Enum
@Retention(RetentionPolicy.SOURCE)
public @interface Magic {

}

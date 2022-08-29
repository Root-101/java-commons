package dev.root101.clean.core.utils.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Enum target must implement an static 'isPresent' method.
 * <pre>
 *
 *  public enum Type {
 *      a("a"),
 *      b("b"),
 *      c("c"),
 *      d("d"),
 *      e("e"),
 *      f("f"),
 *      g("g"),
 *      h("h"),
 *      i("i");
 *
 *      private final String name;
 *
 *      private Type(String name) {
 *          this.name = name;
 *      }
 *
 *      @Override
 *      public String toString() {
 *          return name;
 *      }
 *
 *      public boolean equals(String statusName) {
 *          return this.toString().equalsIgnoreCase(statusName.trim().toLowerCase());
 *      }
 *      
 *      
 *      public static boolean isPresent(String event) {
 *          return List.of(Type.values()
 *          ).stream().anyMatch((Type t) -> {
 *              return t.equals(event);
 *          });
 *      }
 *  }
 *
 * </pre>
 *
 * @author Yo
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumValueRegister_String.class})
public @interface EnumValue {

    String message() default "Value is not present in enum list.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public String detailMessage() default "";

    public Class<? extends Enum> target();
}

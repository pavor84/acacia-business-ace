/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.annotation;

import java.awt.LayoutManager;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import net.miginfocom.swing.MigLayout;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Miro
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
public @interface Layout {

    public static final String DEFAULT_CONSTRAINTS = "wrap {0}, fillx";
    public static final String[] DEFAULT_COLUMNS_CONSTRAINTS_PAIR =
        {
            "sizegroup labelSG",
            "sizegroup valueSG, grow 50, fill"
        };
    public static final int DEFAULT_COLUMNS_PAIR_GAP = 10;
    public static final String DEFAULT_ROW_CONSTRAINTS = "";

    Class<? extends LayoutManager> layoutClass() default MigLayout.class;

    int columnsPairs() default 2;

    int columnCount() default -1;

    String constraints() default "";

    String[] columnsConstraints() default {};

    String[] rowsConstraints() default {};

    String extraConstraints() default "";

    String[] extraColumnsConstraints() default {};

    String[] extraRowsConstraints() default {};

    int[] columnsGaps() default {};

    int[] rowsGaps() default {};
}

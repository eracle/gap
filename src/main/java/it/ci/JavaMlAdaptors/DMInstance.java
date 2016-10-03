package it.ci.JavaMlAdaptors;

import net.sf.javaml.core.Instance;

/**
 * Created by eracle on 08/09/16.
 */
public class DMInstance extends NotAVectorInstance {
    @Override
    public String toString() {
        return "" + matrix_index;
    }

    protected int matrix_index;

    public DMInstance(int matrix_index){
        this.matrix_index = matrix_index;
    }

    public Instance copy() {
        return new DMInstance(this.matrix_index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DMInstance doubles = (DMInstance) o;

        return matrix_index == doubles.matrix_index;

    }

    @Override
    public int getID() {
        return matrix_index;
    }
}

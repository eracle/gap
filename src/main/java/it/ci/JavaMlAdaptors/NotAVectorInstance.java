package it.ci.JavaMlAdaptors;

import net.sf.javaml.core.Instance;

import java.util.*;

/**
 * Created by eracle on 03/10/16.
 */
public abstract class NotAVectorInstance implements Instance {


    public int noAttributes() {
        return 0;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public Instance divide(Instance currentRange) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public Instance divide(double value) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public Instance multiply(Instance value) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public Instance multiply(double value) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public Object classValue() {
        return null;
    }

    @Override
    public Iterator<Double> iterator() {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }


    @Override
    public Instance minus(Instance min) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public Instance sqrt() {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public void setClassValue(Object value) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public Instance add(Instance max) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public Instance minus(double value) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }

    @Override
    public Instance add(double value) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }
    @Override
    public void removeAttribute(int i) {
        throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");
    }
    @Override
    public double value(int i) {throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public void removeAttributes(java.util.Set<java.lang.Integer> indices){throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public void    clear(){ throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public boolean containsKey(Object key){ throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public boolean containsValue(Object value) { throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public Set<Entry<Integer,Double>> entrySet(){ throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public Double   get(Object key){ throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public boolean isEmpty(){ throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public int size(){ throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public Collection<Double> values(){ throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public SortedSet<Integer> keySet(){ throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public Double   put(Integer key, Double value){throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public void    putAll(Map<? extends Integer,? extends Double> m){throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}
    @Override
    public Double   remove(Object key){throw new UnsupportedOperationException("DistanceOnlyInstance do not map attributes to values");}

}

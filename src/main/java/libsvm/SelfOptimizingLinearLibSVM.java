/**
 * This file is part of the Java Machine Learning Library
 * 
 * The Java Machine Learning Library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * The Java Machine Learning Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the Java Machine Learning Library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * Copyright (c) 2006-2012, Thomas Abeel
 * 
 * Project: http://java-ml.sourceforge.net/
 * 
 */
package libsvm;

import java.util.Map;
import java.util.Random;

import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.utils.ArrayUtils;

/**
 * A svm variant the optimizes the C-paramater by itself.
 * 
 * @author Thomas Abeel
 * 
 */
public class SelfOptimizingLinearLibSVM extends LibSVM {

	private static final long serialVersionUID = -2828394692981048239L;

	private LibSVM optimal;

	private double optimalC;

	private int folds;

	private Random rg = new Random(System.currentTimeMillis());

	private int lowerC, upperC;

	public SelfOptimizingLinearLibSVM() {
		this(-5, 5);
	}

	public SelfOptimizingLinearLibSVM(int lowerC, int upperC) {
		this(lowerC, upperC, 4);

	}

	public SelfOptimizingLinearLibSVM(int lowerC, int upperC, int internalFolds) {
		this.lowerC = lowerC;
		this.upperC = upperC;
		this.folds = internalFolds;
	}

	/**
	 * Returns a map of all f-measure that were encountered while searching for
	 * the optimal C value.
	 * 
	 * @return
	 */
	public double[] getFMeasures() {
		return fmeasures;
	}

	private double[] fmeasures;

	// commit test
	public void buildClassifier(Dataset data) {
		double[] result = new double[upperC - lowerC];

		for (int i = lowerC; i < upperC; i++) {
			LibSVM svm = new LibSVM();
			svm.getParameters().C = Math.pow(2, i);
			CrossValidation cv = new CrossValidation(svm);
			Map<Object, PerformanceMeasure> score = cv.crossValidation(data,
					folds, rg);
			try {
				for (Object o : score.keySet())
					result[i - lowerC] += score.get(o).getFMeasure();

			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				// System.out.println(positiveClass.getClass());
				System.err.println(score.keySet().iterator().next().getClass());
				// System.err.println(positiveClass);
				System.err.println(score);
				// System.err.println(score.get(positiveClass));
				e.printStackTrace();
				System.exit(-1);
			}

		}
		fmeasures = result;
		int index = ArrayUtils.maxIndex(result);
		optimal = new LibSVM();
		optimalC = Math.pow(2, index + lowerC);
		optimal.getParameters().C = optimalC;

		optimal.buildClassifier(data);
	}

	@Override
	public Object classify(Instance instance) {
		return optimal.classify(instance);
	}

	@Override
	public Map<Object, Double> classDistribution(Instance instance) {
		return optimal.classDistribution(instance);
	}

	public double getC() {
		return optimalC;
	}

	public double[] getWeights() {
		return optimal.getWeights();
	}

	public final void setFolds(int folds) {
		this.folds = folds;
	}

}

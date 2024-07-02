package com.enoch.reports;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.enoch.common.exception.ServiceException;

public class LineChartGenerator {
	public static void getCHart(String appTitle, String xAxisTitle, String yAxisTitle,
			Supplier<CategoryDataset> dataset,File f ) {
		try {
			JFreeChart chart = ChartFactory.createLineChart(appTitle, // Chart Title
					xAxisTitle, // Category axis
					yAxisTitle, // Value axis
					dataset.get(), PlotOrientation.VERTICAL, true, true, false);
			
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) ((CategoryPlot) chart.getPlot()).getRenderer();

			renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setDefaultItemLabelsVisible(true);
			ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
			renderer.setDefaultPositiveItemLabelPosition(position);

			
			BufferedImage image = chart.createBufferedImage(800, 400);
			ImageIO.write(image, "png", f);
		} catch (Exception e) {
			throw new ServiceException("Error creating chart", e);
		}
	}

	private CategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		// Population in 2005
		dataset.addValue(10, "USA", "2005");
		dataset.addValue(15, "India", "2005");
		dataset.addValue(20, "China", "2005");

		// Population in 2010
		dataset.addValue(15, "USA", "2010");
		dataset.addValue(20, "India", "2010");
		dataset.addValue(25, "China", "2010");

		// Population in 2015
		dataset.addValue(20, "USA", "2015");
		dataset.addValue(25, "India", "2015");
		dataset.addValue(30, "China", "2015");

		return dataset;
	}

}
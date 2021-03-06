package tanks;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Graph extends ApplicationFrame {
    public Graph(String applicationTitle, String chartTitle, XYDataset dataset, long unixTimestamp) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Generace",
                "Fitness",
                dataset,
                PlotOrientation.VERTICAL,
                true, false, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = xylineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        renderer.setSeriesStroke(1, new BasicStroke(1.0f));
        renderer.setBaseShapesVisible(false);
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
        createScreen(xylineChart, unixTimestamp);
    }

    public BufferedImage createScreen(JFreeChart chart, long time) {
        BufferedImage image = new BufferedImage(800, 470, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        //g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
        Rectangle r = new Rectangle(0, 0, 800, 470);
        chart.draw(g2, r);
        String dstDir = "out/img";
        boolean mkdirs = new File(dstDir).mkdirs();
        File f = new File(dstDir + "/RobocodeGraph" + time + ".png");

        BufferedImage chartImage = chart.createBufferedImage(800, 470, null);
        try {
            ImageIO.write(chartImage, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chartImage;
    }
}
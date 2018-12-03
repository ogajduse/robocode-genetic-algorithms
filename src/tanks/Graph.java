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
import java.time.Instant;

public class Graph extends ApplicationFrame {
    public Graph(String applicationTitle, String chartTitle, XYDataset dataset) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Category",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = xylineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
        createScreen(xylineChart);
    }

    public BufferedImage createScreen(JFreeChart chart) {
        BufferedImage image = new BufferedImage(800, 470, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        //g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
        Rectangle r = new Rectangle(0, 0, 800, 470);
        chart.draw(g2, r);
        long time = Instant.now().getEpochSecond();
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
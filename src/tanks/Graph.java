package tanks;

/* installed libraries:
- * org.jfree:jcommon:1.0.17
- * org.jfree:jcommon:1.0.24
- * org.jfree:jfreechart:1.0.19 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class Graph extends ApplicationFrame {
    /**
     * The plot.
     */
    private XYPlot plot;

    public Graph(final String title) {

        super(title);
        final TimeSeriesCollection dataset1 = createRandomDataset("Nejlepsi tank");
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Robocode results graph", "Generace", "Fitness", dataset1
        );
        chart.setBackgroundPaint(Color.white);
        this.plot = chart.getXYPlot();
        this.plot.setBackgroundPaint(Color.lightGray);
        this.plot.setDomainGridlinePaint(Color.white);
        this.plot.setRangeGridlinePaint(Color.white);
        this.plot.setDataset(1, createRandomDataset("Druhy nejlepsi tank"));
        this.plot.setRenderer(1, new StandardXYItemRenderer());
        this.plot.setDataset(2, createRandomDataset("Treti nejlepsi tank"));
        this.plot.setRenderer(2, new StandardXYItemRenderer());
        final ValueAxis axis = this.plot.getDomainAxis();
        axis.setAutoRange(true);
        final JPanel content = new JPanel(new BorderLayout());
        final ChartPanel chartPanel = new ChartPanel(chart);
        content.add(chartPanel);

        chartPanel.setPreferredSize(new java.awt.Dimension(800, 470));
        setContentPane(content);

        // export grafu do PNG
        BufferedImage image = new BufferedImage(800, 470, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true);
        Rectangle r = new Rectangle(0, 0, 800, 470);
        chart.draw(g2, r);
        long time = Instant.now().getEpochSecond();
        File f = new File("/tmp/RobocodeGraph" + time + ".png");

        BufferedImage chartImage = chart.createBufferedImage(800, 470, null);
        try {
            ImageIO.write(chartImage, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TimeSeriesCollection createRandomDataset(final String name) {
        final TimeSeries series = new TimeSeries(name);
        double value = 100.0;
        RegularTimePeriod t = new Day();
        for (int i = 0; i < 50; i++) {
            series.add(t, value);
            t = t.next();
            value = value * (1.0 + Math.random() / 100);
        }
        return new TimeSeriesCollection(series);
    }

    public static void main(final String[] args) {
        final Graph demo = new Graph("Robocode results graph");
        demo.pack();
        //RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
package example.graph

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.ui.RectangleInsets
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.Color
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

class GraphBuilder(title: String, xLabel: String, yLabel: String) {
    private val series: XYSeries
    private val chart: JFreeChart

    init {
        series = XYSeries(title)
        chart = createChart(title, xLabel, yLabel)
    }

    fun addData(x: Double, y: Double) {
        series.add(x, y)
    }

    @Throws(IOException::class)
    fun saveChart(path: Path) {
        if (Files.exists(path)) {
            Files.delete(path)
        }
        ChartUtils.saveChartAsJPEG(path.toFile(), chart, 1200, 600)
    }

    private fun createChart(title: String, xLabel: String, yLabel: String): JFreeChart {
        val chart = ChartFactory.createXYLineChart(
            title,
            xLabel,
            yLabel,
            XYSeriesCollection(series),
            PlotOrientation.VERTICAL,
            false,
            false,
            false
        )
        chart.backgroundPaint = Color.white

        val plot = chart.xyPlot

        plot.backgroundPaint = Color.white
//        plot.backgroundPaint = Color(232, 232, 232)
        plot.domainGridlinePaint = Color.gray
        plot.rangeGridlinePaint = Color.gray
        plot.axisOffset = RectangleInsets(1.0, 1.0, 1.0, 1.0)
//        plot.rangeAxis.isAxisLineVisible = false
        return chart
    }
}

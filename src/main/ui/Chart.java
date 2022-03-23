package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Chart extends JPanel {

    private List<Integer> values;
    private List<String> names;
    private static final String title = "Spending Chart";

    public Chart(List<Integer> values, List<String> names) {
        this.values = values;
        this.names = names;
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values == null || values.size() == 0) {
            return;
        }
        int min = findMin();
        int max = findMax();

        Dimension dim = getSize();
        int width = dim.width;
        int height = dim.height;
        int barWidth = width / values.size();
        Font titleFont = new Font("Book Antiqua", Font.BOLD, 15);
        FontMetrics titleMetrics = g.getFontMetrics(titleFont);

        Font label = new Font("Book Antiqua", Font.PLAIN, 14);
        FontMetrics labelMetrics = g.getFontMetrics(label);

        int titleWidth = titleMetrics.stringWidth(title);
        int stringHeight = titleMetrics.getAscent();
        int stringWidth = (width - titleWidth) / 2;
        g.setFont(titleFont);
        g.drawString(title, stringWidth, stringHeight);

        int top = titleMetrics.getHeight();
        int bottom = labelMetrics.getHeight();
        if (max == min) {
            return;
        }
        int scale = (height - top - bottom) / (max - min);
        stringHeight = height - labelMetrics.getDescent();
        g.setFont(label);
        for (int i = 0; i < values.size(); i++) {
            int valP = i * barWidth + 1;
            int valQ = top;
            int barHeight = (values.get(i) * scale);
            if (values.get(i) >= 0) {
                valQ += (max - values.get(i)) * scale;
            } else {
                valQ += max * scale;
                barHeight = -barHeight;
            }
            g.setColor(new Color(55, 50, 62));
            g.fillRect(valP, valQ, barWidth - 2, barHeight);
            g.setColor(Color.black);
            g.drawRect(valP, valQ, barWidth - 2, barHeight);
            int labelWidth = labelMetrics.stringWidth(names.get(i));
            stringWidth = i * barWidth + (barWidth - labelWidth) / 2;
            g.drawString(names.get(i), stringWidth, stringHeight);
        }
    }

    private int findMin() {
        int min = 0;
        for (Integer i : values) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }

    private int findMax() {
        int max = 0;
        for (Integer i : values) {
            if (max < i) {
                max = i;
            }
        }
        return max;
    }

}

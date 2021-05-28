package com.wirehall.audiorecorder.visualizer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class LineVisualizerView extends BaseVisualizerView {
  private final Rect rect = new Rect();
  private float[] points;
  private float strokeWidth = 0.005f;

  public LineVisualizerView(Context context) {
    super(context);
  }

  public LineVisualizerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public LineVisualizerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void init(@Nullable AttributeSet attrs) {
    // No initialization required
  }

  /**
   * set stroke width for your visualizer takes input between 1-10
   *
   * @param strokeWidth stroke width between 1-10
   */
  public void setStrokeWidth(int strokeWidth) {
    this.strokeWidth = computeStroke(strokeWidth);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (bytes != null) {
      if (points == null || points.length < bytes.length * 4) {
        points = new float[bytes.length * 4];
      }
      paint.setStrokeWidth(getHeight() * strokeWidth);
      rect.set(0, 0, getWidth(), getHeight());

      for (int i = 0; i < bytes.length - 1; i++) {
        points[i * 4] = (float) rect.width() * i / (bytes.length - 1);
        points[i * 4 + 1] =
            (float) (rect.height() >> 1)
                + (((byte) (bytes[i] + 128)) * ((int) (rect.height() / 3.0)) >> 7);
        points[i * 4 + 2] = (float) rect.width() * (i + 1) / (bytes.length - 1);
        points[i * 4 + 3] =
            (float) (rect.height() >> 1)
                + (((byte) (bytes[i + 1] + 128)) * ((int) (rect.height() / 3.0)) >> 7);
      }
      canvas.drawLines(points, paint);
    }
    super.onDraw(canvas);
  }
}

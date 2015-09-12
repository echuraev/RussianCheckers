package com.intel.core.board;

public class CellRect {
    private float left;
    private float top;
    private float right;
    private float bottom;

    public CellRect(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public CellRect(double left, double top, double right, double bottom) {
        this.left = (float) left;
        this.top = (float) top;
        this.right = (float) right;
        this.bottom = (float) bottom;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public float getWidth() {
        return Math.abs(this.right - this.left);
    }

    public float getHeight() {
        return Math.abs(this.bottom - this.top);
    }

    public boolean contains(float x, float y) {
        return left < right && top < bottom  // check for empty first
                && x >= left && x < right && y >= top && y < bottom;
    }
}

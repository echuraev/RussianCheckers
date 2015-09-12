package com.intel.russiancheckers.ui;


import com.intel.core.board.BoardCell;
import com.intel.core.board.CellRect;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.ByValue;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.Owned;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.general.ptr.ConstNFloatPtr;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.coregraphics.c.CoreGraphics;
import ios.coregraphics.enums.CGColorRenderingIntent;
import ios.coregraphics.enums.CGTextDrawingMode;
import ios.coregraphics.enums.CGTextEncoding;
import ios.coregraphics.opaque.CGColorRef;
import ios.coregraphics.opaque.CGContextRef;
import ios.coregraphics.struct.CGRect;
import ios.uikit.UIColor;
import ios.uikit.UIView;
import ios.uikit.c.UIKit;
import com.intel.core.rules.GameBoard;

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("ChessBoardView")
@RegisterOnStartup
public class ChessBoardView extends UIView {
    private final float externalMargin = 5;
    private final int navBarHeight = 64;
    private final CGColorRef WHITE_PIECE_COLOR = UIColor.redColor().CGColor();
    private final CGColorRef BLACK_PIECE_COLOR = UIColor.blueColor().CGColor();
    private final CGColorRef CROWN_COLOR = UIColor.yellowColor().CGColor();
    private final CGColorRef HIGHLIGHT_COLOR = UIColor.greenColor().CGColor();
    private double screenW;
    private double screenH;
    private double cellSize;
    private double boardMargin;
    private CGContextRef context;
    private GameBoard gameBoard;

    static {
        NatJ.register();
    }

    @Generated("NatJ")
    @Owned
    @Selector("alloc")
    public static native ChessBoardView alloc();

    protected ChessBoardView(Pointer peer) {
        super(peer);
        gameBoard = new GameBoard();
    }

    @Override
    @Selector("drawRect:")
    public void drawRect(@ByValue CGRect rect) {
        context = UIKit.UIGraphicsGetCurrentContext();
        CoreGraphics.CGContextClearRect(context, rect);
        screenH = this.bounds().size().height();
        screenW = this.bounds().size().width();
        System.out.println("ScreenH: " + String.valueOf(screenH));
        System.out.println("ScreenW: " + String.valueOf(screenW));
        CoreGraphics.CGContextSetRGBFillColor(context, 255, 255, 255, 1);
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(0, 0, screenW, screenH));

        drawBoard();
        drawCells();
        drawPieces();
    }

    private void drawBoard() {
        CoreGraphics.CGContextSetRGBFillColor(context, 0, 0, 0, 1);
        double boardW = screenW;
        double boardH = screenW;
        if (screenH < screenW)
        {
            boardW = screenH;
            boardH = screenH;
        }
        System.out.println("BoardW: " + String.valueOf(boardW));
        System.out.println("BoardH: " + String.valueOf(boardH));
        double rectX = externalMargin;
        double rectY = externalMargin + navBarHeight;
        double rectWidth = boardW - 2*externalMargin;
        double rectHeight = boardH - 2*externalMargin;
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rectX, rectY, rectWidth, rectHeight));

        CoreGraphics.CGContextSetRGBFillColor(context, 255, 255, 255, 1);

        rectX = externalMargin + 1;
        rectY = externalMargin + navBarHeight + 1;
        rectWidth = boardW - 2*(externalMargin + 1);
        rectHeight = boardH - 2*(externalMargin + 1);
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rectX, rectY, rectWidth, rectHeight));
        CoreGraphics.CGContextSetRGBFillColor(context, 0, 0, 0, 1);
        boardMargin = boardW / 12;
        rectX = boardMargin - 1;
        rectY = boardMargin - 1 + navBarHeight;
        rectWidth = boardW - 2*(boardMargin - 1);
        rectHeight = boardH - 2*(boardMargin - 1);
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rectX, rectY, rectWidth, rectHeight));
        double boardSize = boardW - 2*boardMargin;
        cellSize = boardSize / GameBoard.CELL_COUNT;
        drawCellsNames();
    }

    private void drawCellsNames() {
        String[] xTitle = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] yTitle = {"1", "2", "3", "4", "5", "6", "7", "8"};


        CoreGraphics.CGContextSelectFont(context, "Helvetica", cellSize / 2, CGTextEncoding.kCGEncodingMacRoman);
        CoreGraphics.CGContextSetTextDrawingMode(context, CGTextDrawingMode.Fill);
//        UIFont font = UIFont.fontWithNameSize("Arial", cellSize / 2);
//        NSDictionary stringAttrs = NSDictionary.dictionaryWithObjectsAndKeys(UIKit.UITextAttributeFont(), font);
//                .init();//initWithObjectsAndKeys(UIKit.UITextAttributeFont(), font);
//        stringAttrs.setValueForKey(font, UIKit.UITextAttributeFont());

        for (int i = 0; i < xTitle.length; ++i) {
//            NSAttributedString attrStr = NSAttributedString.alloc().initWithStringAttributes(xTitle[i], stringAttrs);
//            attrStr.drawAtPoint(CoreGraphics.CGPointMake(cellSize / 3 + boardMargin + i * cellSize, screenH - (externalMargin + boardMargin / 3)));
        }

        for (int i = 0; i < yTitle.length; ++i) {
            //canvas.drawText(yTitle[yTitle.length - i - 1], externalMargin + boardMargin/3, cellSize/2 + 5*externalMargin/2 + boardMargin + i*cellSize, paint);
        }
    }

    private void drawCells() {
        for (int row = 0; row < GameBoard.CELL_COUNT; ++row) {
            for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                CellRect rect = new CellRect(boardMargin + cellSize*col,
                        boardMargin + cellSize*row + navBarHeight,
                        boardMargin + cellSize*(col+1),
                        boardMargin + cellSize*(row+1) + navBarHeight);
                gameBoard.getCell(row, col).setRect(rect);
                if ((row+col) % 2 == 0) {
                    CoreGraphics.CGContextSetRGBFillColor(context, 255, 255, 255, 1);
                }
                else {
                    CoreGraphics.CGContextSetRGBFillColor(context, 0, 0, 0, 1);
                    if (gameBoard.getCell(row, col).isHighlight())
                        CoreGraphics.CGContextSetFillColor(context, CoreGraphics.CGColorGetComponents(HIGHLIGHT_COLOR));
                        //CoreGraphics.CGContextSetRGBFillColor(context, 0, 255, 0, 1); // TODO: Make it like variable
                }
                CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rect.getLeft(), rect.getTop(),
                        rect.getWidth(), rect.getHeight()));
            }
        }
    }

    private void drawPieces() {
        double radius = cellSize/3;
        for (int row = 0; row < GameBoard.CELL_COUNT; ++row) {
            for (int col = 0; col < GameBoard.CELL_COUNT; ++col) {
                if (gameBoard.getCell(row, col).getCondition() == BoardCell.WHITE_PIECE)
                    CoreGraphics.CGContextSetFillColor(context, CoreGraphics.CGColorGetComponents(WHITE_PIECE_COLOR));
                else if (gameBoard.getCell(row, col).getCondition() == BoardCell.BLACK_PIECE)
                    CoreGraphics.CGContextSetFillColor(context, CoreGraphics.CGColorGetComponents(BLACK_PIECE_COLOR));
                else
                    continue;
                double circleCenterX = gameBoard.getCell(row, col).getRect().getLeft() + cellSize/2;
                double circleCenterY = gameBoard.getCell(row, col).getRect().getTop() + cellSize/2;
                double x = circleCenterX - radius;
                double y = circleCenterY - radius;
                CoreGraphics.CGContextFillEllipseInRect(context, CoreGraphics.CGRectMake(x, y, 2*radius, 2*radius));
                if (gameBoard.getCell(row, col).isKingPiece())
                    drawCrown(circleCenterX, circleCenterY, radius);
            }
        }
    }

    private void drawCrown(double cx, double cy, double radius) {
//        Path path = new Path();
//        float crownWidth = ((cx + radius/2) - (cx - radius/2));
//        path.moveTo(cx + radius/3, cy + radius / 3);
//        path.lineTo(cx - radius/3, cy + radius / 3);
//        path.lineTo(cx - radius/2, cy - radius / 3);
//        path.lineTo(cx - radius/2 + crownWidth/4, cy);
//        path.lineTo(cx - radius/2 + crownWidth/2, cy - radius / 3);
//        path.lineTo(cx - radius/2 + 3*crownWidth/4, cy);
//        path.lineTo(cx - radius/2 + crownWidth, cy - radius / 3);
//        path.moveTo(cx + radius/3, cy + radius / 3);
//        path.close();
//        paint.setColor(CROWN_COLOR);
//        canvas.drawPath(path, paint);
    }
}

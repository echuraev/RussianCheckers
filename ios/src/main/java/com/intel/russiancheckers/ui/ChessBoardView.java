package com.intel.russiancheckers.ui;


import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.ByValue;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.Owned;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.c.Globals;
import ios.coregraphics.c.CoreGraphics;
import ios.coregraphics.enums.CGTextDrawingMode;
import ios.coregraphics.enums.CGTextEncoding;
import ios.coregraphics.opaque.CGContextRef;
import ios.coregraphics.struct.CGRect;
import ios.coretext.c.CoreText;
import ios.foundation.NSAttributedString;
import ios.foundation.NSDictionary;
import ios.foundation.NSString;
import ios.uikit.UIFont;
import ios.uikit.UIView;
import ios.uikit.c.UIKit;
import com.intel.core.rules.GameBoard;

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("ChessBoardView")
@RegisterOnStartup
public class ChessBoardView extends UIView {
    private final float externalMargin = 5;
    private final int navBarHeight = 64;
    private double screenW;
    private double screenH;
    private double cellSize;
    private double boardMargin;
    private CGContextRef context;

    static {
        NatJ.register();
    }

    @Generated("NatJ")
    @Owned
    @Selector("alloc")
    public static native ChessBoardView alloc();

    protected ChessBoardView(Pointer peer) {
        super(peer);
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
        double rectWidth = boardW - externalMargin;
        double rectHeight = boardH - externalMargin;
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rectX, rectY, rectWidth, rectHeight));

        CoreGraphics.CGContextSetRGBFillColor(context, 255, 255, 255, 1);

        rectX = externalMargin + 1;
        rectY = externalMargin + navBarHeight + 1;
        rectWidth = boardW - (externalMargin + 2);
        rectHeight = boardH - (externalMargin + 2);
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(rectX, rectY, rectWidth, rectHeight));
        CoreGraphics.CGContextSetRGBFillColor(context, 0, 0, 0, 1);
        boardMargin = boardW / 12;
        rectX = boardMargin - 1;
        rectY = boardMargin - 1 + navBarHeight;
        rectWidth = boardW - 2*(boardMargin - 1) - externalMargin;
        rectHeight = boardH - 2*(boardMargin - 1) - externalMargin;
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
        UIFont font = UIFont.fontWithNameSize("Arial", cellSize / 2);
        NSDictionary stringAttrs = NSDictionary.dictionaryWithObjectsAndKeys(UIKit.UITextAttributeFont(), font);
//                .init();//initWithObjectsAndKeys(UIKit.UITextAttributeFont(), font);
//        stringAttrs.setValueForKey(font, UIKit.UITextAttributeFont());

        for (int i = 0; i < xTitle.length; ++i) {
            NSAttributedString attrStr = NSAttributedString.alloc().initWithStringAttributes(xTitle[i], stringAttrs);
            attrStr.drawAtPoint(CoreGraphics.CGPointMake(cellSize / 3 + boardMargin + i * cellSize, screenH - (externalMargin + boardMargin / 3)));
        }

        for (int i = 0; i < yTitle.length; ++i) {
            //canvas.drawText(yTitle[yTitle.length - i - 1], externalMargin + boardMargin/3, cellSize/2 + 5*externalMargin/2 + boardMargin + i*cellSize, paint);
        }
    }

    private void drawCells() {

    }

    private void drawPieces() {

    }

    private void drawCrown(float cx, float cy, float radius) {

    }
}

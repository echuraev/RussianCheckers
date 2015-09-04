package com.intel.russiancheckers.ui;


import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.ByValue;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.coregraphics.c.CoreGraphics;
import ios.coregraphics.opaque.CGContextRef;
import ios.coregraphics.struct.CGRect;
import ios.uikit.UIView;
import ios.uikit.c.UIKit;

public class ChessBoardView extends UIView {

    static {
        NatJ.register();
    }

    protected ChessBoardView(Pointer peer) {
        super(peer);
    }

    @Override
    @Selector("drawRect:")
    public void drawRect(@ByValue CGRect rect) {
        CGContextRef context = UIKit.UIGraphicsGetCurrentContext();
        CoreGraphics.CGContextClearRect(context, rect);
        double screenH = this.bounds().size().height();
        double screenW = this.bounds().size().width();
        CoreGraphics.CGContextSetRGBFillColor(context, 255, 255, 255, 1);
        CoreGraphics.CGContextFillRect(context, CoreGraphics.CGRectMake(0, 0, screenW, screenH));
    }
}

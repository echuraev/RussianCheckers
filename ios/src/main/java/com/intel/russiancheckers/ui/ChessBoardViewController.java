package com.intel.russiancheckers.ui;

import com.intel.core.algorithm.AlgorithmType;
import com.intel.core.algorithm.AlphaBetaPruning;
import com.intel.core.algorithm.IAlgorithm;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.Owned;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.coregraphics.c.CoreGraphics;
import ios.coregraphics.struct.CGRect;
import ios.uikit.UILabel;
import ios.uikit.UIStoryboardSegue;
import ios.uikit.UIViewController;

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("ChessBoardViewController")
@RegisterOnStartup
public class ChessBoardViewController extends UIViewController {

    private ChessBoardView chessBoardView;
    private final int navBarHeight = 64;
    private AlgorithmType algorithmType;

    static {
        NatJ.register();
    }

    @Generated("NatJ")
    @Owned
    @Selector("alloc")
    public static native ChessBoardViewController alloc();

    @Generated("NatJ")
    @Selector("init")
    public native ChessBoardViewController init();

    @Generated
    protected ChessBoardViewController(Pointer peer) {
        super(peer);
    }

    public void setAlgorithmType(AlgorithmType algorithmType) {
        this.algorithmType = algorithmType;
    }

    @Override
    @Selector("viewDidLoad")
    public void viewDidLoad() {
        double size = this.view().bounds().size().width();
        if (this.view().bounds().size().width() > this.view().bounds().size().height())
            size = this.view().bounds().size().height();
        chessBoardView = (ChessBoardView) ChessBoardView.alloc().initWithFrameAndParams(
                CoreGraphics.CGRectMake(0, navBarHeight, size, size),
                algorithmType, AlphaBetaPruning.MEDIUM_DIFFICULTY);
        // TODO: Set difficulty
        view().addSubview(chessBoardView);
        //UILabel statusText =
        view().setNeedsDisplay();
    }

    @Override
    public void viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews();
        double size = this.view().bounds().size().width();
        if (this.view().bounds().size().width() > this.view().bounds().size().height())
            size = this.view().bounds().size().height() - navBarHeight;
        chessBoardView.setFrame(CoreGraphics.CGRectMake(0, navBarHeight, size, size));
    }
}

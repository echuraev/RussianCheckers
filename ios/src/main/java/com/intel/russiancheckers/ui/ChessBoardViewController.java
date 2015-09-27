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
import ios.foundation.NSUserDefaults;
import ios.uikit.UIApplication;
import ios.uikit.UILabel;
import ios.uikit.UIStoryboardSegue;
import ios.uikit.UIViewController;
import ios.uikit.enums.UILineBreakMode;
import ios.uikit.enums.UITextAlignment;

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("ChessBoardViewController")
@RegisterOnStartup
public class ChessBoardViewController extends UIViewController {

    private ChessBoardView chessBoardView;
    private AlgorithmType algorithmType;
    private UILabel statusText;

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
        double navBarHeight = navigationController().navigationBar().frame().size().height() +
                UIApplication.sharedApplication().statusBarFrame().size().height();;
        double size = this.view().bounds().size().width();
        double labelX = 5;
        double labelY = navBarHeight + size;
        double labelWidth = this.view().bounds().size().width();
        double labelHeight = 30;
        //double labelHeight = this.view().bounds().size().height() - labelY;
        if (this.view().bounds().size().width() > this.view().bounds().size().height()) {
            size = this.view().bounds().size().height() - navBarHeight;
            labelX = size;
            labelY = navBarHeight;
            labelWidth = this.view().bounds().size().width() - this.view().bounds().size().height();
            //labelHeight = this.view().bounds().size().height() - labelY;
        }
        int difficulty;
        int difficultyIndex = (int) NSUserDefaults.standardUserDefaults().integerForKey("Difficulty");
        if (SettingsViewController.difficultyLabels[difficultyIndex].equals("Low")) {
            difficulty = AlphaBetaPruning.LOW_DIFFICULTY;
        }
        else if (SettingsViewController.difficultyLabels[difficultyIndex].equals("Medium")) {
            difficulty = AlphaBetaPruning.MEDIUM_DIFFICULTY;
        }
        else if (SettingsViewController.difficultyLabels[difficultyIndex].equals("Hard")) {
            difficulty = AlphaBetaPruning.HIGH_DIFFICULTY;
        }
        else {
            difficulty = AlphaBetaPruning.MEDIUM_DIFFICULTY;
        }
        statusText = UILabel.alloc().initWithFrame(CoreGraphics.CGRectMake(labelX, labelY,
                labelWidth, labelHeight));
        statusText.setAdjustsFontSizeToFitWidth(true);
        statusText.setNumberOfLines(0);
        statusText.sizeToFit();
        chessBoardView = (ChessBoardView) ChessBoardView.alloc().initWithFrameAndParams(
                CoreGraphics.CGRectMake(0, navBarHeight, size, size),
                algorithmType, difficulty, statusText);
        view().addSubview(chessBoardView);
        view().addSubview(statusText);
        view().setNeedsDisplay();
    }

    @Override
    public void viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews();
        double navBarHeight = navigationController().navigationBar().frame().size().height() +
                UIApplication.sharedApplication().statusBarFrame().size().height();;
        double size = this.view().bounds().size().width();
        double labelX = 5;
        double labelY = navBarHeight + size;
        double labelWidth = this.view().bounds().size().width();
        double labelHeight = 30;
        //double labelHeight = this.view().bounds().size().height() - labelY;
        if (this.view().bounds().size().width() > this.view().bounds().size().height()) {
            size = this.view().bounds().size().height() - navBarHeight;
            labelX = size;
            labelY = navBarHeight;
            labelWidth = this.view().bounds().size().width() - this.view().bounds().size().height();
            //labelHeight = this.view().bounds().size().height() - labelY;
        }
        chessBoardView.setFrame(CoreGraphics.CGRectMake(0, navBarHeight, size, size));
        statusText.setFrame(CoreGraphics.CGRectMake(labelX, labelY, labelWidth, labelHeight));
    }
}

package com.intel.russiancheckers.ui;

import com.intel.core.algorithm.AlgorithmType;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.Owned;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.uikit.UIStoryboardSegue;
import ios.uikit.UIViewController;

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("AppViewController")
@RegisterOnStartup
public class AppViewController extends UIViewController {

    static {
        NatJ.register();
    }

    @Generated("NatJ")
    @Owned
    @Selector("alloc")
    public static native AppViewController alloc();

    @Generated("NatJ")
    @Selector("init")
    public native AppViewController init();

    @Generated
    protected AppViewController(Pointer peer) {
        super(peer);
    }

    @Override
    @Selector("viewDidLoad")
    public void viewDidLoad() {
    }

    @Override
    public void prepareForSegueSender(UIStoryboardSegue uiStoryboardSegue, Object o) {
        super.prepareForSegueSender(uiStoryboardSegue, o);
        if (uiStoryboardSegue.identifier().equals("newGameVSComputer")) {
            ChessBoardViewController vc = (ChessBoardViewController) uiStoryboardSegue.destinationViewController();
            vc.setAlgorithmType(AlgorithmType.COMPUTER);
        }
        else if (uiStoryboardSegue.identifier().equals("newGameVSHuman")) {
            ChessBoardViewController vc = (ChessBoardViewController) uiStoryboardSegue.destinationViewController();
            vc.setAlgorithmType(AlgorithmType.HUMAN);
        }
    }
}

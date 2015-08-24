package com.intel.russiancheckers;

import com.intel.russiancheckers.ui.AppViewController;

import ios.NSObject;
import ios.foundation.NSDictionary;
import ios.uikit.UIApplication;
import ios.uikit.UIWindow;
import ios.uikit.c.UIKit;
import ios.uikit.protocol.UIApplicationDelegate;

import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ann.Selector;

@RegisterOnStartup
public class Main extends NSObject implements UIApplicationDelegate {

    static
    {
        NatJ.register();
    }

    public static void main(String[] args) {
        UIKit.UIApplicationMain(0, null, null, Main.class.getName());
    }

    @Generated("NatJ")
    @Selector("alloc")
    public static native Main alloc();

    @Generated("NatJ")
    protected Main(Pointer peer) {
        super(peer);
    }

    private UIWindow window;

    @Override
    @Selector("application:didFinishLaunchingWithOptions:")
    public boolean applicationDidFinishLaunchingWithOptions(UIApplication application, NSDictionary launchOptions) {
        return true;
    }

    @Override
    @Selector("setWindow:")
    public void setWindow(UIWindow value) {
        window = value;
    }

    @Override
    @Selector("window")
    public UIWindow window() {
        return window;
    }
}

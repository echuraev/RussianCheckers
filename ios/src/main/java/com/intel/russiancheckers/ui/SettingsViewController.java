package com.intel.russiancheckers.ui;

import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.NInt;
import com.intel.inde.moe.natj.general.ann.Owned;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.IBAction;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Property;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.NSObject;
import ios.foundation.NSUserDefaults;
import ios.uikit.UIAlertView;
import ios.uikit.UIPickerView;
import ios.uikit.UIViewController;

@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("SettingsViewController")
@RegisterOnStartup
public class SettingsViewController extends UIViewController {

    private UIPickerView difficultyPicker = null;
    public static final String[] difficultyLabels = {"Low", "Medium", "Hard"};

    static {
        NatJ.register();
    }

    @Generated("NatJ")
    @Owned
    @Selector("alloc")
    public static native SettingsViewController alloc();

    @Generated("NatJ")
    @Selector("init")
    public native SettingsViewController init();

    @Generated
    protected SettingsViewController(Pointer peer) {
        super(peer);
    }

    @Override
    @Selector("viewDidLoad")
    public void viewDidLoad() {
        difficultyPicker = getDifficultyPicker();
        difficultyPicker.setDataSource(this);
        difficultyPicker.setDelegate(this);
        difficultyPicker.selectRowInComponentAnimated(NSUserDefaults.standardUserDefaults().integerForKey("Difficulty"), 0, true);
    }

    @Selector("numberOfComponentsInPickerView:")
    @NInt
    public long numberOfComponentsInPickerView(UIPickerView pickerView) {
        return 1;
    }

    @Selector("pickerView:numberOfRowsInComponent:")
    @NInt
    public long pickerViewNumberOfRowsInComponent(UIPickerView pickerView, @NInt long component) {
        return difficultyLabels.length;
    }

    @Selector("pickerView:titleForRow:forComponent:")
    public String pickerViewTitleForRowForComponent(UIPickerView pickerView, @NInt long row, @NInt long component) {
        return difficultyLabels[(int) row];
    }

    @Property
    @Selector("difficultyPicker")
    public native UIPickerView getDifficultyPicker();

    @IBAction
    @Selector("BtnPressedCancel_saveButton:")
    public void BtnPressedCancel_saveButton(NSObject sender) {
        NSUserDefaults userDefaults = NSUserDefaults.standardUserDefaults();
        userDefaults.setIntegerForKey(difficultyPicker.selectedRowInComponent(0), "Difficulty");
        userDefaults.synchronize();
        navigationController().popToRootViewControllerAnimated(true);
    }
}

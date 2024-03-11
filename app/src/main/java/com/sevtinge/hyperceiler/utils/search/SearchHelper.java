/*
 * This file is part of HyperCeiler.

 * HyperCeiler is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.

 * Copyright (C) 2023-2024 HyperCeiler Contributions
 */
package com.sevtinge.hyperceiler.utils.search;

import static com.sevtinge.hyperceiler.utils.api.VoyagerApisKt.isPad;
import static com.sevtinge.hyperceiler.utils.devicesdk.SystemSDKKt.isMoreHyperOSVersion;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.text.TextUtils;

import com.sevtinge.hyperceiler.R;
import com.sevtinge.hyperceiler.data.ModData;
import com.sevtinge.hyperceiler.utils.log.AndroidLogUtils;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchHelper {

    public static final int MARK_COLOR_VIBRANT = Color.rgb(255, 0, 0);
    public static final String NEW_MODS_SEARCH_QUERY = "\uD83C\uDD95";
    public static ArrayList<ModData> allModsList = new ArrayList<>();

    public static String TAG = "SearchHelper";

    public static final HashSet<String> NEW_MODS = new HashSet<>(
            Set.of(
                    "pref_key_launcher_nozoomanim"
            )
    );

    public static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    public static final String APP_NS = "http://schemas.android.com/apk/res-auto";
    public static final String LOCATION = "http://schemas.android.com/apk/location";

    public static void getAllMods(Context context, boolean force) {
        if (force) {
            allModsList.clear();
        } else if (!allModsList.isEmpty()) {
            return;
        }
        // 系统框架页面相关
        parsePrefXml(context, R.xml.framework_freeform,
                R.string.system_framework,
                "com.sevtinge.hyperceiler.ui.fragment.framework.FreeFormSettings",
                R.string.system_framework
        );

        parsePrefXml(context, R.xml.framework_volume,
                R.string.system_framework_volume_title,
                "com.sevtinge.hyperceiler.ui.fragment.framework.VolumeSettings",
                R.string.system_framework);

        parsePrefXml(context, R.xml.framework_phone,
                R.string.system_framework_phone_title,
                "com.sevtinge.hyperceiler.ui.fragment.framework.NetworkSettings",
                R.string.system_framework);

        parsePrefXml(context, R.xml.framework_display,
                R.string.system_framework_display_title,
                "com.sevtinge.hyperceiler.ui.fragment.framework.DisplaySettings",
                R.string.system_framework);

        parsePrefXml(context, R.xml.framework_other,
                R.string.system_framework_other_title,
                "com.sevtinge.hyperceiler.ui.fragment.framework.OtherSettings",
                R.string.system_framework);

        // 系统界面页面相关
        parsePrefXml(context, R.xml.system_ui_lock_screen,
                R.string.system_ui_lockscreen_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.LockScreenSettings",
                R.string.system_ui);

        parsePrefXml(context, R.xml.system_ui_status_bar,
                R.string.system_ui_statusbar_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.StatusBarSettings",
                R.string.system_ui);

        parsePrefXml(context, !isMoreHyperOSVersion(1f) ? R.xml.system_ui_status_bar_icon_manage : R.xml.system_ui_status_bar_icon_manage_new,
                R.string.system_ui_statusbar_iconmanage_title,
                !isMoreHyperOSVersion(1f) ? "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.IconManageSettings" : "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.IconManageNewSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_mobile_network_type,
                R.string.system_ui_status_bar_mobile_type_single_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.MobileNetworkTypeSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title,
                R.string.system_ui_statusbar_iconmanage_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_doubleline_network,
                R.string.system_ui_statusbar_iconmanage_mobile_network_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.DoubleLineNetworkSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title,
                R.string.system_ui_statusbar_iconmanage_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_battery_styles,
                R.string.system_ui_status_bar_battery_style_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.BatteryStyleSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title,
                R.string.system_ui_statusbar_iconmanage_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_network_speed_indicator,
                R.string.system_ui_statusbar_network_speed_indicator_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.NetworkSpeedIndicatorSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        parsePrefXml(context, !isMoreHyperOSVersion(1f) ? R.xml.system_ui_status_bar_clock_indicator : R.xml.system_ui_status_bar_new_clock_indicator,
                R.string.system_ui_statusbar_clock_title,
                !isMoreHyperOSVersion(1f) ? "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.ClockIndicatorSettings" : "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.NewClockIndicatorSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        // 这里
        parsePrefXml(context, R.xml.system_ui_status_bar_hardware_detail_indicator,
                R.string.system_ui_statusbar_device_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.BatteryDetailIndicatorSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        parsePrefXml(context, R.xml.system_ui_status_bar_strong_toast,
                R.string.system_ui_statusbar_strong_toast_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.statusbar.StrongToastSettings",
                R.string.system_ui,
                R.string.system_ui_statusbar_title);

        parsePrefXml(context, R.xml.system_ui_navigation,
                R.string.system_ui_navigation_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.NavigationSettings",
                R.string.system_ui);

        parsePrefXml(context, R.xml.system_ui_control_center,
                R.string.system_ui_controlcenter_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.ControlCenterSettings",
                R.string.system_ui);

        parsePrefXml(context, R.xml.system_ui_other,
                R.string.system_ui_other_title,
                "com.sevtinge.hyperceiler.ui.fragment.systemui.SystemUIOtherSettings",
                R.string.system_ui);

        // 系统桌面相关
        parsePrefXml(context, R.xml.home_gesture,
                R.string.home_gesture,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeGestureSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_layout,
                R.string.home_layout,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeLayoutSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_folder,
                R.string.home_folder,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeFolderSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_drawer,
                R.string.home_drawer,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeDrawerSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_title,
                R.string.home_title,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeTitleSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_title_anim,
                R.string.home_title_custom_anim_param,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeTitleAnimSettings",
                R.string.mihome,
                R.string.home_title);

        parsePrefXml(context, R.xml.home_title_anim_1,
                R.string.home_title_custom_anim_param_1_title,
                "com.sevtinge.hyperceiler.ui.fragment.home.anim.HomeTitleAnim1Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_2,
                R.string.home_title_custom_anim_param_2_title,
                "com.sevtinge.hyperceiler.ui.fragment.home.anim.HomeTitleAnim2Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_3,
                R.string.home_title_custom_anim_param_3_title,
                "com.sevtinge.hyperceiler.ui.fragment.home.anim.HomeTitleAnim3Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_4,
                R.string.home_title_custom_anim_param_4_title,
                "com.sevtinge.hyperceiler.ui.fragment.home.anim.HomeTitleAnim4Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_5,
                R.string.home_title_custom_anim_param_5_title,
                "com.sevtinge.hyperceiler.ui.fragment.home.anim.HomeTitleAnim5Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_6,
                R.string.home_title_custom_anim_param_6_title,
                "com.sevtinge.hyperceiler.ui.fragment.home.anim.HomeTitleAnimSettings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_7,
                R.string.home_title_custom_anim_param_7_title,
                "com.sevtinge.hyperceiler.ui.fragment.home.anim.HomeTitleAnim7Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_title_anim_8,
                R.string.home_title_custom_anim_param_8_title,
                "com.sevtinge.hyperceiler.ui.fragment.home.anim.HomeTitleAnim8Settings",
                R.string.mihome,
                R.string.home_title,
                R.string.home_title_custom_anim_param);

        parsePrefXml(context, R.xml.home_recent,
                R.string.home_recent,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeRecentSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_widget,
                R.string.home_widget,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeWidgetSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_dock,
                R.string.home_dock,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeDockSettings",
                R.string.mihome);

        parsePrefXml(context, R.xml.home_other,
                R.string.home_other,
                "com.sevtinge.hyperceiler.ui.fragment.home.HomeOtherSettings",
                R.string.mihome);

        // 设置相关
        parsePrefXml(context, R.xml.system_settings,
                R.string.system_settings,
                "com.sevtinge.hyperceiler.ui.fragment.SystemSettingsFragment");

        // 其他杂项
        parsePrefXml(context, R.xml.browser,
                R.string.browser,
                "com.sevtinge.hyperceiler.ui.fragment.BrowserFragment");

        parsePrefXml(context, R.xml.camera,
                R.string.camera,
                "com.sevtinge.hyperceiler.ui.fragment.CameraFragment");

        parsePrefXml(context, R.xml.clock,
                R.string.clock,
                "com.sevtinge.hyperceiler.ui.fragment.ClockFragment");

        parsePrefXml(context, R.xml.fileexplorer,
                R.string.fileexplorer,
                "com.sevtinge.hyperceiler.ui.fragment.FileExplorerFragment");

        parsePrefXml(context, R.xml.incallui,
                R.string.incallui,
                "com.sevtinge.hyperceiler.ui.fragment.InCallUiFragment");

        parsePrefXml(context, R.xml.mms,
                R.string.mms,
                "com.sevtinge.hyperceiler.ui.fragment.MmsFragment");

        parsePrefXml(context, R.xml.nfc,
                R.string.nfc,
                "com.sevtinge.hyperceiler.ui.fragment.NfcFragment");

        parsePrefXml(context, R.xml.phone,
                R.string.phone,
                "com.sevtinge.hyperceiler.ui.fragment.PhoneFragment");

        parsePrefXml(context, R.xml.downloads,
                R.string.downloads,
                "com.sevtinge.hyperceiler.ui.fragment.DownloadsFragment");

        parsePrefXml(context, R.xml.updater,
                R.string.updater,
                "com.sevtinge.hyperceiler.ui.fragment.UpdaterFragment");

        if (!isMoreHyperOSVersion(1f)) {
            parsePrefXml(context, R.xml.lbe_security,
                    R.string.lbe,
                    "com.sevtinge.hyperceiler.ui.fragment.LbeFragment");
        }

        parsePrefXml(context, R.xml.milink,
                R.string.milink,
                "com.sevtinge.hyperceiler.ui.fragment.MiLinkFragment");

        parsePrefXml(context, R.xml.aod,
                !isMoreHyperOSVersion(1f) ? R.string.aod : R.string.aod_hyperos,
                "com.sevtinge.hyperceiler.ui.fragment.AodFragment");

        parsePrefXml(context, R.xml.content_extension,
                R.string.content_extension,
                "com.sevtinge.hyperceiler.ui.fragment.ContentExtensionFragment");

        parsePrefXml(context, R.xml.gallery,
                R.string.gallery,
                "com.sevtinge.hyperceiler.ui.fragment.GalleryFragment");

        parsePrefXml(context, R.xml.guard_provider,
                !isMoreHyperOSVersion(1f) ? R.string.guard_provider : R.string.guard_provider_hyperos,
                "com.sevtinge.hyperceiler.ui.fragment.GuardProviderFragment");

        parsePrefXml(context, R.xml.mediaeditor,
                R.string.mediaeditor,
                "com.sevtinge.hyperceiler.ui.fragment.MediaEditorFragment");

        parsePrefXml(context, R.xml.mishare,
                R.string.mishare,
                "com.sevtinge.hyperceiler.ui.fragment.MiShareFragment");

        parsePrefXml(context, R.xml.miwallpaper,
                R.string.miwallpaper,
                "com.sevtinge.hyperceiler.ui.fragment.MiWallpaperFragment");

        parsePrefXml(context, R.xml.package_installer,
                R.string.package_installer,
                "com.sevtinge.hyperceiler.ui.fragment.MiuiPackageInstallerFragment");

        parsePrefXml(context, R.xml.music,
                R.string.music,
                "com.sevtinge.hyperceiler.ui.fragment.MusicFragment");

        parsePrefXml(context, R.xml.powerkeeper,
                R.string.powerkeeper,
                "com.sevtinge.hyperceiler.ui.fragment.PowerKeeperFragment");

        parsePrefXml(context, R.xml.screenrecorder,
                R.string.screenrecorder,
                "com.sevtinge.hyperceiler.ui.fragment.ScreenRecorderFragment");

        parsePrefXml(context, R.xml.screenshot,
                R.string.screenshot,
                "com.sevtinge.hyperceiler.ui.fragment.ScreenShotFragment");

        parsePrefXml(context, R.xml.security_center,
                !isMoreHyperOSVersion(1f) ? (!isPad() ? R.string.security_center : R.string.security_center_pad) : R.string.security_center_hyperos,
                "com.sevtinge.hyperceiler.ui.fragment.SecurityCenterFragment");

        parsePrefXml(context, R.xml.tsmclient,
                R.string.tsmclient,
                "com.sevtinge.hyperceiler.ui.fragment.TsmClientFragment");

        parsePrefXml(context, R.xml.weather,
                R.string.weather,
                "com.sevtinge.hyperceiler.ui.fragment.WeatherFragment");

        parsePrefXml(context, R.xml.aiasst,
                R.string.aiasst,
                "com.sevtinge.hyperceiler.ui.fragment.AiAsstFragment");

        parsePrefXml(context, R.xml.tsmclient,
                R.string.tsmclient,
                "com.sevtinge.hyperceiler.ui.fragment.TsmClientFragment");

        parsePrefXml(context, R.xml.barrage,
                R.string.barrage,
                "com.sevtinge.hyperceiler.ui.fragment.BarrageFragment");

        parsePrefXml(context, R.xml.joyose,
                R.string.joyose,
                "com.sevtinge.hyperceiler.ui.fragment.JoyoseFragment");

        parsePrefXml(context, R.xml.market,
                R.string.market,
                "com.sevtinge.hyperceiler.ui.fragment.MarketFragment");

        parsePrefXml(context, R.xml.mirror,
                !isMoreHyperOSVersion(1f) ? R.string.mirror : R.string.mirror_hyperos,
                "com.sevtinge.hyperceiler.ui.fragment.MirrorFragment");

        parsePrefXml(context, R.xml.mtb,
                R.string.mtb,
                "com.sevtinge.hyperceiler.ui.fragment.MtbFragment");

        parsePrefXml(context, R.xml.scanner,
                R.string.scanner,
                "com.sevtinge.hyperceiler.ui.fragment.ScannerFragment");

        parsePrefXml(context, R.xml.micloud_service,
                R.string.micloud_service,
                "com.sevtinge.hyperceiler.ui.fragment.MiCloudServiceFragment");

        parsePrefXml(context, R.xml.creation,
                R.string.creation,
                "com.sevtinge.hyperceiler.ui.fragment.CreationFragment");

        parsePrefXml(context, R.xml.huanji,
                R.string.huanji,
                "com.sevtinge.hyperceiler.ui.fragment.HuanjiFragment");

        parsePrefXml(context, R.xml.misound,
                R.string.misound,
                "com.sevtinge.hyperceiler.ui.fragment.MiSoundFragment");

        parsePrefXml(context, R.xml.trustservice,
                R.string.trustservice,
                "com.sevtinge.hyperceiler.ui.fragment.TrustServiceFragment");

        parsePrefXml(context, R.xml.calendar,
                R.string.calendar,
                "com.sevtinge.hyperceiler.ui.fragment.CalendarFragment");

        parsePrefXml(context, R.xml.various,
                R.string.various,
                "com.sevtinge.hyperceiler.ui.fragment.VariousFragment");

        parsePrefXml(context, R.xml.various_aosp,
                R.string.various_open_aosp_something_title,
                "com.sevtinge.hyperceiler.ui.fragment.various.AOSPSettings",
                R.string.various);

        if (isPad()) {
            parsePrefXml(context, R.xml.various_mipad,
                    R.string.various_mipad_title,
                    "com.sevtinge.hyperceiler.ui.fragment.VariousFragment",
                    R.string.various);
        }

        // 实验性
        parsePrefXml(context, R.xml.theme_manager,
                R.string.theme_manager,
                "com.sevtinge.hyperceiler.ui.fragment.ThemeManagerFragment");

        parsePrefXml(context, R.xml.personal_assistant,
                R.string.personal_assistant,
                "com.sevtinge.hyperceiler.ui.fragment.PersonalAssistantFragment");
    }

    private static void parsePrefXml(Context context, int xmlResId, int fragmentTitle, String catPrefsFragment, int... internalId) {
        Resources res = context.getResources();
        try (XmlResourceParser xml = res.getXml(xmlResId)) {
            int order = 0;
            String location = null;
            String locationHyper = null;
            String locationPad = null;
            StringBuilder internalName = null;
            int eventType = xml.getEventType();
            if (internalId.length != 0) {
                for (int id : internalId) {
                    String getString = res.getString(id);
                    if (internalName == null) {
                        internalName = new StringBuilder(getString);
                    } else {
                        internalName.append("/").append(getString);
                    }
                }
            }
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && !xml.getName().equals("PreferenceCategory")) {
                    try {
                        ModData modData = new ModData();
                        modData.title = getModTitle(res, xml.getAttributeValue(ANDROID_NS, "title"));
                        boolean isPreferenceVisible = Boolean.parseBoolean(xml.getAttributeValue(APP_NS, "isPreferenceVisible"));
                        if (locationHyper == null)
                            locationHyper = getModTitle(res, xml.getAttributeValue(LOCATION, "myLocationHyper"));
                        if (locationPad == null)
                            locationPad = getModTitle(res, xml.getAttributeValue(LOCATION, "myLocationPad"));
                        if (location == null)
                            location = getModTitle(res, xml.getAttributeValue(LOCATION, "myLocation"));
                        if (!TextUtils.isEmpty(modData.title) && !isPreferenceVisible) {
                            String internalHyper = internalName == null ? locationHyper : internalName.toString() + "/" + locationHyper;
                            String internalPad = internalName == null ? locationPad : internalName.toString() + "/" + locationPad;
                            String internalMiui = internalName == null ? location : internalName.toString() + "/" + location;
                            if (locationHyper == null || location == null || locationPad == null) {
                                if (location != null) {
                                    modData.breadcrumbs = internalMiui;
                                } else if (locationHyper != null) {
                                    modData.breadcrumbs = internalHyper;
                                } else if (locationPad != null) {
                                    modData.breadcrumbs = internalPad;
                                } else {
                                    modData.breadcrumbs = res.getString(fragmentTitle);
                                }
                            } else {
                                if (!isPad()) {
                                    if (isMoreHyperOSVersion(1f)) {
                                        modData.breadcrumbs = internalHyper;
                                    } else
                                        modData.breadcrumbs = internalMiui;
                                } else {
                                    modData.breadcrumbs = internalPad;
                                }
                            }
                            modData.key = xml.getAttributeValue(ANDROID_NS, "key");
                            modData.order = order;
                            modData.catTitleResId = fragmentTitle;
                            modData.fragment = catPrefsFragment;
                            allModsList.add(modData);
                        }
                        order++;
                    } catch (Throwable t) {
                        AndroidLogUtils.logE(TAG, "Failed to get xml keyword object!", t);
                    }
                }
                eventType = xml.next();
            }
        } catch (Throwable t) {
            AndroidLogUtils.logE(TAG, "Failed to access XML resource!", t);
        }
    }

    private static String getModTitle(Resources res, String title) {
        if (title == null) {
            return null;
        }
        int titleResId = Integer.parseInt(title.substring(1));
        if (titleResId <= 0) {
            return null;
        }
        return res.getString(titleResId);
    }
}

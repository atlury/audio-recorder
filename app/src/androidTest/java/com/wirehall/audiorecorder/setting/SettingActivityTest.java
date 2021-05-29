package com.wirehall.audiorecorder.setting;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.wirehall.audiorecorder.R;
import com.wirehall.audiorecorder.setting.pathpref.PathPrefDialog;
import com.wirehall.audiorecorder.setting.pathpref.StorageItem;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class SettingActivityTest {
  @Rule
  public ActivityScenarioRule<SettingActivity> rule =
      new ActivityScenarioRule<>(SettingActivity.class);

  @Rule
  public GrantPermissionRule permissionRule =
      GrantPermissionRule.grant(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE);

  @Test
  public void testLaunch_setting_activity() {
    onView(withId(androidx.preference.R.id.recycler_view))
        .check(
            matches(
                hasDescendant(
                    allOf(
                        withText(R.string.pref_confirm_delete_summary),
                        withText(R.string.pref_confirm_delete_summary)))));

    onView(withId(androidx.preference.R.id.recycler_view))
        .check(matches(hasDescendant(withText(R.string.pref_request_filename_title))));

    onView(withId(androidx.preference.R.id.recycler_view))
        .check(matches(hasDescendant(withText(R.string.pref_list_audio_quality_title))));

    onView(withId(androidx.preference.R.id.recycler_view))
        .check(matches(hasDescendant(withText(R.string.pref_recording_storage_path_title))));
  }

  @Test
  public void testUpdate_path_with_path_pref_dialog() {
    onView(withId(androidx.preference.R.id.recycler_view))
        .perform(
            actionOnItem(
                hasDescendant(withText(R.string.pref_recording_storage_path_title)), click()));

    onView(withTagValue(is((PathPrefDialog.TAG_NEW_FOLDER_BUTTON)))).perform(click());
    onView(withText(R.string.pref_path_new_folder_dialog_title)).check(matches(isDisplayed()));
    onView(withTagValue(is((PathPrefDialog.TAG_NEW_FOLDER_INPUT)))).check(matches(isDisplayed()));
    onView(withId(android.R.id.button2)).perform(click()); // Cancel the input dialog
    onView(withId(android.R.id.button1)).perform(click()); // Click okay

    onView(withTagValue(is((PathPrefDialog.TAG_NEW_FOLDER_INPUT)))).check(doesNotExist());
  }

  @Test
  @Ignore("Crashing emulator on api-29 runner")
  public void testNavigate_path() {
    onView(withId(androidx.preference.R.id.recycler_view))
        .perform(
            actionOnItem(
                hasDescendant(withText(R.string.pref_recording_storage_path_title)), click()));

    onData(is(instanceOf(StorageItem.class))).atPosition(0).perform(click()); // go to 1st dir
    onData(is(instanceOf(StorageItem.class))).atPosition(0).check(matches(isDisplayed()));
    onData(is(instanceOf(StorageItem.class))).atPosition(0).perform(click()); // back to parent dir
  }
}

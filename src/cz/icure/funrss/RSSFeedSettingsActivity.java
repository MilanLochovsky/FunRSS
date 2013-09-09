
package cz.icure.funrss;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class RSSFeedSettingsActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.feed_preference);
        EditTextPreference mLabel = (EditTextPreference) findPreference("label");
        mLabel.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                        // Set the summary based on the new label.
                        p.setSummary((String) newValue);
                        return true;
                    }
                });
        
        EditTextPreference mUrl = (EditTextPreference) findPreference("url");
        mUrl.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                        // Set the summary based on the new label.
                        p.setSummary((String) newValue);
                        return true;
                    }
                });
        
        EditTextPreference mUsername = (EditTextPreference) findPreference("loginUsername");
        mUsername.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                        // Set the summary based on the new label.
                        p.setSummary((String) newValue);
                        return true;
                    }
                });
        
        EditTextPreference mPassword = (EditTextPreference) findPreference("loginPassword");
        mPassword.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                        // Set the summary based on the new label.
                        p.setSummary((String) newValue);
                        return true;
                    }
                });
        
        CheckBoxPreference mVibratePref = (CheckBoxPreference) findPreference("login");
        mVibratePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                    	if(((Boolean)newValue) == true) {
                    		((EditTextPreference) findPreference("loginUsername")).setEnabled(true);
                    		((EditTextPreference) findPreference("loginPassword")).setEnabled(true);
                    	}
                    	else {
                    		((EditTextPreference) findPreference("loginUsername")).setEnabled(false);
                    		((EditTextPreference) findPreference("loginPassword")).setEnabled(false);
                    	}
                    	
                        return true;
                    }
                });
        
        getListView().setItemsCanFocus(true);

        // Grab the content view so we can modify it.
        FrameLayout content = (FrameLayout) getWindow().getDecorView()
                .findViewById(android.R.id.content);

        // Get the main ListView and remove it from the content view.
        ListView lv = getListView();
        content.removeView(lv);

        // Create the new LinearLayout that will become the content view and
        // make it vertical.
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        // Have the ListView expand to fill the screen minus the save/cancel
        // buttons.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        ll.addView(lv, lp);

        // Inflate the buttons onto the LinearLayout.
        View v = LayoutInflater.from(this).inflate(
                R.layout.save_cancel_alarm, ll);

        // Attach actions to each button.
        Button b = (Button) v.findViewById(R.id.alarm_save);
        b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
        });
        b = (Button) v.findViewById(R.id.alarm_cancel);
        b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
        });

        // Replace the old content view with our new one.
        setContentView(ll);
    }
    /*
	 // Get each preference so we can retrieve the value later.
    mLabel = (EditTextPreference) findPreference("label");
    mLabel.setOnPreferenceChangeListener(
            new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference p,
                        Object newValue) {
                    // Set the summary based on the new label.
                    p.setSummary((String) newValue);
                    return true;
                }
            });
    mTimePref = findPreference("time");
    mAlarmPref = (AlarmPreference) findPreference("alarm");
    mVibratePref = (CheckBoxPreference) findPreference("vibrate");
    mRepeatPref = (RepeatPreference) findPreference("setRepeat");

    Intent i = getIntent();
    mId = i.getIntExtra(Alarms.ALARM_ID, -1);
    if (Log.LOGV) {
        Log.v("In SetAlarm, alarm id = " + mId);
    }

    /* load alarm details from database 
    Alarm alarm = Alarms.getAlarm(getContentResolver(), mId);
    // Bad alarm, bail to avoid a NPE.
    if (alarm == null) {
        finish();
        return;
    }
    mEnabled = alarm.enabled;
    mLabel.setText(alarm.label);
    mLabel.setSummary(alarm.label);
    mHour = alarm.hour;
    mMinutes = alarm.minutes;
    mRepeatPref.setDaysOfWeek(alarm.daysOfWeek);
    mVibratePref.setChecked(alarm.vibrate);
    // Give the alert uri to the preference.
    mAlarmPref.setAlert(alarm.alert);
    updateTime();

    // We have to do this to get the save/cancel buttons to highlight on
    // their own.
    getListView().setItemsCanFocus(true);

    // Grab the content view so we can modify it.
    FrameLayout content = (FrameLayout) getWindow().getDecorView()
            .findViewById(com.android.internal.R.id.content);

    // Get the main ListView and remove it from the content view.
    ListView lv = getListView();
    content.removeView(lv);

    // Create the new LinearLayout that will become the content view and
    // make it vertical.
    LinearLayout ll = new LinearLayout(this);
    ll.setOrientation(LinearLayout.VERTICAL);

    // Have the ListView expand to fill the screen minus the save/cancel
    // buttons.
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);
    lp.weight = 1;
    ll.addView(lv, lp);

    // Inflate the buttons onto the LinearLayout.
    View v = LayoutInflater.from(this).inflate(
            R.layout.save_cancel_alarm, ll);

    // Attach actions to each button.
    Button b = (Button) v.findViewById(R.id.alarm_save);
    b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Enable the alarm when clicking "Done"
                mEnabled = true;
                saveAlarm();
                finish();
            }
    });
    b = (Button) v.findViewById(R.id.alarm_cancel);
    b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
    });

    // Replace the old content view with our new one.
    setContentView(ll);
}

@Override
public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
        Preference preference) {
    if (preference == mTimePref) {
        new TimePickerDialog(this, this, mHour, mMinutes,
                DateFormat.is24HourFormat(this)).show();
    }

    return super.onPreferenceTreeClick(preferenceScreen, preference);
}

@Override
public void onBackPressed() {
    saveAlarm();
    finish();
}

public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    mHour = hourOfDay;
    mMinutes = minute;
    updateTime();
    // If the time has been changed, enable the alarm.
    mEnabled = true;
}

private void updateTime() {
    if (Log.LOGV) {
        Log.v("updateTime " + mId);
    }
    mTimePref.setSummary(Alarms.formatTime(this, mHour, mMinutes,
            mRepeatPref.getDaysOfWeek()));
}

private void saveAlarm() {
    final String alert = mAlarmPref.getAlertString();
    long time = Alarms.setAlarm(this, mId, mEnabled, mHour, mMinutes,
            mRepeatPref.getDaysOfWeek(), mVibratePref.isChecked(),
            mLabel.getText(), alert);

    if (mEnabled) {
        popAlarmSetToast(this, time);
    }
}

/**
 * Write alarm out to persistent store and pops toast if alarm
 * enabled.
 * Used only in test code.
 *
private static void saveAlarm(
        Context context, int id, boolean enabled, int hour, int minute,
        Alarm.DaysOfWeek daysOfWeek, boolean vibrate, String label,
        String alert, boolean popToast) {
    if (Log.LOGV) Log.v("** saveAlarm " + id + " " + label + " " + enabled
            + " " + hour + " " + minute + " vibe " + vibrate);

    // Fix alert string first
    long time = Alarms.setAlarm(context, id, enabled, hour, minute,
            daysOfWeek, vibrate, label, alert);

    if (enabled && popToast) {
        popAlarmSetToast(context, time);
    }
}

/**
 * Display a toast that tells the user how long until the alarm
 * goes off.  This helps prevent "am/pm" mistakes.
 *
static void popAlarmSetToast(Context context, int hour, int minute,
                             Alarm.DaysOfWeek daysOfWeek) {
    popAlarmSetToast(context,
            Alarms.calculateAlarm(hour, minute, daysOfWeek)
            .getTimeInMillis());
}

private static void popAlarmSetToast(Context context, long timeInMillis) {
    String toastText = formatToast(context, timeInMillis);
    Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
    ToastMaster.setToast(toast);
    toast.show();
}

/**
 * format "Alarm set for 2 days 7 hours and 53 minutes from
 * now"
 *
static String formatToast(Context context, long timeInMillis) {
    long delta = timeInMillis - System.currentTimeMillis();
    long hours = delta / (1000 * 60 * 60);
    long minutes = delta / (1000 * 60) % 60;
    long days = hours / 24;
    hours = hours % 24;

    String daySeq = (days == 0) ? "" :
            (days == 1) ? context.getString(R.string.day) :
            context.getString(R.string.days, Long.toString(days));

    String minSeq = (minutes == 0) ? "" :
            (minutes == 1) ? context.getString(R.string.minute) :
            context.getString(R.string.minutes, Long.toString(minutes));

    String hourSeq = (hours == 0) ? "" :
            (hours == 1) ? context.getString(R.string.hour) :
            context.getString(R.string.hours, Long.toString(hours));

    boolean dispDays = days > 0;
    boolean dispHour = hours > 0;
    boolean dispMinute = minutes > 0;

    int index = (dispDays ? 1 : 0) |
                (dispHour ? 2 : 0) |
                (dispMinute ? 4 : 0);

    String[] formats = context.getResources().getStringArray(R.array.alarm_set);
    return String.format(formats[index], daySeq, hourSeq, minSeq);
}

public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);

    mDeleteAlarmItem = menu.add(0, 0, 0, R.string.delete_alarm);
    mDeleteAlarmItem.setIcon(android.R.drawable.ic_menu_delete);

    if (AlarmClock.DEBUG) {
        mTestAlarmItem = menu.add(0, 0, 0, "test alarm");
    }

    return true;
}

public boolean onOptionsItemSelected(MenuItem item) {
    if (item == mDeleteAlarmItem) {
        Alarms.deleteAlarm(this, mId);
        finish();
        return true;
    }
    if (AlarmClock.DEBUG) {
        if (item == mTestAlarmItem) {
            setTestAlarm();
            return true;
        }
    }

    return false;
}


/**
 * Test code: this is disabled for production build.  Sets
 * this alarm to go off on the next minute
 *
void setTestAlarm() {

    // start with now
    java.util.Calendar c = java.util.Calendar.getInstance();
    c.setTimeInMillis(System.currentTimeMillis());

    int nowHour = c.get(java.util.Calendar.HOUR_OF_DAY);
    int nowMinute = c.get(java.util.Calendar.MINUTE);

    int minutes = (nowMinute + 1) % 60;
    int hour = nowHour + (nowMinute == 0 ? 1 : 0);

    saveAlarm(this, mId, true, hour, minutes, mRepeatPref.getDaysOfWeek(),
            true, mLabel.getText(), mAlarmPref.getAlertString(), true);
}
*/
}

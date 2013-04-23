package com.fizz.attenda;

import android.R.color;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {
	    
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.preferences);
        Preference myPref1=findPreference("setup_schedule");
        myPref1.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
            	gosettings();
				return true;
            //handle action on click here
            }
        });
        Preference myPref2=findPreference("setup_subjects");
        myPref2.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
            	gosetupsubs();
				return true;
            //handle action on click here
            }
        });
    }

	protected void gosetupsubs() {
		Intent i = new Intent(this, SubjectSetup.class);
	  	startActivity(i);
	}

	protected void gosettings() {
		Intent i = new Intent(this, Timetable.class);
	  	startActivity(i);
	}

}

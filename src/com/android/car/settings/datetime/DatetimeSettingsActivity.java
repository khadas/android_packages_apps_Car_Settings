/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package com.android.car.settings.datetime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import com.android.car.settings.R;
import com.android.car.settings.common.ListSettingsActivity;
import com.android.car.settings.common.TypedPagedListAdapter;

import java.util.ArrayList;

/**
 * Configures date time
 */
public class DatetimeSettingsActivity extends ListSettingsActivity {
    private static final String TAG = "DatetimeSettingsActivity";

    private static final IntentFilter TIME_CHANGED_FILTER =
            new IntentFilter(Intent.ACTION_TIME_CHANGED);

    // Minimum time is Nov 5, 2007, 0:00.
    public static final long MIN_DATE = 1194220800000L;

    private final TimeChangedBroadCastReceiver mTimeChangedBroadCastReceiver =
            new TimeChangedBroadCastReceiver();

    @Override
    public ArrayList<TypedPagedListAdapter.LineItem> getLineItems() {
        ArrayList<TypedPagedListAdapter.LineItem> lineItems = new ArrayList<>();
        lineItems.add(new DateTimeToggleLineItem(this,
                getString(R.string.date_time_auto),
                getString(R.string.date_time_auto_summary),
                Settings.Global.AUTO_TIME));
        lineItems.add(new DateTimeToggleLineItem(this,
                getString(R.string.zone_auto),
                getString(R.string.zone_auto_summary),
                Settings.Global.AUTO_TIME_ZONE));
        lineItems.add(new SetDateLineItem(this));
        lineItems.add(new SetTimeLineItem(this));
        lineItems.add(new SetTimeZoneLineItem(this));
        lineItems.add(new TimeFormatToggleLineItem(this));
        return lineItems;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(mTimeChangedBroadCastReceiver, TIME_CHANGED_FILTER);
        mPagedListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(mTimeChangedBroadCastReceiver);
    }

    private class TimeChangedBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPagedListAdapter.notifyDataSetChanged();
        }
    }
}

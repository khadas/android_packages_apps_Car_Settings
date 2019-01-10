/*
 * Copyright (C) 2019 The Android Open Source Project
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
 * limitations under the License.
 */

package com.android.car.settings.testutils;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TtsEngines;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.Resetter;

import java.util.List;

@Implements(TtsEngines.class)
public class ShadowTtsEngines {
    private static TtsEngines sInstance;

    public static void setInstance(TtsEngines ttsEngines) {
        sInstance = ttsEngines;
    }

    @Resetter
    public static void reset() {
        sInstance = null;
    }

    @Implementation
    public List<TextToSpeech.EngineInfo> getEngines() {
        return sInstance.getEngines();
    }

    @Implementation
    public TextToSpeech.EngineInfo getEngineInfo(String packageName) {
        return sInstance.getEngineInfo(packageName);
    }
}
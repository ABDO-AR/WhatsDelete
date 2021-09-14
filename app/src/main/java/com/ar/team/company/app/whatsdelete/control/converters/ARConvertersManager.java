package com.ar.team.company.app.whatsdelete.control.converters;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class ARConvertersManager {

    // Converter(CharSequence):
    public static class CharSequenceConverter implements InstanceCreator<CharSequence> {
        @Override // InstanceCreatorOfCharSequence:
        public CharSequence createInstance(Type type) {
            return "{aaa}";
        }
    }
}

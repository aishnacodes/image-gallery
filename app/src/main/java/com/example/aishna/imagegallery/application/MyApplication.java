package com.example.aishna.imagegallery.application;

import android.app.Application;

import com.example.aishna.imagegallery.BuildConfig;
import com.example.aishna.imagegallery.component.AppComponent;
import com.example.aishna.imagegallery.component.DaggerAppComponent;
import com.example.aishna.imagegallery.module.CommonModule;
import com.firebase.client.Firebase;

import timber.log.Timber;

public class MyApplication extends Application {
    AppComponent appComponent;

    public void onCreate() {
        Firebase.setAndroidContext(this);
        getComponent();
        appComponent.inject(this);

        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().commonModule(new CommonModule(this)).build();
        }
        return appComponent;
    }
}



package com.example.aishna.imagegallery.module;

import com.example.aishna.imagegallery.application.MyApplication;
import com.example.aishna.imagegallery.broadcasts.NetworkChangeReceiver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CommonModule {
    public CommonModule(MyApplication myApplication) {

    }

    @Provides
    @Singleton
    NetworkChangeReceiver provideNetwork() {
        return new NetworkChangeReceiver();
    }
}

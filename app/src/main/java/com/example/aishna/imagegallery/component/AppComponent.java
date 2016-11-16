package com.example.aishna.imagegallery.component;

import com.example.aishna.imagegallery.application.MyApplication;
import com.example.aishna.imagegallery.broadcasts.NetworkChangeReceiver;
import com.example.aishna.imagegallery.fragments.ImageSearchFragment;
import com.example.aishna.imagegallery.module.CommonModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CommonModule.class})
public interface AppComponent {
    void inject(MyApplication myApplication);

    void inject(ImageSearchFragment imageSearchFragment);

    NetworkChangeReceiver provideNetwork();
}

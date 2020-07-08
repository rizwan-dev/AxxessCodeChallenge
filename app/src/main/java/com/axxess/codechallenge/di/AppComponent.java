package com.axxess.codechallenge.di;


import com.axxess.codechallenge.ui.GalleryActivity;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(GalleryActivity galleryActivity);

}

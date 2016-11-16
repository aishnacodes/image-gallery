package com.example.aishna.imagegallery.realm.imgur;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Aishna on 26/02/16.
 */
public class RealmImgurData extends RealmObject {

    private RealmList<RealmImgurItem> items;

    public RealmList<RealmImgurItem> getItems() {
        return items;
    }

    public void setItems(RealmList<RealmImgurItem> items) {
        this.items = items;
    }


}

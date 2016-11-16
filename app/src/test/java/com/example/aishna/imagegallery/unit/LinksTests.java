package com.example.aishna.imagegallery.unit;

import com.example.aishna.imagegallery.model.imgur.ImgurItem;
import com.example.aishna.imagegallery.utils.LinksUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assert_;

/**
 * Created by Aishna on 25/02/16.
 */
public class LinksTests {

    @Test
    public void canGetImgurItemList() {
        ArrayList<ImgurItem> itemList = new ArrayList<>(5);
        ImgurItem item = new ImgurItem(false, "www", false);

        for (int i = 0; i < 5; i++) {
            itemList.add(item);
        }

        List<String> filteredList = LinksUtils.getImgurLinks(itemList);
        assert_().that(filteredList).isNotNull();
        assert_().that(filteredList.get(0)).isNotNull();
    }

    @Test
    public void canGetImgurItemListWhenAlbumsAndAnimationsAreFalse() {
        ArrayList<ImgurItem> items = new ArrayList<>(5);
        ImgurItem item = new ImgurItem(false, "www", false);

        for (int i = 0; i < 5; i++) {
            items.add(item);
        }

        List<String> filteredList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isAlbum && !items.get(i).animated) {
                filteredList.add(items.get(i).link);
            }
        }
        assert_().that(filteredList.size()).isEqualTo(5);

    }

    @Test
    public void canReturnEmptyImgurListWhenAlbumsAndAnimationAreTrue() {
        ArrayList<ImgurItem> items = new ArrayList<>(5);
        ImgurItem item = new ImgurItem(false, "www", true);

        for (int i = 0; i < 5; i++) {
            items.add(item);
        }

        List<String> filteredList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isAlbum && !items.get(i).animated) {
                filteredList.add(items.get(i).link);
            }
        }
        assert_().that(filteredList.size()).isEqualTo(0);
    }
}

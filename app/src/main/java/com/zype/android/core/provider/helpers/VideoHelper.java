package com.zype.android.core.provider.helpers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.zype.android.core.provider.Contract;
import com.zype.android.core.provider.CursorHelper;
import com.zype.android.utils.Logger;
import com.zype.android.webapi.model.search.Segment;
import com.zype.android.webapi.model.video.Category;
import com.zype.android.webapi.model.video.Thumbnail;
import com.zype.android.webapi.model.video.VideoData;
import com.zype.android.webapi.model.video.VideoZobject;
import com.zype.android.webapi.model.zobjects.ZObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vasya
 * @version 1
 *          date 7/8/15
 */
public class VideoHelper {

    @NonNull
    public static ContentValues objectToContentValues(@NonNull VideoData videoData) {
        final ContentValues contentValues = new ContentValues(30); // wow, such optimization

        contentValues.put(Contract.Video.COLUMN_ID, videoData.getId());
        contentValues.put(Contract.Video.COLUMN_ACTIVE, videoData.isActive() ? 1 : 0);
        contentValues.put(Contract.Video.COLUMN_CATEGORY, new Gson().toJson(videoData.getCategories()));
        contentValues.put(Contract.Video.COLUMN_COUNTRY, videoData.getCountry());
        contentValues.put(Contract.Video.COLUMN_CREATED_AT, videoData.getCreatedAt());
        contentValues.put(Contract.Video.COLUMN_DESCRIPTION, (videoData.getDescription() == null) ? "" : videoData.getDescription());
        contentValues.put(Contract.Video.COLUMN_DISCOVERY_URL, videoData.getDiscoveryUrl());
        contentValues.put(Contract.Video.COLUMN_DURATION, videoData.getDuration());
        contentValues.put(Contract.Video.COLUMN_EPISODE, videoData.getEpisode());
        contentValues.put(Contract.Video.COLUMN_EXPIRE_AT, videoData.getExpireAt());
        contentValues.put(Contract.Video.COLUMN_FEATURED, videoData.isFeatured() ? 1 : 0);
        contentValues.put(Contract.Video.COLUMN_FOREIGN_ID, videoData.getForeignId());
        contentValues.put(Contract.Video.COLUMN_MATURE_CONTENT, videoData.isMatureContent() ? 1 : 0);
        contentValues.put(Contract.Video.COLUMN_ON_AIR, videoData.isOnAir() ? 1 : 0);
        contentValues.put(Contract.Video.COLUMN_PUBLISHED_AT, videoData.getPublishedAt());
        contentValues.put(Contract.Video.COLUMN_RATING, videoData.getRating());
        contentValues.put(Contract.Video.COLUMN_RELATED_PLAYLIST_IDS, new Gson().toJson(videoData.getRelatedPlaylistIds()));
        contentValues.put(Contract.Video.COLUMN_REQUEST_COUNT, videoData.getRequestCount());
        contentValues.put(Contract.Video.COLUMN_SEASON, videoData.getSeason());
        contentValues.put(Contract.Video.COLUMN_SHORT_DESCRIPTION, videoData.getShortDescription());
        contentValues.put(Contract.Video.COLUMN_SITE_ID, videoData.getSiteId());
        contentValues.put(Contract.Video.COLUMN_START_AT, videoData.getStartAt());
        contentValues.put(Contract.Video.COLUMN_STATUS, videoData.getStatus());
        contentValues.put(Contract.Video.COLUMN_SUBSCRIPTION_REQUIRED, videoData.isSubscriptionRequired() ? 1 : 0);
        contentValues.put(Contract.Video.COLUMN_TITLE, videoData.getTitle());
        contentValues.put(Contract.Video.COLUMN_UPDATED_AT, videoData.getUpdatedAt());
        contentValues.put(Contract.Video.COLUMN_THUMBNAILS, new Gson().toJson(videoData.getThumbnails()));
        contentValues.put(Contract.Video.COLUMN_TRANSCODED, videoData.isTranscoded() ? 1 : 0);
        contentValues.put(Contract.Video.COLUMN_HULU_ID, videoData.getHuluId());
        contentValues.put(Contract.Video.COLUMN_YOUTUBE_ID, videoData.getYoutubeId());
        contentValues.put(Contract.Video.COLUMN_CRUNCHYROLL_ID, videoData.getCrunchyrollId());
        contentValues.put(Contract.Video.COLUMN_KEYWORDS, new Gson().toJson(videoData.getKeywords()));
        contentValues.put(Contract.Video.COLUMN_VIDEO_ZOBJECTS, new Gson().toJson(videoData.getVideoZobjects()));
        contentValues.put(Contract.Video.COLUMN_ZOBJECT_IDS, new Gson().toJson(videoData.getZobjectIds()));
        contentValues.put(Contract.Video.COLUMN_SEGMENTS, new Gson().toJson(videoData.getSegments()));

        return contentValues;
    }

    @NonNull
    public static VideoData objectFromCursor(@NonNull Cursor cursor) {
        Gson gson = new Gson();
        VideoData video = VideoData.newVideo(
                cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_ACTIVE)) == 1,
                cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_COUNTRY))
        );
        if (cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_CATEGORY)) != null) {
            Type categoryType = new TypeToken<List<Category>>() {
            }.getType();
            video.setCategories(gson.<List<Category>>fromJson(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_CATEGORY)), categoryType));
        }
        video.setCreatedAt(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_CREATED_AT)));
        video.setDescription(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_DESCRIPTION)));
        video.setDiscoveryUrl(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_DISCOVERY_URL)));
        video.setDuration(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_DURATION)));
        if (cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_GUESTS)) != null) {
            Type guestsType = new TypeToken<List<ZObject>>() {
            }.getType();
            video.setGuests(gson.<List<ZObject>>fromJson(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_GUESTS)), guestsType));
        }
        video.setEpisode(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_EPISODE)));
        video.setExpireAt(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_EXPIRE_AT)));
        video.setFeatured(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_FEATURED)) == 1);
        video.setForeignId(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_FOREIGN_ID)));
        video.setMatureContent(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_MATURE_CONTENT)) == 1);
        video.setOnAir(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_ON_AIR)) == 1);
        video.setPlayingPosition(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_PLAY_TIME)));
        video.setPlayerAudioUrl(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_PLAYER_AUDIO_URL)));
        video.setPlayerVideoUrl(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_PLAYER_VIDEO_URL)));
        video.setPublishedAt(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_PUBLISHED_AT)));
        video.setRating(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_RATING)));
        video.setRequestCount(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_REQUEST_COUNT)));
        if (cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_RELATED_PLAYLIST_IDS)) != null) {
            Type relatedPlaylistIdsType = new TypeToken<List<String>>() {
            }.getType();
            video.setRelatedPlaylistIds(gson.<List<String>>fromJson(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_RELATED_PLAYLIST_IDS)), relatedPlaylistIdsType));
        }
        video.setSeason(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_SEASON)));
        video.setShortDescription(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_SHORT_DESCRIPTION)));
        video.setSiteId(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_SITE_ID)));
        video.setStartAt(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_START_AT)));
        video.setStatus(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_STATUS)));
        video.setSubscriptionRequired(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_SUBSCRIPTION_REQUIRED)) == 1);
        video.setTitle(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_TITLE)));

        try {
            if (cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_THUMBNAILS)) != null) {
                Type thumbnailType = new TypeToken<List<Thumbnail>>() {
                }.getType();
                video.setThumbnails(gson.<List<Thumbnail>>fromJson(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_THUMBNAILS)), thumbnailType));
            }
        } catch (IllegalStateException e) {
            Logger.e("objectFromCursor", e);
        }

        video.setUpdatedAt(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_UPDATED_AT)));
        video.setHuluId(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_HULU_ID)));
        video.setYoutubeId(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_YOUTUBE_ID)));
        video.setTranscoded(cursor.getInt(cursor.getColumnIndex(Contract.Video.COLUMN_TRANSCODED)) == 1);
        video.setCrunchyrollId(cursor.getString(cursor.getColumnIndex(Contract.Video.COLUMN_CRUNCHYROLL_ID)));

        if (cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_KEYWORDS)) != null) {
            Type keywordsType = new TypeToken<List<String>>() {
            }.getType();
            video.setKeywords(gson.<List<String>>fromJson(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_KEYWORDS)), keywordsType));
        }
        if (cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_VIDEO_ZOBJECTS)) != null) {
            Type videoZObjectsType = new TypeToken<List<VideoZobject>>() {
            }.getType();
            video.setVideoZobjects(gson.<List<VideoZobject>>fromJson(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_VIDEO_ZOBJECTS)), videoZObjectsType));
        }
        if (cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_ZOBJECT_IDS)) != null) {
            Type zObjectIdsType = new TypeToken<List<String>>() {
            }.getType();
            video.setZobjectIds(gson.<List<String>>fromJson(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_ZOBJECT_IDS)), zObjectIdsType));
        }
        if (cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_SEGMENTS)) != null) {
            Type segmentType = new TypeToken<List<Segment>>() {
            }.getType();
            video.setSegments(gson.<List<Segment>>fromJson(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_SEGMENTS)), segmentType));
        }
        return video;
    }

    private static VideoData getDownloadedData(Cursor cursor, VideoData videoData) {

        videoData.setDownloadAudioPath(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_DOWNLOAD_AUDIO_PATH)));
        videoData.setDownloadAudioUrl(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_DOWNLOAD_AUDIO_URL)));
        videoData.setDownloadVideoPath(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_DOWNLOAD_VIDEO_PATH)));
        videoData.setDownloadVideoUrl(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_DOWNLOAD_VIDEO_URL)));
        videoData.setPlayerVideoUrl(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_PLAYER_VIDEO_URL)));
        videoData.setPlayerAudioUrl(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_PLAYER_AUDIO_URL)));
        videoData.setVideoDownloaded(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_IS_DOWNLOADED_VIDEO)) == 1);
        videoData.setAudioDownloaded(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_IS_DOWNLOADED_AUDIO)) == 1);
        videoData.setAdVideoTag(cursor.getString(cursor.getColumnIndexOrThrow(Contract.Video.COLUMN_AD_VIDEO_TAG)));
        return videoData;
    }


    @Nullable
    private static VideoData getDownloadedData(ContentResolver contentResolver, String videoId) {
        if (TextUtils.isEmpty(videoId)) {
            return null;
        }
        VideoData videoData = null;
        Cursor cursor = CursorHelper.getVideoCursor(contentResolver, videoId);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                videoData = VideoHelper.objectFromCursor(cursor);
                videoData = VideoHelper.getDownloadedData(cursor, videoData);
            } else {
                return null;
//            throw new IllegalStateException("DB not contains video with ID=" + videoId);
            }
            cursor.close();
        }
        return videoData;
    }

    @Nullable
    public static VideoData getVideo(ContentResolver contentResolver, @Nullable String videoId) throws IllegalStateException {
        if (TextUtils.isEmpty(videoId)) {
            return null;
        }
        Cursor cursor = CursorHelper.getVideoCursor(contentResolver, videoId);
        VideoData videoData = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                videoData = VideoHelper.objectFromCursor(cursor);
            }
            cursor.close();
        }
        return videoData;
    }

    @Nullable
    public static VideoData getFullData(ContentResolver contentResolver, @Nullable String videoId) {
        return getDownloadedData(contentResolver, videoId);
    }


    @Nullable
    public static List<VideoData> getAllDownloads(ContentResolver contentResolver) {
        List<VideoData> downloads = new ArrayList<>();
        Cursor cursor = CursorHelper.getAllDownloadsCursor(contentResolver);
        VideoData videoData;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    videoData = VideoHelper.objectFromCursor(cursor);
                    videoData = VideoHelper.getDownloadedData(cursor, videoData);
                    downloads.add(videoData);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return downloads;
    }

    @Nullable
    public static void removeFavoritesInLocalVideoDb(ContentResolver contentResolver) {
        List<VideoData> favoritesList = new ArrayList<>();
        Cursor cursor = CursorHelper.getAllFavoritesVideoCursor(contentResolver);
        VideoData videoData;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    videoData = VideoHelper.objectFromCursor(cursor);
                    videoData = VideoHelper.getDownloadedData(cursor, videoData);
                    favoritesList.add(videoData);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        for (int i = 0; i < favoritesList.size(); i++) {
            Uri uri = Contract.Video.CONTENT_URI;
            ContentValues value = new ContentValues();
            value.put(Contract.Video.COLUMN_IS_FAVORITE, 0);
            contentResolver.update(uri, value, Contract.Video.COLUMN_ID + " =?", new String[]{favoritesList.get(i).getId()});
        }
    }
}

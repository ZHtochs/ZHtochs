package com.example.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import com.example.myapplication.ui.home.HomeFragment;

import java.io.*;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2022-05-28 21:59
 **/
public class Provider extends ContentProvider {
    @Override
    public boolean onCreate() {
        return false;
    }

    // https://blog.csdn.net/se7en1305687887/article/details/17398659
    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        ParcelFileDescriptor pfd = null;
        String filename = uri.getPathSegments().get(1);
        File file = new File(getContext().getFilesDir() + File.separator + "test", "test.mp3");
        if (!file.exists()) {
            try {
                InputStream in = getContext().getAssets().open(filename);
                BufferedInputStream bis = new BufferedInputStream(in);
                FileOutputStream fos = new FileOutputStream(file);
                int len = 0;
                byte[] b = new byte[1024];
                while ((len = bis.read(b)) != -1) {
                    fos.write(b, 0, len);
                }
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pfd = ParcelFileDescriptor.open(file,
                ParcelFileDescriptor.MODE_READ_ONLY);
        return pfd;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        File file = new File(getContext().getFilesDir() + File.separator + "test", "test.mp3");
        Uri uri = FileProvider.getUriForFile(getContext(), HomeFragment.au, file);
        Bundle bundle = new Bundle();
        bundle.putString(HomeFragment.au, uri.toString());
        return bundle;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
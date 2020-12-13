package com.github.zhtouchs;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ZHActivityManager {
    INSTANCE;
    private Context context;
    private final List<WeakReference<Activity>> weakReferenceList = new ArrayList<>();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Optional<Activity> getActivity(String activityName) {
        for (WeakReference<Activity> re : weakReferenceList) {
            if (re.get() != null && TextUtils.equals(re.get().getComponentName().getClassName(), activityName)) {
                return Optional.of(re.get());
            }
        }
        return Optional.empty();
    }

    public void addActivity(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        weakReferenceList.add(weakReference);
    }

    public void removeActivity(String activityName) {
        for (WeakReference<Activity> refer : weakReferenceList) {
            if (refer != null && TextUtils.equals(refer.get().getComponentName().getClassName(), activityName)) {
                refer.get().finish();
            }
        }
    }
}

package com.utopia.mvp.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ArrayBlockingQueue;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.util.Pools;

public final class AsyncLayoutInflater {
    private static final String TAG = "AsyncLayoutInflater";

    LayoutInflater mInflater;
    Handler mHandler;
    AsyncLayoutInflater.InflateThread mInflateThread;

    public AsyncLayoutInflater(@NonNull Context context) {
        mInflater = new AsyncLayoutInflater.BasicInflater(context);
        mHandler = new Handler(msg -> {
            InflateRequest request = (InflateRequest) msg.obj;
            if (request.view == null) {
                request.view = mInflater.inflate(
                        request.resid, request.parent, false);
            }
            request.callback.onInflateFinished(request.view);
            mInflateThread.releaseRequest(request);
            return true;
        });
        mInflateThread = AsyncLayoutInflater.InflateThread.getInstance();
    }

    @UiThread
    public void inflate(@LayoutRes int resid, @Nullable ViewGroup parent,
                        AsyncLayoutInflater.OnInflateFinishedListener callback) {
        AsyncLayoutInflater.InflateRequest request = mInflateThread.obtainRequest();
        request.inflater = this;
        request.resid = resid;
        request.parent = parent;
        request.callback = callback;
        mInflateThread.enqueue(request);
    }

    @UiThread
    public void inflate(@LayoutRes int resid, AsyncLayoutInflater.OnInflateFinishedListener callback) {
        AsyncLayoutInflater.InflateRequest request = mInflateThread.obtainRequest();
        request.inflater = this;
        request.resid = resid;
        request.callback = callback;
        mInflateThread.enqueue(request);
    }

    public interface OnInflateFinishedListener {
        void onInflateFinished(@NonNull View view);
    }

    private static class InflateRequest {
        AsyncLayoutInflater inflater;
        ViewGroup parent;
        int resid;
        View view;
        AsyncLayoutInflater.OnInflateFinishedListener callback;

        InflateRequest() {
        }
    }

    private static class BasicInflater extends LayoutInflater {
        private static final String[] sClassPrefixList = {
                "android.widget.",
                "android.webkit.",
                "android.app."
        };

        BasicInflater(Context context) {
            super(context);
        }

        @Override
        public LayoutInflater cloneInContext(Context newContext) {
            return new AsyncLayoutInflater.BasicInflater(newContext);
        }

        @Override
        protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
            for (String prefix : sClassPrefixList) {
                try {
                    View view = createView(name, prefix, attrs);
                    if (view != null) {
                        return view;
                    }
                } catch (ClassNotFoundException e) {
                    // In this case we want to let the base class take a crack
                    // at it.
                }
            }

            return super.onCreateView(name, attrs);
        }
    }

    private static class InflateThread extends Thread {
        private static final AsyncLayoutInflater.InflateThread sInstance;
        static {
            sInstance = new AsyncLayoutInflater.InflateThread();
            sInstance.start();
        }

        public static AsyncLayoutInflater.InflateThread getInstance() {
            return sInstance;
        }

        private final ArrayBlockingQueue<AsyncLayoutInflater.InflateRequest> mQueue = new ArrayBlockingQueue<>(10);
        private final Pools.SynchronizedPool<AsyncLayoutInflater.InflateRequest> mRequestPool = new Pools.SynchronizedPool<>(10);

        // Extracted to its own method to ensure locals have a constrained liveness
        // scope by the GC. This is needed to avoid keeping previous request references
        // alive for an indeterminate amount of time, see b/33158143 for details
        public void runInner() {
            AsyncLayoutInflater.InflateRequest request;
            try {
                request = mQueue.take();
            } catch (InterruptedException ex) {
                // Odd, just continue
                Log.w(TAG, ex);
                return;
            }

            try {
                request.view = request.inflater.mInflater.inflate(
                        request.resid, request.parent, false);
            } catch (RuntimeException ex) {
                // Probably a Looper failure, retry on the UI thread
                Log.w(TAG, "Failed to inflate resource in the background! Retrying on the UI"
                        + " thread", ex);
            }
            Message.obtain(request.inflater.mHandler, 0, request)
                    .sendToTarget();
        }

        @Override
        public void run() {
            while (true) {
                runInner();
            }
        }

        public AsyncLayoutInflater.InflateRequest obtainRequest() {
            AsyncLayoutInflater.InflateRequest obj = mRequestPool.acquire();
            if (obj == null) {
                obj = new AsyncLayoutInflater.InflateRequest();
            }
            return obj;
        }

        public void releaseRequest(AsyncLayoutInflater.InflateRequest obj) {
            obj.callback = null;
            obj.inflater = null;
            obj.parent = null;
            obj.resid = 0;
            obj.view = null;
            mRequestPool.release(obj);
        }

        public void enqueue(AsyncLayoutInflater.InflateRequest request) {
            try {
                mQueue.put(request);
            } catch (InterruptedException e) {
                throw new RuntimeException(
                        "Failed to enqueue async inflate request", e);
            }
        }
    }
}
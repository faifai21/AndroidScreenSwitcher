package com.github.androidscreenswitcher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class ScreenSwitcher implements FragmentManager.OnBackStackChangedListener{

    private final FragmentManager mFragmentManager;
    private final int mContainerID;
    private List<OnScreenSwitchListener> mOnScreenSwitchListeners = new ArrayList<>();
    private Stack<String> mCheckPoints = new Stack<>();
    private boolean mAddToStack, mClearStack, mIsCheckPoint;
    private String mTag;
    /**
     * To keep our checkpoints up to date, whenever the back stack of fragments changes, we check to see
     * if our checkpoint is still in the stack.
     * Not sure if this is entirely needed; we can move the code to onBackPressed() as well.
     */
    @Override
    public void onBackStackChanged() {
        if(mCheckPoints.isEmpty())
            return;
        final String latestCheckPoint = mCheckPoints.peek();

        if(findBackStackEntryByName(latestCheckPoint) == null)
            mCheckPoints.pop();
    }

    public static interface OnScreenSwitchListener {
        public void onScreenSwitched(ScreenFragment screenFragment);
    }

    public ScreenSwitcher(ActionBarActivity activity) {
        this(activity, R.id.screens_container);
    }

    public ScreenSwitcher(ActionBarActivity activity, int containerID) {
        mFragmentManager = activity.getSupportFragmentManager();
        mContainerID = containerID < 0 ? R.id.screens_container : containerID;
        mFragmentManager.addOnBackStackChangedListener(this);
    }

    public ScreenSwitcher addToStack(boolean add){
        mAddToStack = add;
        return this;
    }

    public ScreenSwitcher clearStack(boolean clear){
        mClearStack = clear;
        return this;
    }

    public ScreenSwitcher isCheckpoint(boolean checkpoint){
        mIsCheckPoint = checkpoint;
        return this;
    }

    public ScreenSwitcher withTag(String tag){
        mTag = tag;
        return this;
    }

    public void commit(ScreenFragment fragment){
        if (mClearStack) {
            clearBackStack();
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        final String tag = TextUtils.isEmpty(mTag) ? UUID.randomUUID().toString() : mTag;

        if (mAddToStack || mIsCheckPoint) {
            transaction.addToBackStack(tag);
        }

        notifyFragmentSwitch(fragment);

        transaction.replace(mContainerID, fragment, tag);
        transaction.commit();
        if(mIsCheckPoint)
            mCheckPoints.push(tag);
        // reset variables
        mIsCheckPoint = mAddToStack = mClearStack = false;
        mTag = "";
    }


    /**
     * Remove all fragments including the current fragment up until the checkpoint. If no checkpoints,
     * we do nothing.
     * @return true if pop was successful, false if there were no checkpoints to pop to
     */
    public boolean popTillCheckpoint(){
        if(mCheckPoints.isEmpty()) {
            Log.e("ScreenSwitcher", "No checkpoints to go to...");
            return false;
        }
        else{
            final String checkpointTag = mCheckPoints.peek();
            return popToTag(checkpointTag);
        }
    }

    public boolean popToTag(String tag){
        boolean success = mFragmentManager.popBackStackImmediate(tag, 0);
        newVisibleFragment();
        return success;
    }

    public boolean popToId(int id){
        boolean success = mFragmentManager.popBackStackImmediate(id, 0);
        newVisibleFragment();
        return success;
    }

    public boolean isFragmentOnBackstack(String tag){
        return mFragmentManager.findFragmentByTag(tag) != null;
    }

    public boolean isFragmentOnBackstack(int id){
        return mFragmentManager.findFragmentById(id) != null;
    }

    public void clearCheckpoints(){
        mCheckPoints.clear();
    }

    /**
     * Pop a number of screens from the stack
     * @param count the number of screens to pop
     */
    public void pop(int count){
        final int backstackPositionToGoTo = mFragmentManager.getBackStackEntryCount() - 1 - count;
        final FragmentManager.BackStackEntry backStackEntry = mFragmentManager.getBackStackEntryAt(backstackPositionToGoTo);
        String tag = backStackEntry.getName();
        if(!TextUtils.isEmpty(tag))
            popToTag(tag);
        else
            popToId(backStackEntry.getId());
    }

    /**
     * Pop a single screen from stack
     */
    public void pop(){
        if(mFragmentManager.getBackStackEntryCount() < 1)
            Log.w("ScreenSwitcher", "Fragment backstack is empty; nothing to pop");
        else {
            mFragmentManager.popBackStackImmediate();
            newVisibleFragment();
        }
    }

    private void newVisibleFragment(){
        Fragment visible = getVisibleFragment();
        if(visible != null && visible instanceof ScreenFragment)
            notifyFragmentSwitch((ScreenFragment) visible);
    }

    public void clearBackStack() {
        for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
            mFragmentManager.popBackStackImmediate();
        }
        mCheckPoints.clear();
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = mFragmentManager;
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    public void addOnScreenSwitchListener(OnScreenSwitchListener onScreenSwitchListener) {
        mOnScreenSwitchListeners.add(onScreenSwitchListener);
    }

    public void onBackPressed() {
        newVisibleFragment();
    }

    /**
     * Find a backstack entry by its tag
     * @param tag name or tag of backstack entry
     * @return BackStackEntry or null if none could be found
     */
    private FragmentManager.BackStackEntry findBackStackEntryByName(String tag){
        for(int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++)
            if(mFragmentManager.getBackStackEntryAt(i).getName().equals(tag))
                return mFragmentManager.getBackStackEntryAt(i);
        return null;
    }

    private void notifyFragmentSwitch(ScreenFragment screenFragment) {
        for (OnScreenSwitchListener onScreenSwitchListener : mOnScreenSwitchListeners) {
            onScreenSwitchListener.onScreenSwitched(screenFragment);
        }
    }

}

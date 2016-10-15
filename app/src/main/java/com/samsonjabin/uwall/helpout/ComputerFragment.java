package com.samsonjabin.uwall.helpout;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by AravindRaj on 09-04-2015.
 */
public class ComputerFragment extends Fragment {
    public static ComputerFragment getInstances(int position) {
        ComputerFragment compfrag = new ComputerFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        compfrag.setArguments(args);
        return compfrag;
    }
}

package com.samsonjabin.uwall.helpout;

import android.os.Bundle;
import android.support.v4.app.Fragment;


public class ComputerFragment extends Fragment {
    public static ComputerFragment getInstances(int position) {
        ComputerFragment compfrag = new ComputerFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        compfrag.setArguments(args);
        return compfrag;
    }
}

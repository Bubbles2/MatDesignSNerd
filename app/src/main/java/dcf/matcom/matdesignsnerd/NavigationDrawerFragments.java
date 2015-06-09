package dcf.matcom.matdesignsnerd;


import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragments extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    //
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    final static String PREF_FILE_NAME = "test_pref";
    final static String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    //
    private View containerView;


    public NavigationDrawerFragments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        mUserLearnedDrawer =  Boolean.valueOf(readPreference(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        // coming back from rotation
        if (savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }


    public void setup( int fragmentId,DrawerLayout dl,Toolbar toolbar) {
        //
        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = dl;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), dl, toolbar, R.string.drawer_open, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Check if first time
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreference(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");

                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        // If very first time
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);

        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // Add Hamburger Icon
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }
        //

    public static void saveToPreference(Context context,String preferenceName,String preferenceValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        //editor.commit();
        editor.apply();

    }
   //
   public static String readPreference(Context context,String preferenceName,String defaultValue)
   {
       SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,context.MODE_PRIVATE);
       return sharedPreferences.getString(preferenceName,defaultValue);

   }
}

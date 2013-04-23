package com.fizz.attenda;

import com.fizz.attenda.sqlhelpers.SQLhelper;

import android.R.color;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SubjectSetup extends ListActivity{
	protected Object mActionMode;
	private int selectedItem=-1;
	  
	private SQLhelper db=null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		db=new SQLhelper(getApplicationContext());
		new LoadCursorTask().execute();
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

		      @Override
		      public boolean onItemLongClick(AdapterView<?> parent, View view,
		          int position, long id) {

		        if (mActionMode != null) {
		          return false;
		        }
		       selectedItem =position+1;
		       Log.d("test", "in actionmode, delete no "+selectedItem);
		       
				

		        // Start the CAB using the ActionMode.Callback defined above
		        mActionMode = SubjectSetup.this.startActionMode(mActionModeCallback);
		        view.setSelected(true);
		    	return true;
		    	  
		      }
		    });
	}


	  private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	      MenuInflater inflater = mode.getMenuInflater();
	      inflater.inflate(R.menu.menusubsel, menu);
	      return true;
	    }

	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	      return false; // Return false if nothing is done
	    }

	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.delete:
	    	  deletesubject();
	        // Action picked, so close the CAB
	        mode.finish();
	        return true;
	      default:
	        return false;
	      }
	    }

	    // Called when the user exits the action mode
	    public void onDestroyActionMode(ActionMode mode) {
	      mActionMode = null;
	    }
	  };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menusub, menu);
        return true;
    }
    protected void deletesubject() {
    	new DeleteTask().execute(selectedItem);
		// TODO Auto-generated method stub
		
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.add_subject:
    	  add();
        break;

      default:
        break;
      }

      return true;
    } 
	private int a=0;
    private void add() {
    	 ContentValues values=new ContentValues(2);
    	 
    	 values.put(SQLhelper.NAME, "hello  testing");
    	  values.put(SQLhelper.CREDITS, "okiedok"+a);
    	  a++;
    	  values.put(SQLhelper.LAB, "T");

    	    new InsertTask().execute(values);
        //LayoutInflater inflater=getApplicationContext().getLayoutInflater();
        //View addView=inflater.inflate(R.layout.add_edit, null);
        //AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        //builder.setTitle(R.string.add_title).setView(addView)
         //      .setPositiveButton(R.string.ok, this)
          //     .setNegativeButton(R.string.cancel, null).show();
     }
	@Override
	public void onDestroy() {
	super.onDestroy();
	((CursorAdapter)getListAdapter()).getCursor().close();
	db.close();}

	  private Cursor doQuery() {
	    return(db.getReadableDatabase().rawQuery("SELECT _id, name, credits,lab "
	                                                 + "FROM subjects ORDER BY name",
	                                             null));
	  }

	  private class LoadCursorTask extends AsyncTask<Void, Void, Void> {
	    private Cursor constantsCursor=null;

	    @Override
	    protected Void doInBackground(Void... params) {
	      constantsCursor=doQuery();
	      constantsCursor.getCount();

	      return(null);
	    }

	    @SuppressWarnings("deprecation")
	    @Override
	    public void onPostExecute(Void arg0) {
	      SimpleCursorAdapter adapter;
	      
	      if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
	        adapter=new SimpleCursorAdapter(getApplicationContext(), R.layout.row_subjects,
	                                  constantsCursor, new String[] { SQLhelper.NAME,SQLhelper.CREDITS,SQLhelper.LAB },
	                                  new int[] { R.id.name, R.id.credits,R.id.lab },
	                                  0);
	      }
	      else {
	        adapter=new SimpleCursorAdapter(getApplicationContext(), R.layout.row_subjects,
	                                        constantsCursor, new String[] {   SQLhelper.NAME,
                SQLhelper.CREDITS,SQLhelper.LAB },
	                                        new int[] {R.id.name, R.id.credits,R.id.lab });
	      }

	      setListAdapter(adapter);
	    }
	  }
	  private class InsertTask extends AsyncTask<ContentValues, Void, Void> {
		    private Cursor subjectCursor=null;

		    @Override
		    protected Void doInBackground(ContentValues... values) {
		      db.getWritableDatabase().insert(SQLhelper.DATABASE_NAME,
		                                      SQLhelper.NAME, values[0]);

		      subjectCursor=doQuery();
		      subjectCursor.getCount();

		      return(null);
		    }

		    @Override
		    public void onPostExecute(Void arg0) {
		      ((CursorAdapter)getListAdapter()).changeCursor(subjectCursor);
		    }
		  }
	  private class DeleteTask extends AsyncTask<Integer, Void, Void> {
		    private Cursor subjectCursor=null;

		   
		    @Override
		    public void onPostExecute(Void arg0) {
		    	((CursorAdapter)getListAdapter()).changeCursor(subjectCursor);

			      selectedItem=-1;
				  
		    }


			@Override
			protected Void doInBackground(Integer... params) {
				 Log.d("test", "delete no "+selectedItem);
				//Toast.makeText(getApplicationContext(), "in nooobist"+selectedItem, Toast.LENGTH_SHORT).show();
				db.getWritableDatabase().delete(SQLhelper.DATABASE_NAME, "_id = "+selectedItem,null);
				 subjectCursor=doQuery();
			     
			      subjectCursor.getCount();
				return null;
			}
		  }
	

}

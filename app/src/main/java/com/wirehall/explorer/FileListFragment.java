package com.wirehall.explorer;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.wirehall.audiorecorder.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

public class FileListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    public interface FileListFragmentListener {
        void onFileItemClicked(String filePath);
    }

    FileListFragmentListener activity;
    List<File> fileList;
    public static final String STORAGE_PATH = Environment.getExternalStorageDirectory().toString() + "/Rec/Collection";


    public static FileListFragment newInstance() {
        FileListFragment fileListFragment = new FileListFragment();
        return fileListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (FileListFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.file_list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fileList = FileUtils.getAllFilesFromDirectory(STORAGE_PATH, new FileExtensionFilter());
        FileListAdapter fileListAdapter = new FileListAdapter(getContext(), fileList);
        setListAdapter(fileListAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
        activity.onFileItemClicked(fileList.get(position).getPath());
    }


    /**
     * Class to filter files with .mp3 extension
     */
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".rec") || name.endsWith(".REC"));
        }
    }
}

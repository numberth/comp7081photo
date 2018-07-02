package com.example.trista.photogallery;

import android.util.Log;

import com.example.trista.photogallery.mydb.DataStoreImp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private DataStoreImp ds;
    private ArrayList<String> selectedDelete;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ds = new DataStoreImp();

        selectedDelete = new ArrayList<>();
        selectedDelete.add("R.drawable.a");
        selectedDelete.add("R.drawable.b");
        selectedDelete.add("R.drawable.c");
        selectedDelete.add("R.drawable.d");
        selectedDelete.add("R.drawable.e");
    }

    @Mock
    File mockedFile;

    @Test
    public void testCreateFile(){
        File dir = new File(File.separator + "path");
        File file = ds.createFile("burnaby", "test-caption", dir);
        assertNotNull(file);

        String splitter = File.separator.replace("\\","\\\\");
        String [] filePath = file.getPath().split(splitter);
        String [] fileName = filePath[2].split("_");

        assertEquals(filePath[1], "path");
        assertEquals(fileName[3], "burnaby");
        assertEquals(fileName[4], "test-caption.jpg");
    }

    @Test
    public void testDelete(){
//        when(ds.deleteFile(selectedDelete)).thenReturn(true);
        boolean deleted = ds.deleteFile(selectedDelete);
        assertTrue(deleted);
    }

    @Test
    public void testRename(){
        File old = new File("oldFile.jpg");
        File renamed = new File("newFile.jpg");
        renamed.delete();
        boolean nameChanged = ds.renameFile(old, renamed);

        assertTrue(nameChanged);
        Log.i("Espresso test:", "TEST COMPLETE!");
    }
}
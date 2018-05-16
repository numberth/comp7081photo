package com.example.trista.photogallery;

import com.example.trista.photogallery.mydb.DataStoreImp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private DataStoreImp ds;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ds = new DataStoreImp();
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
        when(ds.deleteFile(mockedFile)).thenReturn(true);
        boolean deleted = ds.deleteFile(mockedFile);
        assertTrue(deleted);
    }

    @Test
    public void testRename(){
        File old = new File("oldFile.jpg");
        File renamed = new File("newFile.jpg");
        renamed.delete();
        boolean nameChanged = ds.renameFile(old, renamed);

        assertTrue(nameChanged);
    }
}
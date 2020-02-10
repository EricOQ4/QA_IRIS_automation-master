package specs;

/**
 * Created by _____ on 2020-02-05
 */

// This class is everything you need to preform a Download Verification, which means everything that happens after you click a button which should download a file

// The current functionality is:
//      get default download directory, verify the latest existing file, if it was downloaded sucessfully store it
//      allow data comparison by reading CSV & excel files and comparing it to a specified grid

import bsh.commands.dir;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import java.util.Vector;

public class downloadVerification {

    private boolean successfulDownload;
    private final File downloadedFile;

    ///////////////////////////////////////////////////////////////////////////////////// private methods /////////////////////////////////////////////////////////////////////////////////////

    // This method takes a parameter dirPath which specifies where the download directory is, and returns
    //      the most recently modified file or null if the directory is empty

    private File getMostRecentFile(String dirPath){
        File downloadDirectory = new File(dirPath);

        File[] listOfDownloads = downloadDirectory.listFiles();
        int length = listOfDownloads.length;

        if (listOfDownloads == null || length == 0) {
            return null;
        }

        File lastModifiedFile = listOfDownloads[0];

        for (int i = 1; i < length; i++) {
            if (lastModifiedFile.lastModified() < listOfDownloads[i].lastModified()) {
                lastModifiedFile = listOfDownloads[i];
            }
        }

        return lastModifiedFile;
    }

    // This method takes a parameter downloadPath which specifies where the download directory is, and returns
    //      the file that was most recently downloaded file, or null if the download failed

    private File getLatestDownloadFromDirectory(String downloadPath){

        File latestDownload = getMostRecentFile(downloadPath);

        if(latestDownload == null){
            System.out.println("Did not wait long enough for download to start or incorrect download path");
            return null;
        }

        // This statement just checks if the latest file modified was modified more than a quarter of a second ago (meaning it was not newly downloaded)
        //      to verify that the most recent file was recent enough to be freshly downloaded
        //      (this might fail if in SavedTargetsPage the wait is not long enough to allow the download to start)

        if(System.currentTimeMillis() - latestDownload.lastModified() > 250){
            return null;
        }

        // Here we just wait for the download to finish by making sure that the most recent file has stopped changing size, this might fail if the internet connection is unstable
        //      The complication in this section is that a file that's downloading goes through multiple names in the process (thus changing latestDownload)
        //      So you must loop a few times to make sure that the file size you have does not keep changing (indicating a download is in progress still)

        while(true) {

            long size1 = latestDownload.length();

            try {
                Thread.sleep(100L); // INCREASE THIS VALUE IF INTERNET IS UNSTABLE AND DOWNLOAD IS STALLING MID DOWNLOAD
            } catch (Exception InterruptedException) {
                //Not sure how to handle this, since it should never happen
                InterruptedException.printStackTrace();
            }

            long size2 = latestDownload.length();

            if(size1 == size2){ //if the file is done downloading then break out
                break;
            }

            while (size1 != size2) { //if the file didn't finish downloading then set your bool to false

                size1 = latestDownload.length();

                try {
                    Thread.sleep(100L); // INCREASE THIS VALUE IF INTERNET IS UNSTABLE AND DOWNLOAD IS STALLING MID DOWNLOAD
                } catch (Exception InterruptedException) {
                    //Not sure how to handle this, since it should never happen
                    InterruptedException.printStackTrace();
                }

                size2 = latestDownload.length();
            }

            latestDownload = getMostRecentFile(downloadPath);
        }

        return latestDownload;
    }

    ///////////////////////////////////////////////////////////////////////////////////// public methods (you call these) /////////////////////////////////////////////////////////////////////////////////

        // this is the constructor, it is called when you make a new object of this class type
    public downloadVerification(){

        // This is currently how you find the default download on windows, feel free to update or add stuff if it's no longer working
        String downloadDirectory = System.getProperty("user.home") + "/Downloads/";

        // Initialize this to false since we don't know if it was successful yet
        successfulDownload = false;

        // Call the above method to grab most recent download, might return null indicating a download fail
        downloadedFile = getLatestDownloadFromDirectory(downloadDirectory);

        if(downloadedFile != null){ // set to true if the downloadedFile exists
            successfulDownload = true;
        }
    }

    public boolean wasSuccessfulDownload(){
        return this.successfulDownload;
    }

    public File getDownloadedFile(){
        return this.downloadedFile;
    }

    // uses apache commons csv library
    public boolean compareDataToCSV(Vector<Vector<String>> grid){

        boolean dataMatches = true;
        int numColumns = grid.size();

        try {

            BufferedReader reader = Files.newBufferedReader(downloadedFile.toPath());
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);

            int currentRow = 1;

            for (CSVRecord record : records) {

                for(int i = 0; i < numColumns; i++){

                  if( !(record.get(grid.elementAt(i).elementAt(0)).equals(grid.elementAt(i).elementAt(currentRow))) ){
                      System.out.println("Mismatch at row " + currentRow + ", Excel: '" + record.get(grid.elementAt(i).elementAt(0)) + "' Website: '" + grid.elementAt(i).elementAt(currentRow) + "'");
                      dataMatches = false;
                  }
                }

                currentRow++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return dataMatches;
    }


}

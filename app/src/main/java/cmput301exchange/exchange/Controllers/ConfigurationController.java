package cmput301exchange.exchange.Controllers;

/**
 * Created by touqir on 29/10/15.
 */
public class ConfigurationController {
    private boolean AutoPictureDownload=true;
    public ConfigurationController() {
    }

    public void enableAutoPicDownloads(){
        if (AutoPictureDownload==true){
            return;
        }
        else {
            // code for saving into the model the boolean variable of autoPicDownloads
            AutoPictureDownload=true;
            return;
        }
    }

    public void disableAutoPicDownloads(){
        if (AutoPictureDownload==false){
            return;
        }
        else {
            // code for saving into the model the boolean variable of autoPicDownloads
            AutoPictureDownload=false;
            return;
        }
    }

    public Boolean getPicDownloadState(){
        return AutoPictureDownload;
    }

}

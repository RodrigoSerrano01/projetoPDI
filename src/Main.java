import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class Main {


    public static void main(String[] args) {

        //OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Imgcodecs imageCodecs = new Imgcodecs();

    Mat matrix = imageCodecs.imread("C:\\Users\\Rodrigo\\Desktop\\Imagens PDI\\imagem1.png");


        System.out.println("Image Loaded");



    }




}
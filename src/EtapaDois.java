import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * Author Felipe
 *
 */

public class EtapaDois {

    public static void main(String[] args) throws IOException {
        // Carrega a biblioteca OpenCV
       // nu.pattern.OpenCV.loadLocally();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("Loaded OpenCV version " + Core.VERSION);

        // Carrega a imagem de entrada
        Mat image = Imgcodecs.imread("C:\\Users\\Rodrigo\\Desktop\\Imagens PDI\\imagem1.png");

        // Verifica se a imagem foi carregada corretamente
        if (image.empty()) {
            System.out.println("Não foi possível carregar a imagem");
            return;
        }
        // Cria uma nova matriz para a imagem em escala de cinza
        Mat grayImage = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);

        // Converte a imagem para escala de cinza
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Exibe as dimensões da imagem
        int largura = image.width();
        int altura = image.height();
        System.out.println("Dimensões da imagem: " + largura + "x" + altura);

        minimoMaximoPercentuaisEscalaCinza(grayImage);

        // Salva a imagem em escala de cinza
        //Imgcodecs.imwrite("imagem_em_escala_de_cinza.jpg", grayImage);

        System.out.println("Imagem convertida para escala de cinza");

        exibirImagem(grayImage);
    }

    private static void minimoMaximoPercentuaisEscalaCinza(Mat grayImage) {
        // Inicializa o mínimo valor de cinza com um valor alto inicial
        double minGrayValue = Double.MAX_VALUE;
        double maxGrayValue = Double.MIN_VALUE;

        Map<Integer, Integer> grayLevelCounts = new HashMap<>();

        // Percorre todos os pixels da imagem em escala de cinza
        int largura = grayImage.width();
        int altura = grayImage.height();
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                double grayValue = grayImage.get(y, x)[0];
                grayLevelCounts.put((int) grayValue, grayLevelCounts.getOrDefault((int) grayValue, 0) + 1);
                // Verifica se o valor de cinza é menor que o mínimo atual
                if (grayValue < minGrayValue) {
                    minGrayValue = grayValue;
                }
                // Verifica se o valor de cinza é Maior que o mínimo atual
                if (grayValue > maxGrayValue) {
                    maxGrayValue = grayValue;
                }
            }
        }

        System.out.println("O mínimo valor de cinza é: " + minGrayValue);
        System.out.println("O Maximo valor de cinza é: " + maxGrayValue);
        int totalPixels = largura * altura;
        calculaPercentuais(grayLevelCounts, totalPixels);
    }

    private static void calculaPercentuais(Map<Integer, Integer> grayLevelCounts, int totalPixels) {

        Map<Integer, Double> grayLevelPercentages = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : grayLevelCounts.entrySet()) {
            int grayLevel = entry.getKey();
            int count = entry.getValue();
            double percentage = (double) count / totalPixels * 100.0;
            grayLevelPercentages.put(grayLevel, percentage);
        }

        // Exibe os percentuais de cada valor de cinza
        for (Map.Entry<Integer, Double> entry : grayLevelPercentages.entrySet()) {
            int grayLevel = entry.getKey();
            double percentage = entry.getValue();
            System.out.println("Valor de cinza: " + grayLevel + ", Percentual: " + percentage + "%");
        }
    }

    private static void exibirImagem(Mat grayImage) throws IOException {
        //Encoding the image
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", grayImage, matOfByte);

        byte[] byteArray = matOfByte.toArray();

        //Preparing the Buffered Image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(in);
        //Instantiate JFrame
        JFrame frame = new JFrame();

        //Set Content to the JFrame
        frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
        frame.pack();
        frame.setVisible(true);
    }
}

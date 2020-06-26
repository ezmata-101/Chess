package MainPackage;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChessItem {
    public static final String SOURCE_PATH = "Resource\\";
    public static final int BLACK=0;
    public static final int WHITE=1;
    enum CHESS_ITEM{
        BISHOP("bishop"),
        KING("king"),
        QUEEN("queen"),
        KNIGHT("knight"),
        PAWN("pawn"),
        ROOK("rook");
        private String fileName;
        CHESS_ITEM(String fileName) {
            this.fileName = fileName;
        }
        public String getFileName(){
            return fileName;
        }
    }
    private ImageView imageView;
    private int posX, posY;
    private int color;
    private CHESS_ITEM type;

    public ChessItem(int color, CHESS_ITEM type){
        this.color = color;
        this.type = type;
        createImageView();
    }
    public ChessItem(int color, CHESS_ITEM type, int row, int col){
        this.color = color;
        this.type = type;
        posX = row;
        posY = col;
        createImageView();
    }
    public void setPosition(int row, int column){
        posY = column;
        posX = row;
    }
    public ImageView createImageView(){
        String filePath = SOURCE_PATH;
        if(color == BLACK) filePath+="Black\\";
        if(color == WHITE) filePath+="White\\";
        filePath+=type.getFileName()+".png";
        Image image;
        try{
            image = new Image(new FileInputStream(filePath));
        }catch (FileNotFoundException e){
            System.out.println("IMAGE NOT FOUND!");
            return null;
        }
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                Color color = pixelReader.getColor(x, y);
                int ok = 0;
                if(Math.abs(color.getRed() - 1.0) < 0.000001) ok++;
                if(Math.abs(color.getBlue() - 1.0) < 0.000001) ok++;
                if(Math.abs(color.getGreen() - 1.0) < 0.000001) ok++;

                if(ok == 3) continue;
                pixelWriter.setColor(x,y,color);
            }
        }
        imageView = new ImageView(writableImage);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);

        return imageView;
    }
    public ImageView getImageView(){
        return imageView;
    }
}

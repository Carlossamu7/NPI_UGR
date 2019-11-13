package com.example.alhambra;

import android.content.Context;

public class PuzzlePiece extends androidx.appcompat.widget.AppCompatImageView {
    public int xCoord;              // abcisa de la pieza
    public int yCoord;              // ordenada de la pieza
    public int pieceWidth;          // anchura de la pieza
    public int pieceHeight;         // altura de la pieza
    public boolean canMove = true;  // booleano que indica si podemos mover la pieza

    public PuzzlePiece(Context context) {
        super(context);
    }
}
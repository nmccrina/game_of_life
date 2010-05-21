/*
* Copyright (c) 2010, Nathan McCrina
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are
* met:
*
*    * Redistributions of source code must retain the above copyright
*    notice, this list of conditions and the following disclaimer.
*    * Redistributions in binary form must reproduce the above copyright
*    notice, this list of conditions and the following disclaimer in the
*    documentation and/or other materials provided with the distribution.
*    * Neither the name of the project nor the names of its
*    contributors may be used to endorse or promote products derived from
*    this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
* IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
* PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
* EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
* PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
* LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.mccrina.GameOfLife;

import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class GameBoard
{
    private boolean[][] board;

    public GameBoard()
    {
	this.board = new boolean[LifeData.BOARD_SIZE][LifeData.BOARD_SIZE];
	
	for (int row = 0; row < LifeData.BOARD_SIZE; row++)
	{
	    for (int col = 0; col < LifeData.BOARD_SIZE; col++)
	    {
		this.board[row][col] = false;
	    }
	}
    }

    public void toggleSquare(int row, int col)
    {
	board[row][col] = !board[row][col];
    }

    public void applyConfiguration(ArrayList<Point> coordinates)
    {
	for (int count = 0; count < coordinates.size(); count++)
	{
	    this.board[coordinates.get(count).x][coordinates.get(count).y] = true;
	}
    }

    public boolean[][] getBoard()
    {
	return this.board;
    }

    public void tick()
    {
	boolean[][] newBoard = new boolean[LifeData.BOARD_SIZE][LifeData.BOARD_SIZE];
	int neighbours;
	
	for (int row = 0; row < LifeData.BOARD_SIZE; row++)
	{
	    for (int col = 0; col < LifeData.BOARD_SIZE; col++)
	    {
		newBoard[row][col] = this.board[row][col];
	    }
	}

	for (int row = 0; row < LifeData.BOARD_SIZE; row++)
	{
	    for (int col = 0; col < LifeData.BOARD_SIZE; col++)
	    {
		neighbours = this.getNumberOfNeighbours(row, col);
		if (this.board[row][col])
		{
		    if (neighbours < 2 || neighbours > 3)
		    {
			newBoard[row][col] = false;
		    }
		}
		else
		{
		    if (neighbours == 3)
		    {
			newBoard[row][col] = true;
		    }
		}
	    }
	}

	this.board = newBoard;
    }

    public void clear()
    {
	for (int row = 0; row < LifeData.BOARD_SIZE; row++)
	{
	    for (int col = 0; col < LifeData.BOARD_SIZE; col++)
	    {
		this.board[row][col] = false;
	    }
	}
    }

    private int getNumberOfNeighbours(int row, int col)
    {
	int sum = 0;
	boolean[] status = new boolean[8];
		
	status[0] = this.getUpperRightStatus(row, col);
	status[1] = this.getUpperMiddleStatus(row, col);
	status[2] = this.getUpperLeftStatus(row, col);
	status[3] = this.getMiddleRightStatus(row, col);
	status[4] = this.getMiddleLeftStatus(row, col);
	status[5] = this.getLowerRightStatus(row, col);
	status[6] = this.getLowerMiddleStatus(row, col);
	status[7] = this.getLowerLeftStatus(row, col);
		
	for (int count = 0; count < 8; count++)
	{
	    if (status[count])
	    {
		sum++;
	    }
	}
	
	return sum;
    }
	
    private boolean getUpperRightStatus(int row, int col)
    {
	return this.board[this.decrementCoordinate(row)][this.incrementCoordinate(col)];
    }
	
    private boolean getUpperMiddleStatus(int row, int col)
    {
	return this.board[this.decrementCoordinate(row)][col];
    }
	
    private boolean getUpperLeftStatus(int row, int col)
    {
	return this.board[this.decrementCoordinate(row)][this.decrementCoordinate(col)];
    }

    private boolean getMiddleRightStatus(int row, int col)
    {
	return this.board[row][this.incrementCoordinate(col)];
    }
	
    private boolean getMiddleLeftStatus(int row, int col)
    {
	return this.board[row][this.decrementCoordinate(col)];
    }
	
    private boolean getLowerRightStatus(int row, int col)
    {
	return this.board[this.incrementCoordinate(row)][this.incrementCoordinate(col)];
    }

    private boolean getLowerMiddleStatus(int row, int col)
    {
	return this.board[this.incrementCoordinate(row)][col];
    }

    private boolean getLowerLeftStatus(int row, int col)
    {
	return this.board[this.incrementCoordinate(row)][this.decrementCoordinate(col)];
    }
	
    private int incrementCoordinate(int c)
    {
	if (c == LifeData.BOARD_SIZE - 1)
	{
	    return 0;
	}
	else
	{
	    return c + 1;
	}
    }

    private int decrementCoordinate(int c)
    {
	if (c == 0)
	{
	    return LifeData.BOARD_SIZE - 1;
	}
	else
	{
	    return c - 1;
	}
    }
}

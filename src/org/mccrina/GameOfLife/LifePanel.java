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

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class LifePanel extends JPanel
{
    private boolean[][] cellData;
    public LifePanel()
    {
	super();
	cellData = new boolean[LifeData.BOARD_SIZE][LifeData.BOARD_SIZE];
    }

    public void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	this.setBackground(Color.WHITE);
	g.setColor(Color.BLACK);

	if (LifeData.EDIT_MODE)
	{
	    drawEditGrid(g);
	    drawCells(g);
	}
	else
	{
	    drawCells(g);
	}
    }

    public void drawCells(Graphics g)
    {
	for (int row = 0; row < LifeData.BOARD_SIZE; row++)
	{
	    for (int col = 0; col < LifeData.BOARD_SIZE; col++)
	    {
		if (cellData[row][col])
		{
		    g.fillRect((col * 10), (row * 10), 10, 10);
		}
	    }
	}
    }

    public void drawEditGrid(Graphics g)
    {
	// Draw the grid lines
	for (int row = 0; row <= LifeData.BOARD_SIZE; row++)
	{
	    g.drawLine(0, row * 10, 650, row * 10);
	}

	for (int col = 0; col < LifeData.BOARD_SIZE; col++)
	{
	    g.drawLine(col * 10, 0, col * 10, 605);
	}
    }

    public void setCellData(boolean[][] data)
    {
	cellData = data;
    }
}

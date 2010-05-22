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
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;

public class LifeController
{
    private GameWindow window;
    private GameBoard board;
    private Thread drawThread;

    public LifeController(GameWindow newWindow, GameBoard newBoard)
    {
	window = newWindow;
	board = newBoard;

	// Set up events
	window.getLifeMenuBar().addExitMenuItemListener(
	    new ExitMenuItemHandler());

	window.getLifeMenuBar().addEditCheckBoxListener(
	    new EditCheckBoxHandler());
	
	window.getLifeMenuBar().addAboutMenuItemListener(
	    new AboutMenuItemHandler());

	window.getLifeMenuBar().addSlowButtonListener(
	    new SlowRadioButtonHandler());

	window.getLifeMenuBar().addMediumButtonListener(
	    new MediumRadioButtonHandler());

	window.getLifeMenuBar().addFastButtonListener(
	    new FastRadioButtonHandler());

	window.getLifeMenuBar().addLoadMenuItemListener(
	    new LoadMenuItemHandler());

	window.getLifeMenuBar().addClearMenuItemListener(
	    new ClearMenuItemHandler());

	window.getLifeMenuBar().addStartMenuItemListener(
	    new StartMenuItemHandler());

	window.getLifeMenuBar().addStopMenuItemListener(
	    new StopMenuItemHandler());

	window.getLifeMenuBar().addSaveMenuItemListener(
	    new SaveMenuItemHandler());

	window.getLifePanel().addMouseListener(
	    new LifePanelMouseHandler());
    }

    // Event handlers for all the GUI elements
    class ExitMenuItemHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    LifeData.RUNNING = false;

	    try
	    {
		Thread.sleep(50);
	    }
	    catch (InterruptedException ex)
	    {
	    }

	    System.exit(0);
	}
    }

    class EditCheckBoxHandler implements ItemListener
    {
	public void itemStateChanged(ItemEvent e)
	{
	    window.getLifeMenuBar().toggleStartMenuItem();
	    window.getLifeMenuBar().toggleStopMenuItem();
	    window.getLifeMenuBar().toggleSaveMenuItem();
	    window.getLifeMenuBar().toggleClearMenuItem();
	    LifeData.EDIT_MODE = !LifeData.EDIT_MODE;
	    window.getLifePanel().repaint();
	}
    }

    class AboutMenuItemHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    JOptionPane.showMessageDialog(window,
		"Copyright (c) 2010, Nathan McCrina\nAll rights reserved.",
		"About", JOptionPane.PLAIN_MESSAGE,
		null);
	}
    }

    class SlowRadioButtonHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    window.getLifeMenuBar().getSlowButton().setSelected(true);
	    window.getLifeMenuBar().getMediumButton().setSelected(false);
	    window.getLifeMenuBar().getFastButton().setSelected(false);
	    LifeData.TICK_DELAY = 1000;
	}
    }

    class MediumRadioButtonHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    window.getLifeMenuBar().getSlowButton().setSelected(false);
	    window.getLifeMenuBar().getMediumButton().setSelected(true);
	    window.getLifeMenuBar().getFastButton().setSelected(false);
	    LifeData.TICK_DELAY = 400;
	}
    }
    
    class FastRadioButtonHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    window.getLifeMenuBar().getSlowButton().setSelected(false);
	    window.getLifeMenuBar().getMediumButton().setSelected(false);
	    window.getLifeMenuBar().getFastButton().setSelected(true);
	    LifeData.TICK_DELAY = 100;
	}
    }

    class LoadMenuItemHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    JFileChooser fileChooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		".gol files", "gol");
	    fileChooser.setFileFilter(filter);
	    if (fileChooser.showOpenDialog(window) ==
		JFileChooser.APPROVE_OPTION)
	    {
		board.clear();
		board.applyConfiguration(FileParser.getConfiguration(
		    fileChooser.getSelectedFile()));
	    }

	    window.getLifePanel().setCellData(board.getBoard());
	    window.getLifePanel().repaint();
	}
    }

    class ClearMenuItemHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    board.clear();
	    window.getLifePanel().setCellData(board.getBoard());
	    window.getLifePanel().repaint();
	}
    }

    class StartMenuItemHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    LifeData.RUNNING = true;
	    window.getStatusBar().setStatusText("Simulation running");
	    window.getLifeMenuBar().disableEditMenu();
	    window.getLifeMenuBar().disableLoadMenuItem();
	    drawThread = new Thread(new Simulate());
	    drawThread.start();
	}
    }

    class StopMenuItemHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    LifeData.RUNNING = false;
	    window.getStatusBar().setStatusText("Simulation stopped");
	    window.getLifeMenuBar().enableEditMenu();
	    window.getLifeMenuBar().enableLoadMenuItem();
	}
    }

    class SaveMenuItemHandler implements ActionListener
    {
	public void actionPerformed(ActionEvent e)
	{
	    JFileChooser fileChooser = new JFileChooser();
	    File f;

	    if (fileChooser.showSaveDialog(window) ==
		JFileChooser.APPROVE_OPTION)
	    {
		f = fileChooser.getSelectedFile();

		try
		{
		    FileWriter fout = new FileWriter(f);

		    for (int row = 0; row < LifeData.BOARD_SIZE; row++)
		    {
			for (int col = 0; col < LifeData.BOARD_SIZE; col++)
			{
			    if (board.getBoard()[row][col])
			    {
				String s = "";
				s += row;
				s += " ";
				s += col;
				fout.write(s + "\n");
			    }
			}
		    }

		    fout.close();
		}
		catch (IOException ex)
		{
		}
	    }
	}
    }

    class LifePanelMouseHandler implements MouseListener
    {
	public void mouseClicked(MouseEvent e)
	{
	    if (LifeData.EDIT_MODE)
	    {
		if (e.getButton() == MouseEvent.BUTTON1)
		{
		    int x = e.getX() / 10;
		    int y = e.getY() / 10;

		    board.toggleSquare(y, x);
		    window.getLifePanel().setCellData(board.getBoard());
		    window.getLifePanel().repaint();
		}
	    }
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}
    }

    class Simulate implements Runnable
    {
	public void run()
	{
	    while (LifeData.RUNNING)
	    {
		board.tick();
		window.getLifePanel().setCellData(board.getBoard());
		window.getLifePanel().repaint();

		try
		{
		    Thread.sleep(LifeData.TICK_DELAY);
		}
		catch (InterruptedException ex)
		{
		}
	    }
	}
    }
}

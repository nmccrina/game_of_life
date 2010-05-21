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
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GameWindow extends JFrame
{

    private MenuBar menuBar;
    private LifePanel panel;
    private StatusBar statusBar; // Panel that holds the label that shows the
		    // the status.

    public GameWindow()
    {
	super();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(new Dimension(605, 669));
	this.setResizable(false);
	this.setTitle("Conway's Game Of Life");

	// Create menu bar
	menuBar = new MenuBar();
	this.setJMenuBar(menuBar);

	// Create the panel holding the game board
	panel = new LifePanel();
	this.getContentPane().add(BorderLayout.CENTER, this.panel);

	// Create the status bar
	statusBar = new StatusBar();
	statusBar.setPreferredSize(new Dimension(605, 20)); 
	statusBar.setStatusText("Hello!");
	this.getContentPane().add(BorderLayout.SOUTH, statusBar);
    }

    public MenuBar getLifeMenuBar()
    {
	return menuBar;
    }

    public LifePanel getLifePanel()
    {
	return panel;
    }

    public StatusBar getStatusBar()
    {
	return statusBar;
    }

    public void showWindow()
    {
	this.setVisible(true);
    }
}

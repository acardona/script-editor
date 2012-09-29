//
// NewPlugin.java
//

/*
ImageJ software for multidimensional image processing and analysis.

Copyright (c) 2010, ImageJDev.org.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the names of the ImageJDev.org developers nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/

package imagej.script.editor.command;

import java.util.ArrayList;
import java.util.List;

import imagej.command.DynamicCommand;
import imagej.module.DefaultModuleItem;
import imagej.plugin.Parameter;
import imagej.script.editor.TextEditor;
import imagej.script.editor.TextEditor.Executer;

/**
 * Kills a running script started in the given script editor.
 * 
 * @author Johannes Schindelin
 */
public class KillScript extends DynamicCommand {

	@Parameter
	private TextEditor editor;

	@Parameter(initializer = "initializeChoice")
	private Executer script;
	private final static String SCRIPT_NAME = "script";

	@Parameter
	private boolean killAll;

	@Override
	public void run() {
		if (killAll) {
			final List<Executer> scripts = new ArrayList<Executer>();
			scripts.addAll(editor.getExecutingTasks());
			for (Executer job : scripts) {
				editor.kill(job);
			}
		} else {
			editor.kill(script);
		}
	}

	protected void initializeChoice() {
		@SuppressWarnings("unchecked")
		DefaultModuleItem<Executer> item =
				(DefaultModuleItem<Executer>) getInfo().getInput(SCRIPT_NAME);
		item.setChoices(editor.getExecutingTasks());
	}
}

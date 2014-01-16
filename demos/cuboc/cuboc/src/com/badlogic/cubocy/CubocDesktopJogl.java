
package com.badlogic.cubocy;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.jogl.JoglApplication;

public class CubocDesktopJogl {
	public static void main (String[] argv) {
		new JoglApplication(new Cubocy(), "Cubocy", 480, 320, true);

		// After creating the Application instance we can set the log level to
		// show the output of calls to Gdx.app.debug
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}
}

/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.backends.jogamp;

import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.newt.Display;
import com.jogamp.newt.MonitorDevice;
import com.jogamp.newt.MonitorMode;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.Window;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.util.MonitorModeUtil;
import com.jogamp.opengl.GLCapabilities;

/** Implements the {@link Graphics} interface with Jogl.
 *
 * @author mzechner */
public class JoglNewtGraphics extends JoglGraphicsBase {
	/**
	 * TODO move most of the code into a separate NEWT JoglGraphicsBase implementation and into a NEWT JoglApplicationConfiguration implementation,
	 * implement getDesktopDisplayMode() and move getDisplayModes() into the latter
	 */
	final JoglNewtDisplayMode desktopMode;

	public JoglNewtGraphics (ApplicationListener listener, JoglNewtApplicationConfiguration config) {
		initialize(listener, config);
		getCanvas().setFullscreen(config.fullscreen);
		getCanvas().setUndecorated(config.fullscreen);
		getCanvas().getScreen().addReference();
		desktopMode = config.getDesktopDisplayMode();
	}

	protected GLWindow createCanvas(final GLCapabilities caps) {
		return GLWindow.create(caps);
	}

	GLWindow getCanvas () {
		return (GLWindow) super.getCanvas();
	}

	@Override
	public int getHeight () {
		return getCanvas().getHeight();
	}

	@Override
	public int getWidth () {
		return getCanvas().getWidth();
	}

	@Override
	public void create() {
		super.create();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void destroy () {
		super.destroy();
		getCanvas().setFullscreen(false);
	}

	@Override
	public boolean supportsDisplayModeChange () {
		return true;
	}

	@Override
	public void setTitle (String title) {
		getCanvas().setTitle(title);
	}

	@Override
	public boolean isFullscreen () {
		return getCanvas().isFullscreen();
	}

	@Override
	public void setCursor(Cursor cursor) {
		if (cursor == null) {
			getCanvas().setPointerIcon(null);
		} else {
			((JoglNewtCursor)cursor).setSystemCursor();
		}
	}

	@Override
	public Cursor newCursor(Pixmap pixmap, int xHotspot, int yHotspot) {
		return new JoglNewtCursor(pixmap, xHotspot, yHotspot, getCanvas());
	}

	@Override
	public Monitor getMonitor() {
		final Window window = getCanvas();
		if (canvas == null) return getPrimaryMonitor();
		return JoglNewtMonitor.from(window.getMainMonitor());
	}

	@Override
	public Monitor getPrimaryMonitor() {
		Display disp = NewtFactory.createDisplay(null);
		final Screen screen = Screen.getFirstScreenOf(disp, Screen.getActiveScreenNumber(), 0);
		screen.addReference();
		final Monitor monitor = JoglNewtMonitor.from(screen.getPrimaryMonitor());
		screen.removeReference();
		screen.destroy();
		disp.destroy();
		return monitor;
	}

	@Override
	public Monitor[] getMonitors() {
		Display disp = NewtFactory.createDisplay(null);
		final Screen screen = Screen.getFirstScreenOf(disp, Screen.getActiveScreenNumber(), 0);
		screen.addReference();
		final List <MonitorDevice> devices = screen.getMonitorDevices();
		screen.removeReference();
		screen.destroy();
		disp.destroy();

		final Monitor[] monitors = new Monitor[devices.size()];
		for (int i = 0; i < monitors.length; i++) {
			monitors[i] = JoglNewtMonitor.from(devices.get(i));
		}
		return monitors;
	}

	@Override
	public DisplayMode getDisplayMode(Monitor monitor) {
		if (!(monitor instanceof JoglNewtMonitor)) {
			throw new IllegalArgumentException("incompatible monitor type: " + monitor.getClass());
		}

		return createDisplayModeFrom(((JoglNewtMonitor) monitor).device.getCurrentMode());
	}

	@Override
	public DisplayMode[] getDisplayModes(Monitor monitor) {
		if (!(monitor instanceof JoglNewtMonitor)) {
			throw new IllegalArgumentException("incompatible monitor type: " + monitor.getClass());
		}

		final JoglNewtMonitor joglMonitor = (JoglNewtMonitor) monitor;
		final List<MonitorMode> monitorModes = joglMonitor.device.getSupportedModes();
		final DisplayMode[] displayModes = new DisplayMode[monitorModes.size()];
		for (int i = 0; i < displayModes.length; i++) {
			displayModes[i] = createDisplayModeFrom(monitorModes.get(i));
		}

		return displayModes;
	}

	@Override
	public boolean setFullscreenMode(DisplayMode displayMode) {
		return setDisplayMode(displayMode, true);
	}

	@Override
	public boolean setWindowedMode(int width, int height) {
		if (getWidth() == width && getHeight() == height && !getCanvas().isFullscreen()) {
			return true;
		}

		return setDisplayMode(width, height, false);
	}

	@Override
	public void setSystemCursor(SystemCursor systemCursor) {
		// FIXME: ????
	}

	private DisplayMode createDisplayModeFrom(MonitorMode monitorMode) {
		return new JoglNewtDisplayMode(monitorMode.getRotatedWidth(), monitorMode.getRotatedHeight(),
				Math.round (monitorMode.getRefreshRate()), monitorMode.getSurfaceSize().getBitsPerPixel(), monitorMode);
	}

	private boolean setDisplayMode (DisplayMode displayMode, boolean fullscreen) {
		MonitorMode screenMode = ((JoglNewtDisplayMode)displayMode).mode;

		getCanvas().setUndecorated(fullscreen);
		getCanvas().setFullscreen(fullscreen);
		getCanvas().getMainMonitor().setCurrentMode(screenMode);
		if (Gdx.gl != null) Gdx.gl.glViewport(0, 0, displayMode.width, displayMode.height);
		config.width = displayMode.width;
		config.height = displayMode.height;

		return true;
	}

	private boolean setDisplayMode (int width, int height, boolean fullscreen) {
		if (width == getCanvas().getWidth() && height == getCanvas().getHeight() && getCanvas().isFullscreen() == fullscreen) {
			return true;
		}
		MonitorMode targetDisplayMode = null;
		final MonitorDevice monitor = getCanvas().getMainMonitor();
        List<MonitorMode> monitorModes = monitor.getSupportedModes();
		Dimension dimension = new Dimension(width,height);
		monitorModes = MonitorModeUtil.filterByResolution(monitorModes, dimension);
		monitorModes = MonitorModeUtil.filterByRate(monitorModes, getCanvas().getMainMonitor().getCurrentMode().getRefreshRate());
		monitorModes = MonitorModeUtil.getHighestAvailableRate(monitorModes);
		if (monitorModes == null || monitorModes.isEmpty()) {
			return false;
		}
		targetDisplayMode = monitorModes.get(0);
		getCanvas().setUndecorated(fullscreen);
		getCanvas().setFullscreen(fullscreen);
		monitor.setCurrentMode(targetDisplayMode);
		if (Gdx.gl != null) Gdx.gl.glViewport(0, 0, targetDisplayMode.getRotatedWidth(), targetDisplayMode.getRotatedHeight());
		config.width = targetDisplayMode.getRotatedWidth();
		config.height = targetDisplayMode.getRotatedHeight();
		return true;
	}

	protected static class JoglNewtDisplayMode extends DisplayMode {
		final MonitorMode mode;

		protected JoglNewtDisplayMode(int width, int height, int refreshRate, int bitsPerPixel, MonitorMode mode) {
			super(width, height, refreshRate, bitsPerPixel);
			this.mode = mode;
		}

		static JoglNewtDisplayMode from(MonitorMode mode) {
			return new JoglNewtDisplayMode(mode.getRotatedWidth(), mode.getRotatedHeight(), Math.round(mode.getRefreshRate()),
					mode.getSurfaceSize().getBitsPerPixel(), mode);
		}
	}

	protected static class JoglNewtMonitor extends Monitor {
		final MonitorDevice device;

		protected JoglNewtMonitor(int virtualX, int virtualY, String name, MonitorDevice device) {
			super(virtualX, virtualY, name);

			this.device = device;
		}

		static JoglNewtMonitor from(MonitorDevice device) {
			return new JoglNewtMonitor(device.getViewport().getX(), device.getViewport().getY(),
					String.valueOf(device.getId()), device);
		}
	}
}

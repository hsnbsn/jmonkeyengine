/*
 * Copyright (c) 2003, jMonkeyEngine - Mojo Monkey Coding
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer. 
 * 
 * Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution. 
 * 
 * Neither the name of the Mojo Monkey Coding, jME, jMonkey Engine, nor the 
 * names of its contributors may be used to endorse or promote products derived 
 * from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */
package com.jme.test.renderer;

import com.jme.app.AbstractGame;
import com.jme.image.Texture;
import com.jme.input.FirstPersonController;
import com.jme.input.InputController;
import com.jme.light.DirectionalLight;
import com.jme.light.SpotLight;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.BezierMesh;
import com.jme.scene.BezierPatch;
import com.jme.scene.BoundingSphere;
import com.jme.scene.Node;
import com.jme.scene.TriMesh;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.WireframeState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.system.JmeException;
import com.jme.util.TextureManager;

/**
 * <code>TestLightState</code>
 * @author Mark Powell
 * @version $Id: TestBezierMesh.java,v 1.1 2004-01-11 01:58:12 mojomonkey Exp $
 */
public class TestBezierMesh extends AbstractGame {
    private TriMesh t;
    private Camera cam;
    private Node root;
    private Node scene;
    private InputController input;
    private Thread thread;

    /**
     * Entry point for the test, 
     * @param args
     */
    public static void main(String[] args) {
        TestBezierMesh app = new TestBezierMesh();
        app.useDialogAlways(true);
        app.start();

    }

    /**
     * Not used in this test.
     * @see com.jme.app.AbstractGame#update()
     */
    protected void update() {
        input.update(0.02f);
    }

    /** 
     * clears the buffers and then draws the TriMesh.
     * @see com.jme.app.AbstractGame#render()
     */
    protected void render() {
        display.getRenderer().clearBuffers();

        display.getRenderer().draw(root);

    }

    /**
     * creates the displays and sets up the viewport.
     * @see com.jme.app.AbstractGame#initSystem()
     */
    protected void initSystem() {
        try {
            display = DisplaySystem.getDisplaySystem(properties.getRenderer());
            display.createWindow(
                properties.getWidth(),
                properties.getHeight(),
                properties.getDepth(),
                properties.getFreq(),
                properties.getFullscreen());
            cam =
                display.getRenderer().getCamera(
                    properties.getWidth(),
                    properties.getHeight());

        } catch (JmeException e) {
            e.printStackTrace();
            System.exit(1);
        }
        ColorRGBA blackColor = new ColorRGBA(0, 0, 0, 1);
        display.getRenderer().setBackgroundColor(blackColor);
        cam.setFrustum(1.0f, 1000.0f, -0.55f, 0.55f, 0.4125f, -0.4125f);
        Vector3f loc = new Vector3f(4.0f, 0.0f, 0.0f);
        Vector3f left = new Vector3f(0.0f, -1.0f, 0.0f);
        Vector3f up = new Vector3f(0.0f, 0.0f, 1.0f);
        Vector3f dir = new Vector3f(-1.0f, 0f, 0.0f);
        cam.setFrame(loc, left, up, dir);
        display.getRenderer().setCamera(cam);

        input = new FirstPersonController(this, cam, "LWJGL");
        //display.getRenderer().setCullingMode(Renderer.CULL_BACK);

    }

    /** 
     * builds the trimesh.
     * @see com.jme.app.AbstractGame#initGame()
     */
    protected void initGame() {

        AlphaState as1 = display.getRenderer().getAlphaState();
        as1.setBlendEnabled(true);
        as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
        as1.setDstFunction(AlphaState.DB_ONE);
        as1.setTestEnabled(true);
        as1.setTestFunction(AlphaState.TF_GREATER);

        scene = new Node();

        scene = new Node();
        root = new Node();
        root.attachChild(scene);

        ZBufferState buf = display.getRenderer().getZBufferState();
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.CF_LEQUAL);

        scene.setRenderState(buf);
        cam.update();

        BezierPatch bp = new BezierPatch();
        bp.setAnchor(0, 0, new Vector3f(-0.75f, -0.75f, -0.5f));
        bp.setAnchor(0, 1, new Vector3f(-0.25f, -0.75f, 0.0f));
        bp.setAnchor(0, 2, new Vector3f(0.25f, -0.75f, 0.5f));
        bp.setAnchor(0, 3, new Vector3f(0.75f, -0.75f, 0.5f));
        bp.setAnchor(1, 0, new Vector3f(-0.75f, -0.25f, -0.75f));
        bp.setAnchor(1, 1, new Vector3f(-0.25f, -0.25f, 0.5f));
        bp.setAnchor(1, 2, new Vector3f(0.25f, -0.25f, 0.5f));
        bp.setAnchor(1, 3, new Vector3f(0.75f, -0.25f, 0.75f));
        bp.setAnchor(2, 0, new Vector3f(-0.75f, 0.25f, -0.5f));
        bp.setAnchor(2, 1, new Vector3f(-0.25f, 0.25f, -0.5f));
        bp.setAnchor(2, 2, new Vector3f(0.25f, 0.25f, -0.5f));
        bp.setAnchor(2, 3, new Vector3f(0.75f, 0.25f, 0.0f));
        bp.setAnchor(3, 0, new Vector3f(-0.75f, 0.75f, -0.5f));
        bp.setAnchor(3, 1, new Vector3f(-0.25f, 0.75f, -1.0f));
        bp.setAnchor(3, 2, new Vector3f(0.25f, 0.75f, -1.0f));
        bp.setAnchor(3, 3, new Vector3f(0.75f, 0.75f, -0.5f));

        BezierMesh bez = new BezierMesh();
        bez.tessellate(bp, 20);
        bez.setPatch(bp);
        bez.setWorldBound(new BoundingSphere());
        bez.setForceView(true);
        scene.attachChild(bez);

        SpotLight am = new SpotLight();
        am.setDiffuse(new ColorRGBA(0.0f, 1.0f, 0.0f, 1.0f));
        am.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        am.setDirection(new Vector3f(0, 0, 0));
        am.setLocation(new Vector3f(25, 10, 0));
        am.setAngle(15);

        SpotLight am2 = new SpotLight();
        am2.setDiffuse(new ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f));
        am2.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        am2.setDirection(new Vector3f(0, 0, 0));
        am2.setLocation(new Vector3f(-25, 10, 0));
        am2.setAngle(15);

        DirectionalLight dr = new DirectionalLight();
        dr.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        dr.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        dr.setSpecular(new ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f));
        dr.setDirection(new Vector3f(150, 0, 150));

        LightState state = display.getRenderer().getLightState();
        state.attach(am);
        state.attach(dr);
        state.attach(am2);
        am.setEnabled(true);
        am2.setEnabled(true);
        dr.setEnabled(true);
        // scene.setRenderState(state);

        WireframeState wf = display.getRenderer().getWireframeState();
        wf.setEnabled(true);

        scene.setRenderState(wf);

        TextureState ts = display.getRenderer().getTextureState();
        ts.setEnabled(true);
        ts.setTexture(
            TextureManager.loadTexture(
                "data/Images/Monkey.jpg",
                Texture.MM_LINEAR,
                Texture.FM_LINEAR,
                true));

        scene.setRenderState(ts);

        scene.updateGeometricState(0.0f, true);

    }
    /**
     * not used.
     * @see com.jme.app.AbstractGame#reinit()
     */
    protected void reinit() {

    }

    /** 
     * Not used.
     * @see com.jme.app.AbstractGame#cleanup()
     */
    protected void cleanup() {

    }

}

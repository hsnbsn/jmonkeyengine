package com.jme.scene.model.XMLparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.Stack;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.logging.Level;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;

import com.jme.scene.*;
import com.jme.scene.shape.Box;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.math.Vector3f;
import com.jme.math.Vector2f;
import com.jme.math.Quaternion;
import com.jme.bounding.BoundingBox;
import com.jme.system.DisplaySystem;
import com.jme.image.Texture;
import com.jme.util.TextureManager;
import com.jme.util.LoggingSystem;

/**
 * Started Date: May 31, 2004
 * SAX Stack processor.  Helper class for SAXReader.  increaseStack is called whenever a new element is encountered
 * in the XML file, decreaseStack is called at the end of that element.  Each is basicly a large if statement sequence.
 * The stack can be pop'd to return the element information above the one being processed.Hashtable is to use element
 * sharing, Stack is to keep track of where I am in the XML file.
 *
 * @author Jack Lindamood
 */
class SAXStackProcessor {

    Node myScene;
    private Stack s=new Stack();
    private Hashtable shares=new Hashtable();
    private Renderer renderer;
    HashMap properties;
    SAXStackProcessor(){
        renderer=DisplaySystem.getDisplaySystem().getRenderer();
        properties=new HashMap();
    }

    void increaseStack(String qName, Attributes atts) throws SAXException{

        if (qName.equalsIgnoreCase("Scene")){
            s.push(new Node("XML Scene"));
        } else if (qName.equals("node")){
            s.push(processSpatial(new Node(atts.getValue("name")),atts));
        } else if (qName.equals("materialstate")){
            s.push(buildMaterial(atts));
        } else if (qName.equals("texturestate")){
            s.push(buildTexture(atts));
        } else if (qName.equals("mesh")){
            s.push(processSpatial(new TriMesh(atts.getValue("name")),atts));
        } else if (qName.equals("vertex") || qName.equals("normal") ||
                qName.equals("texturecoords") || qName.equals("index") || qName.equals("color") || qName.equals("sharedtypes")||
                qName.equals("jointindex") || qName.equals("origvertex") || qName.equals("orignormal")){
            // Do nothing, these have no attributes
        } else if (qName.equals("primitive")){
            s.push(processPrimitive(atts));
        } else if (qName.equals("sharednodeitem")){
            s.push(new XMLSharedNode(atts.getValue("ident")));
        } else if (qName.equals("publicobject")){
            Object toAdd=shares.get(atts.getValue("ident"));
            if (toAdd==null){
                throw new SAXException("Unknown publicobject: " +shares.get(atts.getValue("ident")));
            }
            s.push(toAdd);
        } else if (qName.equals("xmlloadable")){
            try {
                Class c=Class.forName(atts.getValue("class"));
                if (!XMLloadable.class.isAssignableFrom(c)){
                    throw new SAXException("Given XML class must implement XMLloadable");
                }
                XMLloadable x=(XMLloadable) c.newInstance();
                Object o=x.loadFromXML(atts.getValue("args"));
                if (o instanceof Spatial){
                    processSpatial((Spatial) o,atts);
                }
                s.push(o);
            } catch (ClassNotFoundException e) {
                throw new SAXException("Unknown class type:" + atts.getValue("class"));
            } catch (IllegalAccessException e) {
                throw new SAXException("XMLloadable classes must have a default() constructor: " + atts.getValue("class"));
            } catch (InstantiationException e) {
                throw new SAXException("XMLloadable classes cannot be abstract: " + atts.getValue("class"));
            }

        } else if (qName.equals("jointcontroller")){
            s.push(new JointController(Integer.parseInt(atts.getValue("numJoints"))));
        } else if (qName.equals("keyframe")){
            Integer jointIndex=(Integer) s.pop();
            JointController jc=(JointController) s.pop();
            if (atts.getValue("rot")!=null){
                String[] values=atts.getValue("rot").split(" ");
                jc.setRotation(jointIndex.intValue(),Float.parseFloat(atts.getValue("time")),new Quaternion(
                        Float.parseFloat(values[0]),Float.parseFloat(values[1]),Float.parseFloat(values[2]),Float.parseFloat(values[3])));
            }
            if (atts.getValue("trans")!=null){
                String[] values=atts.getValue("trans").split(" ");
                jc.setTranslation(jointIndex.intValue(),Float.parseFloat(atts.getValue("time"))
                        ,Float.parseFloat(values[0]),Float.parseFloat(values[1]),Float.parseFloat(values[2]));
            }
            s.push(jc);
            s.push(jointIndex);
        } else if (qName.equals("joint")){
            JointController jc=(JointController) s.pop();
            jc.parentIndex[Integer.parseInt(atts.getValue("index"))]=Integer.parseInt(atts.getValue("parentindex"));
            jc.localRefMatrix[Integer.parseInt(atts.getValue("index"))].set(getQuat(atts.getValue("localrot")),getVec(atts.getValue("localvec")));
            s.push(jc);
            s.push(new Integer(atts.getValue("index")));
        } else if (qName.equals("jointmesh")){
            s.push(processSpatial(new JointMesh(atts.getValue("name")),atts));
        } else if (qName.equals("keyframecontroller")){
            KeyframeController kc=new KeyframeController();
            kc.setActive(true);
            TriMesh parentMesh=(TriMesh) s.pop();
            kc.setMorphingMesh(parentMesh);
            s.push(parentMesh);
            s.push(kc);
        } else if (qName.equals("keyframepointintime")){
            s.push(atts.getValue("time"));  // Store the current time on the stack
            s.push(new EmptyTriMesh());
        } else{
            throw new SAXException("Illegale Qualified name: " + qName);
        }
        return;
    }

    private Vector3f getVec(String value) {
        String[] sp=value.split(" ");
        return new Vector3f(Float.parseFloat(sp[0]),Float.parseFloat(sp[1]),Float.parseFloat(sp[2]));
    }

    private Quaternion getQuat(String value) {
        String[] sp=value.split(" ");
        return new Quaternion(Float.parseFloat(sp[0]),Float.parseFloat(sp[1]),Float.parseFloat(sp[2]),Float.parseFloat(sp[3]));
    }


    void decreaseStack(String qName,StringBuffer data) throws SAXException {
        Node childNode,parentNode;
        Spatial parentSpatial,childSpatial;
        if (qName.equalsIgnoreCase("Scene")){
            myScene=(Node) s.pop();
        } else if (qName.equals("node")){
            childNode=(Node) s.pop();
            parentNode=(Node) s.pop();
            parentNode.attachChild(childNode);
            s.push(parentNode);
        } else if (qName.equals("materialstate")){
            MaterialState childMaterial=(MaterialState) s.pop();
            parentSpatial=(Spatial) s.pop();
            parentSpatial.setRenderState(childMaterial);
            s.push(parentSpatial);
        } else if (qName.equals("texturestate")){
            TextureState childMaterial=(TextureState) s.pop();
            parentSpatial=(Spatial) s.pop();
            parentSpatial.setRenderState(childMaterial);
            s.push(parentSpatial);
        } else if (qName.equals("mesh") || qName.equals("jointmesh")){
            Geometry childMesh=(Geometry) s.pop();
            if (childMesh.getModelBound()==null){
                childMesh.setModelBound(new BoundingBox());
                childMesh.updateModelBound();
            }
            parentNode=(Node) s.pop();
            parentNode.attachChild(childMesh);
            s.push(parentNode);
        } else if (qName.equals("vertex")){
            Geometry childGeometry=(Geometry) s.pop();
            childGeometry.setVertices(createVector3f(data));
            s.push(childGeometry);
        } else if (qName.equals("normal")){
            Geometry childGeometry=(Geometry) s.pop();
            childGeometry.setNormals(createVector3f(data));
            s.push(childGeometry);
        } else if (qName.equals("color")){
            Geometry childGeometry=(Geometry) s.pop();
            childGeometry.setColors(createColors(data));
            s.push(childGeometry);
        } else if (qName.equals("texturecoords")){
            Geometry childGeometry=(Geometry) s.pop();
            childGeometry.setTextures(createVector2f(data));
            s.push(childGeometry);
        } else if (qName.equals("index")){
            TriMesh childGeometry=(TriMesh) s.pop();
            childGeometry.setIndices(createIntArray(data));
            s.push(childGeometry);
        } else if (qName.equals("primitive")){
            childSpatial=(Spatial) s.pop();
            parentNode=(Node) s.pop();
            parentNode.attachChild(childSpatial);
            s.push(parentNode);
        } else if (qName.equals("sharedtypes") || qName.equals("keyframe")){
            // Nothing to do, these only identify XML areas
        } else if (qName.equals("xmlloadable")){
            Object o=s.pop();
            if (o instanceof RenderState){
                parentSpatial=(Spatial) s.pop();
                parentSpatial.setRenderState((RenderState) o);
                s.push(parentSpatial);
            } else if (o instanceof Controller){
                parentSpatial=(Spatial) s.pop();
                parentSpatial.addController((Controller) o);
                s.push(parentSpatial);
            } else if (o instanceof Spatial){
                parentNode=(Node) s.pop();
                parentNode.attachChild((Spatial) o);
                s.push(parentNode);
            }
        } else if (qName.equals("sharednodeitem")){
            XMLSharedNode XMLShare=(XMLSharedNode) s.pop();
            shares.put(XMLShare.myIdent,XMLShare.whatIReallyAm);
        } else if (qName.equals("publicobject")){
            Object o=s.pop();
            if (o instanceof RenderState){
                parentSpatial=(Spatial) s.pop();
                parentSpatial.setRenderState((RenderState) o);
                s.push(parentSpatial);
            } else if (o instanceof Controller){
                parentSpatial=(Spatial) s.pop();
                parentSpatial.addController((Controller) o);
                s.push(parentSpatial);
            } else if (o instanceof Spatial){
                parentNode=(Node) s.pop();
                parentNode.attachChild((Spatial) o);
                s.push(parentNode);
            }
        } else if (qName.equals("jointcontroller")){
            JointController jc=(JointController) s.pop();
            parentNode=(Node) s.pop();
            for (int i=0;i<parentNode.getQuantity();i++){
                if (parentNode.getChild(i) instanceof JointMesh)
                    jc.addJointMesh((JointMesh) parentNode.getChild(i));
            }
            jc.processController();
            parentNode.addController(jc);
            s.push(parentNode);
        } else if (qName.equals("joint")){
            s.pop();    // remove unneeded information tag
        } else if (qName.equals("jointindex")){
            JointMesh jm=(JointMesh) s.pop();
            jm.jointIndex=createIntArray(data);
            s.push(jm);
        } else if (qName.equals("origvertex")){
            JointMesh jm=(JointMesh) s.pop();
            jm.originalVertex=createVector3f(data);
            s.push(jm);
        } else if (qName.equals("orignormal")){
            JointMesh jm=(JointMesh) s.pop();
            jm.originalNormal=createVector3f(data);
            s.push(jm);
        } else if (qName.equals("keyframecontroller")){
            KeyframeController kc=(KeyframeController) s.pop();
            TriMesh parentMesh=(TriMesh) s.pop();
            parentMesh.addController(kc);
            s.push(parentMesh);
        } else if (qName.equals("keyframepointintime")){
            TriMesh parentMesh=(TriMesh) s.pop();
            float time=Float.parseFloat((String) s.pop());
            KeyframeController kc=(KeyframeController)s.pop();
            kc.setKeyframe(time,parentMesh);
            s.push(kc);
        } else {
            throw new SAXException("Illegale Qualified name: " + qName);
        }
    }

    private TextureState buildTexture(Attributes atts) throws SAXException {
        TextureState t=renderer.getTextureState();
        try {
            Texture p=null;
            if (atts.getValue("URL")!=null && !atts.getValue("URL").equals("null")){
                p=TextureManager.loadTexture(new URL(atts.getValue("URL")),
                        Texture.MM_LINEAR,Texture.FM_LINEAR,true);
            }else if (atts.getValue("file")!=null && !atts.getValue("file").equals("null")){
                URL context;
                if (properties.containsKey("texurl")){
                    context=new URL((URL) properties.get("texurl"),atts.getValue("file"));
                } else{
                    context=new File(atts.getValue("file")).toURI().toURL();
                }
                p=TextureManager.loadTexture(context,
                        Texture.MM_LINEAR,Texture.FM_LINEAR,true);
                p.setImageLocation("file:/"+atts.getValue("file"));
            }
            if (p==null)
                LoggingSystem.getLogger().log(Level.INFO,"Unable to load file: " + atts.getValue("file"));
            else
                t.setTexture(p);
        } catch (MalformedURLException e) {
            throw new SAXException("Bad file name: " + atts.getValue("file") + "*" + atts.getValue("URL"));
        }
        t.setEnabled(true);
        return t;
    }

    private MaterialState buildMaterial(Attributes atts) throws SAXException {
        MaterialState m=renderer.getMaterialState();
        m.setAlpha(Float.parseFloat(atts.getValue("alpha")));
        m.setAmbient(createSingleColor(atts.getValue("ambient")));
        m.setDiffuse(createSingleColor(atts.getValue("diffuse")));
        m.setEmissive(createSingleColor(atts.getValue("emissive")));
        m.setShininess(Float.parseFloat(atts.getValue("shiny")));
        m.setSpecular(createSingleColor(atts.getValue("specular")));
        m.setEnabled(true);
        return m;
    }

    private Spatial processSpatial(Spatial toAdd, Attributes atts) {
        if (atts.getValue("name")!=null){
            toAdd.setName(atts.getValue("name"));
        }
        if (atts.getValue("translation")!=null){
            String [] split=atts.getValue("translation").trim().split(" ");
            toAdd.setLocalTranslation(new Vector3f(
                    Float.parseFloat(split[0]),
                    Float.parseFloat(split[1]),
                    Float.parseFloat(split[2])
            ));
        }
        if (atts.getValue("rotation")!=null){
            String [] split=atts.getValue("rotation").trim().split(" ");
            toAdd.setLocalRotation(new Quaternion(
                    Float.parseFloat(split[0]),
                    Float.parseFloat(split[1]),
                    Float.parseFloat(split[2]),
                    Float.parseFloat(split[3])
            ));
        }
        if (atts.getValue("scale")!=null){
            toAdd.setLocalScale(Float.parseFloat(atts.getValue("scale")));
        }
        return toAdd;
    }


    private Spatial processPrimitive(Attributes atts) throws SAXException {
        String parameters=atts.getValue("params");
        String type=atts.getValue("type");
        if (parameters==null) throw new SAXException("Must specify parameters");
        Spatial toReturn=null;
        String[] parts=parameters.trim().split(" ");
        if (type.equalsIgnoreCase("box")){
            if (parts.length!=7) throw new SAXException("Box must have 7 parameters");
            Geometry box=new Box(parts[0],new Vector3f(
                    Float.parseFloat(parts[1]),
                    Float.parseFloat(parts[2]),
                    Float.parseFloat(parts[3])),
                    new Vector3f(Float.parseFloat(parts[4]),
                    Float.parseFloat(parts[5]),
                    Float.parseFloat(parts[6])));
            box.setModelBound(new BoundingBox());
            box.updateModelBound();
            toReturn=box;
        }else{
            throw new SAXException("Unknown primitive type: " + type);
        }
        return processSpatial(toReturn,atts);
    }


    private static ColorRGBA createSingleColor(String data) throws SAXException {
        if (data==null || data.length()==0) return null;
        String[] information=data.toString().trim().split(" ");
        if (information.length!=4){
            throw new SAXException("Colors must be of length 4:" + data);
        }
        return new ColorRGBA(
                Float.parseFloat(information[0]),
                Float.parseFloat(information[1]),
                Float.parseFloat(information[2]),
                Float.parseFloat(information[3])
        );
    }

    private static Vector2f[] createVector2f(StringBuffer data) throws SAXException {
        if (data.length()==0) return null;
        String [] information=data.toString().trim().split(" ");
        if (information.length==1 && information[0].equals("")) return null;
        if (information.length%2!=0){
            throw new SAXException("Vector2f length not modulus of 2: " + information.length);
        }
        Vector2f[] vecs=new Vector2f[information.length/2];
        for (int i=0;i<vecs.length;i++){
            vecs[i]=new Vector2f(Float.parseFloat(information[i*2+0]),
                    Float.parseFloat(information[i*2+1]));
        }
        return vecs;
    }

    private static Vector3f[] createVector3f(StringBuffer data) throws SAXException {
        if (data.length()==0) return null;
        String [] information=data.toString().trim().split(" ");
        if (information.length==1 && information[0].equals("")) return null;
        if (information.length%3!=0){
            throw new SAXException("Vector3f length not modulus of 3: " + information.length);
        }
        Vector3f[] vecs=new Vector3f[information.length/3];
        for (int i=0;i<vecs.length;i++){
            vecs[i]=new Vector3f(Float.parseFloat(information[i*3+0]),
                    Float.parseFloat(information[i*3+1]),
                    Float.parseFloat(information[i*3+2]));
        }
        return vecs;
    }

    private static int[] createIntArray(StringBuffer data) {
        if (data.length()==0) return null;
        String [] information=data.toString().trim().split("\\p{Space}");
        if (information.length==1 && information[0].equals("")) return null;
        int count=0;
        for (int i=0;i<information.length;i++)
            if (information[i].length()!=0) count++;
        int[] indexes=new int[count];
        count=0;
        for (int i=0;i<information.length;i++){
            if (information[i].length()!=0){
                indexes[count]=Integer.parseInt(information[i]);
                count++;
            }
        }
        return indexes;
    }

    private static ColorRGBA[] createColors(StringBuffer data) throws SAXException {
        if (data.length()==0) return null;
        String [] information=data.toString().trim().split(" ");
        if (information.length==1 && information[0].equals("")) return null;
        if (information.length%4!=0){
            throw new SAXException("Color length not modulus of 4: " + information.length);
        }
        ColorRGBA[] colors=new ColorRGBA[information.length/4];
        for (int i=0;i<colors.length;i++){
            colors[i]=new ColorRGBA(Float.parseFloat(information[i*4+0]),
                    Float.parseFloat(information[i*4+1]),
                    Float.parseFloat(information[i*4+2]),
                    Float.parseFloat(information[i*4+3]));
        }
        return colors;
    }

    public Node fetchCopy() {
        return myScene; // cloning would go on here.
    }

    public Node fetchOriginal() {
        return myScene;
    }

    public void reInitialize() {
        myScene=null;
        s.clear();
        shares.clear();
    }
}
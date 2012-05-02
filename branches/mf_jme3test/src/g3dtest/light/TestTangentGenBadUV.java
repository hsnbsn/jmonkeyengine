package g3dtest.light;

import com.g3d.app.SimpleApplication;
import com.g3d.light.DirectionalLight;
import com.g3d.light.PointLight;
import com.g3d.material.Material;
import com.g3d.math.ColorRGBA;
import com.g3d.math.FastMath;
import com.g3d.math.Vector3f;
import com.g3d.scene.Geometry;
import com.g3d.scene.Spatial;
import com.g3d.scene.shape.Sphere;
import com.g3d.util.TangentBinormalGenerator;

public class TestTangentGenBadUV extends SimpleApplication {

    float angle;
    PointLight pl;
    Geometry lightMdl;

    public static void main(String[] args){
        TestTangentGenBadUV app = new TestTangentGenBadUV();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Spatial teapot = manager.loadModel("teapot.obj");
        if (teapot instanceof Geometry){
            Geometry g = (Geometry) teapot;
            TangentBinormalGenerator.generate(g.getMesh());
        }else{
            throw new RuntimeException();
        }
        teapot.setLocalScale(2f);
        Material mat = manager.loadMaterial("tangentBinormal.j3m");
        teapot.setMaterial(mat);
        rootNode.attachChild(teapot);

        Geometry debug = new Geometry(
                "Debug Teapot",
                TangentBinormalGenerator.genTbnLines(((Geometry) teapot).getMesh(), 0.03f)
        );
        Material debugMat = manager.loadMaterial("vertex_color.j3m");
        debug.setMaterial(debugMat);
        debug.setCullHint(Spatial.CullHint.Never);
        debug.getLocalTranslation().set(teapot.getLocalTranslation());
        debug.getLocalScale().set(teapot.getLocalScale());
        rootNode.attachChild(debug);


        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(1,-1,-1).normalizeLocal());
        dl.setColor(ColorRGBA.White);
        rootNode.addLight(dl);

        lightMdl = new Geometry("Light", new Sphere(10, 10, 0.1f));
        lightMdl.setMaterial(manager.loadMaterial("red_color.j3m"));
        lightMdl.getMesh().setStatic();
        rootNode.attachChild(lightMdl);

        pl = new PointLight();
        pl.setColor(ColorRGBA.White);
        //pl.setRadius(3f);
        rootNode.addLight(pl);
    }

    @Override
    public void simpleUpdate(float tpf){
        angle += tpf;
        angle %= FastMath.TWO_PI;
        
        pl.setPosition(new Vector3f(FastMath.cos(angle) * 2f, 0.5f, FastMath.sin(angle) * 2f));
        lightMdl.setLocalTranslation(pl.getPosition());
    }

}